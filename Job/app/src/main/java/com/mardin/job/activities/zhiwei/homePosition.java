package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/8/26.
 */
public class homePosition extends Activity {

    public homePosition() {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_question_main, container, false);
    }


}