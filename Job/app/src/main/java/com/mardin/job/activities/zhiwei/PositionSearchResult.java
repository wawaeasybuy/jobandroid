package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/9/1.
 */
public class PositionSearchResult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_search_result);

        LinearLayout turn_left= (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        ImageView position_header_search= (ImageView) findViewById(R.id.position_header_search);
        position_header_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PositionSearchResult.this, PositionSearchActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout position1= (LinearLayout) findViewById(R.id.position1);
        position1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PositionSearchResult.this, PositionDetail.class);
                startActivity(intent);
            }
        });

        LinearLayout position2= (LinearLayout) findViewById(R.id.position2);
        position2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PositionSearchResult.this, PositionDetail.class);
                startActivity(intent);
            }
        });

        LinearLayout position_area= (LinearLayout) findViewById(R.id.position_area);
        position_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PositionSearchResult.this,SpinnerTest1.class);
                startActivity(intent);
            }
        });
    }
}
