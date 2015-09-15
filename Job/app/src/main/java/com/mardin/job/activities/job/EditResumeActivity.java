package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mardin.job.R;


/**
 * Created by Ryo on 2015/9/14.
 */
public class EditResumeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resume);

        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        LinearLayout personal_base_info= (LinearLayout) findViewById(R.id.personal_base_info);
        personal_base_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( EditResumeActivity.this,BaseDataActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout job_intention= (LinearLayout) findViewById(R.id.job_intention);
        job_intention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( EditResumeActivity.this,JobIntentionActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout personal_education= (LinearLayout) findViewById(R.id.personal_education);
        personal_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( EditResumeActivity.this,EducationInfoActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout personal_important_info= (LinearLayout) findViewById(R.id.personal_important_info);
        personal_important_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( EditResumeActivity.this,ImportantInfoActivity.class);
                startActivity(intent);
            }
        });

    }
}
