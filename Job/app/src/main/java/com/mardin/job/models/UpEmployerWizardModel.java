package com.mardin.job.models;

import android.content.Context;

import com.mardin.job.R;
import com.mardin.job.widgets.wizard.model.AbstractWizardModel;
import com.mardin.job.widgets.wizard.model.PageList;
import com.mardin.job.widgets.wizard.model.UpdateEmployerPage;

/**
 * Created by Mardin on 7/13/15.
 */
public class UpEmployerWizardModel extends AbstractWizardModel {

    public UpEmployerWizardModel(Context context) {
        super(context);

    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new UpdateEmployerPage(this, "Your Information").setRequired(true)
        );
    }
}
