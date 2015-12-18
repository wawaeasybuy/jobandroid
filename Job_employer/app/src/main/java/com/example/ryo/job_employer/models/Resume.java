package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/22.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resume implements Serializable {
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String _id;
    public Candidate _candidate;
    public int gender;
    public String tel;
    public CityBodyDown address;
    public String name;
    public String birth;
    public Date updateEdit;
    public String imgName;
    public String expectedIndustry;
    public String expectedPosition;
    public CityBodyDown expectedAddress;
    public String selfEvaluation;
    public String experience;
    public String works;
    public String schoolName;

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public Candidate get_candidate() {
        return _candidate;
    }

    public void set_candidate(Candidate _candidate) {
        this._candidate = _candidate;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public CityBodyDown getAddress() {
        return address;
    }

    public void setAddress(CityBodyDown address) {
        this.address = address;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdateEdit() {
        return updateEdit;
    }

    public void setUpdateEdit(Date updateEdit) {
        this.updateEdit = updateEdit;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getExpectedIndustry() {
        return expectedIndustry;
    }

    public void setExpectedIndustry(String expectedIndustry) {
        this.expectedIndustry = expectedIndustry;
    }

    public String getExpectedPosition() {
        return expectedPosition;
    }

    public void setExpectedPosition(String expectedPosition) {
        this.expectedPosition = expectedPosition;
    }

    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public CityBodyDown getExpectedAddress() {
        return expectedAddress;
    }

    public void setExpectedAddress(CityBodyDown expectedAddress) {
        this.expectedAddress = expectedAddress;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(String graduationTime) {
        this.graduationTime = graduationTime;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getInternshipExprience() {
        return internshipExprience;
    }

    public void setInternshipExprience(String internshipExprience) {
        this.internshipExprience = internshipExprience;
    }

    public int getTestValue() {
        return testValue;
    }

    public void setTestValue(int testValue) {
        this.testValue = testValue;
    }

    public Boolean getBeOpen() {
        return beOpen;
    }

    public void setBeOpen(Boolean beOpen) {
        this.beOpen = beOpen;
    }

    public List<TimeUpdate> getEmployer() {
        return employer;
    }

    public void setEmployer(List<TimeUpdate> employer) {
        this.employer = employer;
    }

    public List<Ignored> getBeIgnored() {
        return beIgnored;
    }

    public void setBeIgnored(List<Ignored> beIgnored) {
        this.beIgnored = beIgnored;
    }

    public String professional;
    public String graduationTime;
    public String grade;
    public String internshipExprience;
    public int testValue;
    public Boolean beOpen;
    public List<TimeUpdate> employer;
    public List<Ignored> beIgnored;
}
