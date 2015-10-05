package com.mardin.job.fragments.job;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.activities.job.EditResumeActivity;
import com.mardin.job.activities.job.FunctionScoreActivity;
import com.mardin.job.activities.job.PersonalEditDataActivity;
import com.mardin.job.activities.job.PersonalSettingActivity;


/**
 * Created by Ryo on 2015/9/27.
 */
public class PersonalCenterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_personal_center_logined, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout ID= (LinearLayout) getActivity().findViewById(R.id.ID);
        ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalEditDataActivity.class);
                startActivity(intent);
            }
        });

        ImageView turn_right= (ImageView) getActivity().findViewById(R.id.turn_right);
        turn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),  EditResumeActivity.class);
                startActivity(intent);
            }
        });

        TextView personal_setting= (TextView) getActivity().findViewById(R.id.personal_setting);
        personal_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PersonalSettingActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout resume_edit= (LinearLayout) getActivity().findViewById(R.id.resume_edit);
        resume_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),EditResumeActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout resume_release= (LinearLayout) getActivity().findViewById(R.id.resume_release);
        resume_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),FunctionScoreActivity.class);
                startActivity(intent);
            }
        });

    }
}
