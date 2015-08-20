package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mardin.job.MainActivity;
import com.mardin.job.R;
import com.mardin.job.activities.JobDetailActivity;

/**
 * Created by Ryo on 2015/8/18.
 */
public class Topic extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_main);

        ImageView turn_left= (ImageView) findViewById(R.id.turn_left);
        TextView edit= (TextView) findViewById(R.id.edit);

        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Topic.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



}

