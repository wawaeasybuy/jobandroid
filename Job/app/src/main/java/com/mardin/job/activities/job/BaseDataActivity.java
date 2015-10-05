package com.mardin.job.activities.job;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/9/15.
 */
public class BaseDataActivity extends Activity implements View.OnClickListener{

    private LinearLayout male;
    private LinearLayout female;

    private TextView male_t;
    private TextView female_t;

    private ImageView male_p;
    private ImageView female_p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_base_data);


        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        male_t= (TextView) findViewById(R.id.male_t);
        female_t= (TextView) findViewById(R.id.female_t);

        male_p= (ImageView) findViewById(R.id.male_p);
        female_p= (ImageView) findViewById(R.id.female_p);

        initView();

        male.setOnClickListener(this);
        female.setOnClickListener(this);

        setSelect(0);

    }

    private void initView() {

        male= (LinearLayout) findViewById(R.id.male);
        female=(LinearLayout)findViewById(R.id.female);

    }


//Usage

    public void setSelect(int i){

        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        //hide(transaction);
        switch(i){

            case 0:

              //  InternshipsFragment home= new InternshipsFragment();
                //transaction.replace(R.id.main, home);

                female_t.setTextColor(0xff0080fe);
                male_t.setTextColor(0xFF000000);

                male_p.setImageResource(R.drawable.male);
                female_p.setImageResource(R.drawable.female_blue);

                male.setBackgroundColor(0xff0080fe);
                female.setBackgroundColor(0xffffffff);

                break;
            case 1:

               // PersonalCenterFragment personal=new PersonalCenterFragment();
                //transaction.replace(R.id.main,personal);

                female_t.setTextColor(0xFF000000);
                male_t.setTextColor(0xff0080fe);

                male_p.setImageResource(R.drawable.male_bule);
                female_p.setImageResource(R.drawable.female);

                female.setBackgroundColor(0xff0080fe);
                male.setBackgroundColor(0xffffffff);

                break;

        }
        transaction.commit();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.male:

                setSelect(0);

                break;
            case R.id.female:
                setSelect(1);
        }
    }

}
