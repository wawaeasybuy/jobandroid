package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TalentList implements Serializable{
    public Talent _talent;
    public String _id;
}
