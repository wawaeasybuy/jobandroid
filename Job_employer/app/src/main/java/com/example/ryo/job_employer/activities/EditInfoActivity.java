package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
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
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.CityBodyUp;
import com.example.ryo.job_employer.models.Employer;
import com.example.ryo.job_employer.models.EmployerUpdate;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by Ryo on 2015/9/18.
 */
public class EditInfoActivity extends Activity implements View.OnClickListener{
    public ImageView turn_left;
    public TextView needText;
    public LinearLayout firm_info ;
    public LinearLayout changePsd;
    public TextView save;
    public EditText name;
    public Employer GolEmployer=GlobalProvider.getInstance().employer;
    public EmployerUpdate employer=new EmployerUpdate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        initView();
        initAction();
        if(GolEmployer.name!=null){name.setText(GolEmployer.getName());}
        doAdjust();

    }
    public void doAdjust(){
        if(GolEmployer.getCompanyname()!=null&&!GolEmployer.getCompanyname().equals("")&&GolEmployer.getMainBusiness()!=null&&!GolEmployer.getMainBusiness().equals("")&&GolEmployer.getCompanyInfo()!=null&&!GolEmployer.getCompanyInfo().equals("")&&GolEmployer.getCompanyAddress()!=null&&!GolEmployer.getCompanyAddress().equals("")&&GolEmployer.getDetailedCompanyAddress()!=null&&GolEmployer.getCompanyURL()!=null&&!GolEmployer.getCompanyURL().equals("")){
            needText.setText("已完善");
        }else{
            needText.setText("需完善");
        }
    }
    public boolean adJustToOpen(){
        if(name.getText()==null||name.getText().toString().equals("")||GolEmployer.getName()==null||GolEmployer.getName().equals("")||GolEmployer.getCompanyname()==null||GolEmployer.getCompanyname().equals("")||GolEmployer.getMainBusiness()==null||GolEmployer.getMainBusiness().equals("")||GolEmployer.getCompanyURL()==null||GolEmployer.getCompanyURL().equals("")||GolEmployer.getCompanyAddress()==null||GolEmployer.getCompanyAddress().equals("")||GolEmployer.getDetailedCompanyAddress()==null||GolEmployer.getCompanyInfo()==null||GolEmployer.getCompanyInfo().equals("")){
            return false;
        }else{
            return true;
        }
    }
