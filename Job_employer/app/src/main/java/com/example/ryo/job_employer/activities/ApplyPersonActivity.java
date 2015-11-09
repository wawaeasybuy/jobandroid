package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.adapter.ApplyPersonAdapter;
import com.example.ryo.job_employer.adapter.PositionFitAllAdapter;
import com.example.ryo.job_employer.adapter.PositionFitHeaderAdapter;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.ApplyPersonList;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.models.JobFitList;
import com.example.ryo.job_employer.models.PublicResume;
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
public class ApplyPersonActivity extends Activity implements View.OnClickListener {
    public ImageView turn_left;
    public LinearLayout applyHeader_layout;
    public TextView applyHeader_text;
    public ImageView applyHeader_Img;
    public ListView lv_one;
    public ListView lv_all;
    public ListView lv_pull_down;

    public PositionFitHeaderAdapter adapter;
    public ApplyPersonAdapter adapterAll;
    // public PositionFitOneAdapter adapterOne;
    //public ArrayAdapter adapter;
    public List<Job> list;
    public List<PublicResume> mItems;
    public List<PublicResume> mItems_all;

    public Job JOB;
//    private Integer mPage;
//    private Integer mItemsPerPage;

    public String job="";
    public Boolean isShowing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_person);
        initView();
        initAction();
//        mPage = 1;
//        mItemsPerPage = 10;

        list=new ArrayList<Job>();
        mItems=new ArrayList<PublicResume>();
        mItems_all=new ArrayList<PublicResume>();

        adapter=new PositionFitHeaderAdapter(this,list);
        adapterAll=new ApplyPersonAdapter(this,mItems);
        //adapterOne=new PositionFitOneAdapter(this,mItems);

        lv_pull_down.setAdapter(adapter);
        lv_all.setAdapter(adapterAll);
        //lv_one.setAdapter(adapterOne);

        lv_all.setVisibility(View.VISIBLE);
        lv_pull_down.setVisibility(View.GONE);
        //lv_one.setVisibility(View.GONE);
        applyHeader_text.setText("全部职位");
        applyHeader_Img.setImageResource(R.drawable.turn_down);
        applyHeader_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShowing){
                    lv_pull_down.setVisibility(View.VISIBLE);
                    lv_all.setVisibility(View.GONE);
                    applyHeader_Img.setImageResource(R.drawable.turn_up);
                }else{
                    lv_pull_down.setVisibility(View.GONE);
                    lv_all.setVisibility(View.VISIBLE);
                    applyHeader_Img.setImageResource(R.drawable.turn_down);
                }
                isShowing=!isShowing;
            }
        });
        LoadApplyList();
    }
    public void initAction() {
        turn_left.setOnClickListener(this);
        lv_pull_down.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mItems.clear();
                if(position>0){
                    //job=list.get(position).get_id();
                    for(int i=0;i<mItems_all.size();i++){
                        if(list.get(position).get_id().equals(mItems_all.get(i)._job)){
                            mItems.add(mItems_all.get(i));
                        }
                    }
                    //applyHeader_text.setText(list.get(position-1).getPositionName());
                }else{
                    //job="";
                    for(int i=0;i<mItems_all.size();i++){
                        mItems.add(mItems_all.get(i));
                    }
                    //applyHeader_text.setText("全部职位");
                }
                applyHeader_text.setText(list.get(position).getPositionName());
                lv_pull_down.setVisibility(View.GONE);
                lv_all.setVisibility(View.VISIBLE);
                applyHeader_Img.setImageResource(R.drawable.turn_down);
                isShowing=false;
                adapterAll.notifyDataSetChanged();
                //LoadApplyList();
            }
        });
    }
    public void initView() {
        turn_left = (ImageView) findViewById(R.id.turn_left);
        lv_one= (ListView) findViewById(R.id.lv_one);
        lv_all= (ListView) findViewById(R.id.lv_all);
        lv_pull_down= (ListView) findViewById(R.id.lv_pull_down);
        applyHeader_layout= (LinearLayout) findViewById(R.id.applyHeader_layout);
        applyHeader_Img= (ImageView) findViewById(R.id.applyHeader_Img);
        applyHeader_text= (TextView) findViewById(R.id.applyHeader_text);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.turn_left:
                finish();
                break;
        }
    }
    public void LoadApplyList(){
//        RequestParams params = new RequestParams();
////        params.put("page", mPage);
////        params.put("itemsPerPage", mItemsPerPage);
////        params.put("id", GlobalProvider.getInstance().employerId);
//        if(!job.equals("")){
//            params.put("job",job);
//        }
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        String Url=Constants.ApplyPersonStr+"/"+GlobalProvider.getInstance().employerId;
        globalProvider.get(this,Url,new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseApplyList(new String(responseBody));
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
    public void parseApplyList(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            ApplyPersonList applyPersonList = (ApplyPersonList) objectMapper.readValue(jsonParser, ApplyPersonList.class);
//            this.mItems.clear();
//            this.mItems.addAll(applyPersonList.resumes);
            if(this.mItems_all.size()==0){
                this.mItems_all.clear();
                this.mItems_all.addAll(applyPersonList.resumes);
                this.mItems.addAll(applyPersonList.resumes);
            }
            if(list.size()==0){
                if(JOB==null){JOB=new Job();JOB.setPositionName("全部职位");}
                list.add(JOB);
                for(int i=0;i<applyPersonList.jobs.size();i++){
                    list.add(applyPersonList.jobs.get(i));
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
//    public void ApplyPersonUpdate(final Resume resume){
//        RequestParams params = new RequestParams();
//        params.put("id",GlobalProvider.getInstance().employerId );
//
//        GlobalProvider globalProvider = GlobalProvider.getInstance();
//        String Url=Constants.PositionFitUpdateStr+"/"+resume.get_id();
//        globalProvider.put(this, Url, params, new RequestListener() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                Intent intent=new Intent(ApplyPersonActivity.this,PersonalResumeActivity.class);
//                intent.putExtra("resume",resume);
//                startActivity(intent);
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                //Log.v("err", new String(responseBody));
//
//            }
//            @Override
//            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
//
//            }
//        });
//    }
    public void ApplyPersonIgnore(final String id){
        //params.put("_employer",GlobalProvider.getInstance().employerId);
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        String Url=Constants.ApplyPersonStr+"/"+id;
        globalProvider.delete(this, Url,new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mItems_all.clear();
                mItems.clear();
                list.clear();
                LoadApplyList();

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
