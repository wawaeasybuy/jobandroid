package com.example.ryo.job_employer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.activities.ApplyPersonActivity;
import com.example.ryo.job_employer.activities.EditInfoActivity;
import com.example.ryo.job_employer.activities.IntervieweeActivity;
import com.example.ryo.job_employer.activities.MyPositionActivity;
import com.example.ryo.job_employer.activities.PositionFitActivity;
import com.example.ryo.job_employer.activities.ScoreActivity;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.Employer;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.PersonalInfo;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;


public class MainActivity extends Activity implements View.OnClickListener {
    public  LinearLayout base_data;
    public  LinearLayout score;
    public  LinearLayout my_position;
    public  LinearLayout position_fit;
    public LinearLayout apply_person;
    public  LinearLayout interviewee;
    public TextView name;
    public TextView companyName;
    public TextView myScore;
    public TextView setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initAction();
        LoadPersonalInfo();
    }

    private void initAction() {
        base_data.setOnClickListener(this);
        score.setOnClickListener(this);
        my_position.setOnClickListener(this);
        position_fit.setOnClickListener(this);
        apply_person.setOnClickListener(this);
        interviewee.setOnClickListener(this);
        setting.setOnClickListener(this);

    }

    private void initView() {
        base_data = (LinearLayout) findViewById(R.id.base_data);
        score = (LinearLayout) findViewById(R.id.score);
        my_position = (LinearLayout) findViewById(R.id.my_position);
        position_fit = (LinearLayout) findViewById(R.id.position_fit);
        apply_person = (LinearLayout) findViewById(R.id.apply_person);
        interviewee = (LinearLayout) findViewById(R.id.interviewee);
        name= (TextView) findViewById(R.id.name);
        companyName= (TextView) findViewById(R.id.companyName);
        myScore= (TextView) findViewById(R.id.Myscore);
        setting= (TextView) findViewById(R.id.setting);

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.base_data:
                Intent intent = new Intent(MainActivity.this, EditInfoActivity.class);
                startActivityForResult(intent, Constants.UpdateInfoIntent);
                break;
            case R.id.score:
                Intent intent1 = new Intent( MainActivity.this, ScoreActivity.class);
                startActivityForResult(intent1, Constants.SCOREINTENT);
                break;
            case R.id.my_position:
                Intent intent2 = new Intent( MainActivity.this, MyPositionActivity.class);
                startActivity(intent2);
                break;
            case R.id.position_fit:
                Intent intent3 = new Intent( MainActivity.this, PositionFitActivity.class);
                startActivity(intent3);
                break;
            case R.id.apply_person:
                Intent intent4 = new Intent( MainActivity.this, ApplyPersonActivity.class);
                startActivity(intent4);
                break;
            case R.id.interviewee:
                Intent intent5 = new Intent( MainActivity.this, IntervieweeActivity.class);
                startActivity(intent5);
                break;
            case  R.id.setting:
                doLogout();

        }
    }

    private void doLogout() {
                if (Constants.getToken(this).equals("")) {
                    Toast.makeText(this, "you have not logged in", Toast.LENGTH_SHORT).show();
                }else {
                    Constants.setToken(this, "");
                    Toast.makeText(this, "you have successfully logged out", Toast.LENGTH_SHORT).show();
                    GlobalProvider.getInstance().employerId="";
                    LoadPersonalInfo();
                }
            }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.LoginIntent:
                if (resultCode == RESULT_OK) {
                    LoadPersonalInfo();
                }
                break;
            case Constants.UpdateInfoIntent:
                if(resultCode == RESULT_OK){
                    LoadPersonalInfo();
                }
                break;
            case Constants.SCOREINTENT:
                if(resultCode == RESULT_OK){
                    LoadPersonalInfo();
                }
                break;
        }
    }
    public void LoadPersonalInfo(){
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        //RequestParams params = new RequestParams();
        //params
        globalProvider.get(this,Constants.personalInfo,new RequestListener() {
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
            GlobalProvider.getInstance().employer=employer;
            if(employer.name!=null){name.setText(employer.getName());}else{name.setText("请填写名字");}
            if(employer.companyname!=null){companyName.setText(employer.getCompanyname());}else{companyName.setText("请填写公司名字");}
            myScore.setText(employer.score+"");
            GlobalProvider.getInstance().employerId=employer._id;
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}


