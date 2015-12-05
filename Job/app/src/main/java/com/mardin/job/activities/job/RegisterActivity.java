package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.MainActivityN;
import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.RegisterBody;
import com.mardin.job.network.Constants;

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
 * Created by Ryo on 2015/9/14.
 */
public class RegisterActivity extends Activity {
    public EditText name;
    public EditText tel;
    public EditText psd;
    public Button ok;
    public TextView get_verification_code;
    private int time = 60;
    private Timer timer = new Timer();
    TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_register);

        ImageView turn_left = (ImageView) findViewById(R.id.turn_left);
        get_verification_code= (TextView) findViewById(R.id.get_verification_code);
        name= (EditText) findViewById(R.id.name);
        tel= (EditText) findViewById(R.id.tel);
        psd= (EditText) findViewById(R.id.psd);
        ok= (Button) findViewById(R.id.ok);
        get_verification_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_verification_code.setEnabled(false);
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
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void doRegister(){
        RegisterBody body=new RegisterBody();
        body.setName(name.getText().toString());
        body.setPassword(psd.getText().toString());
        body.setTel(tel.getText().toString());
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            globalProvider.post(this, Constants.regCanStr, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    parseLoginResult(new String(responseBody));
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
            Constants.setToken(RegisterActivity.this, token);//绑定LoginActivity中的token
            //Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
            //显示token信息
            Log.v("err", token);
            Intent intent=new Intent(RegisterActivity.this, MainActivityN.class);
            startActivity(intent);
            this.finish();
//            this.setResult(Activity.RESULT_OK);//为结果绑定Activity.RESULT_OK
//            this.finish();//完成
        } catch (IOException e) {
            e.printStackTrace();
        }//当try中代码发生错误时，就会返回所写异常的处理

    }
}
