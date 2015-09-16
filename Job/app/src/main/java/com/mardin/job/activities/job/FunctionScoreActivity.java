package com.mardin.job.activities.job;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mardin.job.R;
import com.mardin.job.fragments.job.FunctionEvaluateOneFragment;
import com.mardin.job.fragments.job.FunctionEvaluateTwoFragment;

/**
 * Created by Ryo on 2015/9/15.
 */
public class FunctionScoreActivity extends Activity implements View.OnClickListener {

    private LinearLayout changeone;
    private LinearLayout changetwo;

    private ImageView change1;
    private ImageView change2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_score);

        change1 = (ImageView) findViewById(R.id.change1);
        change2 = (ImageView) findViewById(R.id.change2);

        initView();

        changeone.setOnClickListener(this);
        changetwo.setOnClickListener(this);

        setSelect(0);

    }

    private void initView() {

        changeone = (LinearLayout) findViewById(R.id.changeone);
        changetwo = (LinearLayout) findViewById(R.id.changetwo);

    }


    public void setSelect(int i) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //hide(transaction);
        switch (i) {

            case 0:

                FunctionEvaluateOneFragment eva_one = new FunctionEvaluateOneFragment();
                transaction.replace(R.id.change, eva_one);

                change1.setImageResource(R.drawable.u48);
                change2.setImageResource(R.drawable.u46);

                break;
            case 1:

                FunctionEvaluateTwoFragment eva_two = new FunctionEvaluateTwoFragment();
                transaction.replace(R.id.change, eva_two);

                change1.setImageResource(R.drawable.u46);
                change2.setImageResource(R.drawable.u48);

                break;
        }
        transaction.commit();

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.changeone:

              setSelect(0);

                break;
            case R.id.changetwo:
                setSelect(1);
        }

        }


//    @Override
//    public void onClick(View v) {
//
//
//    }

    }

