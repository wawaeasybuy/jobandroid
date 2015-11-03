package com.mardin.job.activities.job;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.models.Resume;

/**
 * Created by Ryo on 2015/9/15.
 */
public class EducationInfoActivity extends Activity {

    public EditText schoolName;
    public EditText professional;
    public EditText  graduationTime;
    public EditText grade;
    public EditText internshipExprience;
    public Resume resume;

    public TextView save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_education_info);
        schoolName= (EditText) findViewById(R.id.schoolName);
        professional= (EditText) findViewById(R.id.professional);
        graduationTime= (EditText) findViewById(R.id.graduationTime);
        grade= (EditText) findViewById(R.id.grade);
        internshipExprience= (EditText) findViewById(R.id.internshipExprience);
        save= (TextView) findViewById(R.id.save);

        this.resume= GlobalProvider.getInstance().resume;
        if(resume.getSchoolName()!=null){schoolName.setText(resume.getSchoolName());}
        if(resume.getProfessional()!=null){professional.setText(resume.getProfessional());}
        if(resume.getGraduationTime()!=null){graduationTime.setText(resume.getGraduationTime());}
        if(resume.getGrade()!=null){grade.setText(resume.getGrade());}
        if(resume.getInternshipExprience()!=null){internshipExprience.setText(resume.getInternshipExprience());}

        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalProvider.getInstance().resume.setSchoolName(schoolName.getText().toString());
                GlobalProvider.getInstance().resume.setProfessional(professional.getText().toString());
                //GlobalProvider.getInstance().resume.setGraduationTime(graduationTime.getText().toString());
                GlobalProvider.getInstance().resume.setGrade(grade.getText().toString());
                GlobalProvider.getInstance().resume.setInternshipExprience(internshipExprience.getText().toString());
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
