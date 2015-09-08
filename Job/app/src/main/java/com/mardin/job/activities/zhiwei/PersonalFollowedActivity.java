package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.mardin.job.MainActivity;
import com.mardin.job.R;
import com.mardin.job.fragments.zhiwei.PersonalFragment;

/**
 * Created by Ryo on 2015/9/8.
 */
public class PersonalFollowedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_followed);

        LinearLayout turn_left= (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        LinearLayout followed= (LinearLayout) findViewById(R.id.followed);
        followed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( PersonalFollowedActivity.this,PersonalFragment.class);
                startActivity(intent);
            }
        });

        LinearLayout followed1= (LinearLayout) findViewById(R.id.followed1);
        followed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( PersonalFollowedActivity.this,PersonalFragment.class);
                startActivity(intent);
            }
        });

    }
}
