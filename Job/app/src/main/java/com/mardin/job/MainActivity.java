package com.mardin.job;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.activities.zhiwei.Topic;
import com.mardin.job.fragments.zhiwei.HomeFragment;
import com.mardin.job.fragments.zhiwei.PersonalFragment;
import com.mardin.job.fragments.zhiwei.PositionFragment;

public class MainActivity extends Activity implements View.OnClickListener {

    private LinearLayout homePosition;
    private LinearLayout home_jobMarket;
    private LinearLayout homePersonal;

    //private TextView homeTopic;



    private static MainActivity defaultActivity = null;
    public static MainActivity getDefault() {

        return defaultActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

        defaultActivity = this;


        initView();

        homePosition.setOnClickListener(this);
        home_jobMarket.setOnClickListener(this);
        homePersonal.setOnClickListener(this);

        setSelect(0);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(defaultActivity!=null){
            defaultActivity = null;
        }
    }

    private void initView() {

        homePosition= (LinearLayout) findViewById(R.id.homePosition);
        home_jobMarket= (LinearLayout) findViewById(R.id.home_jobMarket);
        homePersonal=(LinearLayout)findViewById(R.id.homePersonal);

        //homeTopic=(TextView)findViewById(R.id.homeTopic);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


//Usage

    public void setSelect(int i){

        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        //hide(transaction);
        switch(i){

            case 0:

                HomeFragment home_fragment= new HomeFragment();
                transaction.replace(R.id.main, home_fragment);

                break;
            case 1:

                PositionFragment position_fragment=new PositionFragment();
                transaction.replace(R.id.main,position_fragment);

                break;
            case 2:

                PersonalFragment personal_fragment=new PersonalFragment();
                transaction.replace(R.id.main, personal_fragment);
                break;
        }
        transaction.commit();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.home_jobMarket:

                setSelect(0);

                break;
            case R.id.homePersonal:
                setSelect(2);

                break;
            case R.id.homePosition:
                setSelect(1);
        }
    }
}
