package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalInfo implements Serializable {

    public int score;
    public int UrTicket;
    public int TopTicket;
    public String _user;
    public String companyname;
    public String _id;
    public Boolean pubResume;
    public EmpMessage message;

}
