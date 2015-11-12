package com.mardin.job.activities.job;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.models.Resume;

/**
 * Created by Ryo on 2015/9/15.
 */
public class BaseDataActivity extends Activity implements View.OnClickListener{

    private LinearLayout male;
    private LinearLayout female;
    public LinearLayout turn_left;

    private TextView male_t;
    private TextView female_t;
    public TextView save;

    private ImageView male_p;
    private ImageView female_p;

    public EditText name;
    public EditText tel;
    public EditText address;
    public EditText birth;

    public Resume resume;

public TextView saveToNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_base_data);

        initView();
        initAction();
        setSelect(0);

        this.resume= GlobalProvider.getInstance().resume;
        if(resume.getName()!=null){name.setText(resume.getName());}
        if(resume.getBirth()!=null){birth.setText(resume.getBirth());}
        if(resume.getAddress()!=null){address.setText(resume.getAddress());}
        if(resume.getTel()!=null){tel.setText(resume.getTel());}

    }
public void initAction(){
    turn_left.setOnClickListener(this);
    male.setOnClickListener(this);
    female.setOnClickListener(this);
    save.setOnClickListener(this);
    saveToNext.setOnClickListener(this);
}
    private void initView() {

        male= (LinearLayout) findViewById(R.id.male);
        female=(LinearLayout)findViewById(R.id.female);
        turn_left = (LinearLayout) findViewById(R.id.turn_left);

        male_t= (TextView) findViewById(R.id.male_t);
        female_t= (TextView) findViewById(R.id.female_t);
        save= (TextView) findViewById(R.id.save);

        male_p= (ImageView) findViewById(R.id.male_p);
        female_p= (ImageView) findViewById(R.id.female_p);

        name= (EditText) findViewById(R.id.name);
        address= (EditText) findViewById(R.id.address);
        tel= (EditText) findViewById(R.id.tel);
        birth= (EditText) findViewById(R.id.birth);
        saveToNext= (TextView) findViewById(R.id.saveToNext);

    }
    public void doSave(){
        GlobalProvider.getInstance().resume.setName(name.getText().toString());
        GlobalProvider.getInstance().resume.setAddress(address.getText().toString());
        GlobalProvider.getInstance().resume.setTel(tel.getText().toString());
        this.setResult(Activity.RESULT_OK);
        this.finish();
    }

    public void setSelect(int i){
        switch(i){

            case 0:
                female_t.setTextColor(0xff0080fe);
                male_t.setTextColor(0xFF000000);

                male_p.setImageResource(R.drawable.male);
                female_p.setImageResource(R.drawable.female_blue);

                male.setBackgroundColor(0xff0080fe);
                female.setBackgroundColor(0xffffffff);

                break;
            case 1:
                female_t.setTextColor(0xFF000000);
                male_t.setTextColor(0xff0080fe);

                male_p.setImageResource(R.drawable.male_bule);
                female_p.setImageResource(R.drawable.female);

                female.setBackgroundColor(0xff0080fe);
                male.setBackgroundColor(0xffffffff);

                break;

        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.male:
                setSelect(0);
                break;
            case R.id.female:
                setSelect(1);
                break;
            case R.id.turn_left:
                finish();
                break;
            case R.id.save:
                doSave();
                break;
            case R.id.saveToNext:
                doSave();
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
