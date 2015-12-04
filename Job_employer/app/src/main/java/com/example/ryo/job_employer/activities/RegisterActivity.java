package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.MainActivity;
import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initAction();
    }

    private void initAction() {
        ok.setOnClickListener(this);
        get_verification_code.setOnClickListener(this);
    }

    private void initView() {
        phone_num= (EditText) findViewById(R.id.phone_num);
        verification_code= (EditText) findViewById(R.id.verification_code);
        name= (EditText) findViewById(R.id.name);
        psd= (EditText) findViewById(R.id.password);
        get_verification_code= (TextView) findViewById(R.id.get_verification_code);
        ok= (Button) findViewById(R.id.ok);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.ok:
              RegisterAction();
              break;
          case R.id.get_verification_code:
              break;

      }
    }
    private void RegisterAction() {
        RegisterBody body=new RegisterBody();
        body.setName(name.getText().toString());
        body.setTel(phone_num.getText().toString());
        body.setPassword(psd.getText().toString());

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
