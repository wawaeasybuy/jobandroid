package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/10/30.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TalentList implements Serializable {
   public List<Talent> talents;
   public int count;
   public int page;
}
