package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.Employer;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by Ryo on 2015/9/22.
 */
public class EditPositionActivity extends Activity implements View.OnClickListener {
    public LinearLayout turn_left;
    public TextView save;
    public EditText positionName;
    public EditText industry;
    public EditText salary;
    public EditText requirement;
    public EditText positionCharacter;
    public Job job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_position);
        initView();
        initAction();
        Intent intent = this.getIntent();
        if(intent.getSerializableExtra("job")!=null)
        {
            job = (Job)intent.getSerializableExtra("job");
            if(job.getPositionName()!=null){positionName.setText(job.getPositionName());}
            if(job.getIndustryCategory()!=null){industry.setText(job.getIndustryCategory());}
            if(job.getRequirement()!=null){requirement.setText(job.getRequirement());}
            if(job.getPositionCharacter()!=null){positionCharacter.setText(job.getPositionCharacter());}
            salary.setText(job.getSalary()+"");
        }
    }
    private void initAction() {
        turn_left.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void initView() {
        turn_left = (LinearLayout) findViewById(R.id.turn_left);
        save= (TextView) findViewById(R.id.save);
        positionName= (EditText) findViewById(R.id.positionName);
        industry= (EditText) findViewById(R.id.industry);
        positionCharacter= (EditText) findViewById(R.id.positionCharacter);
        salary= (EditText) findViewById(R.id.salary);
        requirement= (EditText) findViewById(R.id.requirement);
    }
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.turn_left:
               dofinish();
               break;
           case R.id.save:
               doSave();
               break;
       }
    }
    public void dofinish(){
        new AlertDialog.Builder(this)
                .setMessage("是否保存当前修改？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(job!=null){
                            job.setPositionName(positionName.getText().toString());
                            job.setIndustryCategory(industry.getText().toString());
                            job.setSalary(Integer.parseInt(salary.getText().toString()));
                            job.setRequirement(requirement.getText().toString());
                            job.setPositionCharacter(positionCharacter.getText().toString());
                            update();
                        }else{
                            job=new Job();
                            job.setPositionName(positionName.getText().toString());
                            job.setIndustryCategory(industry.getText().toString());
                            job.setSalary(Integer.parseInt(salary.getText().toString()));
                            job.setRequirement(requirement.getText().toString());
                            job.setPositionCharacter(positionCharacter.getText().toString());
                            createJob();
                        }
                    }
                }).setNegativeButton("取消",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            doResult();
            }
        }).show();
    }
    public void doSave(){
           if(job!=null){
              job.setPositionName(positionName.getText().toString());
               job.setIndustryCategory(industry.getText().toString());
              job.setSalary(Integer.parseInt(salary.getText().toString()));
              job.setRequirement(requirement.getText().toString());
              job.setPositionCharacter(positionCharacter.getText().toString());
              update();
          }else{
              job=new Job();
              job.setPositionName(positionName.getText().toString());
              job.setIndustryCategory(industry.getText().toString());
              job.setSalary(Integer.parseInt(salary.getText().toString()));
              job.setRequirement(requirement.getText().toString());
              job.setPositionCharacter(positionCharacter.getText().toString());
              createJob();
          }
    }
    public void update(){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(job);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            String URL= Constants.UpdateJobStr+"/"+job.get_id();
            globalProvider.put(this, URL, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(EditPositionActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
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
    public void createJob(){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(job);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            globalProvider.post(this, Constants.CreateJobStr, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(EditPositionActivity.this, "创建成功！", Toast.LENGTH_SHORT).show();
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
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
                v.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {

                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
