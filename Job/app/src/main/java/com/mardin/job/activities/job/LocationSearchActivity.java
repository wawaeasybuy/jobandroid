package com.mardin.job.activities.job;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mardin.job.R;

/**
 * Created by Administrator on 2015/12/15.
 */
public class LocationSearchActivity extends Activity{
    public LinearLayout turn_left;
    public EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);
        turn_left= (LinearLayout) findViewById(R.id.turn_left);
        input= (EditText) findViewById(R.id.input);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (!input.getText().toString().equals("")) {
//                    retrieval = input.getText().toString();
//                    LoadRecJobList();
//                } else {
//                    retrieval = "";
//                    mItems.clear();
//                    adapter.notifyDataSetChanged();
//                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
