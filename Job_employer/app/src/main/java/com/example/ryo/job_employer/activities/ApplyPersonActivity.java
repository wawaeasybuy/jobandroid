package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ryo.job_employer.R;

/**
 * Created by Ryo on 2015/9/22.
 */
public class ApplyPersonActivity extends Activity implements View.OnClickListener {
    public  ImageView turn_left;
    public  LinearLayout resume1;
    public LinearLayout resume2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_person);

        initView();
        initAction();
    }

    private void initAction() {

        turn_left.setOnClickListener(this);
        resume1.setOnClickListener(this);
        resume2.setOnClickListener(this);

    }

    private void initView() {

        turn_left = (ImageView) findViewById(R.id.turn_left);
        resume1 = (LinearLayout) findViewById(R.id.resume1);
        resume2 = (LinearLayout) findViewById(R.id.resume2);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.turn_left:
                finish();
                break;
            case R.id.resume1:
                Intent intent = new Intent( ApplyPersonActivity.this, PersonalResumeActivity.class);
                startActivity(intent);
                break;
            case R.id.resume2:
                Intent intent1 = new Intent( ApplyPersonActivity.this, PersonalResumeActivity.class);
                startActivity(intent1);
                break;

        }

    }
}
