package com.mardin.job.models;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/4.
 */
public class JobApplyBody implements Serializable{
    public String _id;

    public String get_employer() {
        return _employer;
    }

    public void set_employer(String _employer) {
        this._employer = _employer;
    }

    public String get_candidate() {
        return _candidate;
    }

    public void set_candidate(String _candidate) {
        this._candidate = _candidate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String _employer;
    public String _candidate;
}
