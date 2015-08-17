package com.mardin.job.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.adapters.JobDetailAdapter;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Job;
import com.mardin.job.models.JobDetail;
import com.mardin.job.models.JobList;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class JobDetailActivityFragment extends Fragment {

    RecyclerView mRv;
    JobDetailAdapter mAdapter;
    Job mJob;
    JobDetail mJobDetail;
    private JobDetailActivity mContext;

    public JobDetailActivityFragment() {
    }

    public void onAttach(Activity mActivity) {
        super.onAttach(mActivity);
        mContext = (JobDetailActivity)mActivity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_job_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRv = (RecyclerView)getView().findViewById(R.id.rv);
        //mRv.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRv.setLayoutManager(llm);

        mJob = mContext.getmJob();
        mJobDetail = new JobDetail();
        mAdapter = new JobDetailAdapter(mJobDetail, this);
        mRv.setAdapter(mAdapter);
        loadJobDetail();

    }

//    public void setMDetailJob(JobDetail job) {
//        mJobDetail._id = job._id;
//        mJobDetail.jobtype = job.jobtype;
//        mJobDetail.jobname = job.jobname;
//        mJobDetail.vacancynum = job.vacancynum;
//        mJobDetail.education = job.education;
//        mJobDetail.experienceyear = job.experienceyear;
//        mJobDetail.wage = job.wage;
//        mJobDetail.description = job.description;
//        mJobDetail._benefits = job._benefits;
//        mJobDetail.companyname = job.companyname;
//        mJobDetail.lon = job.lon;
//        mJobDetail.lat = job.lat;
//        mJobDetail.address = job.address;
//        mJobDetail.city = job.city;
//        mJobDetail.district = job.district;
//        mJobDetail.contact = job.contact;
//        mJobDetail.phone = job.phone;
//        mJobDetail.email = job.email;
//        mJobDetail.gender = job.gender;
//        mJobDetail.workinghour = job.workinghour;
//        mJobDetail.weight = job.weight;
//        mJobDetail.createtime = job.createtime;
//        mJobDetail.applylink = job.applylink;
//        mJobDetail._employer = job._employer;
//
//    }

    public void loadJobDetail() {

        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(getActivity(), Constants.jobDetailUrlStr + "//" + mJob._id, null, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
                parseJobDetail(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });

    }

    public void parseJobDetail(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobDetail job_l = (JobDetail)objectMapper.readValue(jsonParser, JobDetail.class);
            mJobDetail = job_l;
            mAdapter.setMJob(mJobDetail);
            mAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void actionCollect() {
        String collectStr = Constants.jobCollectUrlStr + "/" + mJobDetail._id + "/collect" ;
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(getActivity(), collectStr, null, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    Toast.makeText(getActivity(), "collect successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "fail to collect", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }

    public void parseCollectResult(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoWeb() {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", mJobDetail.applylink);

        getActivity().startActivity(intent);
    }


}
