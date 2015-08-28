package com.mardin.job.fragments.zhiwei;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;

import com.mardin.job.activities.zhiwei.Comment;
import com.mardin.job.activities.zhiwei.JobMarketSearch;
import com.mardin.job.activities.zhiwei.Question;
import com.mardin.job.activities.zhiwei.Quiz;
import com.mardin.job.activities.zhiwei.Topic;

/**
 * Created by Ryo on 2015/8/19.
 */
public class HomeFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_headercontent, null);
        return view;
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_home_headercontent, container, false);
    }
    */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout homeTopic= (LinearLayout) getActivity().findViewById(R.id.homeTopic);
        homeTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Topic.class);
                startActivity(intent);
            }
        });

        ImageView home_header_add= (ImageView) getActivity().findViewById(R.id.home_header_add);
        home_header_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Quiz.class);
                startActivity(intent);
            }
        });

        ImageView home_header_search= (ImageView) getActivity().findViewById(R.id.home_header_search);
        home_header_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),JobMarketSearch.class);
                startActivity(intent);
            }
        });

        TextView home_question_title1= (TextView) getActivity().findViewById(R.id.home_question_title1);
        home_question_title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Question.class);
                startActivity(intent);
            }
        });

        TextView home_question_title2= (TextView) getActivity().findViewById(R.id.home_question_title2);
        home_question_title2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Question.class);
                startActivity(intent);
            }
        });

        TextView question_comment= (TextView) getActivity().findViewById(R.id.question_comment);
        question_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Comment.class);
                startActivity(intent);
            }
        });

    }
}