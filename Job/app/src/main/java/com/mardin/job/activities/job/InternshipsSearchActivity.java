package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
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
public class InternshipsSearchActivity extends Activity {
    public ListView lv_job_search;
    public JobListAdapter adapter;
    public int page;
    public int itemsPerPage;
    public List<Job> mItems;
    public String retrieval="";
    public EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_search);
        lv_job_search= (ListView) findViewById(R.id.lv_job_search);
        input= (EditText) findViewById(R.id.input);
        page=1;
        itemsPerPage=10;
        mItems=new ArrayList<Job>();
        adapter=new JobListAdapter(this,mItems);
        lv_job_search.setAdapter(adapter);
        lv_job_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(InternshipsSearchActivity.this,PositionDetailActivity.class);
                intent.putExtra("job",mItems.get(position));
                startActivity(intent);
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!input.getText().toString().equals("")) {
                    retrieval = input.getText().toString();
                }else{
                    retrieval="";
                }
                LoadRecJobList();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //LoadRecJobList();
    }
    public void LoadRecJobList(){
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("itemsPerPage", itemsPerPage);
        params.put("retrieval", retrieval);
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.jobListUrlStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseJobList(new String(responseBody));
//                noLogin.setVisibility(View.GONE);
//                lv_rec.setVisibility(View.VISIBLE);

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.v("err", new String(responseBody));
//                noLogin.setVisibility(View.VISIBLE);
//                lv_rec.setVisibility(View.GONE);
            }
            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }
    public void parseJobList(String json){
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
}