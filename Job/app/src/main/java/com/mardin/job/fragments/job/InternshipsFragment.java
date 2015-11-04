package com.mardin.job.fragments.job;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mardin.job.R;
import com.mardin.job.activities.job.InternshipsSearchActivity;
import com.mardin.job.activities.job.PositionDetailActivity;
import com.mardin.job.activities.job.PositionSearchActivity;

/**
 * Created by Ryo on 2015/9/27.
 */
public class InternshipsFragment extends Fragment implements View.OnClickListener {
    public LinearLayout search;
    public LinearLayout classify;
    public LinearLayout position_recommend;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_search_internships, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initAction();
    }
    public void initView(){
       search= ( LinearLayout) getActivity().findViewById(R.id.search);
       classify= (LinearLayout) getActivity().findViewById(R.id.classify);
       position_recommend= (LinearLayout) getActivity().findViewById(R.id.position_recommend);
    }
    public void initAction(){
        search.setOnClickListener(this);
        classify.setOnClickListener(this);
        position_recommend.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                Intent intent = new Intent(getActivity(), InternshipsSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.classify:
                Intent intent1 = new Intent(getActivity(), PositionSearchActivity.class);
                startActivity(intent1);
                break;
            case R.id.position_recommend:
                Intent intent2 = new Intent(getActivity(), PositionDetailActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
