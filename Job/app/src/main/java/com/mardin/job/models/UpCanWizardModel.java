package com.mardin.job.models;

import android.content.Context;

import com.mardin.job.widgets.wizard.model.AbstractWizardModel;
import com.mardin.job.widgets.wizard.model.PageList;
import com.mardin.job.widgets.wizard.model.UpdateCandidatePage;

/**
 * Created by Mardin on 7/14/15.
 */
public class UpCanWizardModel extends AbstractWizardModel {


    public UpCanWizardModel(Context context) {
        super(context);
    }

    @Override
    protected PageList onNewRootPageList() {
        return new PageList(
                new UpdateCandidatePage(this, "Your Information").setRequired(true)
        );
    }
}
