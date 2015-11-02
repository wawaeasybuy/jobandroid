package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Candidate;
import com.mardin.job.models.CandidateUpBody;
import com.mardin.job.models.Resume;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by Ryo on 2015/9/14.
 */
public class PersonalEditDataActivity extends Activity implements View.OnClickListener {
    public TextView save;
    public LinearLayout turn_left;
    public EditText schoolName;
    public EditText name;
    public ImageView touxiang;
    public Candidate candidate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_edit_data);

        initView();
        initAction();
        Intent intent = this.getIntent();
        if(intent.getSerializableExtra("candidate")!=null){
            candidate= (Candidate) intent.getSerializableExtra("candidate");
            if(candidate.getSchoolName()!=null){schoolName.setText(candidate.getSchoolName());}
            if(candidate.getName()!=null){name.setText(candidate.getName());}
        }
    }
    private void initAction() {
        turn_left.setOnClickListener(this);
        save.setOnClickListener(this);
    }
    private void initView() {
        turn_left = (LinearLayout) findViewById(R.id.turn_left);
        save= (TextView) findViewById(R.id.save);
        schoolName= (EditText) findViewById(R.id.schoolName);
        name= (EditText) findViewById(R.id.name);
        touxiang= (ImageView) findViewById(R.id.touxiang);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.turn_left:
                finish();
                break;
            case  R.id.save:
                doUpdateResume();
                break;
        }
    }
    private void doUpdateResume() {
        CandidateUpBody CandidateUp=new CandidateUpBody();
        CandidateUp.setSchoolName(schoolName.getText().toString());
        CandidateUp.setName(name.getText().toString());

        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(CandidateUp);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            String URL= Constants.updateCanStr+"/"+candidate.get_id();
            globalProvider.put(this, URL, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(PersonalEditDataActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                    doResult();
                    //parseLoginResult(new String(responseBody));
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
    private void doResult() {
        this.setResult(RESULT_OK);
        this.finish();
    }
}
