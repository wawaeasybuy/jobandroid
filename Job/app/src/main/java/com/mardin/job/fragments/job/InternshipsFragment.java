package com.mardin.job.fragments.job;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.mardin.job.activities.job.InternshipsSearchActivity;
import com.mardin.job.activities.job.PositionDetailActivity;
import com.mardin.job.activities.job.PositionSearchActivity;
import com.mardin.job.adapters.job.JobListAdapter;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Job;
import com.mardin.job.models.JobList;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
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
    public JobListAdapter adapter;
    public int page;
    public int itemsPerPage;
    public List<Job> mItems;
    public String retrieval="";

    public EditText search_edit;
    public TextView address;
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
        adapter=new JobListAdapter(getActivity(),mItems);
        initView();
        initAction();

        Location mLocation = getLocation(getActivity());
        GeoPoint gp = getGeoByLocation(mLocation);
        Address mAddress = getAddressbyGeoPoint(getActivity(), gp);
//        address.setText("Address: " + mAddress.getCountryName() + ","
//                + mAddress.getLocality() + "," + mAddress.getThoroughfare());
        address.setText(mAddress.getThoroughfare());
        search_edit.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Intent intent=new Intent(getActivity(),InternshipsSearchActivity.class);
                    startActivity(intent);
                }
            }
        });
        lv_rec.addHeaderView(headerView);
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
        if(GlobalProvider.getInstance().resume.get_id()!=null){
            LoadRecJobList();
        }
    }
    public void initView(){
        lv_rec= (ListView) getActivity().findViewById(R.id.lv_rec);
        LayoutInflater inflater =  (LayoutInflater)getActivity().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        headerView=inflater.inflate(R.layout.header_view,lv_rec, false);


        search= ( LinearLayout) getActivity().findViewById(R.id.search);
        classify= (LinearLayout) headerView.findViewById(R.id.classify);
        rec= (TextView) headerView.findViewById(R.id.rec);
        search_edit= (EditText) getActivity().findViewById(R.id.search_edit);
        address= (TextView) getActivity().findViewById(R.id.address);
        //position_recommend= (LinearLayout) getActivity().findViewById(R.id.position_recommend);

    }
    public void initAction(){
        search.setOnClickListener(this);
        classify.setOnClickListener(this);
        // position_recommend.setOnClickListener(this);
    }
    public void LoadRecJobList(){
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("itemsPerPage", itemsPerPage);
        if(GlobalProvider.getInstance().resume.getExpectedPosition()!=null){
            retrieval=GlobalProvider.getInstance().resume.getExpectedPosition();
            params.put("retrieval",retrieval);
        }
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(getActivity(), Constants.jobListUrlStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseJobList(new String(responseBody));
//                noLogin.setVisibility(View.GONE);
//                lv_rec.setVisibility(View.VISIBLE);
                rec.setText("职位推荐");
                rec.setTextColor(0xffee2400);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.v("err", new String(responseBody));
//                noLogin.setVisibility(View.VISIBLE);
//                lv_rec.setVisibility(View.GONE);
                rec.setText("您还未登录，请前往登录！");
                rec.setTextColor(0xff666666);

            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }
    public void parseJobList(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobList joblist = (JobList) objectMapper.readValue(jsonParser, JobList.class);
            this.mItems.clear();
            this.mItems.addAll(joblist.jobs);
            //GlobalProvider.getInstance().shangpingListDefault=mItems;
            adapter.notifyDataSetChanged();
            //do something
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
            case R.id.classify:
                Intent intent1 = new Intent(getActivity(), PositionSearchActivity.class);
                startActivity(intent1);
                break;
//            case R.id.position_recommend:
//                Intent intent2 = new Intent(getActivity(), PositionDetailActivity.class);
//                startActivity(intent2);
//                break;
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
