package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
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

        //�ֱ��email��psd��ֵ���ݸ�usernameStr��passwordStr
        String tel = phone.getText().toString();
        String passwordStr = psd.getText().toString();

        // �󶨲���
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
            globalProvider.post(this, Constants.loginUrlStr, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    parseLoginResult(new String(responseBody));
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

        //Toast.makeText(LoginActivity.this, usernameStr , Toast.LENGTH_SHORT).show();
        //��ʾ�û���
    }
    //��������parseLoginResult(String json)
    public void parseLoginResult(String json) {

        // �󶨲���
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JsonNode rootNode =  mapper.readTree(jsonParser);
            JsonNode tokenNode = rootNode.path("token");
            String token = tokenNode.toString();
            token = token.replaceAll("\"", "");//��token�е�"\""ȫ���滻��""
            Constants.setToken(LoginActivity.this, token);//��LoginActivity�е�token
            //Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
            //��ʾtoken��Ϣ
            Log.v("err",token);
            this.setResult(Activity.RESULT_OK);//Ϊ�����Activity.RESULT_OK
            this.finish();//���
        } catch (IOException e) {
            e.printStackTrace();
        }//��try�д��뷢������ʱ���ͻ᷵����д�쳣�Ĵ���

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                LoginAction();
                break;
            case R.id.register:
                Intent inten=new Intent(LoginActivity.this,RegisterActivity.class);
                break;
            case R.id.forget_password:

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
}
