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


    public View login_id_layout;
    public View no_login_id_layout;
    public View no_resume_layout;
    public View resume_layout;
    public View change_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.activity_personal_center_logined, container, false);

        return view;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initAction();
        ID.removeAllViews();
        ID.addView(no_login_id_layout);
        ID.addView(no_resume_layout);
        LoadCandidateInfo();

    }
    public void LoadCandidateInfo(){
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(getActivity(), Constants.regCanStr, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ID.removeAllViews();
                ID.addView(login_id_layout);
                //ID.addView(resume_layout);
//                change.setVisibility(View.VISIBLE);
//                resumeLayout.setVisibility(View.VISIBLE);
//                ID.setVisibility(View.VISIBLE);
//                noLogin.setVisibility(View.GONE);
//                isLoging = true;
                parseInfo(new String(responseBody));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                change.setVisibility(View.GONE);
//                resumeLayout.setVisibility(View.GONE);
//                ID.setVisibility(View.GONE);
//                noResumeLayout.setVisibility(View.GONE);
//                noLogin.setVisibility(View.VISIBLE);
//                isLoging = false;
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
                //personalName.setText(candidate.getName());
                name.setText(candidate.getName());
            }
            if(candidate.getSchoolName()!=null){schoolName.setText(candidate.getSchoolName());}
            if(candidate.resume!=null){
                this.resume=candidate.resume;
                GlobalProvider.getInstance().resume=candidate.resume;
                ID.addView(resume_layout);
                if(candidate.getName()!=null){
                    //personalName.setText(candidate.getName());
                    personalName.setText(candidate.getName());
                }
//                noResumeLayout.setVisibility(View.GONE);
//                resumeLayout.setVisibility(View.VISIBLE);
                if(candidate.resume.getUpdateEdit()!=null){
                    updateTime.setText(ConverToString(candidate.resume.getUpdateEdit()));
                }
            }else{
                ID.addView(no_resume_layout);
                GlobalProvider.getInstance().resume=new Resume();
//                noResumeLayout.setVisibility(View.VISIBLE);
//                resumeLayout.setVisibility(View.GONE);
            }
            ID.addView(change_layout);
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

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        login_id_layout= inflater.inflate(R.layout.login_id, null);
        no_login_id_layout=inflater.inflate(R.layout.no_login_id, null);
        no_resume_layout= inflater.inflate(R.layout.no_resume, null);
        resume_layout= inflater.inflate(R.layout.resume, null);
        change_layout=inflater.inflate(R.layout.change, null);

        ID= (LinearLayout) getActivity().findViewById(R.id.ID);
        //noLoginID= (LinearLayout) getActivity().findViewById(R.id.noLoginID);
        turn_right= (ImageView) resume_layout.findViewById(R.id.turn_right);
        //addResume= (ImageView) getActivity().findViewById(R.id.addResume);
        personal_setting= (TextView) getActivity().findViewById(R.id.personal_setting);
        personalName= (TextView)resume_layout.findViewById(R.id.personal_name);
        name= (TextView) login_id_layout.findViewById(R.id.name);
        schoolName= (TextView) login_id_layout.findViewById(R.id.schoolName);
        resume_delete= (TextView) resume_layout.findViewById(R.id.resume_delete);
        updateTime= (TextView) resume_layout.findViewById(R.id.update_time);
        resume_edit= (LinearLayout) resume_layout.findViewById(R.id.resume_edit);
        resume_release= (LinearLayout) resume_layout.findViewById(R.id.resume_release);
        //main= (LinearLayout) getActivity().findViewById(R.id.main);
        //noLogin= (LinearLayout) getActivity().findViewById(R.id.noLogin);
        change= (LinearLayout) change_layout.findViewById(R.id.change);
        //resumeLayout= (LinearLayout) getActivity().findViewById(R.id.resume);

        //ResumeLayout= (LinearLayout) getActivity().findViewById(R.id.resume);
        //noResumeLayout= (LinearLayout) getActivity().findViewById(R.id.noResume);
        createResume= (ImageView) no_resume_layout.findViewById(R.id.createResume);



    }
    public void initAction(){
        login_id_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonalEditDataActivity.class);
                intent.putExtra("candidate", candidate);
                getActivity().startActivityForResult(intent, Constants.UPECANDIDATE_INTENT);
            }
        });
        no_login_id_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_login = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivityForResult(intent_login, Constants.LoginIntent);
            }
        });
        turn_right.setOnClickListener(this);
        personal_setting.setOnClickListener(this);
        resume_edit.setOnClickListener(this);
        //addResume.setOnClickListener(this);
        resume_release.setOnClickListener(this);
        createResume.setOnClickListener(this);
        resume_delete.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
//            case R.id.addResume:
//                Intent intent5=new Intent(getActivity(),EditResumeActivity.class);
//                startActivity(intent5);
//                break;
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
