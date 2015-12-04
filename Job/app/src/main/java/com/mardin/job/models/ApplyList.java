package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/30.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyList implements Serializable {
    public List<PublicResume> resumes;
}
