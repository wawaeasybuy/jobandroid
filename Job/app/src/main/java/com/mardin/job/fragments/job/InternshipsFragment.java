package com.mardin.job.fragments.job;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.Utils.PositionIndustryUtil;
import com.mardin.job.activities.job.InternshipsSearchActivity;
import com.mardin.job.activities.job.LocationSearchActivity;
import com.mardin.job.activities.job.PositionDetailActivity;
import com.mardin.job.activities.job.PositionSearchActivity;
import com.mardin.job.adapters.job.JobListAdapter;
import com.mardin.job.adapters.job.JobRecAdapter;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Candidate;
import com.mardin.job.models.City;
import com.mardin.job.models.Job;
import com.mardin.job.models.JobList;
import com.mardin.job.models.Resume;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;


/**
 * Created by Ryo on 2015/9/27.
 */
public class InternshipsFragment extends Fragment implements View.OnClickListener {
    public LinearLayout search;
    public LinearLayout classify;
    public LinearLayout position_recommend;
    public LinearLayout noLogin;

    public TextView rec;

    public ListView lv_rec;
    public  View headerView;
    public JobRecAdapter adapter;
    public int page;
    public int itemsPerPage;
    public List<Job> mItems;
    public String retrieval="";

    public EditText search_edit;
    public TextView address;
    public LinearLayout IT;
    public LinearLayout bank;
    public LinearLayout house;
    public LinearLayout industry;
    public LinearLayout hospital;
    public LinearLayout ad;
    public LinearLayout outBuy;
    public LinearLayout other;
    public String country=GlobalProvider.getInstance().country;

    public LinearLayout area;
    public String industryCategory;
    private String[] country_arr={"中国","United States","Singapore"};
    private int choose_country=3;

    public String[] arr;
    public String[] array;
    public Hashtable<String,String[]> hashtable;
    public EditText location;
    public City interCity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_search_internships, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        page=1;
        itemsPerPage=10;
        mItems=new ArrayList<Job>();
        adapter=new JobRecAdapter(getActivity(),mItems);
        initView();
        initAction();
//        hashtable= PositionIndustryUtil.initPositionIndustryHashtable();
//        arr= PositionIndustryUtil.getIndustryCategory(hashtable);
//        array=PositionIndustryUtil.getPositionCategory(hashtable,arr[1]);
        Location mLocation = getLocation(getActivity());
        GeoPoint gp = getGeoByLocation(mLocation);
        Address mAddress = getAddressbyGeoPoint(getActivity(), gp);
//        address.setText("Address: " + mAddress.getCountryName() + ","
//                + mAddress.getLocality() + "," + mAddress.getThoroughfare());
        address.setText(country);
        search_edit.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(getActivity(), InternshipsSearchActivity.class);
                    startActivity(intent);
                }
            }
        });
        location.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent = new Intent(getActivity(), LocationSearchActivity.class);
                    getActivity().startActivityForResult(intent, Constants.GETLOCATIONINTENT);
                }
            }
        });
        lv_rec.addHeaderView(headerView, null, true);
        lv_rec.setAdapter(adapter);
        lv_rec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PositionDetailActivity.class);
                if (position >= 1) {
                    intent.putExtra("job", mItems.get(position - 1));
                    getActivity().startActivity(intent);
                }
            }
        });
        if(GlobalProvider.getInstance().city!=null){
            interCity=GlobalProvider.getInstance().city;
            location.setText(interCity.getC_city());
        }
        lv_rec.setHeaderDividersEnabled(false);
        rec.setText("您还未登录，请前往登录！");
        rec.setTextColor(0xff666666);
        LoadCandidateInfo();
    }
    public void initView(){
        lv_rec= (ListView) getActivity().findViewById(R.id.lv_rec);
        LayoutInflater inflater =  (LayoutInflater)getActivity().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        headerView=inflater.inflate(R.layout.header_view,lv_rec, false);

        search= ( LinearLayout) getActivity().findViewById(R.id.search);
        classify= (LinearLayout) headerView.findViewById(R.id.classify);
        location= (EditText) headerView.findViewById(R.id.location);
        IT= (LinearLayout) headerView.findViewById(R.id.IT);
        bank= (LinearLayout) headerView.findViewById(R.id.bank);
        house= (LinearLayout) headerView.findViewById(R.id.house);
        industry= (LinearLayout) headerView.findViewById(R.id.industry);
        hospital= (LinearLayout) headerView.findViewById(R.id.hospital);
        ad= (LinearLayout) headerView.findViewById(R.id.ad);
        outBuy= (LinearLayout) headerView.findViewById(R.id.outBuy);
        other= (LinearLayout) headerView.findViewById(R.id.other);

        rec= (TextView) headerView.findViewById(R.id.rec);
        search_edit= (EditText) getActivity().findViewById(R.id.search_edit);
        address= (TextView) getActivity().findViewById(R.id.address);
        area= (LinearLayout) getActivity().findViewById(R.id.area);
        //position_recommend= (LinearLayout) getActivity().findViewById(R.id.position_recommend);
    }
    public void initAction(){
        search.setOnClickListener(this);
        IT.setOnClickListener(this);
        bank.setOnClickListener(this);
        house.setOnClickListener(this);
        industry.setOnClickListener(this);
        hospital.setOnClickListener(this);
        ad.setOnClickListener(this);
        outBuy.setOnClickListener(this);
        other.setOnClickListener(this);
        area.setOnClickListener(this);

        //classify.setOnClickListener(this);
        // position_recommend.setOnClickListener(this);
    }
    public void LoadRecJobList(){
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("itemsPerPage", itemsPerPage);
//        if(GlobalProvider.getInstance().resume.getExpectedPosition()!=null){
//            retrieval=GlobalProvider.getInstance().resume.getExpectedPosition();
//            params.put("retrieval",retrieval);
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            String url=Constants.recommendStr+"/"+GlobalProvider.getInstance().candidate.get_id();
            globalProvider.get(getActivity(), url, params, new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    parseJobList(new String(responseBody));
//                noLogin.setVisibility(View.GONE);
//                lv_rec.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Log.v("err", new String(responseBody));
//                noLogin.setVisibility(View.VISIBLE);
//                lv_rec.setVisibility(View.GONE);
                    rec.setText("暂无可推荐职位！");
                    rec.setTextColor(0xffee2400);
                }

                @Override
                public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

                }
            });
       // }
