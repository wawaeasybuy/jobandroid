package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/10/26.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobList implements Serializable {
    public List<Job> jobs;
    public int count;
    public int page;
}
