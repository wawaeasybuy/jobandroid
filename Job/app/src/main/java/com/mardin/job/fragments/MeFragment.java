package com.mardin.job.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.MainActivity;
import com.mardin.job.R;
import com.mardin.job.activities.CollectedJobListActivity;
import com.mardin.job.activities.LoginActivity;
import com.mardin.job.activities.PostedJobListActivity;
import com.mardin.job.activities.UpdateCanActivity;
import com.mardin.job.activities.UpdateEmployerActivity;
import com.mardin.job.adapters.MeAdapter;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Candidate;
import com.mardin.job.models.User;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ListView mListView;
    private User mUser;
    private MeAdapter mAdapter;




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (Constants.needReflesh) {
            loadMe();
            Constants.needReflesh = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView = (ListView)getView().findViewById(R.id.lv);
        mAdapter = new MeAdapter(getActivity(), mUser);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == 1 || position == 2) {
                    if (mUser != null && mUser.role.equals(Constants.employer)) {
                        gotoUpEmployer();
                    }else {
                        gotoUpCandidate();
                    }

                }else if (position == 3) {
                    if (Constants.getToken(getActivity()) == null || Constants.getToken(getActivity()).equals("")) {
                        gotoLogin();
                    }else {
                        gotoJobs();
                    }

                }else if (position == 4) {
                    if (Constants.getToken(getActivity()) == null || Constants.getToken(getActivity()).equals("")) {
                        gotoLogin();
                        //Toast.makeText(getActivity(), "you have not logged in", Toast.LENGTH_SHORT).show();
                    }else {
                        Constants.setToken(getActivity(), "");
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "you have successfully logged out", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        loadMe();

    }

    public void gotoUpCandidate() {
        Intent intent = new Intent(getActivity(), UpdateCanActivity.class);
        intent.putExtra("user", mUser);
        getActivity().startActivityForResult(intent, Constants.UPECANDIDATE_INTENT);
    }


    public void gotoUpEmployer() {
        Intent intent = new Intent(getActivity(), UpdateEmployerActivity.class);
        intent.putExtra("user", mUser);
        getActivity().startActivityForResult(intent, Constants.UPEMPLOYER_INTENT);
    }

    public void gotoLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivityForResult(intent, Constants.LoginIntent);
        //this.startActivity(intent);
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case Constants.LoginIntent:
//                loadMe();
//                break;
//        }
//    }

    public void gotoJobs() {
        if (mUser != null) {
            if (mUser.role.equals(Constants.employer)) {
                Intent intent = new Intent(getActivity(), PostedJobListActivity.class);
                intent.putExtra("employerid", mUser._id);
                this.startActivity(intent);

            }else {
                Intent intent = new Intent(getActivity(), CollectedJobListActivity.class);

                //intent.putExtra("user", mUser);
                //intent.putCharSequenceArrayListExtra("jobs", mUser._candidateProfile._collectjobs);
                //intent.putParcelableArrayListExtra("jobs", (ArrayList<? extends Parcelable>) mUser._candidateProfile._collectjobs);
                intent.putExtra("jobs", (Serializable) mUser._candidateProfile._collectjobs);
                this.startActivity(intent);
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            ((MainActivity)mListener).onSectionAttached(2);
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
        public void onFragmentInteraction(Uri uri);
    }

    public void loadMe() {
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(getActivity(), Constants.meUrlStr, null, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseMe(new String(responseBody));
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

    public void parseMe(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            User user_l = (User)objectMapper.readValue(jsonParser, User.class);
            mUser = user_l;
            mAdapter.setMUser(mUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
