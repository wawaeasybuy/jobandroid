package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Mardin on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Candidate implements Serializable {
    public String _id;
    public String email;
    public String phone;
    public String role;
    public CandidateProfile _candidateProfile;

}
