package com.mardin.job;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mardin.job.fragments.job.AbilityFragment;
import com.mardin.job.fragments.job.InternshipsFragment;
import com.mardin.job.fragments.job.PersonalCenterFragment;


public class MainActivityN extends Activity implements View.OnClickListener{

    private LinearLayout home;
    private LinearLayout personal;
    private LinearLayout ability;

    private TextView home_t;
    private TextView personal_t;
    private TextView ability_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_main);

        //  defaultActivity = this;

        home_t= (TextView) findViewById(R.id.home_t);
        personal_t= (TextView) findViewById(R.id.personal_t);
        ability_t= (TextView) findViewById(R.id.ability_t);

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
                personal_t.setTextColor(0xFF000000);
                ability_t.setTextColor(0xff000000);

                break;
            case 1:

                PersonalCenterFragment personal=new PersonalCenterFragment();
                transaction.replace(R.id.main,personal);

                home_t.setTextColor(0xFF000000);
                personal_t.setTextColor(0xff0080fe);
                ability_t.setTextColor(0xff000000);

                break;
            case 2:

                AbilityFragment ability=new AbilityFragment();
                transaction.replace(R.id.main,ability);

                home_t.setTextColor(0xff000000);
                personal_t.setTextColor(0xff000000);
                ability_t.setTextColor(0xFF0080fe);

                break;
        }
        transaction.commit();

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
}
