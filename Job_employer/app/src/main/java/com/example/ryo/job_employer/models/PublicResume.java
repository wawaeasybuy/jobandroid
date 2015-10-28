package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2015/10/28.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicResume implements Serializable {
    public String _employer;
    public String _candidate;
    public String _job;
    public int gender;
    public String companyName;
    public String tel;
    public String address;
    public String name;
    public Date birth;
    public String imgName;
    public String expectedIndustry;
    public String expectedPosition;
    public String expectedAddress;
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

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getDelieverTime() {
        return delieverTime;
    }

    public void setDelieverTime(String delieverTime) {
        this.delieverTime = delieverTime;
    }

    public Boolean getIsTalent() {
        return isTalent;
    }

    public void setIsTalent(Boolean isTalent) {
        this.isTalent = isTalent;
    }

    public Date getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(Date timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public String getInternshipExprience() {
        return internshipExprience;
    }

    public void setInternshipExprience(String internshipExprience) {
        this.internshipExprience = internshipExprience;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGraduationTime() {
        return graduationTime;
    }

    public void setGraduationTime(String graduationTime) {
        this.graduationTime = graduationTime;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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

    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public String getExpectedAddress() {
        return expectedAddress;
    }

    public void setExpectedAddress(String expectedAddress) {
        this.expectedAddress = expectedAddress;
    }

    public String getExpectedPosition() {
        return expectedPosition;
    }

    public void setExpectedPosition(String expectedPosition) {
        this.expectedPosition = expectedPosition;
    }

    public String getExpectedIndustry() {
        return expectedIndustry;
    }

    public void setExpectedIndustry(String expectedIndustry) {
        this.expectedIndustry = expectedIndustry;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String get_job() {
        return _job;
    }

    public void set_job(String _job) {
        this._job = _job;
    }

    public String get_candidate() {
        return _candidate;
    }

    public void set_candidate(String _candidate) {
        this._candidate = _candidate;
    }

    public String get_employer() {
        return _employer;
    }

    public void set_employer(String _employer) {
        this._employer = _employer;
    }

    public String professional;
    public String graduationTime;
    public String grade;
    public String internshipExprience;
    public Date timeUpdate;
    public Boolean isTalent;
    public String delieverTime;
    public Boolean isRead;

}