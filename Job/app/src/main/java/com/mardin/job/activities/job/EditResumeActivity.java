package com.mardin.job.activities.job;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Resume;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;
import java.util.List;


/**
 * Created by Ryo on 2015/9/14.
 */
public class EditResumeActivity extends Activity implements View.OnClickListener {
    public LinearLayout turn_left;
    public LinearLayout personal_base_info;
    public LinearLayout job_intention;
    public LinearLayout education_info;
    public LinearLayout important_info;

    public TextView name;
    public TextView tel;
    public TextView address;
    public TextView birth;

    public TextView expectedIndustry;
    public TextView expectedPosition;
    public TextView expectedAddress;

    public TextView schoolName;
    public TextView professional;
    public TextView graduationTime;
    public TextView grade;
    public TextView internshipExprience;

    public TextView selfEvaluation;
    public TextView experience;
    public TextView works;

    public TextView personal_base_info_nofill;
    public TextView job_intention_nofill;
    public TextView education_info_nofill;
    public TextView important_info_nofill;

    public TextView clickToFillBaseInfo;
    public TextView clickToFillJobIntent;
    public TextView clickToFillEducationInfo;
    public TextView clickToFillImportanteInfo;

    public  LinearLayout no_personal_base_info;
    public LinearLayout no_job_intention;
    public LinearLayout no_education_info;
    public LinearLayout no_important_info;



