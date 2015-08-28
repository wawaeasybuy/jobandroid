package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/8/28.
 */
public class TopicAdd extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_addtopic);

        LinearLayout recommended_Topic= (LinearLayout) findViewById(R.id.recommended_Topic);

        recommended_Topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TopicAdd.this, TopicEdit.class);
                startActivity(intent);
            }
        });

    }
}
