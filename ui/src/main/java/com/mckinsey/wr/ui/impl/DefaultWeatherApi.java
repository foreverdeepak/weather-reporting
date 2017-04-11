package com.deepak.wr.ui.impl;

import com.deepak.wr.db.repository.Weather;
import com.deepak.wr.db.repository.WeatherRepository;
import com.deepak.wr.ui.WeatherApi;

import java.util.Collection;

/**
 * Created by deepakc on 10/04/17.
 */
public class DefaultWeatherApi implements WeatherApi {

    private WeatherRepository weatherRepository;

    public DefaultWeatherApi(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public Collection<Weather> getWeatherInfo(int limit, int offset) {
        return weatherRepository.getWeatherInfo(offset, limit);
    }
}
