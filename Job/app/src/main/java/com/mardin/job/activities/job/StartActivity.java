package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mardin.job.MainActivityN;
import com.mardin.job.R;


/**
 * Created by Administrator on 2015/12/11.
 */
public class StartActivity extends Activity{
    private final int SPLASH_DISPLAY_LENGHT = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent mainIntent = new Intent(StartActivity.this, MainActivityN.class);
                StartActivity.this.startActivity(mainIntent);
                StartActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
