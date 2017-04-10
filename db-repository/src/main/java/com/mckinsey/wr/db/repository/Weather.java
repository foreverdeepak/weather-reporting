package com.mckinsey.wr.db.repository;

import java.util.Date;

/**
 * Created by deepakc on 10/04/17.
 */
public class Weather {

    private String country;
    private String city;
    private Date previousObservationTime;
    private Date latestObservationTime;
    private Double pTempF;
    private Double pTempC;

    private Double lTempF;
    private Double lTempC;

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

    public Date getPreviousObservationTime() {
        return previousObservationTime;
    }

    public void setPreviousObservationTime(Date previousObservationTime) {
        this.previousObservationTime = previousObservationTime;
    }

    public Date getLatestObservationTime() {
        return latestObservationTime;
    }

    public void setLatestObservationTime(Date latestObservationTime) {
        this.latestObservationTime = latestObservationTime;
    }

    public Double getpTempF() {
        return pTempF;
    }

    public void setpTempF(Double pTempF) {
        this.pTempF = pTempF;
    }

    public Double getpTempC() {
        return pTempC;
    }

    public void setpTempC(Double pTempC) {
        this.pTempC = pTempC;
    }

    public Double getlTempF() {
        return lTempF;
    }

    public void setlTempF(Double lTempF) {
        this.lTempF = lTempF;
    }

    public Double getlTempC() {
        return lTempC;
    }

    public void setlTempC(Double lTempC) {
        this.lTempC = lTempC;
    }
}
