package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2015/11/5.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job_delivered implements Serializable {
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String _id;
    public Employer _employer;
    public String employerName;
    public String positionName;
    public String positionCategory;
    public String industryCategory;
    public int salary;
    public String requirement;

    public String getPositionCharacter() {
        return positionCharacter;
    }

    public void setPositionCharacter(String positionCharacter) {
        this.positionCharacter = positionCharacter;
    }

    public Employer get_employer() {
        return _employer;
    }

    public void set_employer(Employer _employer) {
        this._employer = _employer;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionCategory() {
        return positionCategory;
    }

    public void setPositionCategory(String positionCategory) {
        this.positionCategory = positionCategory;
    }

    public String getIndustryCategory() {
        return industryCategory;
    }

    public void setIndustryCategory(String industryCategory) {
        this.industryCategory = industryCategory;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Boolean isRelease) {
        this.isRelease = isRelease;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Boolean getIsUrg() {
        return isUrg;
    }

    public void setIsUrg(Boolean isUrg) {
        this.isUrg = isUrg;
    }

    public Date getTopTime() {
        return topTime;
    }

    public void setTopTime(Date topTime) {
        this.topTime = topTime;
    }

    public Date getUrgTime() {
        return UrgTime;
    }

    public void setUrgTime(Date urgTime) {
        UrgTime = urgTime;
    }

    public Date getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(Date timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public String positionCharacter;
    public String city;
    public Boolean isRelease;
    public Boolean isTop;
    public Boolean isUrg;
    public Date topTime;   //for employer msg
    public Date UrgTime; //for employer msg
    public Date timeUpdate;
}
