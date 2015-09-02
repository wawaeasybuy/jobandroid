package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.MainActivity;
import com.mardin.job.R;
import com.mardin.job.activities.JobDetailActivity;

/**
 * Created by Ryo on 2015/8/18.
 */
public class Topic extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_main);

        LinearLayout turn_left= (LinearLayout) findViewById(R.id.turn_left);
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

                Intent intent = new Intent(Topic.this, TopicEdit.class);
                startActivity(intent);
            }
        });

        LinearLayout my_Topic= (LinearLayout) findViewById(R.id.my_Topic);
        my_Topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Topic.this, TopicDetail.class);
                startActivity(intent);
            }
        });

        LinearLayout hot_Topic= (LinearLayout) findViewById(R.id.hot_Topic);
        hot_Topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Topic.this, TopicDetail.class);
                startActivity(intent);
            }
        });
    }



}

