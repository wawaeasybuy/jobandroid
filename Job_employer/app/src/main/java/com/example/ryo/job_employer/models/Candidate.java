package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Candidate implements Serializable {
    public String _id;

    public int getTestValue() {
        return testValue;
    }

    public void setTestValue(int testValue) {
        this.testValue = testValue;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int testValue;



}
