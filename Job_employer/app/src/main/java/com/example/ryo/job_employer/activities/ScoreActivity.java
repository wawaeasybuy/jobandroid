package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.ryo.job_employer.R;

/**
 * Created by Ryo on 2015/10/7.
 */
public class ScoreActivity extends Activity {
public RelativeLayout position_ad;
    public ImageView turn_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);
        initView();

        position_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScoreActivity.this,PositionAdActivity.class);
                startActivity(intent);
            }
        });
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    private void initView() {
        position_ad= (RelativeLayout) findViewById(R.id.position_ad);
        turn_left = (ImageView) findViewById(R.id.turn_left);
    }
}