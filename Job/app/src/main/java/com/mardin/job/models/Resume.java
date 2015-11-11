package com.mardin.job.models;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/29.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Resume implements Serializable {
    public List<CanMsg> employer;
    public Boolean beOpen;
    public int gender;
    public String tel;
    public String address;
    public String name;
    public String birth;
    public String imgName;
    public String expectedPosition;
    public String expectedAddress;
    public String selfEvaluation;
    public String experience;
    public String works;

    public Boolean getBeOpen() {
        return beOpen;
    }

    public void setBeOpen(Boolean beOpen) {
        this.beOpen = beOpen;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getExpectedPosition() {
        return expectedPosition;
    }

    public void setExpectedPosition(String expectedPosition) {
        this.expectedPosition = expectedPosition;
    }

    public String getExpectedAddress() {
        return expectedAddress;
    }

    public void setExpectedAddress(String expectedAddress) {
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

    public Date getUpdateEdit() {
        return updateEdit;
    }

    public void setUpdateEdit(Date updateEdit) {
        this.updateEdit = updateEdit;
    }

    public String get_candidate() {
        return _candidate;
    }

    public void set_candidate(String _candidate) {
        this._candidate = _candidate;
    }

    public String getExpectedIndustry() {
        return expectedIndustry;
    }

    public void setExpectedIndustry(String expectedIndustry) {
        this.expectedIndustry = expectedIndustry;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String schoolName;
    public String professional;
    public String graduationTime;
    public String grade;
    public String internshipExprience;
    public Date updateEdit;
    public String _candidate;
    public String expectedIndustry;
    public String _id;
}
