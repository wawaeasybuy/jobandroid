package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.network.Constants;

/**
 * Created by Administrator on 2015/11/18.
 */
public class SettingActivity extends Activity{
    public Button logout;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_setting);
        logout= (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogout();
            }
        });
    }
    private void doLogout() {
                if (Constants.getToken(this).equals("")) {
                    Toast.makeText(this, "you have not logged in", Toast.LENGTH_SHORT).show();
                }else {
                    Constants.setToken(this, "");
                    Toast.makeText(this, "you have successfully logged out", Toast.LENGTH_SHORT).show();
                    GlobalProvider.getInstance().employerId="";
                }
        this.setResult(Activity.RESULT_OK);
        this.finish();
    }
}
