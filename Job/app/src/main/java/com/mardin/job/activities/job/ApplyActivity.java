package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.MainActivityN;
import com.mardin.job.R;
import com.mardin.job.adapters.job.ApplyListAdapter;
import com.mardin.job.adapters.job.JobRecAdapter;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Apply;
import com.mardin.job.models.ApplyList;
import com.mardin.job.models.Candidate;
import com.mardin.job.models.Job;
import com.mardin.job.models.PublicResume;
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
 * Created by Administrator on 2015/11/26.
 */
public class ApplyActivity extends Activity implements View.OnClickListener{
    private ListView lv;
    private TextView textView;
    public List<PublicResume> mItems;
    public ApplyListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        initView();
        textView = (TextView)findViewById(R.id.move);
        initAction();
        mItems=new ArrayList<PublicResume>();
        adapter=new ApplyListAdapter(this,mItems);

//        lv.addHeaderView(headerView, null, true);
        lv.setAdapter(adapter);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), PositionDetailActivity.class);
//                if (position >= 1) {
//                    intent.putExtra("job", mItems.get(position - 1));
//                    getActivity().startActivity(intent);
//                }
//
//            }
//        });
//        lv.setHeaderDividersEnabled(false);
        LoadCandidateInfo();
    }

    private void LoadCandidateInfo() {
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        String Url=Constants.crePubResume+"/"+globalProvider.candidate.get_id();
        globalProvider.get(this, Url, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseInfo(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }

    private void parseInfo(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
           ApplyList list = (ApplyList) objectMapper.readValue(jsonParser, ApplyList.class);
           this.mItems.clear();
           this.mItems.addAll(list.resumes);
           adapter.notifyDataSetChanged();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        lv = (ListView)findViewById(R.id.apply_lv);

    }



    private void initAction() {
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.move:
                finish();
                break;
            default:
                break;
        }
    }
}
