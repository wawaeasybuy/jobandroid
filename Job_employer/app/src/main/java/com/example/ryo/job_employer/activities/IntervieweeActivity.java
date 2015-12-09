package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.adapter.InterViewPulldownAdapter;
import com.example.ryo.job_employer.adapter.PositionFitHeaderAdapter;
import com.example.ryo.job_employer.adapter.TalentAdapter;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.models.JobFitList;
import com.example.ryo.job_employer.models.JobList;
import com.example.ryo.job_employer.models.Talent;
import com.example.ryo.job_employer.models.TalentList;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryo on 2015/9/22.
 */
public class IntervieweeActivity extends Activity implements View.OnClickListener {
    public ImageView turn_left;
    public TextView interviewee_to_evaluate;
    public ListView lv_pull_down_position_name;
    public ListView lv_pull_down_interview;
    public ListView lv_main;
    public ImageView turn_img;
    public LinearLayout turn;
    public TextView turn_text;
    public LinearLayout interview_layout;
    public TextView interview_text;
    public ImageView interview_img;

    public TalentAdapter mAdapter;
    public PositionFitHeaderAdapter adapter;
    public InterViewPulldownAdapter Adapter;
    public List<Talent> mItems;
    public List<Talent> mItems_all;
    public List<Job> items;

    private Integer mPage;
    private Integer mItemsPerPage;
    private Boolean isShowing=false;
    public  Boolean isShowing1=false;
    public String[] data ={"全部","已评价","待评价"};

    public Job JOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internships_interviewee);

        initView();
        initAction();
        mItems=new ArrayList<Talent>();
       // mItems_all=new ArrayList<Talent>();
        items=new ArrayList<Job>();
        mAdapter=new TalentAdapter(this,mItems,items);
        adapter=new PositionFitHeaderAdapter(this,items);
        Adapter=new InterViewPulldownAdapter(this,data);
        lv_pull_down_interview.setAdapter(Adapter);
        lv_pull_down_interview.setVisibility(View.GONE);
        lv_main.setAdapter(mAdapter);
       // lv_pull_down_interview.setAdapter(adapter);
        lv_pull_down_position_name.setAdapter(adapter);
        lv_main.setVisibility(View.VISIBLE);
        lv_pull_down_position_name.setVisibility(View.GONE);

        turn_text.setText("全部职位");

        lv_pull_down_position_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    //job=list.get(position).get_id();
                    LoadTalentListByJobId(items.get(position).get_id());
                    //applyHeader_text.setText(list.get(position-1).getPositionName());
                } else {
                    //job="";
                    LoadTalentList();
                    //applyHeader_text.setText("全部职位");
                }
                turn_text.setText(items.get(position).getPositionName());
                lv_pull_down_position_name.setVisibility(View.GONE);
                lv_main.setVisibility(View.VISIBLE);
                turn_img.setImageResource(R.drawable.turn_down);
                isShowing = false;
                mAdapter.notifyDataSetChanged();

            }
        });
        lv_pull_down_interview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                interview_text.setText(data[position]);
                lv_pull_down_interview.setVisibility(View.GONE);
                lv_main.setVisibility(View.VISIBLE);
                interview_img.setImageResource(R.drawable.turn_down);
                isShowing1 = false;
            }
        });
        interview_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShowing1){
                    interview_img.setImageResource(R.drawable.turn_up);
                    lv_pull_down_interview.setVisibility(View.VISIBLE);
                    lv_main.setVisibility(View.GONE);
                }else{
                    interview_img.setImageResource(R.drawable.turn_down);
                    lv_pull_down_interview.setVisibility(View.GONE);
                    lv_main.setVisibility(View.VISIBLE);
                }
                isShowing1=!isShowing1;
                isShowing=false;
                turn_img.setImageResource(R.drawable.turn_down);
                lv_pull_down_position_name.setVisibility(View.GONE);

            }
        });

        mPage = 1;
        mItemsPerPage = 10;
        LoadJobList();
        LoadTalentList();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.turn_left:
                setResult(Activity.RESULT_OK);
                finish();
                break;
            case R.id.turn:
                if(!isShowing){
                    lv_pull_down_position_name.setVisibility(View.VISIBLE);
                    lv_main.setVisibility(View.GONE);
                    turn_img.setImageResource(R.drawable.turn_up);
                }else{
                    lv_pull_down_position_name.setVisibility(View.GONE);
                    lv_main.setVisibility(View.VISIBLE);
                    turn_img.setImageResource(R.drawable.turn_down);
                }
                interview_img.setImageResource(R.drawable.turn_down);
                lv_pull_down_interview.setVisibility(View.GONE);
                isShowing1=false;
                isShowing=!isShowing;
                break;
