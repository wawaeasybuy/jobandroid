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
public class InternshipsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_search_internships, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout search= ( LinearLayout) getActivity().findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InternshipsSearchActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout classify= (LinearLayout) getActivity().findViewById(R.id.classify);
        classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PositionSearchActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout position_recommend= (LinearLayout) getActivity().findViewById(R.id.position_recommend);
        position_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PositionDetailActivity.class);
                startActivity(intent);
            }
        });



    }
}
