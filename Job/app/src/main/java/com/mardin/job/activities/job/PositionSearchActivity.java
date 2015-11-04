package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.adapters.job.AddressSelectListAdapter;
import com.mardin.job.adapters.job.JobListAdapter;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Job;
import com.mardin.job.models.JobList;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryo on 2015/9/17.
 */
public class PositionSearchActivity extends Activity implements View.OnClickListener {
    public LinearLayout turn_left;
    public ListView lv_job;
    public List<Job> mItems;
    public JobListAdapter adapter;
    public int page;
    public int itemsPerPage;
    public LinearLayout address;
    public LinearLayout selectAddress;
    public boolean lv1_showing=false;
    public boolean lv2_showing=false;
    public boolean lv3_showing=false;

    public AddressSelectListAdapter mAdapter;
    public ListView lv1,lv2,lv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internships_position_search);
        initView();
        initAction();
        mItems=new ArrayList<Job>();
        adapter=new JobListAdapter(this,mItems);
        mAdapter=new AddressSelectListAdapter(this,Data());
        lv1.setAdapter(mAdapter);
        lv_job.setAdapter(adapter);
        page=1;
        itemsPerPage=10;
        selectAddress.setVisibility(View.GONE);
        lv1.setVisibility(View.GONE);
        lv2.setVisibility(View.GONE);
        lv3.setVisibility(View.GONE);

        lv_job.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PositionSearchActivity.this,PositionDetailActivity.class);
                intent.putExtra("job",mItems.get(position));
                startActivity(intent);
            }
        });

        LoadJobList();
    }
    public void LoadJobList(){
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("itemsPerPage", itemsPerPage);

        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.jobListUrlStr, params, new RequestListener() {
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
    private void parseJobList(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobList joblist = (JobList) objectMapper.readValue(jsonParser, JobList.class);
            this.mItems.clear();
            this.mItems.addAll(joblist.jobs);
            //GlobalProvider.getInstance().shangpingListDefault=mItems;
            adapter.notifyDataSetChanged();
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initView(){
        turn_left = (LinearLayout) findViewById(R.id.turn_left);
        lv_job= (ListView) findViewById(R.id.lv_job);
        address= (LinearLayout) findViewById(R.id.address);
        lv1= (ListView) findViewById(R.id.lv1);
        lv2= (ListView) findViewById(R.id.lv2);
        lv3= (ListView) findViewById(R.id.lv3);
        selectAddress= (LinearLayout) findViewById(R.id.selectAddress);
    }
    public void initAction(){
        turn_left.setOnClickListener(this);
        address.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.turn_left:
                finish();
                break;
            case R.id.address:
                if(lv1_showing)
                {
                  selectAddress.setVisibility(View.GONE);
                  lv1.setVisibility(View.GONE);
                }else {
                  selectAddress.setVisibility(View.VISIBLE);
                  lv1.setVisibility(View.VISIBLE);
                }
                lv1_showing=!lv1_showing;
                break;
        }
    }
    public List<String> Data(){
        List<String> list=new ArrayList<String>();
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        return list;
    }
}
