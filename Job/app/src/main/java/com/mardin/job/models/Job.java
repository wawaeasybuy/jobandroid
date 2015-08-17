package com.mardin.job.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mardin on 7/6/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Job implements Serializable{
    public String _id;
    public String jobtype;
    public String jobname;
    public int vacancynum;
    public String education;
    public String experienceyear;
    public String wage;
    public String description;
    public ArrayList<String> _benefits = new ArrayList<String>();
    public String companyname;
    public float lon;
    public float lat;
    public String address;
    public String city;
    public String district;
    public String contact;
    public String phone;
    public String email;
    public String gender;
    public String workinghour;
    public int weight;
    public Date createtime;
    public String applylink;
    //public Employer _employer;

    public Job() {

    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public int getVacancynum() {
        return vacancynum;
    }

    public void setVacancynum(int vacancynum) {
        this.vacancynum = vacancynum;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperienceyear() {
        return experienceyear;
    }

    public void setExperienceyear(String experienceyear) {
        this.experienceyear = experienceyear;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> get_benefits() {
        return _benefits;
    }

    public void set_benefits(ArrayList<String> _benefits) {
        this._benefits = _benefits;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWorkinghour() {
        return workinghour;
    }

    public void setWorkinghour(String workinghour) {
        this.workinghour = workinghour;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getApplylink() {
        return applylink;
    }

    public void setApplylink(String applylink) {
        this.applylink = applylink;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(_id);
//        dest.writeString(jobtype);
//        dest.writeString(jobname);
//        dest.writeInt(vacancynum);
//        dest.writeString(education);
//        dest.writeString(experienceyear);
//        dest.writeString(wage);
//        dest.writeString(description);
//        dest.writeStringList(_benefits);
//        dest.writeString(companyname);
//        dest.writeFloat(lon);
//        dest.writeFloat(lat);
//        dest.writeString(address);
//        dest.writeString(city);
//        dest.writeString(district);
//        dest.writeString(contact);
//        dest.writeString(phone);
//        dest.writeString(email);
//        dest.writeString(gender);
//        dest.writeString(workinghour);
//        dest.writeInt(weight);
//        dest.writeSerializable(createtime);
//        dest.writeString(applylink);
//
//    }
//
//    public static final Parcelable.Creator<Job> CREATOR
//            = new Parcelable.Creator<Job>() {
//
//        @Override
//        public Job createFromParcel(Parcel source) {
//            return new Job(source);
//        }
//
//        public Job[] newArray(int size) {
//            return new Job[size];
//        }
//    };
//
//    private Job(Parcel in) {
//        _id = in.readString();
//        jobtype = in.readString();
//        jobname = in.readString();
//        vacancynum = in.readInt();
//        education = in.readString();
//        experienceyear = in.readString();
//        wage = in.readString();
//        description = in.readString();
//        in.readStringList(_benefits);
//        companyname = in.readString();
//        lon = in.readFloat();
//        lat = in.readFloat();
//        address = in.readString();
//        city = in.readString();
//        district = in.readString();
//        contact = in.readString();
//        phone = in.readString();
//        email = in.readString();
//        gender = in.readString();
//        workinghour = in.readString();
//        weight = in.readInt();
//        createtime = (Date)in.readSerializable();
//        applylink = in.readString();
//
//    }



}
