package com.mardin.job.widgets.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.mardin.job.widgets.wizard.ui.AddJobFirstFragment;

import java.util.ArrayList;

/**
 * Created by Mardin on 7/9/15.
 */
public class AddJobFirstPage extends Page {
    public static final String JOBNAME_DATA_KEY = "jobname";
    public static final String DISTRICT_DATA_KEY = "district";
    public static final String COMPANY_DATA_KEY = "companyname";

    public AddJobFirstPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }


    @Override
    public Fragment createFragment() {
        return AddJobFirstFragment.newInstance(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Job Name", mData.getString(JOBNAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("District", mData.getString(DISTRICT_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Company", mData.getString(COMPANY_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(JOBNAME_DATA_KEY))
                && !TextUtils.isEmpty(mData.getString(DISTRICT_DATA_KEY))
                && !TextUtils.isEmpty(mData.getString(COMPANY_DATA_KEY));
    }
}
