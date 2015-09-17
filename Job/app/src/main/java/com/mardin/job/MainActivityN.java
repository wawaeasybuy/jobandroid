package com.mardin.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.activities.job.EditResumeActivity;
import com.mardin.job.activities.job.FunctionScoreActivity;
import com.mardin.job.activities.job.LoginActivity;
import com.mardin.job.activities.job.PersonalEditDataActivity;
import com.mardin.job.activities.job.PersonalSettingActivity;
import com.mardin.job.activities.job.SearchInternshipsActivity;


public class MainActivityN extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center_logined);

        LinearLayout edit_data= (LinearLayout) findViewById(R.id.edit_data);
        edit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityN.this, PersonalEditDataActivity.class);
                startActivity(intent);
            }
        });

        TextView personal_setting= (TextView) findViewById(R.id.personal_setting);
        personal_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityN.this,SearchInternshipsActivity.class);
                startActivity(intent);
            }
        });

        TextView personal_login= (TextView) findViewById(R.id.personal_login);
        personal_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityN.this,  LoginActivity.class);
                startActivity(intent);
            }
        });

        ImageView turn_right= (ImageView) findViewById(R.id.turn_right);
        turn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityN.this, EditResumeActivity.class);
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

        LinearLayout resume_release= (LinearLayout) findViewById(R.id.resume_release);
        resume_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityN.this, FunctionScoreActivity.class);
                startActivity(intent);
            }
        });


    }


}
