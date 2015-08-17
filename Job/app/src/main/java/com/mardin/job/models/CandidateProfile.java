package com.mardin.job.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Mardin on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateProfile implements Serializable {
    public String _id;
    public String name;
    public String gender;
    public String education;
    public String experience;
    public List<Job> _collectjobs;

    public CandidateProfile() {

    }

//    public CandidateProfile(Parcel in) {
//        name = in.readString();
//        gender = in.readString();
//        education = in.readString();
//        experience = in.readString();
//        in.readTypedList(_collectjobs, Job.CREATOR);
//
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeString(gender);
//        dest.writeString(education);
//        dest.writeString(experience);
//        dest.writeTypedList(_collectjobs);
//
//    }
//
//    public static final Parcelable.Creator<CandidateProfile> CREATOR
//            = new Parcelable.Creator<CandidateProfile>() {
//
//        @Override
//        public CandidateProfile createFromParcel(Parcel source) {
//            return new CandidateProfile(source);
//        }
//
//        @Override
//        public CandidateProfile[] newArray(int size) {
//            return new CandidateProfile[size];
//        }
//    };

}
