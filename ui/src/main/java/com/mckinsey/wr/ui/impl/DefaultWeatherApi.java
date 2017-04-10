package com.mckinsey.wr.ui.impl;

import com.mckinsey.wr.db.repository.Weather;
import com.mckinsey.wr.db.repository.WeatherRepository;
import com.mckinsey.wr.ui.WeatherApi;

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
