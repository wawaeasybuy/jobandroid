package com.mardin.job.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Mardin on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    public String _id;
    public String email;
    public String phone;
    public String role;
    public CandidateProfile _candidateProfile = new CandidateProfile();
    public EmployerProfile _employerProfile;

    public User() {

    }

//    public User(Parcel in) {
//        _id = in.readString();
//        email = in.readString();
//        phone = in.readString();
//        role = in.readString();
//        _candidateProfile = in.readParcelable(CandidateProfile.class.getClassLoader());
//        _employerProfile = (EmployerProfile) in.readSerializable();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(_id);
//        dest.writeString(email);
//        dest.writeString(phone);
//        dest.writeString(role);
//        //dest.writeSerializable(_candidateProfile);
//        dest.writeParcelable(_candidateProfile, flags);
//        dest.writeSerializable(_employerProfile);
//    }
//
//    public static final Parcelable.Creator<User> CREATOR
//            = new Parcelable.Creator<User>() {
//        @Override
//        public User createFromParcel(Parcel source) {
//            return new User(source);
//        }
//        public User[] newArray(int size){
//            return new User[size];
//        }
//    };


}
