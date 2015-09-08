package com.mardin.job.fragments.zhiwei;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.activities.zhiwei.PersonalFollowedActivity;
import com.mardin.job.activities.zhiwei.PersonalFollowerActivity;
import com.mardin.job.activities.zhiwei.PersonalMyQuizActivity;
import com.mardin.job.activities.zhiwei.PersonalMyTopicActivity;
import com.mardin.job.activities.zhiwei.PersonalSelfIntroduceActivity;

/**
 * Created by Ryo on 2015/8/26.
 */
public class PersonalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_personal_header, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        TextView introduce= (TextView) getActivity().findViewById(R.id.introduce_sentence);
        introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalSelfIntroduceActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout personal_myTopic= ( LinearLayout) getActivity().findViewById(R.id.personal_myTopic);
        personal_myTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PersonalMyTopicActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout personal_followed= ( LinearLayout) getActivity().findViewById(R.id.personal_followed);
        personal_followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PersonalFollowedActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout personal_follower= ( LinearLayout) getActivity().findViewById(R.id.personal_follower);
        personal_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalFollowerActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout personal_my_quiz= ( LinearLayout) getActivity().findViewById(R.id.personal_my_quiz);
        personal_my_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalMyQuizActivity.class);
                startActivity(intent);
            }
        });

    }
}