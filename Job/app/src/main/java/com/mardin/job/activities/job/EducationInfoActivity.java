package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    public TextView saveToNext;
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
        saveToNext= (TextView) findViewById(R.id.saveToNext);

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
        saveToNext.setOnClickListener(new View.OnClickListener() {
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
