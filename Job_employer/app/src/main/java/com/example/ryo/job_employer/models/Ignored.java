package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ignored implements Serializable{
       public String Employer_id;
}
