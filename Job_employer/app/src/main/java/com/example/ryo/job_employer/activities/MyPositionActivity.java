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
public class MyPositionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_position);

        ImageView turn_left = (ImageView) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        LinearLayout edit = (LinearLayout) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MyPositionActivity.this, EditPositionActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout ad = (LinearLayout) findViewById(R.id.ad);
        ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( MyPositionActivity.this,PositionAdActivity.class);
                startActivity(intent);
            }
        });

    }
}
