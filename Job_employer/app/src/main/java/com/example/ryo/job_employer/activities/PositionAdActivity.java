package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.ryo.job_employer.models.DoReleaseBody;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.models.JobList;
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
 * Created by Ryo on 2015/9/22.
 */
public class PositionAdActivity extends Activity implements View.OnClickListener {
    public ImageView turn_left;
    public Job job;
    public String key;
    public TextView topOpen;
    public TextView urgOpen;
    public int state;
    public int stateTop;
    public int stateUrg;
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
                stateTop=1;
                //topOpen.setClickable(false);
            } else {
                topOpen.setText("开通");
                topOpen.setTextColor(0xff0080fe);
                stateTop=0;
                //topOpen.setClickable(true);
            }
        }else{
            topOpen.setText("开通");
            topOpen.setTextColor(0xff0080fe);
            stateTop=0;
            //topOpen.setClickable(true);
        }
        if(job.getIsUrg()!=null) {
            if (job.getIsUrg()) {
                urgOpen.setText("已开通");
                urgOpen.setTextColor(0xff666666);
                stateUrg=1;
                //urgOpen.setClickable(false);
            } else {
                urgOpen.setText("开通");
                urgOpen.setTextColor(0xff0080fe);
                stateUrg=0;
                //urgOpen.setClickable(true);
            }
        }else {
            urgOpen.setText("开通");
            urgOpen.setTextColor(0xff0080fe);
            stateUrg=0;
            //urgOpen.setClickable(true);
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
                //state=1;
                state=stateTop;
                doExcute();
                break;
            case R.id.urgOpen:
                key="urg";
                state=stateUrg;
                //state=1;
                doExcute();
                break;
        }
    }
    public String elertMessage(String key){
        String message="";
        if(key.equals("top")){
            message="置顶券不足，请前往兑换";
        }else if(key.equals("urg")){
            message="加急券不足，请前往兑换";
        }
        return message;
    }
    public void doExcute(){
        DoReleaseBody body=new DoReleaseBody();
        body.setKey(key);
        body.setState(state);
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity = new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            String URL = Constants.PositionStr + "/" + job.get_id();
            globalProvider.put(this, URL, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    parseResult(new String(responseBody));

                    //parseLoginResult(new String(responseBody));
                    //loadjobList();
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
    private void parseResult(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            Job job = (Job) objectMapper.readValue(jsonParser, Job.class);
            if(job.error!=null){
                new AlertDialog.Builder(PositionAdActivity.this)
                        .setMessage(elertMessage(key))
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent intent=new Intent(PositionAdActivity.this,ScoreActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }else{
                Toast.makeText(PositionAdActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                doResult();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void doResult(){
        this.setResult(Activity.RESULT_OK);
        this.finish();
    }
}