//        else{
//            rec.setText("暂无可推荐职位！");
//            rec.setTextColor(0xffee2400);
//        }
    }
    public void parseJobList(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobList joblist = (JobList) objectMapper.readValue(jsonParser, JobList.class);
            if(joblist.jobs.size()>0){
                rec.setText("职位推荐");
                rec.setTextColor(0xffee2400);
            }else{
                rec.setText("暂无可推荐职位！");
                rec.setTextColor(0xffee2400);
            }
            this.mItems.clear();
            this.mItems.addAll(joblist.jobs);
            //GlobalProvider.getInstance().shangpingListDefault=mItems;
            adapter.notifyDataSetChanged();
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void LoadCandidateInfo(){
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(getActivity(), Constants.regCanStr, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseInfo(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }
    public void parseInfo(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            Candidate candidate = (Candidate) objectMapper.readValue(jsonParser, Candidate.class);
            GlobalProvider.getInstance().candidate=candidate;
//            if(candidate.resume!=null){
//                GlobalProvider.getInstance().resume=candidate.resume;
//            }
            LoadRecJobList();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search:
                Intent intent = new Intent(getActivity(), InternshipsSearchActivity.class);
                startActivity(intent);
                break;
//            case R.id.classify:
//                industryCategory="XIAOZHANG";
//                Intent intent1 = new Intent(getActivity(), PositionSearchActivity.class);
//                intent1.putExtra("industryCategory",industryCategory);
//                startActivity(intent1);
//                break;
            case R.id.IT:
                if(interCity==null){
                    new AlertDialog.Builder(getActivity())
                            .setMessage("地址不能为空！")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();
                }else{
                    industryCategory="互联网";
                    Intent intent2 = new Intent(getActivity(), PositionSearchActivity.class);
                    intent2.putExtra("industryCategory",industryCategory);
                    intent2.putExtra("city",interCity.get_id());
                    startActivity(intent2);
                }

                break;
            case R.id.bank:
                if(interCity==null){
                    new AlertDialog.Builder(getActivity())
                            .setMessage("地址不能为空！")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();
                }else{
                industryCategory="金融财会";
                Intent intent3 = new Intent(getActivity(), PositionSearchActivity.class);
                intent3.putExtra("industryCategory",industryCategory);
                    intent3.putExtra("city",interCity.get_id());
                    startActivity(intent3);
                }
                break;
            case R.id.house:
                if(interCity==null){
                    new AlertDialog.Builder(getActivity())
                            .setMessage("地址不能为空！")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();
                }else{
                industryCategory="房产建筑";
                Intent intent4 = new Intent(getActivity(), PositionSearchActivity.class);
                intent4.putExtra("industryCategory",industryCategory);
                    intent4.putExtra("city",interCity.get_id());
                    startActivity(intent4);
                }
                break;
            case R.id.industry:
                if(interCity==null){
                    new AlertDialog.Builder(getActivity())
                            .setMessage("地址不能为空！")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();
                }else{
                industryCategory="工业制造";
                Intent intent5 = new Intent(getActivity(), PositionSearchActivity.class);
                intent5.putExtra("industryCategory",industryCategory);
                    intent5.putExtra("city",interCity.get_id());
                    startActivity(intent5);
                }
                break;
            case R.id.hospital:
                if(interCity==null){
                    new AlertDialog.Builder(getActivity())
                            .setMessage("地址不能为空！")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();
                }else {
                    industryCategory = "医药化学";
                    Intent intent6 = new Intent(getActivity(), PositionSearchActivity.class);
                    intent6.putExtra("industryCategory", industryCategory);
                    intent6.putExtra("city",interCity.get_id());
                    startActivity(intent6);
                }
                break;
            case R.id.ad:
                if(interCity==null){
                    new AlertDialog.Builder(getActivity())
                            .setMessage("地址不能为空！")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();
                }else {
                    industryCategory = "广告传媒";
                    Intent intent7 = new Intent(getActivity(), PositionSearchActivity.class);
                    intent7.putExtra("industryCategory", industryCategory);
                    intent7.putExtra("city", interCity.get_id());
                    startActivity(intent7);
                }
                break;
            case R.id.outBuy:
                if(interCity==null){
                    new AlertDialog.Builder(getActivity())
                            .setMessage("地址不能为空！")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();
                }else {
                    industryCategory = "外贸行业";
                    Intent intent8 = new Intent(getActivity(), PositionSearchActivity.class);
                    intent8.putExtra("industryCategory", industryCategory);
                    intent8.putExtra("city",interCity.get_id());
                    startActivity(intent8);
                }
                break;
            case R.id.other:
                if(interCity==null){
                    new AlertDialog.Builder(getActivity())
                            .setMessage("地址不能为空！")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).show();
                }else {
                    industryCategory = "其他行业";
                    Intent intent9 = new Intent(getActivity(), PositionSearchActivity.class);
                    intent9.putExtra("industryCategory", industryCategory);
                    intent9.putExtra("city",interCity.get_id());
                    startActivity(intent9);
                }
                break;
            case R.id.area:
                new AlertDialog.Builder(getActivity())
                        .setSingleChoiceItems(country_arr, 0,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        choose_country = which;
                                    }
                                }
                        ).setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choose_country == 3) {
                            GlobalProvider.getInstance().country = country_arr[0];
                        }else{
                            GlobalProvider.getInstance().country = country_arr[choose_country];
                        }
                        address.setText(GlobalProvider.getInstance().country);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GlobalProvider.getInstance().country = country_arr[0];
                        address.setText(GlobalProvider.getInstance().country);
                    }
                }).show();
                break;
        }
    }
    public Location getLocation(Context context) {
        LocationManager locMan = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        Location location = locMan
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locMan
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        return location;
    }
    public GeoPoint getGeoByLocation(Location location) {
        GeoPoint gp = null;
        try {
            if (location != null) {
                double geoLatitude = location.getLatitude() * 1E6;
                double geoLongitude = location.getLongitude() * 1E6;
                gp = new GeoPoint((int) geoLatitude, (int) geoLongitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gp;
    }
    public Address getAddressbyGeoPoint(Context cntext, GeoPoint gp) {
        Address result = null;
        try {
            if (gp == null) {
                Toast.makeText(getActivity().getApplicationContext(), "抱歉,暂未获取到信息", Toast.LENGTH_SHORT).show();
            }
            if (gp != null) {
                Geocoder gc = new Geocoder(cntext, Locale.CHINA);
                double geoLatitude = (int) gp.getLatitudeE6() / 1E6;
                double geoLongitude = (int) gp.getLongitudeE6() / 1E6;
                List<Address> lstAddress = gc.getFromLocation(geoLatitude,
                        geoLongitude, 1);
                if (lstAddress.size() > 0) {
                    result = lstAddress.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
