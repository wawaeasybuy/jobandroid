package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

/**
 * Created by Ryo on 2015/9/22.
 */
public class PositionAdActivity extends Activity implements View.OnClickListener {
    public ImageView turn_left;
    public Job job;
    public String key;
    public TextView topOpen;
    public TextView urgOpen;
    public int state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_ad);
        initView();
        initAction();
        Intent intent=this.getIntent();
        if(intent.getSerializableExtra("job")!=null)
        {
            this.job= (Job) intent.getSerializableExtra("job");
        }
        doReload();
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
    public void doReload(){
        if(job.getIsTop()!=null) {
            if (job.getIsTop()) {
                topOpen.setText("已开通");
                topOpen.setTextColor(0xff666666);
                topOpen.setClickable(false);
            } else {
                topOpen.setText("开通");
                topOpen.setTextColor(0xff0080fe);
                topOpen.setClickable(true);
            }
        }else{
            topOpen.setText("开通");
            topOpen.setTextColor(0xff0080fe);
            topOpen.setClickable(true);
        }
        if(job.getIsUrg()!=null) {
            if (job.getIsUrg()) {
                urgOpen.setText("已开通");
                urgOpen.setTextColor(0xff666666);
                urgOpen.setClickable(false);
            } else {
                urgOpen.setText("开通");
                urgOpen.setTextColor(0xff0080fe);
                urgOpen.setClickable(true);
            }
        }else {
            urgOpen.setText("开通");
            urgOpen.setTextColor(0xff0080fe);
            urgOpen.setClickable(true);
        }

    }
    public void initAction(){
        topOpen.setOnClickListener(this);
        urgOpen.setOnClickListener(this);

    }
    private void initView() {
        turn_left = (ImageView) findViewById(R.id.turn_left);
        topOpen= (TextView) findViewById(R.id.topOpen);
        urgOpen= (TextView) findViewById(R.id.urgOpen);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.topOpen:
                key="top";
                state=1;
                doExcute();
                break;
            case R.id.urgOpen:
                key="urg";
                state=1;
                doExcute();
                break;
        }
    }
    public void doExcute(){
            RequestParams params = new RequestParams();
            params.put("key",key );
            params.put("state",state);
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            String url=Constants.PositionStr+"/"+job.get_id();
            globalProvider.put(this,url, params, new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(PositionAdActivity.this,"推广成功",Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Log.v("err", new String(responseBody));
                }
                @Override
                public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

                }
            });

    }
}
