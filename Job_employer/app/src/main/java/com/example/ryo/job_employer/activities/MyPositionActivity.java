package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.adapter.PositionAdapter;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.DoReleaseBody;
import com.example.ryo.job_employer.models.Employer;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Ryo on 2015/9/22.
 */
public class MyPositionActivity extends Activity implements View.OnClickListener {

    public ImageView add_position;
    public ImageView turn_left;
    //    public  LinearLayout edit;
//    public LinearLayout ad;
    public ListView lv;
    public PositionAdapter adapter;
    public List<Job> list;
    private Integer mPage;
    private Integer mItemsPerPage;
    public OnekeyShare onekeyShare;
    public SwipeRefreshLayout swiperefresh;
    public Boolean mNomore=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_position);
        ShareSDK.initSDK(getApplicationContext());
        initView();
        initAction();
        mPage = 1;
        mItemsPerPage = 10;
        list = new ArrayList<Job>();
        adapter = new PositionAdapter(this, list);
        lv.setAdapter(adapter);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNomore = false;
                mPage = 1;
                loadjobList();
//                if (GlobalProvider.getInstance().ShangpingHeaderLoadCategory.equals("")) {
//                    loadJobList();
//                } else {
//                    loadProduct(GlobalProvider.getInstance().ShangpingHeaderLoadCategory);
//                }
            }
        });
        lv.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (!mNomore && lv.getCount() != 0 && lv.getLastVisiblePosition() >= (lv.getCount() - 1)) {
                    loadMoreJobList();
                }
            }
        });
        loadjobList();
    }

    private void initAction() {
        add_position.setOnClickListener(this);
        turn_left.setOnClickListener(this);
//        edit.setOnClickListener(this);
//        ad.setOnClickListener(this);

    }

    private void initView() {
        add_position = (ImageView) findViewById(R.id.add_position);
        turn_left = (ImageView) findViewById(R.id.turn_left);
        lv = (ListView) findViewById(R.id.lv);
        swiperefresh= (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_position:
                Intent intent = new Intent(MyPositionActivity.this, EditPositionActivity.class);
                startActivityForResult(intent, Constants.EditJobIntent);
                break;
            case R.id.turn_left:
                finish();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.EditJobIntent:
                if (resultCode == RESULT_OK) {
                    loadjobList();
                }
                break;
            case Constants.TUIGUANGINTENT:
                if (resultCode == RESULT_OK) {
                    loadjobList();
                }
                break;
        }
    }

    private void loadjobList() {
        RequestParams params = new RequestParams();
        params.put("page", mPage);
        params.put("itemsPerPage", mItemsPerPage);
        params.put("employerId", GlobalProvider.getInstance().employerId);

        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.PositionStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseJobList(new String(responseBody));
                swiperefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.v("err", new String(responseBody));
                swiperefresh.setRefreshing(false);

            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }
public void loadMoreJobList(){
    mPage=mPage+1;
    RequestParams params = new RequestParams();
    params.put("page", mPage);
    params.put("itemsPerPage", mItemsPerPage);
    params.put("employerId", GlobalProvider.getInstance().employerId);

    GlobalProvider globalProvider = GlobalProvider.getInstance();
    globalProvider.get(this, Constants.PositionStr, params, new RequestListener() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            parseMoreJobList(new String(responseBody));
            //swiperefresh.setRefreshing(false);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            //Log.v("err", new String(responseBody));
            //swiperefresh.setRefreshing(false);

        }

        @Override
        public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

        }
    });
}
    public void parseMoreJobList(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobList joblist = (JobList) objectMapper.readValue(jsonParser, JobList.class);
            if(joblist.jobs.size()<mItemsPerPage){
                mNomore = true;
            }
            this.list.addAll(joblist.jobs);
            //GlobalProvider.getInstance().shangpingListDefault=mItems;
            adapter.notifyDataSetChanged();
            //loadme();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void parseJobList(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobList joblist = (JobList) objectMapper.readValue(jsonParser, JobList.class);
            this.list.clear();
            this.list.addAll(joblist.jobs);
            //GlobalProvider.getInstance().shangpingListDefault=mItems;
            adapter.notifyDataSetChanged();
            //do something
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePosition(String _id) {
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        String url_l = Constants.DeleteJobStr + "/" + _id;

        globalProvider.delete(this, url_l, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //String result_l = new String(responseBody);
                loadjobList();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Toast.makeText(this, new String(responseBody), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }

    public void doRelease(String id, int state) {
        DoReleaseBody body=new DoReleaseBody();
        body.setKey("release");
        body.setState(state);
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity = new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            String URL = Constants.PositionStr + "/" + id;
            globalProvider.put(this, URL, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(MyPositionActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
                    //parseLoginResult(new String(responseBody));
                    loadjobList();
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
    public void doShare(Job job){
        if(onekeyShare==null){
            onekeyShare=new OnekeyShare();
        }
        onekeyShare.setTitle(job.getPositionName());
        onekeyShare.setText(job.getIndustryCategory());
        onekeyShare.show(MyPositionActivity.this);
    }
}