package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.MainActivity;
import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.ErrorList;
import com.example.ryo.job_employer.models.GetCodeBody;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.RegisterBody;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/10/23.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {
    public EditText phone_num;
    public EditText verification_code;
    public EditText name;
    public EditText psd;
    public TextView get_verification_code;
    public Button ok;
    public LinearLayout turn_left;

    private int time = 60;
    private Timer timer = new Timer();
    TimerTask task;
    public TextView areaCode;
    public String[] codeArr={"+86 中国","+01 美国","+65 新加坡"};
    public int select_code=3;
    public String areacode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initAction();
        areaCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaCode.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        select_code=0;
                        new AlertDialog.Builder(RegisterActivity.this)
                                .setSingleChoiceItems(codeArr, 0,
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                select_code = which;
                                            }
                                        }
                                ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (select_code){
                                    case 3:
                                        areacode="86";
                                        break;
                                    case 0:
                                        areacode="86";
                                        break;
                                    case 1:
                                        areacode="01";
                                        break;
                                    case 2:
                                        areacode="65";
                                        break;
                                }
                                areaCode.setText(areacode+"+");
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                areacode="86";
                                areaCode.setText(areacode+"+");
                            }
                        }).show();
                    }
                });
            }
        });
    }

    private void initAction() {
        ok.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
        turn_left.setOnClickListener(this);
    }
    private void initView() {
        phone_num= (EditText) findViewById(R.id.phone_num);
        verification_code= (EditText) findViewById(R.id.verification_code);
        name= (EditText) findViewById(R.id.name);
        psd= (EditText) findViewById(R.id.password);
        get_verification_code= (TextView) findViewById(R.id.get_verification_code);
        ok= (Button) findViewById(R.id.ok);
        turn_left= (LinearLayout) findViewById(R.id.turn_left);
        areaCode= (TextView) findViewById(R.id.areaCode);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.ok:
              RegisterAction();
              break;
          case R.id.get_verification_code:
              getCode();
              break;
          case R.id.turn_left:
              finish();
              break;
      }
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
        //body.setName(name.getText().toString());
        if(phone_num.getText()==null||phone_num.getText().toString().equals("")){
            new AlertDialog.Builder(RegisterActivity.this)
                    .setMessage("手机号码不能为空！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
            return;
        }
        if(areacode.equals("")){
            new AlertDialog.Builder(RegisterActivity.this)
                    .setMessage("请输入区号！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
            return;
        }
        body.setTel(areacode+phone_num.getText().toString());
        //body.setPassword(psd.getText().toString());
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
                    //doRegister(new String(responseBody));
                    Toast.makeText(RegisterActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                    startThread();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setMessage("发送失败！请重新输入你的号码")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();
                }
                @Override
                public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void RegisterAction() {
        if(name.getText()==null||name.getText().toString().length()==0||phone_num.getText()==null||phone_num.getText().toString().length()==0||psd.getText()==null||psd.getText().toString().length()==0||verification_code.getText()==null||verification_code.getText().toString().length()==0){
            new AlertDialog.Builder(RegisterActivity.this)
                    .setMessage("输入不能为空，请重新输入！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }).show();
            return;
        }
        RegisterBody body=new RegisterBody();
        body.setName(name.getText().toString());
        body.setTel(areacode+phone_num.getText().toString());
        body.setPassword(psd.getText().toString());
        body.setCode(verification_code.getText().toString());

        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            globalProvider.post(this, Constants.personalInfo, entity, "application/json", new RequestListener() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                       doRegister(new String(responseBody));
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        //Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
//                        new AlertDialog.Builder(RegisterActivity.this)
//                                .setMessage("注册失败，请重新输入！")
//                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//
//                                    }
//                                }).show();
                        if(responseBody!=null) {
                            JsonFactory jsonFactory = new JsonFactory();
                            ObjectMapper objectMapper = new ObjectMapper();
                            try {
                                JsonParser jsonParser = jsonFactory.createJsonParser(new String(responseBody));
                                ErrorList errorList = (ErrorList) objectMapper.readValue(jsonParser, ErrorList.class);
                                new AlertDialog.Builder(RegisterActivity.this)
                                        .setMessage(errorList.error.msg)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void doRegister(String json){

        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JsonNode rootNode =  mapper.readTree(jsonParser);
            JsonNode tokenNode = rootNode.path("token");
            String token = tokenNode.toString();
            token = token.replaceAll("\"", "");//把token中的"\""全部替换成""
            Constants.setToken(RegisterActivity.this,token);//绑定LoginActivity中的token
            //Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
            //显示token信息
            Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }//当try中代码发生错误时，就会返回所写异常的处理
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
