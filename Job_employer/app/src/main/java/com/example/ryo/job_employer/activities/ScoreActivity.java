package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by Ryo on 2015/10/7.
 */
public class ScoreActivity extends Activity implements View.OnClickListener {
public RelativeLayout position_ad;
    public ImageView turn_left;
    public RelativeLayout urg;
    public RelativeLayout top;
    public String key="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);
        initView();
        initAction();
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key="top";
               doChange();
            }
        });
        urg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key="urg";
                finish();

            }
        });

    }

    private void initView() {
       // position_ad= (RelativeLayout) findViewById(R.id.position_ad);
        turn_left = (ImageView) findViewById(R.id.turn_left);
        top= (RelativeLayout) findViewById(R.id.top);
        urg= (RelativeLayout) findViewById(R.id.urg);
    }
    public void initAction(){
        turn_left.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
   switch (v.getId()){
       case R.id.turn_left:
           finish();
           break;
   }
    }
    public void doChange(){
        RequestParams params = new RequestParams();
        params.put("key",key);
        GlobalProvider globalProvider=GlobalProvider.getInstance();
            String URL= Constants.personalInfo+"/"+GlobalProvider.getInstance().employerId;
            globalProvider.put(this, URL, params, new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(ScoreActivity.this, "置换成功！", Toast.LENGTH_SHORT).show();
                    //parseLoginResult(new String(responseBody));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                }
            });


    }


}
