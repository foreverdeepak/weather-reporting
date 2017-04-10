package com.mckinsey.wr.supervisor;

/**
 * Created by deepakc on 10/04/17.
 */
public class CityWeatherConfiguration {

    private String country;
    private String city;
    private String topic;
    private long pollingInterval;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(long pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
