package com.mardin.job.fragments.job;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.activities.job.EditResumeActivity;
import com.mardin.job.activities.job.FunctionScoreActivity;
import com.mardin.job.activities.job.LoginActivity;
import com.mardin.job.activities.job.PersonalEditDataActivity;
import com.mardin.job.activities.job.PersonalSettingActivity;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Candidate;
import com.mardin.job.models.Resume;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;


/**
 * Created by Ryo on 2015/9/27.
 */
public class PersonalCenterFragment extends Fragment implements View.OnClickListener {

    public LinearLayout ID;
    public LinearLayout noLoginID;
    public ImageView turn_right;
    public ImageView addResume;
    public TextView personal_setting;
    public TextView personalName;
    public TextView name;
    public TextView schoolName;
    public TextView resume_delete;
    public TextView updateTime;

    public LinearLayout main;
    public LinearLayout noLogin;
    public LinearLayout resume_edit;
    public LinearLayout resume_release;
    public LinearLayout change;
    public LinearLayout resumeLayout;

    public LinearLayout ResumeLayout;
    public LinearLayout noResumeLayout;
    public ImageView createResume;


    public Boolean isLoging;
    public Resume resume;
    public Candidate candidate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_personal_center_logined, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initAction();
        LoadCandidateInfo();

    }
    public void LoadCandidateInfo(){
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(getActivity(), Constants.regCanStr, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                change.setVisibility(View.VISIBLE);
                resumeLayout.setVisibility(View.VISIBLE);
                ID.setVisibility(View.VISIBLE);
                noLogin.setVisibility(View.GONE);
                isLoging = true;
                parseInfo(new String(responseBody));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                change.setVisibility(View.GONE);
                resumeLayout.setVisibility(View.GONE);
                ID.setVisibility(View.GONE);
                noResumeLayout.setVisibility(View.GONE);
                noLogin.setVisibility(View.VISIBLE);
                isLoging = false;
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
            Candidate candidate = (Candidate) objectMapper.readValue(jsonParser, Candidate.class);
            GlobalProvider.getInstance().candidate=candidate;
            this.candidate=candidate;
            if(candidate.getName()!=null){
                personalName.setText(candidate.getName());
                name.setText(candidate.getName());
            }
            if(candidate.getSchoolName()!=null){schoolName.setText(candidate.getSchoolName());}
            if(candidate.resume!=null){
                this.resume=candidate.resume;
                GlobalProvider.getInstance().resume=candidate.resume;
                noResumeLayout.setVisibility(View.GONE);
                resumeLayout.setVisibility(View.VISIBLE);
                if(candidate.resume.getUpdateEdit()!=null){
                    updateTime.setText(ConverToString(candidate.resume.getUpdateEdit()));
                }
            }else{
                GlobalProvider.getInstance().resume=new Resume();
                noResumeLayout.setVisibility(View.VISIBLE);
                resumeLayout.setVisibility(View.GONE);
            }
//            adapter.notifyDataSetChanged();
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void doDelete(){
        GlobalProvider globalProvider=GlobalProvider.getInstance();
        String Url=Constants.createResumeStr+"/"+resume.get_id();
        globalProvider.delete(getActivity(),Url, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                LoadCandidateInfo();
                Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }
    public void initView(){
        ID= (LinearLayout) getActivity().findViewById(R.id.ID);
        noLoginID= (LinearLayout) getActivity().findViewById(R.id.noLoginID);
        turn_right= (ImageView) getActivity().findViewById(R.id.turn_right);
        addResume= (ImageView) getActivity().findViewById(R.id.addResume);
        personal_setting= (TextView) getActivity().findViewById(R.id.personal_setting);
        personalName= (TextView) getActivity().findViewById(R.id.personal_name);
        name= (TextView) getActivity().findViewById(R.id.name);
        schoolName= (TextView) getActivity().findViewById(R.id.schoolName);
        resume_delete= (TextView) getActivity().findViewById(R.id.resume_delete);
        updateTime= (TextView) getActivity().findViewById(R.id.update_time);
        resume_edit= (LinearLayout) getActivity().findViewById(R.id.resume_edit);
        resume_release= (LinearLayout) getActivity().findViewById(R.id.resume_release);
        main= (LinearLayout) getActivity().findViewById(R.id.main);
        noLogin= (LinearLayout) getActivity().findViewById(R.id.noLogin);
        change= (LinearLayout) getActivity().findViewById(R.id.change);
        resumeLayout= (LinearLayout) getActivity().findViewById(R.id.resume);

        //ResumeLayout= (LinearLayout) getActivity().findViewById(R.id.resume);
        noResumeLayout= (LinearLayout) getActivity().findViewById(R.id.noResume);
        createResume= (ImageView) getActivity().findViewById(R.id.createResume);
    }
    public void initAction(){
        ID.setOnClickListener(this);
        noLoginID.setOnClickListener(this);
        turn_right.setOnClickListener(this);
        personal_setting.setOnClickListener(this);
        resume_edit.setOnClickListener(this);
        addResume.setOnClickListener(this);
        resume_release.setOnClickListener(this);
        createResume.setOnClickListener(this);
        resume_delete.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ID:
                Intent intent = new Intent(getActivity(), PersonalEditDataActivity.class);
                intent.putExtra("candidate", candidate);
                getActivity().startActivityForResult(intent, Constants.UPECANDIDATE_INTENT);
                //startActivity(intent);
                break;
            case R.id.noLoginID:
                Intent intent_login = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivityForResult(intent_login, Constants.LoginIntent);
                break;
            case R.id.turn_right:
                Intent intent1=new Intent(getActivity(),  EditResumeActivity.class);
                //intent1.putExtra("resume", resume);
                getActivity().startActivityForResult(intent1, Constants.UPDATERESUME);
                break;
            case R.id.personal_setting:
                Intent intent2=new Intent(getActivity(),PersonalSettingActivity.class);
                getActivity().startActivityForResult(intent2, Constants.GOTOLOGOUT);
                break;
            case R.id.resume_edit:
                Intent intent3=new Intent(getActivity(),EditResumeActivity.class);
                getActivity().startActivityForResult(intent3, Constants.UPDATERESUME);
                break;
            case R.id.addResume:
                Intent intent5=new Intent(getActivity(),EditResumeActivity.class);
                startActivity(intent5);
                break;
            case R.id.resume_release:
                Intent intent4=new Intent(getActivity(),FunctionScoreActivity.class);
                startActivity(intent4);
                break;
            case R.id.createResume:
                Intent intent6=new Intent(getActivity(),  EditResumeActivity.class);
                //intent1.putExtra("resume", resume);
                getActivity().startActivityForResult(intent6, Constants.UPDATERESUME);
                break;
            case R.id.resume_delete:
                doDelete();
                break;
        }
    }
}
