package com.mckinsey.wr.platform.conf;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by deepakc on 10/04/17.
 */
class ConfigImpl implements Config {

    @JsonProperty("service")
    private Service[] services;

    @JsonProperty("kafka")
    private Kafka[] kafkas;

    @JsonProperty("wunderground")
    private Wunderground wunderground;

    @JsonProperty("rdbbms")
    private Rdbbms[] rdbbms;

    @JsonProperty("weather-config")
    private WeatherConfig weatherConfig;

    @Override
    public Service getServiceByName(String name) {
        if (services != null) {
            for (Service service : services) {
                if (service.getName().equals(name)) {
                    return service;
                }
            }
        }
        return null;
    }

    @Override
    public Kafka getKafkaConfById(String id) {
        if (kafkas != null) {
            for(Kafka kafka : kafkas) {
                if (kafka.getId().equals(id)) {
                    return kafka;
                }
            }
        }
        return null;
    }

    @Override
    public Rdbbms getRdbbmsById(String id) {
        if (rdbbms != null) {
            for (Rdbbms rdbbm : rdbbms) {
                if (rdbbm.getId().equalsIgnoreCase(id)) {
                    return rdbbm;
                }
            }
        }
        return null;
    }

    @Override
    public Wunderground getWunderground() {
        return wunderground;
    }

    @Override
    public WeatherConfig getWeatherConfig() {
        return weatherConfig;
    }
}
