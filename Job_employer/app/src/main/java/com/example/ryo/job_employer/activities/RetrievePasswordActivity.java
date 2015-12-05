package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.GetCodeBody;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.ResetPsdBody;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ryo on 2015/9/14.
 */
public class RetrievePasswordActivity extends Activity {
    public EditText tel;
    public EditText verification_code;
    public EditText psd;
    public TextView  get_verification_code;
    private int time = 60;
    private Timer timer = new Timer();
    TimerTask task;
    public Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_retrieve_password);
        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        tel= (EditText) findViewById(R.id.tel);
        verification_code= (EditText) findViewById(R.id.verification_code);
        psd= (EditText) findViewById(R.id.psd);
        get_verification_code= (TextView) findViewById(R.id.get_verification_code);
        ok= (Button) findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doResetPsd();
            }
        });
        get_verification_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_verification_code.setEnabled(false);
                getCode();
            }
        });
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
    public void startThread(){
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    @Override
                    public void run() {
                        if (time <= 0) {
                            get_verification_code.setEnabled(true);
                            get_verification_code.setText("获取验证码");
                            task.cancel();
                        } else {
                            get_verification_code.setText("" + time);
                        }
                        time--;
                    }
                });
            }
        };
        //time = 60;
        timer.schedule(task, 0, 1000);
    }
    public void getCode(){
        GetCodeBody body=new GetCodeBody();
        if(tel.getText()==null||tel.getText().equals("")){
            new AlertDialog.Builder(RetrievePasswordActivity.this)
                    .setMessage("手机号码不能为空！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
            return;
        }else{
            body.setTel(tel.getText().toString());
        }
//        body.setPassword(psd.getText().toString());
//        body.setTel(tel.getText().toString());
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            globalProvider.post(this, Constants.getCodeUrlStr, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    //parseLoginResult(new String(responseBody));
                    Toast.makeText(RetrievePasswordActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                    startThread();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void doResetPsd(){
        ResetPsdBody body=new ResetPsdBody();
//        if(psd.getText().toString()==null||psd.getText().toString().equals(""))
        body.setNewPassword(psd.getText().toString());
        body.setTel(tel.getText().toString());
        body.setCode(verification_code.getText().toString());
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            globalProvider.put(this, Constants.forgetPsdUrlStr, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    //parseLoginResult(new String(responseBody));
                    Toast.makeText(RetrievePasswordActivity.this, "更改成功！", Toast.LENGTH_SHORT).show();
                    finish();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
