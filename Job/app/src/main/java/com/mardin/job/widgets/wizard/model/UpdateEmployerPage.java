package com.mardin.job.widgets.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.mardin.job.widgets.wizard.ui.UpdateEmployerFragment;

import java.util.ArrayList;

/**
 * Created by Mardin on 7/13/15.
 */
public class UpdateEmployerPage extends  Page{

    public static final String COMPANYNAME_DATA_KEY = "companyname";
    public static final String ADDRESS_DATA_KEY = "address";
    public static final String CONTACT_DATA_KEY = "contact";
    public static final String SIZE_DATA_KEY = "size";
    public static final String TYPE_DATA_KEY = "type";
    public static final String REVENUE_DATA_KEY = "revenue";
    public static final String FOUNDED_DATA_KEY = "founded";
    public static final String WEBSITE_DATA_KEY = "website";

    public UpdateEmployerPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }


    @Override
    public Fragment createFragment() {
        return UpdateEmployerFragment.newInstance(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Company Name", mData.getString(COMPANYNAME_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Address", mData.getString(ADDRESS_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Contact", mData.getString(CONTACT_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Size", mData.getString(SIZE_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Type", mData.getString(TYPE_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Revenue", mData.getString(REVENUE_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Founded", mData.getString(FOUNDED_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Website", mData.getString(WEBSITE_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(COMPANYNAME_DATA_KEY))
        && !TextUtils.isEmpty(mData.getString(ADDRESS_DATA_KEY));
    }


}
