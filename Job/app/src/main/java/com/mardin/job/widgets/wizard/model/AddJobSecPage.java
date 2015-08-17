package com.mardin.job.widgets.wizard.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.mardin.job.widgets.wizard.ui.AddJobSecFragment;

import java.util.ArrayList;

/**
 * Created by Mardin on 7/9/15.
 */
public class AddJobSecPage extends Page {

    public static final String DESCRIPTION_DATA_KEY = "description";
    public static final String EMAIL_DATA_KEY = "email";
    public static final String WEBLINK_DATA_KEY = "applylink";

    public AddJobSecPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return AddJobSecFragment.newInstance(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Description", mData.getString(DESCRIPTION_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Email", mData.getString(EMAIL_DATA_KEY), getKey(), -1));
        dest.add(new ReviewItem("Weblink", mData.getString(WEBLINK_DATA_KEY), getKey(), -1));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(DESCRIPTION_DATA_KEY))
                && (!TextUtils.isEmpty(mData.getString(EMAIL_DATA_KEY))
                    || !TextUtils.isEmpty(mData.getString(WEBLINK_DATA_KEY))
                    );
    }
}
