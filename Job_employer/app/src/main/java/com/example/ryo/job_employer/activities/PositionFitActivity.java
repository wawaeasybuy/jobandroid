package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.adapter.PositionFitAllAdapter;
import com.example.ryo.job_employer.adapter.PositionFitHeaderAdapter;
import com.example.ryo.job_employer.adapter.PositionFitOneAdapter;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.models.JobFitList;
import com.example.ryo.job_employer.models.JobList;
import com.example.ryo.job_employer.models.Resume;
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
public class PositionFitActivity extends Activity implements View.OnClickListener {
    public ImageView turn_left;
    public LinearLayout fitHeader_layout;
    public TextView fitHeader_text;
    public ImageView fitHeader_Img;
    public ListView lv_one;
    public ListView lv_all;
    public ListView lv_pull_down;

    public PositionFitHeaderAdapter adapter;
    public PositionFitAllAdapter adapterAll;
   // public PositionFitOneAdapter adapterOne;
    //public ArrayAdapter adapter;
    public List<Job> list;
    public List<Resume> mItems;

    private Integer mPage;
    private Integer mItemsPerPage;

    public String job="";
    public Boolean isShowing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_fit);
        initView();
        initAction();
        mPage = 1;
        mItemsPerPage = 10;

        list=new ArrayList<Job>();
        mItems=new ArrayList<Resume>();

        adapter=new PositionFitHeaderAdapter(this,list);
        adapterAll=new PositionFitAllAdapter(this,mItems);
        //adapterOne=new PositionFitOneAdapter(this,mItems);

        lv_pull_down.setAdapter(adapter);
        lv_all.setAdapter(adapterAll);
        //lv_one.setAdapter(adapterOne);

        lv_all.setVisibility(View.VISIBLE);
        lv_pull_down.setVisibility(View.GONE);
        //lv_one.setVisibility(View.GONE);
        fitHeader_text.setText("全部职位");
        fitHeader_Img.setImageResource(R.drawable.turn_down);
        fitHeader_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShowing){
                    lv_pull_down.setVisibility(View.VISIBLE);
                    fitHeader_Img.setImageResource(R.drawable.turn_up);
                }else{
                    lv_pull_down.setVisibility(View.GONE);
                    fitHeader_Img.setImageResource(R.drawable.turn_down);
                }
                isShowing=!isShowing;
            }
        });
        LoadResumeList();
    }
    private void initAction() {
        turn_left.setOnClickListener(this);
        lv_pull_down.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                 job=list.get(position-1).getIndustryCategory();
                 fitHeader_text.setText(list.get(position-1).getPositionName());
                }else{
                 job="";
                 fitHeader_text.setText("全部职位");
                }
                lv_pull_down.setVisibility(View.GONE);
                fitHeader_Img.setImageResource(R.drawable.turn_down);
                isShowing=false;
                LoadResumeList();
            }
        });
    }
    private void initView() {
        turn_left = (ImageView) findViewById(R.id.turn_left);
        lv_one= (ListView) findViewById(R.id.lv_one);
        lv_all= (ListView) findViewById(R.id.lv_all);
        lv_pull_down= (ListView) findViewById(R.id.lv_pull_down);
        fitHeader_layout= (LinearLayout) findViewById(R.id.fitHeader_layout);
        fitHeader_Img= (ImageView) findViewById(R.id.fitHeader_Img);
        fitHeader_text= (TextView) findViewById(R.id.fitHeader_text);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.turn_left:
                finish();
                break;
        }
    }
    public void LoadResumeList(){
        RequestParams params = new RequestParams();
        params.put("page", mPage);
        params.put("itemsPerPage", mItemsPerPage);
        params.put("id", GlobalProvider.getInstance().employerId);
        if(!job.equals("")){
            params.put("job",job);
        }
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.PositionFitStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseResumeList(new String(responseBody));
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
    private void parseResumeList(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobFitList jobFitList = (JobFitList) objectMapper.readValue(jsonParser, JobFitList.class);
            this.mItems.clear();
            this.mItems.addAll(jobFitList.resumes);

            if(list.size()==0){
                for(int i=0;i<jobFitList.jobs.size();i++){
                    list.add(jobFitList.jobs.get(i));
                }
                //this.list.addAll(jobFitList.jobs);
            }
            //GlobalProvider.getInstance().shangpingListDefault=mItems;
            adapter.notifyDataSetChanged();
            adapterAll.notifyDataSetChanged();
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void PositionFitUpdate(final Resume resume){
        RequestParams params = new RequestParams();
        params.put("id",GlobalProvider.getInstance().employerId );

        GlobalProvider globalProvider = GlobalProvider.getInstance();
        String Url=Constants.PositionFitUpdateStr+"/"+resume.get_id();
        globalProvider.put(this, Url, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Intent intent=new Intent(PositionFitActivity.this,PersonalResumeActivity.class);
                intent.putExtra("resume",resume);
                startActivity(intent);
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
    public void PositionFitIgnore(final String id){
        RequestParams params = new RequestParams();
        params.put("id",id );
        params.put("_employer",GlobalProvider.getInstance().employerId);

        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.put(this, Constants.PositionFitIgnoreStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                LoadResumeList();
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
