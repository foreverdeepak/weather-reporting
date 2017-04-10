package com.mckinsey.wr.supervisor.impl;

import com.mckinsey.wr.platform.kafka.Producer;
import com.mckinsey.wr.supervisor.WeatherSupervisor;
import com.mckinsey.wunderground.client.WundergroundApi;
import com.mckinsey.wunderground.client.model.WeatherResponse;

/**
 * Created by deepakc on 10/04/17.
 */
public class WundergroundSupervisor implements WeatherSupervisor {

    private WundergroundApi wundergroundApi;
    private Producer producer;
    private String country;
    private String city;

    public WundergroundSupervisor(WundergroundApi wundergroundApi, Producer producer, String country, String city) {
        this.wundergroundApi = wundergroundApi;
        this.producer = producer;
        this.country = country;
        this.city = city;
    }

    @Override
    public void execute() throws Exception {
        WeatherResponse weatherResponse = wundergroundApi.getWeatherResponse(country, city);
        producer.produce(country + "-" + city, weatherResponse);
    }

    @Override
    public void destroy() throws Exception {

    }
}
