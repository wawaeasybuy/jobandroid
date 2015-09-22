package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ryo.job_employer.R;

/**
 * Created by Ryo on 2015/9/22.
 */
public class IntervieweeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internships_interviewee);

        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        LinearLayout interviewee_to_evaluate = (LinearLayout) findViewById(R.id.interviewee_to_evaluate);
        interviewee_to_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( IntervieweeActivity.this, IntervieweeEvaluateActivity.class);
                startActivity(intent);
            }
        });


    }
}
