package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ryo on 2015/9/14.
 */
public class RetrievePasswordActivity extends Activity {
    public TextView  get_verification_code;
    private int time = 60;
    private Timer timer = new Timer();
    TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_retrieve_password);
        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        get_verification_code= (TextView) findViewById(R.id.get_verification_code);
        get_verification_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_verification_code.setEnabled(false);
                task = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() { // UI thread
                            @Override
                            public void run() {
                                if (time <= 0) {
                                    get_verification_code.setEnabled(true);
                                    get_verification_code.setText("获取验证码");
                                    task.cancel();
                                } else {
                                    get_verification_code.setText("" + time);
                                }
                                time--;
                            }
                        });
                    }
                };
                //time = 60;
                timer.schedule(task, 0, 1000);
            }
        });
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

}
