package com.mardin.job;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mardin.job.fragments.job.AbilityFragment;
import com.mardin.job.fragments.job.InternshipsFragment;
import com.mardin.job.fragments.job.PersonalCenterFragment;
import com.mardin.job.network.Constants;


public class MainActivityN extends Activity implements View.OnClickListener{

    private LinearLayout home;
    private LinearLayout personal;
    private LinearLayout ability;

    private TextView home_t;
    private TextView personal_t;
    private TextView ability_t;

    private ImageView home_p;
    private ImageView personal_p;
    private ImageView ability_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_main);

        //  defaultActivity = this;

        home_t= (TextView) findViewById(R.id.home_t);
        personal_t= (TextView) findViewById(R.id.personal_t);
        ability_t= (TextView) findViewById(R.id.ability_t);

        home_p= (ImageView) findViewById(R.id.home_p);
        personal_p= (ImageView) findViewById(R.id.personal_p);
        ability_p= (ImageView) findViewById(R.id.ability_p);

        initView();

        home.setOnClickListener(this);
        personal.setOnClickListener(this);
        ability.setOnClickListener(this);

        setSelect(0);

    }

    private void initView() {

        home= (LinearLayout) findViewById(R.id.home);
        personal= (LinearLayout) findViewById(R.id.personal);
        ability=(LinearLayout)findViewById(R.id.ability);

        //homeTopic=(TextView)findViewById(R.id.homeTopic);

    }


//Usage

    public void setSelect(int i){

        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        //hide(transaction);
        switch(i){

            case 0:

                InternshipsFragment home= new InternshipsFragment();
                transaction.replace(R.id.main, home);

                home_t.setTextColor(0xff0080fe);
                personal_t.setTextColor(0xFF929292);
                ability_t.setTextColor(0xff929292);

                home_p.setImageResource(R.drawable.sxb_home_blue);
                personal_p.setImageResource(R.drawable.sxb_gerenzhongxin_gray);
                ability_p.setImageResource(R.drawable.sxb_gerenpince_gray);

                break;
            case 1:

                PersonalCenterFragment personal=new PersonalCenterFragment();
                transaction.replace(R.id.main,personal);

                home_t.setTextColor(0xFF929292);
                personal_t.setTextColor(0xff0080fe);
                ability_t.setTextColor(0xFF929292);

                home_p.setImageResource(R.drawable.sxb_home_gray);
                personal_p.setImageResource(R.drawable.sxb_gerenzhongxin_blue);
                ability_p.setImageResource(R.drawable.sxb_gerenpince_gray);


                break;
            case 2:

                AbilityFragment ability=new AbilityFragment();
                transaction.replace(R.id.main,ability);

                home_t.setTextColor(0xFF929292);
                personal_t.setTextColor(0xFF929292);
                ability_t.setTextColor(0xFF0080fe);

                home_p.setImageResource(R.drawable.sxb_home_gray);
                personal_p.setImageResource(R.drawable.sxb_gerenzhongxin_gray);
                ability_p.setImageResource(R.drawable.sxb_gerenpince_blue);


                break;
        }
        transaction.commit();

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.UPECANDIDATE_INTENT:
                if (resultCode == RESULT_OK) {
                    setSelect(1);
                }
                break;
            case Constants.LoginIntent:
                if (resultCode == RESULT_OK) {
                    setSelect(1);
                }
                break;
            case Constants.UPDATERESUME:
                if (resultCode == RESULT_OK) {
                    setSelect(1);
                }
                break;
            case Constants.GOTOLOGOUT:
                if (resultCode == RESULT_OK) {
                    setSelect(1);
                }
                break;
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.home:

                setSelect(0);

                break;
            case R.id.ability:
                setSelect(2);

                break;
            case R.id.personal:
                setSelect(1);
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
