package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/9/17.
 */
public class SearchInternshipsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_internships);

        ImageView search= (ImageView) findViewById(R.id.search);
       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent intent = new Intent(SearchInternshipsActivity.this, InternshipsSearchActivity.class);
               startActivity(intent);
           }
       });

        LinearLayout classify= (LinearLayout) findViewById(R.id.classify);
        classify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchInternshipsActivity.this, PositionSearchActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout position_recommend= (LinearLayout) findViewById(R.id.position_recommend);
        position_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SearchInternshipsActivity.this, PositionDetailActivity.class);
                startActivity(intent);
            }
        });

    }
}
