package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.models.Resume;
import com.mardin.job.network.Constants;


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
            this.setResult(Activity.RESULT_OK);
            this.finish();
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

    }

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
        if(resume.getName()!=null||resume.getTel()!=null||resume.getAddress()!=null||resume.getBirth()!=null){
            no_personal_base_info.setVisibility(View.GONE);
            personal_base_info.setVisibility(View.VISIBLE);
            if(resume.getName()!=null){name.setText(resume.getName());}
            if(resume.getTel()!=null){tel.setText(resume.getTel());}
            if(resume.getAddress()!=null){address.setText(resume.getAddress());}
            if(resume.getBirth()!=null){birth.setText(resume.getBirth());}

            if(resume.getName()!=null&&resume.getTel()!=null&&resume.getAddress()!=null&&resume.getBirth()!=null){
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
        if(resume.getExpectedIndustry()!=null||resume.getExpectedPosition()!=null||resume.getExpectedAddress()!=null){
            no_job_intention.setVisibility(View.GONE);
            job_intention.setVisibility(View.VISIBLE);
            if(resume.getExpectedIndustry()!=null){expectedIndustry.setText(resume.getExpectedIndustry());}
            if(resume.getExpectedPosition()!=null){expectedPosition.setText(resume.getExpectedPosition());}
            if(resume.getExpectedAddress()!=null){expectedAddress.setText(resume.getExpectedAddress());}

            if(resume.getExpectedIndustry()!=null&&resume.getExpectedPosition()!=null&&resume.getExpectedAddress()!=null){
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
        if(resume.getSchoolName()!=null||resume.getProfessional()!=null||resume.getGraduationTime()!=null||resume.getGrade()!=null||resume.getInternshipExprience()!=null){
            no_education_info.setVisibility(View.GONE);
            education_info.setVisibility(View.VISIBLE);
            if(resume.getSchoolName()!=null){schoolName.setText(resume.getSchoolName());}
            if(resume.getProfessional()!=null){professional.setText(resume.getProfessional());}
            if(resume.getGraduationTime()!=null){graduationTime.setText(resume.getGraduationTime());}
            if(resume.getGrade()!=null){grade.setText(resume.getGrade());}
            if(resume.getInternshipExprience()!=null){internshipExprience.setText(resume.getInternshipExprience());}

            if(resume.getSchoolName()!=null&&resume.getProfessional()!=null&&resume.getGraduationTime()!=null&&resume.getGrade()!=null&&resume.getInternshipExprience()!=null){
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
        if(resume.getSelfEvaluation()!=null||resume.getExperience()!=null||resume.getWorks()!=null){
            important_info.setVisibility(View.VISIBLE);
            no_important_info.setVisibility(View.GONE);
            if(resume.getSelfEvaluation()!=null){selfEvaluation.setText(resume.getSelfEvaluation());}
            if(resume.getExperience()!=null){experience.setText(resume.getExperience());}
            if(resume.getWorks()!=null){works.setText(resume.getWorks());}

            if(resume.getSelfEvaluation()!=null&&resume.getExperience()!=null&&resume.getWorks()!=null){
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

    }


}
