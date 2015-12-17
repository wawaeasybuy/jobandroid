package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployerUpdate implements Serializable{
    public String name;

    public String companyname;
    public String companyInfo;

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
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



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String mainBusiness;
    public String companyURL;

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String companyAddress;

    public String getDetailedCompanyAddress() {
        return detailedCompanyAddress;
    }

    public void setDetailedCompanyAddress(String detailedCompanyAddress) {
        this.detailedCompanyAddress = detailedCompanyAddress;
    }

    public String detailedCompanyAddress;




    public boolean isRelease() {
        return isRelease;
    }

    public void setIsRelease(boolean isRelease) {
        this.isRelease = isRelease;
    }

    public boolean isRelease;
}
