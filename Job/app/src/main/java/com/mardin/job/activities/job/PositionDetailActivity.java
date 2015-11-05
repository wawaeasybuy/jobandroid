package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by Ryo on 2015/9/17.
 */
public class PositionDetailActivity extends Activity {
    public Job job;
    public Button position_apply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internships_position_detail);
        position_apply= (Button) findViewById(R.id.position_apply);
        Intent intent = this.getIntent();
        if(intent.getSerializableExtra("job")!=null){
            job= (Job) intent.getSerializableExtra("job");
        }
        position_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 doApply();
            }
        });
        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void getJobInfo(){
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
            Job_delivered joblist = (Job_delivered) objectMapper.readValue(jsonParser, Job_delivered.class);


//            this.mItems.clear();
//            this.mItems.addAll(joblist.jobs);
//            //GlobalProvider.getInstance().shangpingListDefault=mItems;
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
                    Toast.makeText(PositionDetailActivity.this,"投递成功！", Toast.LENGTH_SHORT).show();
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
}
