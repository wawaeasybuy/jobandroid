package com.example.ryo.job_employer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ryo.job_employer.activities.ApplyPersonActivity;
import com.example.ryo.job_employer.activities.EditInfoActivity;
import com.example.ryo.job_employer.activities.IntervieweeActivity;
import com.example.ryo.job_employer.activities.MyPositionActivity;
import com.example.ryo.job_employer.activities.PositionFitActivity;
import com.example.ryo.job_employer.activities.ScoreActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LinearLayout base_data = (LinearLayout) findViewById(R.id.base_data);
        base_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, EditInfoActivity.class);
                startActivity(intent);
            }
        });


        LinearLayout score = (LinearLayout) findViewById(R.id.score);
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, ScoreActivity.class);
                startActivity(intent);
            }
        });


        LinearLayout my_position = (LinearLayout) findViewById(R.id.my_position);
        my_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, MyPositionActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout position_fit = (LinearLayout) findViewById(R.id.position_fit);
        position_fit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, PositionFitActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout apply_person = (LinearLayout) findViewById(R.id.apply_person);
        apply_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, ApplyPersonActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout interviewee = (LinearLayout) findViewById(R.id.interviewee);
        interviewee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MainActivity.this, IntervieweeActivity.class);
                startActivity(intent);
            }
        });
    }
}


