package com.mardin.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.activities.job.EditResumeActivity;
import com.mardin.job.activities.job.LoginActivity;
import com.mardin.job.activities.job.PersonalEditDataActivity;


public class MainActivityN extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        LinearLayout login= (LinearLayout) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityN.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        TextView personal_setting= (TextView) findViewById(R.id.personal_setting);
        personal_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityN.this, PersonalEditDataActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout resume_edit= (LinearLayout) findViewById(R.id.resume_edit);
        resume_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityN.this, EditResumeActivity.class);
                startActivity(intent);
            }
        });

    }


}
