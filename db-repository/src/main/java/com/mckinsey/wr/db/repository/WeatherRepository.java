package com.mckinsey.wr.db.repository;

import java.util.Collection;
import java.util.Date;

/**
 * Created by deepakc on 10/04/17.
 */
public interface WeatherRepository {

    void saveWeatherInfo(WeatherDTO weatherDTO);

    Collection<Weather> getWeatherInfo(int offset, int limit);

    class WeatherDTO {
        private String country;
        private String city;
        private double tempC;
        private double tempF;
        private Date recordTime;

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

        public Date getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(Date recordTime) {
            this.recordTime = recordTime;
        }

        public double getTempC() {
            return tempC;
        }

        public void setTempC(double tempC) {
            this.tempC = tempC;
        }

        public double getTempF() {
            return tempF;
        }

        public void setTempF(double tempF) {
            this.tempF = tempF;
        }
    }

}
