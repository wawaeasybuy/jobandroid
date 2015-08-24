package com.mardin.job.activities.zhiwei;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/8/24.
 */


public class test extends Fragment {

    public test() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_job, container, false);
    }
}

