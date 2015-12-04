package com.mardin.job.fragments.job;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.mardin.job.activities.job.ApplyActivity;
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
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

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
    public LayoutInflater inflater;

    public View login_id_layout;
    public View no_login_id_layout;
    public View no_resume_layout;
    public View resume_layout;
    public View change_layout;

    public LinearLayout message_layout;
    public TextView message_name;
    public TextView message_date;
    public View message_view;

    public ImageView img_release;
    public TextView release;

    public TextView resume_delivery;

    public boolean isLogined;
    public TextView deliveryResource;
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
                message_layout.removeAllViews();
                personal_setting.setVisibility(View.VISIBLE);
                isLogined=true;
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
                personal_setting.setVisibility(View.GONE);
                isLogined=false;
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
            if(candidate.getSchoolName()!=null){
                schoolName.setText(candidate.getSchoolName());
            }else{
                schoolName.setText("请填写大学名字");
            }
            if(candidate.resume!=null){

                if(candidate.resume.getBeOpen()==null||!candidate.resume.getBeOpen()){
                    candidate.resume.setBeOpen(false);
                    img_release.setImageResource(R.drawable.unlocked);
                    release.setText("公开");
                }else{
                    img_release.setImageResource(R.drawable.locked);
                    release.setText("已公开");
                }
                if(candidate.resume.isdelivered){
                    resume_delivery.setText("可投递");
                }else{
                    resume_delivery.setText("不可投递");
                }
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

                if(resume.employer.size()>0){
                    for(int i=0;i<resume.employer.size();i++) {
                        if (resume.employer.get(i).getName() != null) {
                            message_view = inflater.inflate(R.layout.message_layout, null);
                            message_name = (TextView) message_view.findViewById(R.id.message_name);
                            message_date = (TextView) message_view.findViewById(R.id.message_date);
                            message_name.setText(resume.employer.get(i).getName() + "查看了你的简历");
                            message_date.setText(ConverToString(resume.employer.get(i).getTimeUpdate()));
                            message_layout.addView(message_view);
                        }
                    }
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
    public void doRelease(){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        if(resume.getBeOpen()){
            resume.setBeOpen(false);
        }else{
            resume.setBeOpen(true);
        }
        try {
            json = ow.writeValueAsString(resume);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            globalProvider.put(getActivity(), Constants.createResumeStr, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    LoadCandidateInfo();
                    Toast.makeText(getActivity(), "更改成功！", Toast.LENGTH_SHORT).show();
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
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }
    public void initView(){

        inflater = LayoutInflater.from(getActivity());
        login_id_layout= inflater.inflate(R.layout.login_id, null);
        no_login_id_layout=inflater.inflate(R.layout.no_login_id, null);
        no_resume_layout= inflater.inflate(R.layout.no_resume, null);
        resume_layout= inflater.inflate(R.layout.resume, null);
        change_layout=inflater.inflate(R.layout.change, null);
        //
        //message_view=inflater.inflate(R.layout.message_layout,null);

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

        img_release= (ImageView) resume_layout.findViewById(R.id.img_release);
        release= (TextView) resume_layout.findViewById(R.id.release);

        message_layout= (LinearLayout) change_layout.findViewById(R.id.message);

        resume_delivery= (TextView) resume_layout.findViewById(R.id.resume_delivery);
        deliveryResource= (TextView) resume_layout.findViewById(R.id.deliveryResource);

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
        deliveryResource.setOnClickListener(this);
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
//                Intent intent4=new Intent(getActivity(),FunctionScoreActivity.class);
//                startActivity(intent4);
                if(GlobalProvider.getInstance().candidate.resume.isdelivered()){
                    doRelease();
                }else{
                    new AlertDialog.Builder(getActivity())
                            .setMessage("您的简历未完善，暂时不能公开！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.i("alertdialog", " 保存数据");

                                }

                            }).show();
                }

                break;
            case R.id.createResume:
                if(isLogined){
                    Intent intent6=new Intent(getActivity(),  EditResumeActivity.class);
                    //intent1.putExtra("resume", resume);
                    getActivity().startActivityForResult(intent6, Constants.UPDATERESUME);
                }else{
                    new AlertDialog.Builder(getActivity())
                            .setMessage("您还未登录，请先登录！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.i("alertdialog", " 保存数据");

                                }
                            }).show();
                }
                break;
            case R.id.resume_delete:
                doDelete();
                break;
            case R.id.deliveryResource:
                Intent intent=new Intent(getActivity(), ApplyActivity.class);
                startActivity(intent);
        }
    }
}
