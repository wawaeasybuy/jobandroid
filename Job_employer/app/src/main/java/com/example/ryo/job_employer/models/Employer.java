package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employer  implements Serializable {

    public String name;
    public String password;
    public String companyname;
    public String companyInfo;

    public String getMianBusiness() {
        return mianBusiness;
    }

    public void setMianBusiness(String mianBusiness) {
        this.mianBusiness = mianBusiness;
    }

    public String getCompanyAdress() {
        return companyAdress;
    }

    public void setCompanyAdress(String companyAdress) {
        this.companyAdress = companyAdress;
    }

    public String getCompanyURL() {
        return companyURL;
    }

    public void setCompanyURL(String companyURL) {
        this.companyURL = companyURL;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String mianBusiness;
    public String companyURL;
    public String companyAdress;
    public int score;
    public int UrTicket;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_user() {
        return _user;
    }

    public void set_user(String _user) {
        this._user = _user;
    }

    public int getTopTicket() {
        return TopTicket;
    }

    public void setTopTicket(int topTicket) {
        TopTicket = topTicket;
    }

    public int getUrTicket() {
        return UrTicket;
    }

    public void setUrTicket(int urTicket) {
        UrTicket = urTicket;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int TopTicket;
    public String _user;
    public String _id;
    public Boolean pubResume;
    public EmpMessage message;

}