//          case  R.id.interviewee_to_evaluate:
//              Intent intent = new Intent( IntervieweeActivity.this, IntervieweeEvaluateActivity.class);
//              startActivity(intent);
//              break;
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
    private void LoadJobList(){
        RequestParams params = new RequestParams();
        params.put("id", GlobalProvider.getInstance().employerId);
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.personalInfo, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseJobList(new String(responseBody));
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
    private void parseJobList(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobList jobList = (JobList) objectMapper.readValue(jsonParser, JobList.class);
            if(items.size()==0){
                if(JOB==null){JOB=new Job();JOB.setPositionName("全部职位");}
                items.add(JOB);
                for(int i=0;i<jobList.jobs.size();i++){
                    items.add(jobList.jobs.get(i));
                }
                //this.list.addAll(jobFitList.jobs);
            }

//            if(list.size()==0){
//                if(JOB==null){
//                    JOB=new Job();
//                    JOB.setPositionName("全部职位");
//                }
//                list.add(JOB);
//                for(int i=0;i<jobFitList.jobs.size();i++){
//                    list.add(jobFitList.jobs.get(i));
//                }
//                //this.list.addAll(jobFitList.jobs);
//            }
            //GlobalProvider.getInstance().shangpingListDefault=mItems;
            adapter.notifyDataSetChanged();
            //adapterAll.notifyDataSetChanged();
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void LoadTalentList() {
        RequestParams params = new RequestParams();
        params.put("page", mPage);
        params.put("itemsPerPage", mItemsPerPage);
        params.put("id", GlobalProvider.getInstance().employerId);
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.TalentStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseTalentList(new String(responseBody));
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
    private void LoadTalentListByJobId(String jobId) {
        RequestParams params = new RequestParams();
        params.put("page", mPage);
        params.put("itemsPerPage", mItemsPerPage);
        params.put("id", GlobalProvider.getInstance().employerId);
        params.put("jobId", jobId);
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.TalentStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseTalentList(new String(responseBody));
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
    private void parseTalentList(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            TalentList jobFitList = (TalentList) objectMapper.readValue(jsonParser, TalentList.class);
            this.mItems.clear();
            this.mItems.addAll(jobFitList.talents);


//            if(list.size()==0){
//                if(JOB==null){
//                    JOB=new Job();
//                    JOB.setPositionName("全部职位");
//                }
//                list.add(JOB);
//                for(int i=0;i<jobFitList.jobs.size();i++){
//                    list.add(jobFitList.jobs.get(i));
//                }
//                //this.list.addAll(jobFitList.jobs);
//            }
            //GlobalProvider.getInstance().shangpingListDefault=mItems;
            mAdapter.notifyDataSetChanged();
            //adapterAll.notifyDataSetChanged();
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.EVALUATEINTENT:
                if (resultCode == RESULT_OK) {
                    LoadTalentList();
                }
                break;
        }
    }
//    public void doEvaluate(Talent talent){
//        Intent intent=new Intent(IntervieweeActivity.this,IntervieweeEvaluateActivity.class);
//        intent.putExtra("talent",talent);
//        this.startActivityForResult(intent, Constants.EVALUATEINTENT);
//    }

    private void initAction() {
        turn_left.setOnClickListener(this);
        turn.setOnClickListener(this);
        //interviewee_to_evaluate.setOnClickListener(this);
    }
    private void initView() {
        turn_left = (ImageView) findViewById(R.id.turn_left);
        //interviewee_to_evaluate = (TextView) findViewById(R.id.interviewee_to_evaluate);
        lv_pull_down_position_name= (ListView) findViewById(R.id.lv_pull_down_position_name);
        lv_pull_down_interview= (ListView) findViewById(R.id.lv_pull_down_interview);
        lv_main= (ListView) findViewById(R.id.lv_main);
        turn_img= (ImageView) findViewById(R.id.turn_img);
        turn= (LinearLayout) findViewById(R.id.turn);
        turn_text= (TextView) findViewById(R.id.turn_text);
        interview_layout= (LinearLayout) findViewById(R.id.interview_layout);
        interview_text= (TextView) findViewById(R.id.interview_text);
        interview_img= (ImageView) findViewById(R.id.interview_img);
    }
}
