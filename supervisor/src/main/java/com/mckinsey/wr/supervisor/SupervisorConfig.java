package com.deepak.wr.supervisor;

import com.deepak.wr.platform.PlatformConfig;
import com.deepak.wr.platform.conf.Config;
import com.deepak.wr.platform.conf.WeatherConfig;
import com.deepak.wr.platform.kafka.PubsubProvider;
import com.deepak.wr.supervisor.impl.DefaultSupervisorScheduler;
import com.deepak.wunderground.client.WundergroundApi;
import com.deepak.wunderground.client.WundergroundClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * Created by deepakc on 10/04/17.
 */
@Configuration
@Component
@Import({PlatformConfig.class, WundergroundClientConfig.class})
public class SupervisorConfig {

    @Bean
    public SupervisorScheduler supervisorScheduler(Config config, WundergroundApi wundergroundApi, PubsubProvider pubsubProvider) throws Exception {
        SupervisorScheduler scheduler = new DefaultSupervisorScheduler(wundergroundApi, pubsubProvider);

        WeatherConfig weatherConfig = config.getWeatherConfig();
        for (WeatherConfig.Country country : weatherConfig.getCountries()) {
            for (WeatherConfig.City city : country.getCities()) {
                CityWeatherConfiguration conf = new CityWeatherConfiguration();
                conf.setCountry(country.getName());
                conf.setCity(city.getName());
                conf.setPollingInterval(city.getPollingInterval());
                conf.setTopic(country.getTopic());

                scheduler.schedule(conf);
            }
        }
        return scheduler;
    }
}
