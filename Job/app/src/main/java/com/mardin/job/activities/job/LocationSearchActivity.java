package com.mardin.job.activities.job;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.adapters.job.LocationSearchListAdapter;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.City;
import com.mardin.job.models.CityList;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
public class LocationSearchActivity extends Activity{
    public LinearLayout turn_left;
    public EditText input;
    public TextView noResult;
    public ListView lv_location_search;
    public String retrieval="";
    public List<City> mItems;
    private Integer mPage;
    private Integer mItemsPerPage;
    public LocationSearchListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_search);

        mPage=1;
        mItemsPerPage=20;
        mItems=new ArrayList<City>();
        adapter=new LocationSearchListAdapter(this,mItems);
        turn_left= (LinearLayout) findViewById(R.id.turn_left);
        input= (EditText) findViewById(R.id.input);
        noResult= (TextView) findViewById(R.id.no_result);
        lv_location_search= (ListView) findViewById(R.id.lv_location_search);
        lv_location_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GlobalProvider.getInstance().city=mItems.get(position);
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!input.getText().toString().equals("")) {
                    retrieval = input.getText().toString();
                    LoadLocationList();
                } else {
                    retrieval = "";
                    mItems.clear();
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lv_location_search.setAdapter(adapter);

    }
    public void LoadLocationList(){
        RequestParams params = new RequestParams();
        params.put("page", mPage);
        params.put("itemsPerPage", mItemsPerPage);
        params.put("retrieval",retrieval);
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, Constants.locationStr, params, new RequestListener() {
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
    private void parseJobList(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            CityList cityList = (CityList) objectMapper.readValue(jsonParser, CityList.class);
//            if(list.size()==0){
//                if(JOB==null){JOB=new Job();JOB.setPositionName("全部职位");}
//                list.add(JOB);
//                for(int i=0;i<jobList.jobs.size();i++){
//                    list.add(jobList.jobs.get(i));
//                }
//                //this.list.addAll(jobFitList.jobs);
//            }
            this.mItems.clear();
            this.mItems.addAll(cityList.cities);
            adapter.notifyDataSetChanged();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
