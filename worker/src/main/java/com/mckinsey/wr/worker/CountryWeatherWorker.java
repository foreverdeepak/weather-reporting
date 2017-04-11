package com.deepak.wr.worker;

import com.deepak.wr.db.repository.WeatherRepository;
import com.deepak.wr.platform.kafka.Consumer;
import com.deepak.wunderground.client.model.WeatherResponse;

import java.util.Date;

/**
 * Created by deepakc on 10/04/17.
 */
public class CountryWeatherWorker implements Consumer<WeatherResponse> {

    private WeatherRepository weatherRepository;


    public CountryWeatherWorker(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public void consume(WeatherResponse resp) {
        WeatherRepository.WeatherDTO dto = new WeatherRepository.WeatherDTO();
        dto.setCountry(resp.getCountry());
        dto.setCity(resp.getCity());

        dto.setTempC(resp.getCurrentObservation().getTempC());
        dto.setTempF(resp.getCurrentObservation().getTempF());
        dto.setRecordTime(new Date(Long.valueOf(resp.getCurrentObservation().getObservationEpoch())));

        weatherRepository.saveWeatherInfo(dto);
    }
}
