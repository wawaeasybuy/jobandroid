package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.adapter.TalentAdapter;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.models.JobFitList;
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

    public TalentAdapter adapter;
    public List<Talent> mItems;

    private Integer mPage;
    private Integer mItemsPerPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internships_interviewee);

        initView();
        initAction();
        mItems=new ArrayList<Talent>();
        adapter=new TalentAdapter(this,mItems);
        lv_main.setAdapter(adapter);
        mPage = 1;
        mItemsPerPage = 10;
        LoadTalentList();

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
            adapter.notifyDataSetChanged();
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
    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.turn_left:
              finish();
              break;
//          case  R.id.interviewee_to_evaluate:
//              Intent intent = new Intent( IntervieweeActivity.this, IntervieweeEvaluateActivity.class);
//              startActivity(intent);
//              break;
      }
    }
    private void initAction() {
        turn_left.setOnClickListener(this);
        //interviewee_to_evaluate.setOnClickListener(this);
    }
    private void initView() {
        turn_left = (ImageView) findViewById(R.id.turn_left);
        //interviewee_to_evaluate = (TextView) findViewById(R.id.interviewee_to_evaluate);
        lv_pull_down_position_name= (ListView) findViewById(R.id.lv_pull_down_position_name);
        lv_pull_down_interview= (ListView) findViewById(R.id.lv_pull_down_interview);
        lv_main= (ListView) findViewById(R.id.lv_main);
    }
}
