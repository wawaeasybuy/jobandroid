package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.TagLostException;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.Utils.ChinaCityUtil;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.models.Employer;
import com.example.ryo.job_employer.network.Constants;

import java.util.Hashtable;

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
    public EditText CompanyAddress;
    public TextView DetailedCompanyAddress;
    public Employer GoEmployer=GlobalProvider.getInstance().employer;

    public LinearLayout address_layout;
    public TextView address;
    public ImageView address_img;

//    public LinearLayout address_select_layout;
//    public RelativeLayout layout1;
//    public RelativeLayout layout2;
//    public RelativeLayout layout3;
//
//    public TextView layout1_txt;
//    public TextView layout2_txt;
//    public TextView layout3_txt;

    private Hashtable<String, Hashtable<String, String[]>> hashtable;
    //    private String[] arrProvince, arrCity, arrRegion;
//    private String province="", city="", region="";
//    public boolean Ishowing=false;
//    public int choose_province;
//    public int choose_city;
//    public int choose_region;
    //public String country=GlobalProvider.getInstance().country;
    public String[] USA={"Alabama","Alaska","American Samoa","Arizona","Arkansas","Armed Forces Africa","Armed Forces Americas","Armed Forces Canada", "Armed Forces Europe", "Armed Forces Middle East", "Armed Forces Pacific", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Federated States Of Micronesia", "Florida", "Georgia", "Guam", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Marshall Islands", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Northern Mariana Islands", "Ohio", "Oklahoma", "Oregon", "Palau", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virgin Islands", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm_info);
        initView();
        initAction();
        hashtable = ChinaCityUtil.initChinaCitysHashtable();

        if(GoEmployer.companyname!=null){name.setText(GoEmployer.getCompanyname());}
        if(GoEmployer.mainBusiness!=null){mainBusiness.setText(GoEmployer.getMainBusiness());}
        if(GoEmployer.companyInfo!=null){description.setText(GoEmployer.getCompanyInfo());}
        if(GoEmployer.companyAddress!=null){CompanyAddress.setText(GoEmployer.getCompanyAddress());}
        if(GoEmployer.companyURL!=null){companyURL.setText(GoEmployer.getCompanyURL());}
        if(GoEmployer.detailedCompanyAddress!=null)
        {
            if(GoEmployer.detailedCompanyAddress._city!=null){
            DetailedCompanyAddress.setText(GoEmployer.detailedCompanyAddress._city.getC_city());
            }
        }
        //if(country.equals("中国")){
//        arrProvince=ChinaCityUtil.findAreaStringArr(hashtable, ChinaCityUtil.TYPE_PROVINCE);
//       if(GoEmployer.province!=null){
//            layout1_txt.setText(GoEmployer.getProvince());
//            if(GoEmployer.city!=null){
//                layout2_txt.setText(GoEmployer.getCity());
//                if(GoEmployer.region!=null){
//                    layout3_txt.setText(GoEmployer.getRegion());
//                    address.setText(GoEmployer.getProvince()+"."+GoEmployer.getCity()+"."+GoEmployer.getRegion());
//                }
//            }
//        }
//        }else{
//            if(GoEmployer.province!=null){
//                address.setText(GoEmployer.getProvince());
//            }
//            arrProvince=USA;
//        }
        //address_select_layout.setVisibility(View.GONE);
    }
    private void initAction() {
        turn_left.setOnClickListener(this);
        save.setOnClickListener(this);
        address_layout.setOnClickListener(this);
//        layout1.setOnClickListener(this);
//        layout2.setOnClickListener(this);
//        layout3.setOnClickListener(this);
    }
    private void initView() {
        turn_left = (ImageView) findViewById(R.id.turn_left);
        save= (TextView) findViewById(R.id.save);
        name= (EditText) findViewById(R.id.name);
        mainBusiness= (EditText) findViewById(R.id.main_business);
        description= (EditText) findViewById(R.id.description);
        companyURL= (EditText) findViewById(R.id.companyURL);
        DetailedCompanyAddress= (TextView) findViewById(R.id.DetailedCompanyAddress);
        CompanyAddress= (EditText) findViewById(R.id.CompanyAddress);
        address_layout= (LinearLayout) findViewById(R.id.address_layout);
        address= (TextView) findViewById(R.id.address);
        address_img= (ImageView) findViewById(R.id.address_img);

//        address_select_layout= (LinearLayout) findViewById(R.id.address_select_layout);
//        layout1= (RelativeLayout) findViewById(R.id.layout1);
//        layout2= (RelativeLayout) findViewById(R.id.layout2);
//        layout3= (RelativeLayout) findViewById(R.id.layout3);
//
//        layout1_txt= (TextView) findViewById(R.id.layout1_txt);
//        layout2_txt= (TextView) findViewById(R.id.layout2_txt);
//        layout3_txt= (TextView) findViewById(R.id.layout3_txt);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.GETLOCATIONINTENT:
                if (resultCode == RESULT_OK) {
                    if(GlobalProvider.getInstance().city!=null){
                        DetailedCompanyAddress.setText(GlobalProvider.getInstance().city.getC_city());
                        GlobalProvider.getInstance().employer.detailedCompanyAddress.set_city(GlobalProvider.getInstance().city);
                    }
                }
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.turn_left:
                new AlertDialog.Builder(this)
                        .setMessage("是否保存当前编辑？")
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(Activity.RESULT_OK);
                                finish();
                            }
                        })
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                doSave();
                            }
                        }).show();
                break;
            case R.id.save:
                doSave();
                break;
            case R.id.address_layout:
                Intent intent=new Intent(FirmInfoActivity.this,LocationSearchActivity.class);
                startActivityForResult(intent, Constants.GETLOCATIONINTENT);
