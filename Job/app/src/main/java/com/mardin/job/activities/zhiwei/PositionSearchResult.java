package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/9/1.
 */
public class PositionSearchResult extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_search_result);

        ImageView turn_left= (ImageView) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