    public Resume resume;
    public TextView save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_resume_initial);
        initView();
        initAction();
        doUpdateResume();
    }
    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.turn_left:
            doExist();
            break;
        case R.id.no_personal_base_info:
            Intent intent = new Intent( EditResumeActivity.this,BaseDataActivity.class);
            startActivityForResult(intent, Constants.UPDATERESUMEBASEINFO);
            break;
        case R.id.no_job_intention:
            Intent intent1 = new Intent( EditResumeActivity.this,JobIntentionActivity.class);
            startActivityForResult(intent1, Constants.UPDATERESUMEINTEND);
            break;
        case R.id.no_education_info:
            Intent intent2 = new Intent( EditResumeActivity.this,EducationInfoActivity.class);
            startActivityForResult(intent2, Constants.UPDATERESUMEDUCATION);
            break;
        case R.id.no_important_info:
            Intent intent3 = new Intent( EditResumeActivity.this,ImportantInfoActivity.class);
            startActivityForResult(intent3, Constants.UPDATERESUMEIMPORTANTE);
            break;

        case R.id.personal_base_info_nofill:
            Intent intent4 = new Intent( EditResumeActivity.this,BaseDataActivity.class);
            startActivityForResult(intent4, Constants.UPDATERESUMEBASEINFO);
            break;
        case R.id.job_intention_nofill:
            Intent intent5 = new Intent( EditResumeActivity.this,JobIntentionActivity.class);
            startActivityForResult(intent5, Constants.UPDATERESUMEINTEND);
            break;
        case R.id.education_info_nofill:
            Intent intent6 = new Intent( EditResumeActivity.this,EducationInfoActivity.class);
            startActivityForResult(intent6, Constants.UPDATERESUMEDUCATION);
            break;
        case R.id.important_info_nofill:
            Intent intent7 = new Intent( EditResumeActivity.this,ImportantInfoActivity.class);
            startActivityForResult(intent7, Constants.UPDATERESUMEIMPORTANTE);
            break;
        case R.id.save:
            doExist();
            break;

    }
    }
    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }
    public void doExist(){
        if(adjustToSave()){
            new AlertDialog.Builder(this)
                    .setMessage("简历可投递，是否保存当前编辑？")
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doResult();
                        }
                    })
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            GlobalProvider.getInstance().resume.setIsdelivered(true);
                            doSave();

                        }
                    }).show();
        }else{
            new AlertDialog.Builder(this)
                    .setMessage("简历不可投递，还有待完善，是否保存？")
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            doResult();
                        }
                    })
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            GlobalProvider.getInstance().resume.setIsdelivered(false);
                            doSave();
                        }
                    }).show();
        }
    }
    public void doSave(){
        Resume resumeUpdate=GlobalProvider.getInstance().resume;
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(resumeUpdate);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            if(resumeUpdate.get_id()!=null){
                String Url=Constants.createResumeStr+"/"+resumeUpdate.get_id();
                globalProvider.put(this, Url, entity, "application/json", new RequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(EditResumeActivity.this,"保存成功！", Toast.LENGTH_SHORT).show();
                        doResult();
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
            }else{
                String Url=Constants.createResumeStr+"/"+GlobalProvider.getInstance().candidate.get_id();
                globalProvider.post(this, Url, entity, "application/json", new RequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(EditResumeActivity.this, "创建成功！", Toast.LENGTH_SHORT).show();
                        doResult();
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
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void doResult(){
        this.setResult(Activity.RESULT_OK);
        this.finish();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.UPDATERESUMEBASEINFO:
                if (resultCode == RESULT_OK) {
                    doUpdateResume();
                }
                break;
            case Constants.UPDATERESUMEINTEND:
                if (resultCode == RESULT_OK) {
                    doUpdateResume();
                }
                break;
            case Constants.UPDATERESUMEDUCATION:
                if (resultCode == RESULT_OK) {
                    doUpdateResume();
                }
                break;
            case Constants.UPDATERESUMEIMPORTANTE:
                if (resultCode == RESULT_OK) {
                    doUpdateResume();
                }
                break;
        }
    }

    public void doUpdateResume(){
        resume= GlobalProvider.getInstance().resume;
        if((resume.getName()!=null&&!resume.getName().equals(""))||(resume.getTel()!=null&&!resume.getTel().equals(""))||(resume.getAddress()!=null&&!resume.getAddress().equals(""))||(resume.getBirth()!=null&&!resume.getBirth().equals(""))){
            no_personal_base_info.setVisibility(View.GONE);
            personal_base_info.setVisibility(View.VISIBLE);
            if(resume.getName()!=null&&!resume.getName().equals("")){name.setText("姓名: "+resume.getName());}else{name.setText("姓名: 未填写");}
            if(resume.getTel()!=null&&!resume.getTel().equals("")){tel.setText("联系方式: "+resume.getTel());}else{tel.setText("联系方式: 未填写");}
            if(resume.getAddress()!=null&&!resume.getAddress().equals("")){address.setText("居住地: "+resume.getAddress());}else{address.setText("居住地: 未填写");}
            if(resume.getBirth()!=null&&!resume.getBirth().equals("")){birth.setText("出生年月: "+resume.getBirth());}else{birth.setText("出生年月: 未填写");}

            if(resume.getName()!=null&&!resume.getName().equals("")&&resume.getTel()!=null&&!resume.getTel().equals("")&&resume.getAddress()!=null&&!resume.getAddress().equals("")&&resume.getBirth()!=null&&!resume.getBirth().equals("")){
                personal_base_info_nofill.setText("已完善");
                clickToFillBaseInfo.setText("(点击修改)");
                //personal_base_info_nofill.setClickable(true);
            }else{
                personal_base_info_nofill.setText("需完善");
                clickToFillBaseInfo.setText("(点击完善)");
                //personal_base_info_nofill.setClickable(true);
            }

        }else{
            no_personal_base_info.setVisibility(View.VISIBLE);
            personal_base_info.setVisibility(View.GONE);
        }
        if((resume.getExpectedIndustry()!=null&&!resume.getExpectedIndustry().equals(""))||(resume.getExpectedPosition()!=null&&!resume.getExpectedPosition().equals(""))||(resume.getExpectedAddress()!=null&&!resume.getExpectedAddress().equals(""))){
            no_job_intention.setVisibility(View.GONE);
            job_intention.setVisibility(View.VISIBLE);
            if(resume.getExpectedIndustry()!=null&&!resume.getExpectedIndustry().equals("")){expectedIndustry.setText("期望行业: "+resume.getExpectedIndustry());}else{expectedIndustry.setText("期望行业: 未填写");}
            if(resume.getExpectedPosition()!=null&&!resume.getExpectedPosition().equals("")){expectedPosition.setText("期望职位: "+resume.getExpectedPosition());}else{expectedPosition.setText("期望职位: 未填写");}
            if(resume.getExpectedAddress()!=null&&!resume.getExpectedAddress().equals("")){expectedAddress.setText("期望工作地点: "+resume.getExpectedAddress());}else{expectedAddress.setText("期望工作地点: 未填写");}

            if(resume.getExpectedIndustry()!=null&&!resume.getExpectedIndustry().equals("")&&resume.getExpectedPosition()!=null&&!resume.getExpectedPosition().equals("")&&resume.getExpectedAddress()!=null&&!resume.getExpectedAddress().equals("")){
                job_intention_nofill.setText("已完善");
                clickToFillJobIntent.setText("(点击修改)");
                //job_intention_nofill.setClickable(false);
            }else{
                job_intention_nofill.setText("需完善");
                clickToFillJobIntent.setText("(点击完善)");
                //job_intention_nofill.setClickable(true);
            }
        }else{
            no_job_intention.setVisibility(View.VISIBLE);
            job_intention.setVisibility(View.GONE);
        }
        if((resume.getSchoolName()!=null&&!resume.getSchoolName().equals(""))||(resume.getProfessional()!=null&&!resume.getProfessional().equals(""))||(resume.getGraduationTime()!=null&&!resume.getGraduationTime().equals(""))||(resume.getGrade()!=null&&!resume.getGrade().equals(""))||(resume.getInternshipExprience()!=null&&!resume.getInternshipExprience().equals(""))){
            no_education_info.setVisibility(View.GONE);
            education_info.setVisibility(View.VISIBLE);
            if(resume.getSchoolName()!=null&&!resume.getSchoolName().equals("")){schoolName.setText("学校名称: "+resume.getSchoolName());}else{schoolName.setText("学校名称: 未填写");}
            if(resume.getProfessional()!=null&&!resume.getProfessional().equals("")){professional.setText("专业名称: "+resume.getProfessional());}else{professional.setText("学校名称: 未填写");}
            if(resume.getGraduationTime()!=null&&!resume.getGraduationTime().equals("")){graduationTime.setText("毕业时间: "+resume.getGraduationTime());}else{graduationTime.setText("毕业时间: 未填写");}
            if(resume.getGrade()!=null&&!resume.getGrade().equals("")){grade.setText("成绩排名: "+resume.getGrade());}else{grade.setText("成绩排名: 未填写");}
            if(resume.getInternshipExprience()!=null&&!resume.getInternshipExprience().equals("")){internshipExprience.setText("在校实践: "+resume.getInternshipExprience());}else{internshipExprience.setText("在校实践: 未填写");}

            if(resume.getSchoolName()!=null&&!resume.getSchoolName().equals("")&&resume.getProfessional()!=null&&!resume.getProfessional().equals("")&&resume.getGraduationTime()!=null&&!resume.getGraduationTime().equals("")&&resume.getGrade()!=null&&!resume.getGrade().equals("")&&resume.getInternshipExprience()!=null&&!resume.getInternshipExprience().equals("")){
                education_info_nofill.setText("已完善");
                clickToFillEducationInfo.setText("(点击修改)");
                //education_info_nofill.setClickable(false);
            }else{
                education_info_nofill.setText("需完善");
                clickToFillEducationInfo.setText("(点击完善)");
                //education_info_nofill.setClickable(true);
            }

        }else{
            no_education_info.setVisibility(View.VISIBLE);
            education_info.setVisibility(View.GONE);
        }
        if((resume.getSelfEvaluation()!=null&&!resume.getSelfEvaluation().equals(""))||(resume.getExperience()!=null&&!resume.getExperience().equals(""))||(resume.getWorks()!=null&&!resume.getWorks().equals(""))){
            important_info.setVisibility(View.VISIBLE);
            no_important_info.setVisibility(View.GONE);
            if(resume.getSelfEvaluation()!=null&&!resume.getSelfEvaluation().equals("")){selfEvaluation.setText("自我评价: "+resume.getSelfEvaluation());}else{selfEvaluation.setText("自我评价: 未填写");}
            if(resume.getExperience()!=null&&!resume.getExperience().equals("")){experience.setText("实习经历: "+resume.getExperience());}
            if(resume.getWorks()!=null&&!resume.getWorks().equals("")){works.setText("作品附件: "+resume.getWorks());}

            if(resume.getSelfEvaluation()!=null&&!resume.getSelfEvaluation().equals("")&&resume.getExperience()!=null&&!resume.getExperience().equals("")&&resume.getWorks()!=null&&!resume.getWorks().equals("")){
                important_info_nofill.setText("已完善");
                clickToFillImportanteInfo.setText("(点击修改)");
                //important_info_nofill.setClickable(false);
            }else{
                important_info_nofill.setText("需完善");
                clickToFillImportanteInfo.setText("(点击完善)");
                //important_info_nofill.setClickable(true);
            }

        }else{
            important_info.setVisibility(View.GONE);
            no_important_info.setVisibility(View.VISIBLE);
        }
    }
    public boolean adjustToSave(){
        if(resume.getName()!=null&&!resume.getName().equals("")&&resume.getTel()!=null&&!resume.getTel().equals("")&&resume.getAddress()!=null&&!resume.getAddress().equals("")&&resume.getBirth()!=null&&!resume.getBirth().equals("")&&resume.getExpectedIndustry()!=null&&!resume.getExpectedIndustry().equals("")&&resume.getExpectedPosition()!=null&&!resume.getExpectedPosition().equals("")&&resume.getExpectedAddress()!=null&&!resume.getExpectedAddress().equals("")&&resume.getSchoolName()!=null&&!resume.getSchoolName().equals("")&&resume.getProfessional()!=null&&!resume.getProfessional().equals("")&&resume.getGraduationTime()!=null&&!resume.getGraduationTime().equals("")&&resume.getGrade()!=null&&!resume.getGrade().equals("")&&resume.getInternshipExprience()!=null&&!resume.getInternshipExprience().equals("")&&resume.getSelfEvaluation()!=null&&!resume.getSelfEvaluation().equals("")){
            return true;
        }else{
            return false;
        }
    }
    private void initAction() {
        turn_left.setOnClickListener(this);
        personal_base_info.setOnClickListener(this);
        job_intention.setOnClickListener(this);
        education_info.setOnClickListener(this);
        important_info.setOnClickListener(this);

        no_personal_base_info.setOnClickListener(this);
        no_job_intention.setOnClickListener(this);
        no_education_info.setOnClickListener(this);
        no_important_info.setOnClickListener(this);

        personal_base_info_nofill.setOnClickListener(this);
        job_intention_nofill.setOnClickListener(this);
        important_info_nofill.setOnClickListener(this);
        education_info_nofill.setOnClickListener(this);
        save.setOnClickListener(this);

    }

    private void initView() {
        turn_left = (LinearLayout) findViewById(R.id.turn_left);
        personal_base_info= (LinearLayout) findViewById(R.id.personal_base_info);
        job_intention= (LinearLayout) findViewById(R.id.job_intention);
        education_info= (LinearLayout) findViewById(R.id.education_info);
        important_info= (LinearLayout) findViewById(R.id.important_info);

        no_personal_base_info= (LinearLayout) findViewById(R.id.no_personal_base_info);
        no_job_intention= (LinearLayout) findViewById(R.id.no_job_intention);
        no_education_info= (LinearLayout) findViewById(R.id.no_education_info);
        no_important_info= (LinearLayout) findViewById(R.id.no_important_info);

        name= (TextView) findViewById(R.id.name);
        tel= (TextView) findViewById(R.id.tel);
        address= (TextView) findViewById(R.id.address);
        birth= (TextView) findViewById(R.id.birth);

        expectedIndustry= (TextView) findViewById(R.id.expectedIndustry);
        expectedPosition= (TextView) findViewById(R.id.expectedPosition);
        expectedAddress= (TextView) findViewById(R.id.expectedAddress);

        schoolName= (TextView) findViewById(R.id.schoolName);
        professional= (TextView) findViewById(R.id.professional);
        graduationTime= (TextView) findViewById(R.id.graduationTime);
        grade= (TextView) findViewById(R.id.grade);
        internshipExprience= (TextView) findViewById(R.id.internshipExprience);

        selfEvaluation= (TextView) findViewById(R.id.selfEvaluation);
        experience= (TextView) findViewById(R.id.experience);
        works= (TextView) findViewById(R.id.works);

        personal_base_info_nofill= (TextView) findViewById(R.id.personal_base_info_nofill);
        job_intention_nofill= (TextView) findViewById(R.id.job_intention_nofill);
        education_info_nofill= (TextView) findViewById(R.id.education_info_nofill);
        important_info_nofill= (TextView) findViewById(R.id.important_info_nofill);

        clickToFillBaseInfo= (TextView) findViewById(R.id.clickToFillBaseInfo);
        clickToFillEducationInfo= (TextView) findViewById(R.id.clickToFillEducationInfo);
        clickToFillImportanteInfo= (TextView) findViewById(R.id.clickToFillImportanteInfo);
        clickToFillJobIntent= (TextView) findViewById(R.id.clickToFillJobIntent);



        save= (TextView) findViewById(R.id.save);

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
