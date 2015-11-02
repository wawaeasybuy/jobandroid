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
public class EditResumeActivity extends Activity implements View.OnClickListener {
    public LinearLayout turn_left;
    public  LinearLayout personal_base_info;
    public LinearLayout job_intention;
    public LinearLayout education_info;
    public LinearLayout important_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resume_initial);
        initView();
        initAction();

    }
    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.turn_left:
            finish();
            break;
        case R.id.personal_base_info:
            Intent intent = new Intent( EditResumeActivity.this,BaseDataActivity.class);
            startActivity(intent);
            break;
        case R.id.job_intention:
            Intent intent1 = new Intent( EditResumeActivity.this,JobIntentionActivity.class);
            startActivity(intent1);
            break;
        case R.id.education_info:
            Intent intent2 = new Intent( EditResumeActivity.this,EducationInfoActivity.class);
            startActivity(intent2);
            break;
        case R.id.important_info:
            Intent intent3 = new Intent( EditResumeActivity.this,ImportantInfoActivity.class);
            startActivity(intent3);
            break;
    }

    }
    private void initAction() {
        turn_left.setOnClickListener(this);
        personal_base_info.setOnClickListener(this);
        job_intention.setOnClickListener(this);
        education_info.setOnClickListener(this);
        important_info.setOnClickListener(this);

    }

    private void initView() {
        turn_left = (LinearLayout) findViewById(R.id.turn_left);
        personal_base_info= (LinearLayout) findViewById(R.id.personal_base_info);
        job_intention= (LinearLayout) findViewById(R.id.job_intention);
        education_info= (LinearLayout) findViewById(R.id.education_info);
        important_info= (LinearLayout) findViewById(R.id.important_info);
    }


}
