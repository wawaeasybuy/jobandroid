package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.DoReleaseBody;
import com.example.ryo.job_employer.models.Employer;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
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
    public TextView score;
    public TextView go_to_evaluate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);
        initView();
        initAction();
        go_to_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScoreActivity.this,IntervieweeActivity.class);
                startActivity(intent);
            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = "top";
                doChange();
            }
        });
        urg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key="urg";
                doChange();

            }
        });
        LoadPersonalInfo();
    }

    public void LoadPersonalInfo(){
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        //RequestParams params = new RequestParams();
        //params
        globalProvider.get(this, Constants.personalInfo, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parsePersonalInfo(new String(responseBody));
                //mSwipeRefreshlayout.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.v("err", new String(responseBody));
                //mSwipeRefreshlayout.setRefreshing(false);
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }
    public void parsePersonalInfo(String json){

        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            Employer employer = (Employer) objectMapper.readValue(jsonParser, Employer.class);
            //接受全局employer
            score.setText(employer.score+"");
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void initView() {
       // position_ad= (RelativeLayout) findViewById(R.id.position_ad);
        turn_left = (ImageView) findViewById(R.id.turn_left);
        top= (RelativeLayout) findViewById(R.id.top);
        urg= (RelativeLayout) findViewById(R.id.urg);
        score= (TextView) findViewById(R.id.score);
        go_to_evaluate= (TextView) findViewById(R.id.go_to_evaluate);
    }
    public void initAction(){
        turn_left.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
   switch (v.getId()){
       case R.id.turn_left:
           setResult(Activity.RESULT_OK);
           finish();
           break;
      }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下键盘上返回按钮
        if(keyCode == KeyEvent.KEYCODE_BACK){
            setResult(Activity.RESULT_OK);
            finish();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }
    public void doChange(){
        DoReleaseBody body=new DoReleaseBody();
        body.setKey(key);
        //body.setState(state);
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity = new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            String URL = Constants.personalInfo + "/" + GlobalProvider.getInstance().employerId;
            globalProvider.put(this, URL, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(ScoreActivity.this, "兑换成功！", Toast.LENGTH_SHORT).show();
                    LoadPersonalInfo();
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
