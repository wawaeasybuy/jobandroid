package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/5.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPsdBody implements Serializable{
    public String code;
    public String tel;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String newPassword;
}
