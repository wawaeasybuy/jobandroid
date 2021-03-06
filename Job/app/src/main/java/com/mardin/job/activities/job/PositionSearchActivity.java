package com.mardin.job.activities.job;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.Utils.ChinaAlphabetComparator;
import com.mardin.job.Utils.ChinaCityUtil;
import com.mardin.job.Utils.PositionIndustryUtil;
import com.mardin.job.adapters.job.AddressSelectListAdapter;
import com.mardin.job.adapters.job.AddressSelectListAdapter_lv2;
import com.mardin.job.adapters.job.AddressSelectListAdapter_lv3;
import com.mardin.job.adapters.job.JobListAdapter;
import com.mardin.job.adapters.job.PositionFitHeaderAdapter;
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
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Ryo on 2015/9/17.
 */
public class PositionSearchActivity extends Activity implements View.OnClickListener{
    public LinearLayout turn_left;
    public ListView lv_job;
    public List<Job> mItems;
    public JobListAdapter adapter;
    public int page;
    public int itemsPerPage;
    public LinearLayout address;
    public LinearLayout selectAddress;
    public boolean lv1_showing=false;
    public boolean lv2_showing=false;
    public boolean lv3_showing=false;

    public AddressSelectListAdapter mAdapter;
    public AddressSelectListAdapter_lv2 mAdapter_lv2;
    public AddressSelectListAdapter_lv3 mAdapter_lv3;
    public ListView lv1,lv2,lv3;
    public ImageView turn_one,turn_two,turn_three,img1,img2;

    private ChinaAlphabetComparator comparator;
    private Hashtable<String, Hashtable<String, String[]>> hashtable;
    private String[] arrProvince, arrCity, arrRegion;
    //private String province="", city="", region="";
    public String industryCategory="";
    public String PositionCategory="";
    public String[] positionCategory;
    public Hashtable hashtable_position;
    public ListView lv_position_pull;
    public LinearLayout position_Layout;
    public TextView address_text;
    public TextView position_Text;
    public ImageView position_Img;
    public PositionFitHeaderAdapter Adapter;
    public Boolean ishowing=false;
    public TextView industry;
    public ImageView search;
    public int Position;
    public String[] ARR;
    public SwipeRefreshLayout swiperefresh;
    private Boolean mNomore=false;
    public TextView noResult;
    public String country=GlobalProvider.getInstance().country;
    public  AddressSelectListAdapter adapterProvince;
    public String city;

    public String[] USA={"Alabama","Alaska","American Samoa","Arizona","Arkansas","Armed Forces Africa","Armed Forces Americas","Armed Forces Canada", "Armed Forces Europe", "Armed Forces Middle East", "Armed Forces Pacific", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Federated States Of Micronesia", "Florida", "Georgia", "Guam", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Marshall Islands", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Northern Mariana Islands", "Ohio", "Oklahoma", "Oregon", "Palau", "Pennsylvania", "Puerto Rico", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virgin Islands", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internships_position_search);
        initView();
        initAction();
        Intent intent = this.getIntent();
        if(intent.getSerializableExtra("industryCategory")!=null){
            industryCategory= (String) intent.getSerializableExtra("industryCategory");
            industry.setText(industryCategory);
        }
        if(intent.getSerializableExtra("city")!=null){
            city= (String) intent.getSerializableExtra("city");
        }
        hashtable_position=PositionIndustryUtil.initPositionIndustryHashtable();
        positionCategory= PositionIndustryUtil.getPositionCategory(hashtable_position,industryCategory);
//        comparator = new ChinaAlphabetComparator();
//        hashtable = ChinaCityUtil.initChinaCitysHashtable();
//        if(country.equals("中国")){
//            arrProvince = ChinaCityUtil.findAreaStringArr(hashtable, ChinaCityUtil.TYPE_PROVINCE);
//            adapterProvince = getArrayAdapter(arrProvince);
//        }else if(country.equals("United States"))
//        {
//            adapterProvince = getArrayAdapter(USA);
//            arrProvince=USA;
//            //arrProvince=USA;
//        }else if(country.equals("Singapore")){
//            adapterProvince = getArrayAdapter(USA);
//            arrProvince=USA;
//        }

//        lv1.setAdapter(adapterProvince);
//        lv1.setOnItemClickListener(this);
//        lv2.setOnItemClickListener(this);
//        lv3.setOnItemClickListener(this);
        mItems=new ArrayList<Job>();
        adapter=new JobListAdapter(this,mItems);
//        mAdapter=new AddressSelectListAdapter(this,Data());
//        mAdapter_lv2=new AddressSelectListAdapter_lv2(this,Data());
//        mAdapter_lv3=new AddressSelectListAdapter_lv3(this,Data());
//        lv1.setAdapter(mAdapter);
//        lv2.setAdapter(mAdapter_lv2);
//        lv3.setAdapter(mAdapter_lv3);
        lv_job.setAdapter(adapter);
        page=1;
        itemsPerPage=10;
//        selectAddress.setVisibility(View.GONE);
//        turn_one.setImageResource(R.drawable.turn_down);
        //turn_two.setImageResource(R.drawable.turn_down);
        turn_three.setImageResource(R.drawable.turn_down);

