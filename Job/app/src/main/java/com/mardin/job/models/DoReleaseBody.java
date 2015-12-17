package com.mardin.job.models;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/17.
 */
public class DoReleaseBody implements Serializable{
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Boolean getBeOpen() {
        return beOpen;
    }

    public void setBeOpen(Boolean beOpen) {
        this.beOpen = beOpen;
    }

    public String _id;
    public Boolean beOpen;
}
