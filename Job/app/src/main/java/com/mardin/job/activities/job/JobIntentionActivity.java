package com.mardin.job.activities.job;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.Utils.ChinaCityUtil;
import com.mardin.job.Utils.PositionIndustryUtil;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Resume;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;
import java.util.Hashtable;

/**
 * Created by Ryo on 2015/9/15.
 */
public class JobIntentionActivity extends Activity implements View.OnClickListener {
    public TextView expectedIndustry;
    public TextView expectedPosition;
    public TextView expectedAddress;

    public LinearLayout expectedIndustry_layout;
    public LinearLayout expectedPosition_layout;
    public LinearLayout expectedAddress_layout;
    public TextView save;
     public Resume resume;

    public Hashtable<String,String[]> hashtable;
    public String[] industry_arr;
    public String[] position_arr;

    public int chooseItem_industry=0;
    public int getChooseItem_position=0;

    private Hashtable<String, Hashtable<String, String[]>> Hashtable;
    private String[] arrProvince, arrCity, arrRegion;
    private String province="", city="", region="";
    public boolean Ishowing=false;
    public int choose_province;
    public int choose_city;
    public int choose_region;

    public LinearLayout address_select_layout;
    public RelativeLayout layout1;
    public RelativeLayout layout2;
    public RelativeLayout layout3;

    public LinearLayout address_layout;
    public ImageView address_img;

    public TextView layout1_txt;
    public TextView layout2_txt;
    public TextView layout3_txt;

    public TextView SaveToNext;
    //public static String employerId="5628474a583221f00507653b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_job_intention);

        hashtable= PositionIndustryUtil.initPositionIndustryHashtable();
        industry_arr=PositionIndustryUtil.getIndustryCategory(hashtable);

        expectedIndustry= (TextView) findViewById(R.id.expectedIndustry);
        expectedPosition= (TextView) findViewById(R.id.expectedPosition);
        expectedAddress= (TextView) findViewById(R.id.expectedAddress);

        //expectedAddress_layout= (LinearLayout) findViewById(R.id.expectedAddress_layout);
        expectedIndustry_layout= (LinearLayout) findViewById(R.id.expectedIndustry_layout);
        expectedPosition_layout= (LinearLayout) findViewById(R.id.expectedPosition_layout);

        address_layout= (LinearLayout) findViewById(R.id.address_layout);
        //expectedAddress= (TextView) findViewById(R.id.address);
        address_img= (ImageView) findViewById(R.id.address_img);

        address_select_layout= (LinearLayout) findViewById(R.id.address_select_layout);
        layout1= (RelativeLayout) findViewById(R.id.layout1);
        layout2= (RelativeLayout) findViewById(R.id.layout2);
        layout3= (RelativeLayout) findViewById(R.id.layout3);

        layout1_txt= (TextView) findViewById(R.id.layout1_txt);
        layout2_txt= (TextView) findViewById(R.id.layout2_txt);
        layout3_txt= (TextView) findViewById(R.id.layout3_txt);

//        SaveToNext= (TextView) findViewById(R.id.SaveToNext);

