package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.MainActivity;
import com.mardin.job.R;

/**
 * Created by Ryo on 2015/8/28.
 */
public class TopicEdit extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_edittopic);

        LinearLayout turn_left= (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        LinearLayout addTopic= (LinearLayout) findViewById(R.id.addTopic);
        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TopicEdit.this, TopicAdd.class);
                startActivity(intent);
            }
        });


        TextView editTopic_finished= (TextView) findViewById(R.id.editTopic_finished);
        editTopic_finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TopicEdit.this, Topic.class);
                startActivity(intent);
            }
        });
    }
}
