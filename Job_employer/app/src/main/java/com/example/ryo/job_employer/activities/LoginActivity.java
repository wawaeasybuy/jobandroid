package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.Http.AsyncHttpClient;
import com.example.ryo.job_employer.models.Http.AsyncHttpResponseHandler;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.LoginBody;
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

/**
 * Created by Administrator on 2015/10/22.
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    public EditText phone;
    public EditText psd;
    public Button login;
    public TextView register;
    public TextView forgeterPsd;
    private int a=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initAction();
    }

    private void initAction() {
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forgeterPsd.setOnClickListener(this);
    }

    public void initView(){
        phone= (EditText) findViewById(R.id.phone);
        psd= (EditText) findViewById(R.id.password);
        login= (Button) findViewById(R.id.login);
        register= (TextView) findViewById(R.id.register);
        forgeterPsd= (TextView) findViewById(R.id.forget_password);

    }
    public void LoginAction() {

        if(phone.getText()==null||phone.getText().toString().length()==0||psd.getText()==null||psd.getText().toString().length()==0){
            new AlertDialog.Builder(LoginActivity.this)
                    .setMessage("输入不能为空，请重新输入！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }).show();
            return;
        }
        //分别把email、psd的值传递给usernameStr、passwordStr
        String tel = phone.getText().toString();
        String passwordStr = psd.getText().toString();

        // 绑定参数

        LoginBody body=new LoginBody();
        body.setPassword(passwordStr);
        body.setTel(tel);
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            globalProvider.doLog(this, Constants.loginUrlStr, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    parseLoginResult(new String(responseBody));
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    new AlertDialog.Builder(LoginActivity.this)
                            .setMessage("用户名或密码错误！")
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

        //Toast.makeText(LoginActivity.this, usernameStr , Toast.LENGTH_SHORT).show();
        //显示用户名
    }
    //声明方法parseLoginResult(String json)
    public void parseLoginResult(String json) {

        // 绑定参数
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JsonNode rootNode =  mapper.readTree(jsonParser);
            JsonNode tokenNode = rootNode.path("token");
            String token = tokenNode.toString();
            token = token.replaceAll("\"", "");//把token中的"\""全部替换成""
            Constants.setToken(LoginActivity.this, token);//绑定LoginActivity中的token
            //Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
            //显示token信息
            Log.v("err",token);
            this.setResult(Activity.RESULT_OK);//为结果绑定Activity.RESULT_OK
            this.finish();//完成
        } catch (IOException e) {
            e.printStackTrace();
        }//当try中代码发生错误时，就会返回所写异常的处理

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                LoginAction();
                break;
            case R.id.register:
                Intent inten=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(inten);
                break;
            case R.id.forget_password:
                Intent intent=new Intent(LoginActivity.this,RetrievePasswordActivity.class);
                startActivity(intent);
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //按下键盘上返回按钮
        if(keyCode == KeyEvent.KEYCODE_BACK){



            new AlertDialog.Builder(this)
                    .setMessage("您确定退出应用吗？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            a=a+1;
                            finish();

                        }
                    }).show();

            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(a>0){
            System.exit(0);

        }
        a=0;
        //或者下面这种方式
        //android.os.Process.killProcess(android.os.Process.myPid());
    }
}