        Adapter=new PositionFitHeaderAdapter(this,positionCategory);
        lv_position_pull.setAdapter(Adapter);
        lv_position_pull.setVisibility(View.GONE);
        position_Img.setImageResource(R.drawable.turn_down);

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNomore = false;
                page = 1;
                LoadJobListByCondition();
//                if (GlobalProvider.getInstance().ShangpingHeaderLoadCategory.equals("")) {
//                    loadJobList();
//                } else {
//                    loadProduct(GlobalProvider.getInstance().ShangpingHeaderLoadCategory);
//                }
            }
        });

        position_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ishowing){
                    position_Img.setImageResource(R.drawable.turn_up);
                    lv_position_pull.setVisibility(View.VISIBLE);
//                    lv1.setVisibility(View.GONE);
//                    lv2.setVisibility(View.GONE);
//                    lv3.setVisibility(View.GONE);
//                    selectAddress.setVisibility(View.GONE);
                    lv_job.setVisibility(View.GONE);
//                    turn_one.setImageResource(R.drawable.turn_down);
                }else{
                    position_Img.setImageResource(R.drawable.turn_down);
                    lv_position_pull.setVisibility(View.GONE);
//                    lv1.setVisibility(View.GONE);
//                    lv2.setVisibility(View.GONE);
//                    lv3.setVisibility(View.GONE);
//                    selectAddress.setVisibility(View.GONE);
                    lv_job.setVisibility(View.VISIBLE);
                    //turn_one.setImageResource(R.drawable.turn_down);
                }
                ishowing=!ishowing;
                //lv1_showing=false;
            }
        });
        lv_position_pull.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position_Img.setImageResource(R.drawable.turn_down);
                lv_position_pull.setVisibility(View.GONE);
//                lv1.setVisibility(View.GONE);
//                lv2.setVisibility(View.GONE);
//                lv3.setVisibility(View.GONE);
//                selectAddress.setVisibility(View.GONE);
                lv_job.setVisibility(View.VISIBLE);
                //turn_one.setImageResource(R.drawable.turn_down);
                ishowing=false;
                page=1;
                if(position>0){
                    PositionCategory=positionCategory[position];
                }else{
                    PositionCategory="";
                }
                position_Text.setText(positionCategory[position]);
                LoadJobListByCondition();
            }
        });
//        lv1.setVisibility(View.GONE);
//        lv2.setVisibility(View.GONE);
//        lv3.setVisibility(View.GONE);

