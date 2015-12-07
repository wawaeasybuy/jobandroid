package com.mardin.job.activities.job;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Candidate;
import com.mardin.job.models.Job;
import com.mardin.job.models.JobApplyBody;
import com.mardin.job.models.JobList;
import com.mardin.job.models.Job_delivered;
import com.mardin.job.models.PublicResume;
import com.mardin.job.models.PublicResumeDelivery;
import com.mardin.job.models.Resume;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ryo on 2015/9/17.
 */
public class PositionDetailActivity extends Activity {
    public Job job;
    public Button position_apply;
    public TextView job_name;
    public TextView time_update;
    public TextView companyName;
    public TextView mainBusiness;
    public TextView salary;
    public TextView companyAddress;
    public TextView companyUrl;
    public TextView positionCharacter;
    public TextView requirement;
    public TextView companyInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internships_position_detail);
        position_apply= (Button) findViewById(R.id.position_apply);


        job_name= (TextView) findViewById(R.id.job_name);
        time_update= (TextView) findViewById(R.id.time_update);
        salary= (TextView) findViewById(R.id.salary);
        positionCharacter= (TextView) findViewById(R.id.positionCharacter);
        requirement= (TextView) findViewById(R.id.requirement);

        companyName= (TextView) findViewById(R.id.companyName);
        mainBusiness= (TextView) findViewById(R.id.mainBusiness);
        companyAddress= (TextView) findViewById(R.id.companyAddress);
        companyUrl= (TextView) findViewById(R.id.companyUrl);
        companyInfo= (TextView) findViewById(R.id.companyInfo);
        Intent intent = this.getIntent();
        if(intent.getSerializableExtra("job")!=null){
            job= (Job) intent.getSerializableExtra("job");
            if(job.getPositionCharacter()!=null){positionCharacter.setText(job.getPositionCharacter());}
            if(job.getPositionName()!=null){job_name.setText(job.getPositionName());}
            if(job.getTimeUpdate()!=null){time_update.setText(ConverToString(job.getTimeUpdate()));}
            salary.setText("底薪"+job.getSalary()+"加提成");
            if(job.getRequirement()!=null){requirement.setText(job.getRequirement());}
        }
        position_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLoadCandidateInfo();
            }
        });
        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getEmployerInfo();
    }
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }
    public void getEmployerInfo(){
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        String Url=Constants.jobListUrlStr+"/"+job.get_id();
        globalProvider.get(this, Url, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseInfo(new String(responseBody));
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
    public void parseInfo(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            Job_delivered job = (Job_delivered) objectMapper.readValue(jsonParser, Job_delivered.class);
            if(job._employer.getCompanyname()!=null){companyName.setText(job._employer.getCompanyname());}
            if(job._employer.getCompanyAddress()!=null){companyAddress.setText(job._employer.getCompanyAddress());}
            if(job._employer.getCompanyInfo()!=null){companyInfo.setText(job._employer.getCompanyInfo());}
            if(job._employer.getCompanyURL()!=null){companyUrl.setText(job._employer.getCompanyURL());}
            if(job._employer.getMainBusiness()!=null){mainBusiness.setText(job._employer.getMainBusiness());}
//            this.mItems.clear();
//            this.mItems.addAll(joblist.jobs);
//            //GlobalProvider.getInstance().shangpingListDefault=mItems;
//            adapter.notifyDataSetChanged();
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.GOCREATEPUB:
                if (resultCode == RESULT_OK) {
                    doLoadCandidateInfo();
                }
                break;
        }
    }
    public void doLoadCandidateInfo(){
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.regCanStr, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseCandidateInfo(new String(responseBody));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                new AlertDialog.Builder(PositionDetailActivity.this)
                        .setMessage("您还没登录，请先登录")
                        .setPositiveButton("去登陆", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("alertdialog", " 保存数据");
                                Intent intent=new Intent(PositionDetailActivity.this,LoginActivity.class);
                                startActivityForResult(intent,Constants.GOCREATEPUB);
                            }

                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
//                            MainActivity.getDefault().setSelect(2);
                    }

                }).show();
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }

    public void parseCandidateInfo(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            Candidate candidate = (Candidate) objectMapper.readValue(jsonParser, Candidate.class);
            GlobalProvider.getInstance().candidate=candidate;
            if(candidate.resume!=null){
            if(candidate.resume.isdelivered()){
                doApply();
            }else{
                new AlertDialog.Builder(PositionDetailActivity.this)
                        .setMessage("您的简历还没完善，请完善！")
                        .setPositiveButton("去完善", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("alertdialog", " 保存数据");
                                Intent intent = new Intent(PositionDetailActivity.this, EditResumeActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }
            }else{
                new AlertDialog.Builder(PositionDetailActivity.this)
                        .setMessage("您还没有创建简历，请前去创建！")
                        .setPositiveButton("去创建", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("alertdialog", " 保存数据");
                                Intent intent = new Intent(PositionDetailActivity.this, EditResumeActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }
//            adapter.notifyDataSetChanged();
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void doApply(){
        JobApplyBody body=new JobApplyBody();
        body.set_id(job.get_id());
        body.set_employer(job.get_employer());
        body.set_candidate(GlobalProvider.getInstance().candidate.get_id());

        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            globalProvider.post(this, Constants.crePubResume, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    parseResult(new String(responseBody));
//                    GlobalProvider.getInstance().isLoging=true;
//                    parseLoginResult(new String(responseBody));

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
    public void parseResult(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            PublicResumeDelivery resume = (PublicResumeDelivery) objectMapper.readValue(jsonParser, PublicResumeDelivery.class);
            if(resume.getMsg()==null){
                Toast.makeText(PositionDetailActivity.this,"投递成功！", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                new AlertDialog.Builder(this)
                        .setMessage("简历已投递过！不能再次投递！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("alertdialog", " 保存数据");
                            }
                        }).show();
            }
//            adapter.notifyDataSetChanged();
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

