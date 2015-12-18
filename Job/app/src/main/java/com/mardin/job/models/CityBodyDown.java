package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityBodyDown implements Serializable {
    public City _city;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public City get_city() {
        return _city;
    }

    public void set_city(City _city) {
        this._city = _city;
    }

    public String cityCode;
}
