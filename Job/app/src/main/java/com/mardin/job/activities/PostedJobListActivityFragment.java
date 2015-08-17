package com.mardin.job.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.adapters.CollectedJobListAdapter;
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

/**
 * A placeholder fragment containing a simple view.
 */
public class PostedJobListActivityFragment extends Fragment {

    private PostedJobListActivity mContext;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshlayout;

    private CollectedJobListAdapter mAdapter;
    private String mEmployerId;
    private List<Job> mItems = new ArrayList<Job>();
    private Integer mPage;
    private Integer mItemsPerPage;
    private Boolean mNomore;

    public PostedJobListActivityFragment() {
    }

    public void onAttach(Activity mActivity) {
        super.onAttach(mActivity);
        mContext = (PostedJobListActivity)mActivity;

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posted_job_list, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPage = 1;
        mItemsPerPage = 10;
        mNomore = false;
        mEmployerId = mContext.getmEmployerId();
        mAdapter = new CollectedJobListAdapter(getActivity(), mItems);

        // Set the adapter
        mSwipeRefreshlayout = (SwipeRefreshLayout)getView().findViewById(R.id.swiperefresh);
        mSwipeRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                mNomore = false;
                loadJobList();
            }
        });
        mListView = (ListView) getView().findViewById(R.id.lv);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job job = mItems.get(position);
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra("job", job);

                getActivity().startActivity(intent);
            }
        });

        final int endTrigger = 2;
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!mNomore && mListView.getCount() != 0 && mListView.getLastVisiblePosition() >= (mListView.getCount() - 1) - endTrigger) {
                    loadMoreJobList();
                }
            }
        });

        if (mItems.size() != 0) {
            return;
        }
        loadJobList();

    }

    public void loadJobList() {
        mPage = 1;
        RequestParams params = new RequestParams();
        params.put("employerId",mEmployerId);
        params.put("page", mPage);
        params.put("itemsPerPage", mItemsPerPage);

        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(getActivity(), Constants.jobListUrlStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseJobList(new String(responseBody));
                //Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
                mSwipeRefreshlayout.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.v("err", new String(responseBody));
                mSwipeRefreshlayout.setRefreshing(false);
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                //mSwipeRefreshlayout.setRefreshing(false);
            }
        });
    }

    public void parseJobList(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobList jobList = (JobList) objectMapper.readValue(jsonParser, JobList.class);
            this.mItems.clear();
            this.mItems.addAll(jobList.jobs);
            mAdapter.notifyDataSetChanged();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMoreJobList() {
        mPage = mPage + 1;
        RequestParams params = new RequestParams();
        params.put("employerId",mEmployerId);
        params.put("page", mPage);
        params.put("itemsPerPage", mItemsPerPage);

        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(getActivity(), Constants.jobListUrlStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseMoreJobList(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.v("err", new String(responseBody));
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }

    public void parseMoreJobList(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JobList jobList = (JobList) objectMapper.readValue(jsonParser, JobList.class);
            //this.mItems.clear();
            if (jobList.jobs.size() < mItemsPerPage) {
                mNomore = true;
            }
            this.mItems.addAll(jobList.jobs);
            mAdapter.notifyDataSetChanged();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
