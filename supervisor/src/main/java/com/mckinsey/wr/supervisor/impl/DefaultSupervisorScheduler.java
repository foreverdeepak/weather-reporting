package com.deepak.wr.supervisor.impl;

import com.deepak.wr.platform.KryoSerializer;
import com.deepak.wr.platform.conf.WeatherConfig;
import com.deepak.wr.platform.kafka.Producer;
import com.deepak.wr.platform.kafka.PubsubProvider;
import com.deepak.wr.supervisor.CityWeatherConfiguration;
import com.deepak.wr.supervisor.SupervisorScheduler;
import com.deepak.wr.supervisor.WeatherSupervisor;
import com.deepak.wunderground.client.WundergroundApi;
import com.deepak.wunderground.client.model.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by deepakc on 10/04/17.
 */
public class DefaultSupervisorScheduler implements SupervisorScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSupervisorScheduler.class);

    private Map<String, Timer> taskMap = new HashMap<>();
    private Map<String, WeatherSupervisor> supervisorMap = new HashMap<>();

    private WundergroundApi wundergroundApi;
    private PubsubProvider pubsubProvider;

    public DefaultSupervisorScheduler(WundergroundApi wundergroundApi, PubsubProvider pubsubProvider) {
        this.wundergroundApi = wundergroundApi;
        this.pubsubProvider = pubsubProvider;
    }

    @Override
    public void schedule(CityWeatherConfiguration conf) throws Exception {
        String key = conf.getCountry() + "-" + conf.getCountry();
        Timer timer = taskMap.get(key);
        if (timer != null) {
            timer.cancel();
            supervisorMap.get(key).destroy();
        }

        Producer producer = pubsubProvider.provide("weather-queue", conf.getTopic(), new KryoSerializer<>(WeatherResponse.class));
        WeatherSupervisor weatherSupervisor = new WundergroundSupervisor(wundergroundApi, producer , conf.getCountry(), conf.getCity());
        supervisorMap.put(key, weatherSupervisor);

        TimerTask timerTask = new SupervisorTimerTask(weatherSupervisor);
        timer = new Timer(true);
        taskMap.put(key, timer);
        timer.scheduleAtFixedRate(timerTask, 0, conf.getPollingInterval());
    }

    private static class SupervisorTimerTask extends TimerTask {
        private WeatherSupervisor weatherSupervisor;

        public SupervisorTimerTask(WeatherSupervisor weatherSupervisor) {
            this.weatherSupervisor = weatherSupervisor;
        }

        @Override
        public void run() {
            try {
                weatherSupervisor.execute();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }
}
