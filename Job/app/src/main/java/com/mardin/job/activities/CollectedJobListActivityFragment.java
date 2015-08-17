package com.mardin.job.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mardin.job.R;
import com.mardin.job.adapters.CollectedJobListAdapter;
import com.mardin.job.models.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CollectedJobListActivityFragment extends Fragment {

    private CollectedJobListActivity mContext;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshlayout;

    private CollectedJobListAdapter mAdapter;
    private List<Job> mItems = new ArrayList<Job>();
    private Integer mPage;
    private Integer mItemsPerPage;
    private Boolean mNomore;


    public CollectedJobListActivityFragment() {
    }

    public void onAttach(Activity mActivity) {
        super.onAttach(mActivity);
        mContext = (CollectedJobListActivity)mActivity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collected_job_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPage = 1;
        mItemsPerPage = 10;
        mNomore = false;
        mItems = mContext.getJobs();
        mAdapter = new CollectedJobListAdapter(getActivity(), mItems);

        // Set the adapter
        mSwipeRefreshlayout = (SwipeRefreshLayout)getView().findViewById(R.id.swiperefresh);

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

    }

}
