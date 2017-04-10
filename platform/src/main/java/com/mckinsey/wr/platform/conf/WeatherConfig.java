package com.mckinsey.wr.platform.conf;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by deepakc on 10/04/17.
 */
public class WeatherConfig {

    @JsonProperty("countries")
    private Country[] countries;

    public Country[] getCountries() {
        return countries;
    }

    public Country getCountryByName(String name) {
        if (countries != null) {
            for (Country country : countries) {
                if (country.getName().equalsIgnoreCase(name)) {
                    return country;
                }
            }
        }
        return null;
    }

    public static class Country {
        private String name;

        @JsonProperty("topic-name")
        private String topic;

        @JsonProperty("cities")
        private City[] cities;

        public String getName() {
            return name;
        }

        public City getCityByName(String name) {
            if (cities != null) {
                for(City city : cities) {
                    if (city.getName().equalsIgnoreCase(name)) {
                        return city;
                    }
                }
            }
            return null;
        }

        public City[] getCities() {
            return cities;
        }

        public String getTopic() {
            return topic;
        }
    }

    public static class City {
        private String name;

        @JsonProperty("polling-interval")
        private int pollingInterval;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPollingInterval() {
            return pollingInterval;
        }

        public void setPollingInterval(int pollingInterval) {
            this.pollingInterval = pollingInterval;
        }
    }
}
