package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/11/5.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateJobBody implements Serializable{

    public String PositionName;

    public String getIndustryCategory() {
        return IndustryCategory;
    }

    public void setIndustryCategory(String industryCategory) {
        IndustryCategory = industryCategory;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public String getPositionCategory() {
        return PositionCategory;
    }

    public void setPositionCategory(String positionCategory) {
        PositionCategory = positionCategory;
    }

    public String getPositionCharacter() {
        return PositionCharacter;
    }

    public void setPositionCharacter(String positionCharacter) {
        PositionCharacter = positionCharacter;
    }

    public String getRequirement() {
        return Requirement;
    }

    public void setRequirement(String requirement) {
        Requirement = requirement;
    }

    public String getPositionName() {
        return PositionName;
    }

    public void setPositionName(String positionName) {
        PositionName = positionName;
    }

    public String IndustryCategory;
    public String Requirement;
    public String PositionCharacter;
    public String PositionCategory;
    public int Salary;

    public Boolean getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Boolean isRelease) {
        this.isRelease = isRelease;
    }

    public Boolean isRelease;

    public Boolean getIsPush() {
        return isPush;
    }

    public void setIsPush(Boolean isPush) {
        this.isPush = isPush;
    }

    public Boolean isPush;

}
