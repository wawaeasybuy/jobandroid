package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/5.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error implements Serializable{
    public String msg;
}
