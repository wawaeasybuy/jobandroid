package com.mardin.job.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.MainActivity;
import com.mardin.job.MainActivityNew;
import com.mardin.job.R;

import com.mardin.job.activities.JobDetailActivity;
import com.mardin.job.activities.LoginActivity;
import com.mardin.job.adapters.JobListAdapter;
import com.mardin.job.fragments.dummy.DummyContent;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Job;
import com.mardin.job.models.JobList;
import com.mardin.job.network.Constants;
import com.mardin.job.widgets.EndlessScrollListener;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class JobListFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private SwipeRefreshLayout mSwipeRefreshlayout;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    //private ListAdapter mAdapter;
    private JobListAdapter mAdapter;
    private List<Job> mItems = new ArrayList<Job>();
    AsyncHttpClient mClient;
    private Integer mPage;
    private Integer mItemsPerPage;
    private Boolean mNomore;


    // TODO: Rename and change types of parameters
    public static JobListFragment newInstance(String param1, String param2) {
        JobListFragment fragment = new JobListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content

//        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
        if (mItems.size() != 0) {
            return;
        }
        mPage = 1;
        mItemsPerPage = 10;
        mNomore = false;
        mItems = new ArrayList<Job>();
        mAdapter = new JobListAdapter(getActivity(), mItems);
        mClient = new AsyncHttpClient();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joblist, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set the adapter
        mSwipeRefreshlayout = (SwipeRefreshLayout)getView().findViewById(R.id.swiperefresh);
        mSwipeRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                mNomore = false;
                loadJobList();
            }
        });
        mListView = (AbsListView) getView().findViewById(android.R.id.list);
//        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

//        mListView.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                loadMoreJobList(page);
//            }
//        });
        final int endTrigger = 2;
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!mNomore &&mListView.getCount() != 0 && mListView.getLastVisiblePosition() >= (mListView.getCount()-1) - endTrigger) {
                    loadMoreJobList();
                }
            }
        });

        if (mItems.size() != 0) {
            return;
        }
        loadJobList();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            ((MainActivityNew)mListener).onSectionAttached(1);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction("detail");
            Job job = mItems.get(position);
            Intent intent = new Intent(getActivity(), JobDetailActivity.class);
            intent.putExtra("job", job);

            this.startActivity(intent);
//            gotoJobDetail();
        }
    }



    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    public void loadJobList() {
        mPage = 1;
        RequestParams params = new RequestParams();
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
