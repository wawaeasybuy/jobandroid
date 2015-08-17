package com.mardin.job.models;

import android.content.Context;

import com.mardin.job.R;
import com.mardin.job.widgets.wizard.model.AbstractWizardModel;
import com.mardin.job.widgets.wizard.model.AddJobFirstPage;
import com.mardin.job.widgets.wizard.model.AddJobSecPage;
import com.mardin.job.widgets.wizard.model.PageList;

/**
 * Created by Mardin on 7/8/15.
 */
public class JobWizardModel extends AbstractWizardModel {

    Context mContext;
    public JobWizardModel(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
//                new CustomerInfoPage(this, "Your info")
//                        .setRequired(true),
                new AddJobFirstPage(this, "Basic Information")
                .setRequired(true),
                new AddJobSecPage(this, "Detail")
                .setRequired(true)

        );
    }
}
