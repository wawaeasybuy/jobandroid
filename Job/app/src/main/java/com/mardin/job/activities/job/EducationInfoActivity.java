package com.mardin.job.activities.job;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.models.Candidate;
import com.mardin.job.models.Resume;

import java.util.Calendar;

/**
 * Created by Ryo on 2015/9/15.
 */
public class EducationInfoActivity extends Activity {

    public TextView schoolName;
    public EditText professional;
    public TextView  graduationTime;
    public TextView grade;
    public EditText internshipExprience;
    public Resume resume;
    public Candidate candidate;

    public TextView save;
    public TextView saveToNext;
    public LinearLayout graduationTime_select_layout;
    public String Str_data="";
    public RelativeLayout select_grade;
    public int chooseGrade;

    public String[] grade_select={"0-10%","10-20%","20-30%","30-40%","40-50%","50-60%","60-70%","70-80%","80-90%","90-100%"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_education_info);
        schoolName= (TextView) findViewById(R.id.schoolName);
        professional= (EditText) findViewById(R.id.professional);
        graduationTime= (TextView) findViewById(R.id.graduationTime);
        grade= (TextView) findViewById(R.id.grade);
        select_grade= (RelativeLayout) findViewById(R.id.select_grade);
        internshipExprience= (EditText) findViewById(R.id.internshipExprience);
        save= (TextView) findViewById(R.id.save);
        //saveToNext= (TextView) findViewById(R.id.saveToNext);
        graduationTime_select_layout= (LinearLayout) findViewById(R.id.graduationTime_select_layout);

        this.resume= GlobalProvider.getInstance().resume;
        candidate=GlobalProvider.getInstance().candidate;
        if(candidate.getSchoolName()!=null){schoolName.setText(candidate.getSchoolName());}
        if(resume.getProfessional()!=null){professional.setText(resume.getProfessional());}
        if(resume.getGraduationTime()!=null){graduationTime.setText(resume.getGraduationTime());Str_data=resume.getGraduationTime();}
        if(resume.getGrade()!=null){grade.setText(resume.getGrade());}
        if(resume.getInternshipExprience()!=null){internshipExprience.setText(resume.getInternshipExprience());}

        final Calendar c = Calendar.getInstance();
        select_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EducationInfoActivity.this)
                        .setSingleChoiceItems(grade_select, 0,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        chooseGrade = which;
                                    }
                                }
                        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        grade.setText(grade_select[chooseGrade]);
                        //GlobalProvider.getInstance().Adress[0]=items_shiping[chooseItem_one];
                        //shipingAdress_Str=items_shiping[chooseItem_one];
                    }
                }).setNegativeButton("取消",null)
                        .show();
            }
        });
        graduationTime_select_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(EducationInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        Toast.makeText(EducationInfoActivity.this, year + "-" + (monthOfYear + 1) + "-" + dayOfMonth, Toast.LENGTH_SHORT).show();
                        Str_data = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        graduationTime.setText(Str_data);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                datePicker.show();
            }
        });
        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EducationInfoActivity.this)
                        .setMessage("是否保存再退出？")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        })
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                doSave();
                            }
                        }).show();
            }
        });
//        saveToNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GlobalProvider.getInstance().resume.setSchoolName(schoolName.getText().toString());
//                GlobalProvider.getInstance().resume.setProfessional(professional.getText().toString());
//                //GlobalProvider.getInstance().resume.setGraduationTime(graduationTime.getText().toString());
//                GlobalProvider.getInstance().resume.setGrade(grade.getText().toString());
//                GlobalProvider.getInstance().resume.setGraduationTime(Str_data);
//                GlobalProvider.getInstance().resume.setInternshipExprience(internshipExprience.getText().toString());
//                setResult(Activity.RESULT_OK);
//                finish();
//            }
//        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               doSave();
            }
        });
    }
    public void doSave(){
        GlobalProvider.getInstance().resume.setSchoolName(schoolName.getText().toString());
        GlobalProvider.getInstance().resume.setProfessional(professional.getText().toString());
        //GlobalProvider.getInstance().resume.setGraduationTime(graduationTime.getText().toString());
        GlobalProvider.getInstance().resume.setGrade(grade.getText().toString());
        GlobalProvider.getInstance().resume.setGraduationTime(Str_data);
        GlobalProvider.getInstance().resume.setInternshipExprience(internshipExprience.getText().toString());
        setResult(Activity.RESULT_OK);
        finish();
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
