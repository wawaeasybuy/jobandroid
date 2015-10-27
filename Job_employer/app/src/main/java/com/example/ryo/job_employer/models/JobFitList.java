package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobFitList implements Serializable {
    public List<Resume> resumes;
    public List<Job> jobs;
}
