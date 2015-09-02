package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/9/2.
 */
public class PositionSearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_search);

        ImageView turn_left= (ImageView) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        TextView search_history1= (TextView) findViewById(R.id.search_history1);
        search_history1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PositionSearchActivity.this,PositionSearchResult.class);
                startActivity(intent);
            }
        });

        TextView search_history2= (TextView) findViewById(R.id.search_history2);
        search_history2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PositionSearchActivity.this,PositionSearchResult.class);
                startActivity(intent);
            }
        });

    }
}