//          if(country.equals("中国")){
//          if(!Ishowing){
//              address_img.setImageResource(R.drawable.turn_up);
//              address_select_layout.setVisibility(View.VISIBLE);
//          }else{
//              address_img.setImageResource(R.drawable.turn_down);
//              address_select_layout.setVisibility(View.GONE);
//          }
//          Ishowing=!Ishowing;
//          }else{
//              new AlertDialog.Builder(FirmInfoActivity.this)
//                      .setSingleChoiceItems(arrProvince, 0,
//                              new DialogInterface.OnClickListener() {
//                                  public void onClick(DialogInterface dialog, int which) {
//                                      choose_province = which;
//                                  }
//                              }
//                      ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                  @Override
//                  public void onClick(DialogInterface dialog, int which) {
//                      province=arrProvince[choose_province];
//                      address.setText(province);
//                      //GlobalProvider.getInstance().Adress[0]=items_shiping[chooseItem_one];
//                      //shipingAdress_Str=items_shiping[chooseItem_one];
//                  }
//              }).setNegativeButton("取消",null)
//                      .show();
//          }
//          break;
//      case R.id.layout1:
//          new AlertDialog.Builder(FirmInfoActivity.this)
//                  .setSingleChoiceItems(arrProvince, 0,
//                          new DialogInterface.OnClickListener() {
//                              public void onClick(DialogInterface dialog, int which) {
//                                  choose_province = which;
//                              }
//                          }
//                  ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
//              @Override
//              public void onClick(DialogInterface dialog, int which) {
//                  province=arrProvince[choose_province];
//                  layout1_txt.setText(province);
//                  layout2_txt.setText("");
//                  layout3_txt.setText("");
//                  //GlobalProvider.getInstance().Adress[0]=items_shiping[chooseItem_one];
//                  //shipingAdress_Str=items_shiping[chooseItem_one];
//              }
//          }).setNegativeButton("取消",null)
//                  .show();
//          break;
//      case  R.id.layout2:
//          if(!layout1_txt.getText().equals("")) {
//              arrCity = ChinaCityUtil.findAreaStringArr(hashtable, ChinaCityUtil.TYPE_CITY, layout1_txt.getText().toString());
//              new AlertDialog.Builder(FirmInfoActivity.this)
//                      .setSingleChoiceItems(arrCity, 0,
//                              new DialogInterface.OnClickListener() {
//
//                                  public void onClick(DialogInterface dialog, int which) {
//                                      choose_city = which;
//                                  }
//                              }
//                      ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
//
//                  @Override
//                  public void onClick(DialogInterface dialog, int which) {
//                      city = arrCity[choose_city];
//                      layout2_txt.setText(city);
//
//                      layout3_txt.setText("");
//                      //GlobalProvider.getInstance().Adress[0]=items_shiping[chooseItem_one];
//                      //shipingAdress_Str=items_shiping[chooseItem_one];
//                  }
//              }).setNegativeButton("取消", null)
//                      .show();
//          }
                break;
//      case R.id.layout3:
//          if(!layout1_txt.getText().equals("")&&!layout2_txt.getText().equals("")) {
//              arrRegion = ChinaCityUtil.findAreaStringArr(hashtable, ChinaCityUtil.TYPE_REGION, layout1_txt.getText().toString(),
//                      layout2_txt.getText().toString());
//              new AlertDialog.Builder(FirmInfoActivity.this)
//                      .setSingleChoiceItems(arrRegion, 0,
//                              new DialogInterface.OnClickListener() {
//
//                                  public void onClick(DialogInterface dialog, int which) {
//                                      choose_region = which;
//                                  }
//                              }
//                      ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
//
//                  @Override
//                  public void onClick(DialogInterface dialog, int which) {
//                      region = arrRegion[choose_region];
//                      layout3_txt.setText(region);
//
//                      address.setText(province + "." + city + "." + region);
//                      //GlobalProvider.getInstance().Adress[0]=items_shiping[chooseItem_one];
//                      //shipingAdress_Str=items_shiping[chooseItem_one];
//                  }
//              }).setNegativeButton("取消", null)
//                      .show();
//          }
//          break;
        }
    }

    private void doSave() {
        GlobalProvider.getInstance().employer.setCompanyname(name.getText().toString());
        GlobalProvider.getInstance().employer.setMainBusiness(mainBusiness.getText().toString());
        GlobalProvider.getInstance().employer.setCompanyInfo(description.getText().toString());
        GlobalProvider.getInstance().employer.setCompanyAddress(CompanyAddress.getText().toString());
        //GlobalProvider.getInstance().employer.detailedCompanyAddress.set_city(GlobalProvider.getInstance().city);
        GlobalProvider.getInstance().employer.setCompanyURL(companyURL.getText().toString());
//        if(country.equals("中国")){
//            GlobalProvider.getInstance().employer.setProvince(layout1_txt.getText().toString());
//            GlobalProvider.getInstance().employer.setCity(layout2_txt.getText().toString());
//            GlobalProvider.getInstance().employer.setRegion(layout3_txt.getText().toString());
//        }else{
//            GlobalProvider.getInstance().employer.setProvince(province);
//        }
        Toast.makeText(FirmInfoActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下键盘上返回按钮
        if(keyCode == KeyEvent.KEYCODE_BACK){
            new AlertDialog.Builder(this)
                    .setMessage("是否保存当前编辑？")
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    })
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            doSave();
                        }
                    }).show();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
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