//    public boolean adJustToOpen(){
//        if(GlobalProvider.getInstance().employer.getName()==null||GlobalProvider.getInstance().employer.getName().equals("")||GlobalProvider.getInstance().employer.getCompanyname()==null||GlobalProvider.getInstance().employer.getCompanyname().equals("")||GlobalProvider.getInstance().employer.getMainBusiness()==null||GlobalProvider.getInstance().employer.getMainBusiness().equals("")||GlobalProvider.getInstance().employer.getCompanyURL()==null||GlobalProvider.getInstance().employer.getCompanyURL().equals("")||GlobalProvider.getInstance().employer.getProvince()==null||GlobalProvider.getInstance().employer.getCompanyAddress()==null||GlobalProvider.getInstance().employer.getCompanyAddress().equals("")||GlobalProvider.getInstance().employer.getCompanyInfo()==null||GlobalProvider.getInstance().employer.getCompanyInfo().equals("")){
//            return false;
//        }else{
//            return true;
//        }
//    }
    private void initAction() {
        save.setOnClickListener(this);
        turn_left.setOnClickListener(this);
        firm_info.setOnClickListener(this);
        changePsd.setOnClickListener(this);

    }
    private void initView() {
        turn_left = (ImageView) findViewById(R.id.turn_left);
        firm_info = (LinearLayout) findViewById(R.id.firm_info);
        save= (TextView) findViewById(R.id.save);
        name= (EditText) findViewById(R.id.name);
        needText= (TextView) findViewById(R.id.needText);
        changePsd= (LinearLayout) findViewById(R.id.changePsd);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下键盘上返回按钮
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(adJustToOpen()){
                new AlertDialog.Builder(this)
                        .setMessage("是否保存当前编辑？")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doResult();
                            }
                        })
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                doSave(true);
                            }
                        }).show();
            }else{
                new AlertDialog.Builder(this)
                        .setMessage("您的信息不完整，职位暂时不能公开，是否保存？")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doResult();
                            }
                        })
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                doSave(false);
                            }
                        }).show();
            }
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }
    @Override
    public void onClick(View v) {
  switch (v.getId()){
      case R.id.save:
          if(adJustToOpen()){
              new AlertDialog.Builder(this)
                      .setMessage("是否保存当前编辑？")
                      .setNegativeButton("否", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              doResult();
                          }
                      })
                      .setPositiveButton("是", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int whichButton) {
                              doSave(true);
                          }
                      }).show();
          }else{
              new AlertDialog.Builder(this)
                      .setMessage("您的信息不完整，职位暂时不能公开，是否保存？")
                      .setNegativeButton("否", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              doResult();
                          }
                      })
                      .setPositiveButton("是", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int whichButton) {
                              doSave(false);
                          }
                      }).show();
          }
          break;
      case R.id.turn_left:
          if(adJustToOpen()){
              new AlertDialog.Builder(this)
                      .setMessage("是否保存当前编辑？")
                      .setNegativeButton("否", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              doResult();
                          }
                      })
                      .setPositiveButton("是", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int whichButton) {
                              doSave(true);
                          }
                      }).show();
          }else{
              new AlertDialog.Builder(this)
                      .setMessage("您的信息不完整，职位暂时不能公开，是否保存？")
                      .setNegativeButton("否", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              doResult();
                          }
                      })
                      .setPositiveButton("是", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int whichButton) {
                              doSave(false);
                          }
                      }).show();
          }
          break;
      case R.id.firm_info:
          Intent intent = new Intent(EditInfoActivity.this, FirmInfoActivity.class);
          startActivityForResult(intent, Constants.CHANGEFIRMINFO);
          break;
      case R.id.changePsd:
          Intent intent1=new Intent(EditInfoActivity.this,ChangePsdActivity.class);
          startActivity(intent1);
          break;
      }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.CHANGEFIRMINFO:
                if (resultCode == RESULT_OK) {
                    doAdjust();
                }
                break;
        }
    }
    public void doSave(Boolean isRelease){
        //GlobalProvider.getInstance().employer.setName(name.getText().toString());
        if(name.getText()!=null){employer.setName(name.getText().toString());}
        if(GolEmployer.getDetailedCompanyAddress()!=null){
            if(employer.detailedCompanyAddress==null){
                employer.setDetailedCompanyAddress(new CityBodyUp());
            }
            employer.detailedCompanyAddress.set_city(GolEmployer.getDetailedCompanyAddress()._city.get_id());
            employer.detailedCompanyAddress.setCityCode(GolEmployer.getDetailedCompanyAddress()._city.getCityCode());
        }
        if(GolEmployer.getCompanyAddress()!=null){employer.setCompanyAddress(GolEmployer.getCompanyAddress());}
        if(GolEmployer.getCompanyInfo()!=null){employer.setCompanyInfo(GolEmployer.getCompanyInfo());}
        if(GolEmployer.getCompanyURL()!=null){employer.setCompanyURL(GolEmployer.getCompanyURL());}
        if(GolEmployer.getMainBusiness()!=null){employer.setMainBusiness(GolEmployer.getMainBusiness());}
        if(GolEmployer.getCompanyname()!=null){employer.setCompanyname(GolEmployer.getCompanyname());}
        employer.setIsRelease(isRelease);
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(employer);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            String URL= Constants.personalInfo+"/"+GlobalProvider.getInstance().employerId;
            globalProvider.put(this, URL, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(EditInfoActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
                    doResult();
                    //parseLoginResult(new String(responseBody));
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

    private void doResult() {
        this.setResult(RESULT_OK);
        this.finish();
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
