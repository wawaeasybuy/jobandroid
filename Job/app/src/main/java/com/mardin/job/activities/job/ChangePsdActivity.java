package com.mardin.job.activities.job;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.ChangePsdBody;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by Administrator on 2015/12/9.
 */
public class ChangePsdActivity extends Activity{
    public LinearLayout turn_left;
    public EditText newPassword;
    public EditText oldPassword;
    public Button ok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepsd);
        turn_left= (LinearLayout) findViewById(R.id.turn_left);
        newPassword= (EditText) findViewById(R.id.newPassword);
        oldPassword= (EditText) findViewById(R.id.oldPassword);
        ok= (Button) findViewById(R.id.ok);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doChange();
            }
        });
    }
    public void doChange(){
        if(oldPassword.getText()==null||oldPassword.getText().toString().length()==0||newPassword.getText()==null||newPassword.getText().toString().length()==0){
            new AlertDialog.Builder(ChangePsdActivity.this)
                    .setMessage("输入不能为空，请重新输入！")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }).show();
            return;
        }
        //分别把email、psd的值传递给usernameStr、passwordStr
        String oldPassword_str = oldPassword.getText().toString();
        String newPassword_str = newPassword.getText().toString();

        // 绑定参数

        ChangePsdBody body=new ChangePsdBody();
        body.setOldPassword(oldPassword_str);
        body.setNewPassword(newPassword_str);

        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            globalProvider.put(this, Constants.changePsd, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(ChangePsdActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    new AlertDialog.Builder(ChangePsdActivity.this)
                            .setMessage("修改失败！请检查输入是否有误！")
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
}
