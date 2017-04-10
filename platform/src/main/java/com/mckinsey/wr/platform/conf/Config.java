package com.mckinsey.wr.platform.conf;

/**
 * Created by deepakc on 10/04/17.
 */
public interface Config {

    Service getServiceByName(String name);

    Kafka getKafkaConfById(String id);

    Wunderground getWunderground();

    Rdbbms getRdbbmsById(String id);

    WeatherConfig getWeatherConfig();
}
