package com.mardin.job.widgets.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.mardin.job.widgets.wizard.ui.UpdateCanFragment;

import java.util.ArrayList;

/**
 * Created by Mardin on 7/14/15.
 */
public class UpdateCandidatePage extends  Page{

    public static final String NAME_DATA_KEY = "name";
    public static final String GENDER_DATA_KEY = "gender";
    public static final String EDUCATION_DATA_KEY = "education";
    public static final String EXPERIENCE_DATA_KEY = "experience";

    public UpdateCandidatePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }


    @Override
    public Fragment createFragment() {
        return UpdateCanFragment.newInstance(getKey());
        //return null;
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Name", mData.getString(NAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Gender", mData.getString(GENDER_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Education", mData.getString(EDUCATION_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Experience", mData.getString(EXPERIENCE_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(NAME_DATA_KEY))
                && !TextUtils.isEmpty(mData.getString(GENDER_DATA_KEY));
    }
}
