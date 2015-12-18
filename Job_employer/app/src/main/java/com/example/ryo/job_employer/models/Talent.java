package com.example.ryo.job_employer.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2015/10/30.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Talent implements Serializable {
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }

    public String _id;
    public String _employer;
    public Candidate _candidate;

    public String get_job() {
        return _job;
    }

    public void set_job(String _job) {
        this._job = _job;
    }

    public String _job;
    public int gender;
    public String phoneNumber;
    public CityBodyDown address;
    public String name;
    public Date birth;
    public String imgName;
    public String expectedIndustry;
    public String expectedPosition;
    public CityBodyDown expectedAddress;
    public String selfEvaluation;
    public String experience;
    public String works;
    public String schoolName;
    public String professional;
    public String graduationTime;
    public String grade;
    public String internshipExprience;
    public Date timeUpdate;
    public Boolean isInterview;

    public int getTestValue() {
        return testValue;
    }

    public void setTestValue(int testValue) {
        this.testValue = testValue;
    }

    public int testValue;

    public int getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(int professionalLevel) {
        this.professionalLevel = professionalLevel;
    }

    public String get_employer() {
        return _employer;
    }

    public void set_employer(String _employer) {
        this._employer = _employer;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CityBodyDown getAddress() {
        return address;
    }

    public void setAddress(CityBodyDown address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public CityBodyDown getExpectedAddress() {
        return expectedAddress;
    }

    public void setExpectedAddress(CityBodyDown expectedAddress) {
        this.expectedAddress = expectedAddress;
    }

    public String getSelfEvaluation() {
        return selfEvaluation;
    }

    public void setSelfEvaluation(String selfEvaluation) {
        this.selfEvaluation = selfEvaluation;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
        this.works = works;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
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

    public Date getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(Date timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public Boolean getIsInterview() {
        return isInterview;
    }

    public void setIsInterview(Boolean isInterview) {
        this.isInterview = isInterview;
    }

    public int getAnalysis() {
        return analysis;
    }

    public void setAnalysis(int analysis) {
        this.analysis = analysis;
    }

    public int getExpression() {
        return expression;
    }

    public void setExpression(int expression) {
        this.expression = expression;
    }

    public int getCompression() {
        return compression;
    }

    public void setCompression(int compression) {
        this.compression = compression;
    }

    public int getAttitude() {
        return attitude;
    }

    public void setAttitude(int attitude) {
        this.attitude = attitude;
    }

    public String getAdviceText() {
        return adviceText;
    }

    public void setAdviceText(String adviceText) {
        this.adviceText = adviceText;
    }

    public int professionalLevel;
    public int analysis;
    public int expression;
    public int compression;
    public int attitude;
    public String adviceText;
}
