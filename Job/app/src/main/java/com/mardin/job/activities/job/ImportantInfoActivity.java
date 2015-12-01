package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
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
public class ImportantInfoActivity extends Activity {
    public EditText selfEvaluation;
    public EditText experience;
    public EditText works;
    public TextView save;
    public Resume resume;
    public TextView text_length;
    public TextView text_length_two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_important_info);

        selfEvaluation= (EditText) findViewById(R.id.selfEvaluation);
        experience= (EditText) findViewById(R.id.experience);
        works= (EditText) findViewById(R.id.works);
        text_length= (TextView) findViewById(R.id.text_length);
        text_length_two= (TextView) findViewById(R.id.text_length_two);
        selfEvaluation.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String content = selfEvaluation.getText().toString();
                text_length.setText(content.length()+"");
            }

        });
        experience.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String content = experience.getText().toString();
                text_length_two.setText(content.length()+"");
            }

        });
        save= (TextView) findViewById(R.id.save);

        this.resume= GlobalProvider.getInstance().resume;
        if(resume.getSelfEvaluation()!=null){selfEvaluation.setText(resume.getSelfEvaluation());}
        if(resume.getExperience()!=null){experience.setText(resume.getExperience());}
        if(resume.getWorks()!=null){works.setText(resume.getWorks());}





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
                GlobalProvider.getInstance().resume.setSelfEvaluation(selfEvaluation.getText().toString());
                GlobalProvider.getInstance().resume.setExperience(experience.getText().toString());
                GlobalProvider.getInstance().resume.setWorks(works.getText().toString());
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