//        mAdapter.setOnItemClickListener(new AddressSelectListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                lv2.setVisibility(View.VISIBLE);
//                lv3.setVisibility(View.INVISIBLE);
//            }
//        });
//        mAdapter_lv2.setOnItemClickListener(new AddressSelectListAdapter_lv2.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                lv3.setVisibility(View.VISIBLE);
//            }
//        });
//        mAdapter_lv3.setOnItemClickListener(new AddressSelectListAdapter_lv3.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//        });
        lv_job.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PositionSearchActivity.this,PositionDetailActivity.class);
                intent.putExtra("job",mItems.get(position));
                startActivity(intent);
            }
        });
        lv_job.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (!mNomore && lv_job.getCount() != 0 && lv_job.getLastVisiblePosition() >= (lv_job.getCount() - 1)) {
                    loadMoreJobList();
                }
            }
        });
        noResult.setVisibility(View.GONE);
        LoadJobListByCondition();
    }
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        if (parent == lv1) {
//            if(country.equals("中国")){
//                province = arrProvince[position];
//                modifyCity(province);
//                lv2.setVisibility(View.VISIBLE);
//                lv3.setVisibility(View.INVISIBLE);
//                img1.setVisibility(View.VISIBLE);
//                img2.setVisibility(View.VISIBLE);
//            }else{
//                province = arrProvince[position];
//                city="";
//                region="";
//                page=1;
//                lv1.setVisibility(View.GONE);
//                lv2.setVisibility(View.GONE);
//                lv3.setVisibility(View.GONE);
//                selectAddress.setVisibility(View.GONE);
//                lv_job.setVisibility(View.VISIBLE);
//                turn_one.setImageResource(R.drawable.turn_down);
//                address_text.setText(province);
//                lv1_showing=false;
//                LoadJobListByCondition();
//            }
//        } else if (parent == lv2) {
//            city = arrCity[position];
//            modifyRegion(province, city);
//            lv3.setVisibility(View.VISIBLE);
//        } else if (parent == lv3) {
//            page=1;
//            this.Position=position;
//            region = ARR[position];
//            lv1.setVisibility(View.GONE);
//            lv2.setVisibility(View.GONE);
//            lv3.setVisibility(View.GONE);
//            selectAddress.setVisibility(View.GONE);
//            lv_job.setVisibility(View.VISIBLE);
//            turn_one.setImageResource(R.drawable.turn_down);
//            address_text.setText(region);
//            lv1_showing=false;
//            LoadJobListByCondition();
//            //txtInfo.setText(province + " " + city + " " + region);
//        }
//    }
    private void modifyCity(String province) {
        arrCity = ChinaCityUtil.findAreaStringArr(hashtable, ChinaCityUtil.TYPE_CITY, province);
        AddressSelectListAdapter adapterCity = getArrayAdapter(arrCity);
        lv2.setAdapter(adapterCity);
    }
    private void modifyRegion(String province, String city) {
        arrRegion = ChinaCityUtil.findAreaStringArr(hashtable, ChinaCityUtil.TYPE_REGION, province,
                city);
        AddressSelectListAdapter_lv3 adapter_lv3=new AddressSelectListAdapter_lv3(this,changeList(arrRegion,city));
        //AddressSelectListAdapter adapterRegion = getArrayAdapter(changeList(arrRegion,city));
        lv3.setAdapter(adapter_lv3);
    }
    private AddressSelectListAdapter getArrayAdapter(String[] arr) {
        AddressSelectListAdapter adapter = new AddressSelectListAdapter(this,arr);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter.sort(comparator);
        return adapter;
    }
    public String[] changeList(String[] arr,String city_name){
        ARR= Arrays.copyOf(arr, arr.length + 1);
        ARR[0]=city_name;
        for(int i=0;i<arr.length;i++){
            ARR[i+1]=arr[i];
        }
        return ARR;
    }
    public void LoadJobList(){
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("itemsPerPage", itemsPerPage);
        params.put("industryCategory", industryCategory);

        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.jobListUrlStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseJobList(new String(responseBody));
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.v("err", new String(responseBody));

            }
            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }
    private void LoadJobListByCondition(){
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("itemsPerPage", itemsPerPage);
        params.put("industryCategory", industryCategory);
        if(!PositionCategory.equals("")) {
            params.put("positionCategory", PositionCategory);
        }
        if(city!=null){
            params.put("city",city);
        }
//        if(!region.equals("")){
//            if(Position>0){
//            params.put("province",province);
//            params.put("city",city);
//            params.put("region",region);
//            }else{
//                params.put("province",province);
//                params.put("city",city);
//            }
//        }
//        if(!province.equals("")){
//            params.put("province",province);
//        }
//        if(!city.equals("")){
//            params.put("city",city);
//        }
//        if(!region.equals("")&&Position>0){
//            params.put("region",region);
//        }
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.jobListUrlStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseJobList(new String(responseBody));
                swiperefresh.setRefreshing(false);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.v("err", new String(responseBody));
                swiperefresh.setRefreshing(false);

            }
            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }
    public void loadMoreJobList(){
        page = page + 1;
        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("itemsPerPage", itemsPerPage);
        params.put("industryCategory", industryCategory);
        if(!PositionCategory.equals("")) {
            params.put("positionCategory", PositionCategory);
        }
        if(city!=null){
            params.put("city",city);
        }
//        if(!region.equals("")){
//            if(Position>0){
//                params.put("province",province);
//                params.put("city",city);
//                params.put("region",region);
//            }else{
//                params.put("province",province);
//                params.put("city",city);
//            }
//        }
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.jobListUrlStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseMoreJobList(new String(responseBody));
                //swiperefresh.setRefreshing(false);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Log.v("err", new String(responseBody));
                //swiperefresh.setRefreshing(false);

            }
            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }
    public void parseMoreJobList(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobList joblist = (JobList) objectMapper.readValue(jsonParser, JobList.class);
            if(joblist.jobs.size()<itemsPerPage){
                mNomore = true;
            }
            this.mItems.addAll(joblist.jobs);
            //GlobalProvider.getInstance().shangpingListDefault=mItems;
            adapter.notifyDataSetChanged();
            //loadme();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void parseJobList(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobList joblist = (JobList) objectMapper.readValue(jsonParser, JobList.class);
            this.mItems.clear();
            this.mItems.addAll(joblist.jobs);
            //GlobalProvider.getInstance().shangpingListDefault=mItems;
            adapter.notifyDataSetChanged();
            if(joblist.jobs.size()>0){
                lv_job.setVisibility(View.VISIBLE);
                noResult.setVisibility(View.GONE);
            }else{
                lv_job.setVisibility(View.GONE);
                noResult.setVisibility(View.VISIBLE);
            }
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initView(){
        swiperefresh= (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        turn_left = (LinearLayout) findViewById(R.id.turn_left);
        lv_job= (ListView) findViewById(R.id.lv_job);
//        address= (LinearLayout) findViewById(R.id.address);
//        lv1= (ListView) findViewById(R.id.lv1);
//        lv2= (ListView) findViewById(R.id.lv2);
//        lv3= (ListView) findViewById(R.id.lv3);
//        selectAddress= (LinearLayout) findViewById(R.id.selectAddress);
//        turn_one= (ImageView) findViewById(R.id.turn_one);
        //turn_two= (ImageView) findViewById(R.id.turn_two);
        turn_three= (ImageView) findViewById(R.id.turn_three);
//        img1= (ImageView) findViewById(R.id.img_1);
//        img2= (ImageView) findViewById(R.id.img_2);

        lv_position_pull= (ListView) findViewById(R.id.lv_position_pull);
        position_Layout= (LinearLayout) findViewById(R.id.position_Layout);
        position_Img= (ImageView) findViewById(R.id.position_Img);
        position_Text= (TextView) findViewById(R.id.position_Text);
        //address_text= (TextView) findViewById(R.id.address_text);
        industry= (TextView) findViewById(R.id.industry);
        search= (ImageView) findViewById(R.id.search);
        noResult= (TextView) findViewById(R.id.noResult);
    }
    public void initAction(){
        turn_left.setOnClickListener(this);
        //address.setOnClickListener(this);
        search.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.turn_left:
                finish();
                break;
//            case R.id.address:
//                if(lv1_showing)
//                {
//                    lv1.setVisibility(View.INVISIBLE);
//                    lv2.setVisibility(View.INVISIBLE);
//                    lv3.setVisibility(View.INVISIBLE);
//                    img1.setVisibility(View.INVISIBLE);
//                    img2.setVisibility(View.INVISIBLE);
//                    selectAddress.setVisibility(View.GONE);
//                    lv_job.setVisibility(View.VISIBLE);
//                    turn_one.setImageResource(R.drawable.turn_down);
//                  //lv1.setVisibility(View.GONE);
//                }else {
//                  selectAddress.setVisibility(View.VISIBLE);
//                  lv_job.setVisibility(View.GONE);
//                  lv1.setVisibility(View.VISIBLE);
//                  lv2.setVisibility(View.INVISIBLE);
//                  lv3.setVisibility(View.INVISIBLE);
//                  img1.setVisibility(View.VISIBLE);
//                  img2.setVisibility(View.INVISIBLE);
//                  turn_one.setImageResource(R.drawable.turn_up);
//                }
//                position_Img.setImageResource(R.drawable.turn_down);
//                lv_position_pull.setVisibility(View.GONE);
//                ishowing=false;
//                lv1_showing=!lv1_showing;
//                break;
            case R.id.search:
                Intent intent=new Intent(PositionSearchActivity.this,InternshipsSearchActivity.class);
                startActivity(intent);
                break;
        }
    }
    public List<String> Data(){
        List<String> list=new ArrayList<String>();
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        list.add("浙江");
        return list;
    }



}
