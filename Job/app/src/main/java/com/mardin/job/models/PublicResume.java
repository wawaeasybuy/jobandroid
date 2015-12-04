package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/6.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicResume implements Serializable {
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String msg;
    public Job _job;
    public Job get_job() {
        return _job;
    }

    public void set_job(Job _job) {
        this._job = _job;
    }
    public boolean isRead;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getDelieverTime() {
        return delieverTime;
    }

    public void setDelieverTime(Date delieverTime) {
        this.delieverTime = delieverTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String companyName;
    public Date delieverTime;

}
