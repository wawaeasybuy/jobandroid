package com.example.ryo.job_employer.models;

import com.example.ryo.job_employer.activities.ScoreActivity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoReleaseBody implements Serializable{
    public int state;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String key;
}
