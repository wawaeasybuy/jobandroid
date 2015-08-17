package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mardin on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employer implements Serializable {
    public String _id;
    public EmployerProfile _employerProfile;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public EmployerProfile get_employerProfile() {
        return _employerProfile;
    }

    public void set_employerProfile(EmployerProfile _employerProfile) {
        this._employerProfile = _employerProfile;
    }
}
