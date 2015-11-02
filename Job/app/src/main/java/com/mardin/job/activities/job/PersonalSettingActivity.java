package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/9/15.
 */
public class PersonalSettingActivity extends Activity implements View.OnClickListener {
    public Button logout;
   public  LinearLayout turn_left;
    public LinearLayout feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);

        initView();
        initAction();
    }

    private void initAction() {
        turn_left.setOnClickListener(this);
        feedback.setOnClickListener(this);
    }
    private void initView() {
        turn_left = (LinearLayout) findViewById(R.id.turn_left);
        feedback= (LinearLayout) findViewById(R.id.feedback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.turn_left:
                finish();
                break;
            case R.id.feedback:
                Intent intent = new Intent(PersonalSettingActivity.this, SettingFeedbackActivity.class);
                startActivity(intent);
                break;
        }
    }
}
