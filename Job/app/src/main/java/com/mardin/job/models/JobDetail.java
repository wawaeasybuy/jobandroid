package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mardin on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobDetail implements Serializable {
    public String _id;
    public String jobtype;
    public String jobname;
    public Integer vacancynum;
    public String education;
    public String experienceyear;
    public String wage;
    public String description;
    public ArrayList<String> _benefits;
    public String companyname;
    public Float lon;
    public Float lat;
    public String address;
    public String city;
    public String district;
    public String contact;
    public String phone;
    public String email;
    public String gender;
    public String workinghour;
    public Integer weight;
    public Date createtime;
    public String applylink;
    public Employer _employer;
}
