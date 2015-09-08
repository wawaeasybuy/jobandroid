package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/9/8.
 */
public class PersonalMyTopicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_my_topic);

        LinearLayout turn_left= (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        LinearLayout personal_myTopic= (LinearLayout) findViewById(R.id.personal_myTopic);
        personal_myTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( PersonalMyTopicActivity.this,TopicDetail.class);
                startActivity(intent);
            }
        });
    }

}
