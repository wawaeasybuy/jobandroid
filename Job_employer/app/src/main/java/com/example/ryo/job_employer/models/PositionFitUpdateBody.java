package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/9.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionFitUpdateBody implements Serializable{
    public String getEmployerId() {
        return employerId;
    }

    public void setEmployerId(String employerId) {
        this.employerId = employerId;
    }

    public String employerId;
}