        address_layout.setOnClickListener(this);
        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        expectedIndustry_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(JobIntentionActivity.this)
                        .setSingleChoiceItems(industry_arr, 0,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        chooseItem_industry = which;
                                    }
                                }
                        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        expectedIndustry.setText(industry_arr[chooseItem_industry]);
                        expectedPosition.setText("");
                        //GlobalProvider.getInstance().Adress[0]=items_shiping[chooseItem_one];
                        //shipingAdress_Str=items_shiping[chooseItem_one];
                    }
                }).setNegativeButton("取消",null)
                        .show();
            }
        });
        expectedPosition_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position_arr=PositionIndustryUtil.getPositionCategory(hashtable,expectedIndustry.getText().toString());
                new AlertDialog.Builder(JobIntentionActivity.this)
                        .setSingleChoiceItems(position_arr, 0,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        getChooseItem_position = which;
                                    }
                                }
                        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        expectedPosition.setText(position_arr[getChooseItem_position]);
                        //GlobalProvider.getInstance().Adress[0]=items_shiping[chooseItem_one];
                        //shipingAdress_Str=items_shiping[chooseItem_one];
                    }
                }).setNegativeButton("取消",null)
                        .show();
            }
        });

       save= (TextView) findViewById(R.id.save);

        this.resume=GlobalProvider.getInstance().resume;
        if(resume.getExpectedAddress()!=null){expectedAddress.setText(resume.getExpectedAddress());}
        if(resume.getExpectedIndustry()!=null){expectedIndustry.setText(resume.getExpectedIndustry());}
        if(resume.getExpectedPosition()!=null){expectedPosition.setText(resume.getExpectedPosition());}
        save.setOnClickListener(new View.OnClickListener() {
         @Override
          public void onClick(View v) {
           //doCreate();
             doSave();
             }
});
//        SaveToNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GlobalProvider.getInstance().resume.setExpectedIndustry(expectedIndustry.getText().toString());
//                GlobalProvider.getInstance().resume.setExpectedAddress(expectedAddress.getText().toString());
//                GlobalProvider.getInstance().resume.setExpectedPosition(expectedPosition.getText().toString());
//                setResult(Activity.RESULT_OK);
//                finish();
//            }
//        });
        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(JobIntentionActivity.this)
                        .setMessage("是否保存再退出？")
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

            }
        });
        Hashtable = ChinaCityUtil.initChinaCitysHashtable();

        arrProvince=ChinaCityUtil.findAreaStringArr(Hashtable, ChinaCityUtil.TYPE_PROVINCE);

        address_img.setImageResource(R.drawable.turn_down);
        address_select_layout.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.address_layout:
                if(!Ishowing){
                    address_img.setImageResource(R.drawable.turn_up);
                    address_select_layout.setVisibility(View.VISIBLE);
                }else{
                    address_img.setImageResource(R.drawable.turn_down);
                    address_select_layout.setVisibility(View.GONE);
                }
                Ishowing=!Ishowing;
                break;
            case R.id.layout1:
                new AlertDialog.Builder(JobIntentionActivity.this)
                        .setSingleChoiceItems(arrProvince, 0,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        choose_province = which;
                                    }
                                }
                        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        province=arrProvince[choose_province];
                        layout1_txt.setText(province);
                        layout2_txt.setText("");
                        layout3_txt.setText("");
                        //GlobalProvider.getInstance().Adress[0]=items_shiping[chooseItem_one];
                        //shipingAdress_Str=items_shiping[chooseItem_one];
                    }
                }).setNegativeButton("取消",null)
                        .show();
                break;
            case  R.id.layout2:
                if(!layout1_txt.getText().equals("")) {
                    arrCity = ChinaCityUtil.findAreaStringArr(Hashtable, ChinaCityUtil.TYPE_CITY, layout1_txt.getText().toString());
                    new AlertDialog.Builder(JobIntentionActivity.this)
                            .setSingleChoiceItems(arrCity, 0,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            choose_city = which;
                                        }
                                    }
                            ).setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            city = arrCity[choose_city];
                            layout2_txt.setText(city);

                            layout3_txt.setText("");
                            //GlobalProvider.getInstance().Adress[0]=items_shiping[chooseItem_one];
                            //shipingAdress_Str=items_shiping[chooseItem_one];
                        }
                    }).setNegativeButton("取消", null)
                            .show();
                }
                break;
            case R.id.layout3:
                if(!layout1_txt.getText().equals("")&&!layout2_txt.getText().equals("")) {
                    arrRegion = ChinaCityUtil.findAreaStringArr(Hashtable, ChinaCityUtil.TYPE_REGION, layout1_txt.getText().toString(),
                            layout2_txt.getText().toString());
                    new AlertDialog.Builder(JobIntentionActivity.this)
                            .setSingleChoiceItems(arrRegion, 0,
                                    new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            choose_region = which;
                                        }
                                    }
                            ).setPositiveButton("确认", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            region = arrRegion[choose_region];
                            layout3_txt.setText(region);

                            expectedAddress.setText(region);
                            //GlobalProvider.getInstance().Adress[0]=items_shiping[chooseItem_one];
                            //shipingAdress_Str=items_shiping[chooseItem_one];
                        }
                    }).setNegativeButton("取消", null)
                            .show();
                }
                break;
        }
    }
    public void doSave(){
        GlobalProvider.getInstance().resume.setExpectedIndustry(expectedIndustry.getText().toString());
        GlobalProvider.getInstance().resume.setExpectedAddress(expectedAddress.getText().toString());
        GlobalProvider.getInstance().resume.setExpectedPosition(expectedPosition.getText().toString());
        setResult(Activity.RESULT_OK);
        finish();
    }

//    private void doCreate() {
//        Resume resume=new Resume();
//        resume.setExpectedIndustry(expect.getText().toString());
//        JsonFactory jsonFactory = new JsonFactory();
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
//        String json = "";
//        try {
//            json = ow.writeValueAsString(resume);
//            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
//            GlobalProvider globalProvider = GlobalProvider.getInstance();
//            String Url=Constants.createResumeStr+"/"+employerId;
//            globalProvider.post(this,Url, entity, "application/json", new RequestListener() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                    Toast.makeText(JobIntentionActivity.this, "success to create!", Toast.LENGTH_SHORT).show();
//                    //parseLoginResult(new String(responseBody));
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    //Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
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
