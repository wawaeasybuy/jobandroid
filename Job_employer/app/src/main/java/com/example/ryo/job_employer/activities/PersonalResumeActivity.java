package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.AddTalentBody;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.models.PublicResume;
import com.example.ryo.job_employer.models.Resume;
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
public class PersonalResumeActivity extends Activity implements View.OnClickListener {
    public ImageView turn_left;
    public Resume resume;
    public PublicResume Pubresume;
    public TextView name;
    public TextView birth;
    public TextView address;
    public TextView tel;
    public TextView add_talent;
    public TextView experience;
    public TextView selfEvaluation;
    public TextView internshipExprience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_resume);
        initView();
        initAction();
//        if(GlobalProvider.getInstance().isAllowToTalent){
//            add_talent.setVisibility(View.VISIBLE);
//        }else{
//            add_talent.setVisibility(View.GONE);
//        }
        Intent intent = this.getIntent();
        if(intent.getSerializableExtra("resume")!=null&&intent.getSerializableExtra("Pubresume")==null)
        {
            add_talent.setVisibility(View.GONE);
            resume = (Resume)intent.getSerializableExtra("resume");
            if(resume.getName()!=null){name.setText(resume.getName());}
            if(resume.getBirth()!=null){birth.setText(resume.getBirth().toString());}
            if(resume.getAddress()!=null){address.setText(resume.getAddress());}
            if(resume.getTel()!=null){tel.setText(resume.getTel());}
            if(resume.getExperience()!=null){experience.setText(resume.getExperience());}
            if(resume.getInternshipExprience()!=null){internshipExprience.setText(resume.getInternshipExprience());}
            if(resume.getSelfEvaluation()!=null){selfEvaluation.setText(resume.getSelfEvaluation());}
        }else{
            Pubresume = (PublicResume)intent.getSerializableExtra("Pubresume");
            if(Pubresume.getIsTalent()){
                add_talent.setVisibility(View.GONE);
            }else{
                add_talent.setVisibility(View.VISIBLE);
            }
            if(Pubresume.getName()!=null){name.setText(Pubresume.getName());}
            if(Pubresume.getBirth()!=null){birth.setText(Pubresume.getBirth().toString());}
            if(Pubresume.getAddress()!=null){address.setText(Pubresume.getAddress());}
            if(Pubresume.getTel()!=null){tel.setText(Pubresume.getTel());}
            if(Pubresume.getExperience()!=null){experience.setText(Pubresume.getExperience());}
            if(Pubresume.getInternshipExprience()!=null){internshipExprience.setText(Pubresume.getInternshipExprience());}
            if(Pubresume.getSelfEvaluation()!=null){selfEvaluation.setText(Pubresume.getSelfEvaluation());}



        }
    }
    private void initView() {
        turn_left = (ImageView) findViewById(R.id.turn_left);
        name= (TextView) findViewById(R.id.name);
        birth= (TextView) findViewById(R.id.birth);
        address= (TextView) findViewById(R.id.address);
        tel= (TextView) findViewById(R.id.tel);
        add_talent= (TextView) findViewById(R.id.add_talent);
        experience= (TextView) findViewById(R.id.experience);
        internshipExprience= (TextView) findViewById(R.id.internshipExprience);
        selfEvaluation= (TextView) findViewById(R.id.selfEvaluation);
    }
   public void initAction(){
       turn_left.setOnClickListener(this);
       add_talent.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               addToTalent();
           }
       });
    }
    private void addToTalent() {
        AddTalentBody body=new AddTalentBody();
        body.setId(Pubresume.get_id());
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            globalProvider.post(this, Constants.TalentStr, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    //parseLoginResult(new String(responseBody));
                    add_talent.setVisibility(View.GONE);
                    Toast.makeText(PersonalResumeActivity.this,"succese to add talent", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
   switch (v.getId()){
       case R.id.turn_left:
           finish();
           break;
   }
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
