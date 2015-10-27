package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2015/10/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeUpdate implements Serializable{
    public String id;
    public Date timeUpdate;
}
