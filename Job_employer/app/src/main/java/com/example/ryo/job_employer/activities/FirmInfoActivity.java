package com.example.ryo.job_employer.activities;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.models.Employer;

/**
 * Created by Ryo on 2015/9/18.
 */
public class FirmInfoActivity extends Activity implements View.OnClickListener {

    public ImageView turn_left ;
    public TextView save;
    public EditText name;
    public EditText mainBusiness;
    public EditText description;
    public EditText companyURL;
    public EditText companyAddress;
    public Employer GoEmployer=GlobalProvider.getInstance().employer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm_info);
        initView();
        initAction();
        if(GoEmployer.companyname!=null){name.setText(GoEmployer.getCompanyname());}
        if(GoEmployer.mianBusiness!=null){mainBusiness.setText(GoEmployer.getMianBusiness());}
        if(GoEmployer.companyInfo!=null){description.setText(GoEmployer.getCompanyInfo());}
        if(GoEmployer.companyAddress!=null){companyAddress.setText(GoEmployer.getCompanyAddress());}
        if(GoEmployer.companyURL!=null){companyURL.setText(GoEmployer.getCompanyURL());}

    }
    private void initAction() {
        turn_left.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void initView() {
        turn_left = (ImageView) findViewById(R.id.turn_left);
        save= (TextView) findViewById(R.id.save);
        name= (EditText) findViewById(R.id.name);
        mainBusiness= (EditText) findViewById(R.id.main_business);
        description= (EditText) findViewById(R.id.description);
        companyURL= (EditText) findViewById(R.id.companyURL);
        companyAddress= (EditText) findViewById(R.id.companyAddress);

    }
    @Override
    public void onClick(View v) {
  switch(v.getId()){
      case R.id.turn_left:
          finish();
          break;
      case R.id.save:
          doSave();
          break;
      }
    }

    private void doSave() {
        GlobalProvider.getInstance().employer.setCompanyname(name.getText().toString());
        GlobalProvider.getInstance().employer.setMianBusiness(mainBusiness.getText().toString());
        GlobalProvider.getInstance().employer.setCompanyInfo(description.getText().toString());
        GlobalProvider.getInstance().employer.setCompanyAddress(companyAddress.getText().toString());
        GlobalProvider.getInstance().employer.setCompanyURL(companyURL.getText().toString());
        Toast.makeText(FirmInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
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
