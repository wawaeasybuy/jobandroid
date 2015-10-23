package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ryo.job_employer.R;

/**
 * Created by Ryo on 2015/9/22.
 */
public class MyPositionActivity extends Activity implements View.OnClickListener {

    public ImageView add_position;
    public ImageView turn_left;
    public  LinearLayout edit;
    public LinearLayout ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_position);

        initView();
        initAction();
    }

    private void initAction() {
        add_position.setOnClickListener(this);
        turn_left.setOnClickListener(this);
        edit.setOnClickListener(this);
        ad.setOnClickListener(this);

    }

    private void initView() {
        add_position= (ImageView) findViewById(R.id.add_position);
        turn_left = (ImageView) findViewById(R.id.turn_left);
        edit = (LinearLayout) findViewById(R.id.edit);
        ad = (LinearLayout) findViewById(R.id.ad);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.add_position:
                Intent intent=new Intent(MyPositionActivity.this,EditPositionActivity.class);
                startActivity(intent);
                  break;
            case R.id.turn_left:
                finish();
                break;
            case R.id.edit:
                Intent intent1 = new Intent( MyPositionActivity.this, EditPositionActivity.class);
                startActivity(intent1);
                break;
            case R.id.ad:
                Intent intent2 = new Intent( MyPositionActivity.this,PositionAdActivity.class);
                startActivity(intent2);
                break;
        }

    }
}
