package com.mardin.job.fragments.zhiwei;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mardin.job.R;
import com.mardin.job.activities.zhiwei.PositionSearchActivity;
import com.mardin.job.activities.zhiwei.PositionSearchResult;

/**
 * Created by Ryo on 2015/8/26.
 */
public class PositionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_position, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EditText position_search= (EditText) getActivity().findViewById(R.id.position_search);
        position_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PositionSearchActivity.class);
                startActivity(intent);
            }
        });

        Button position_search_button= (Button) getActivity().findViewById(R.id.position_search_button);
        position_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PositionSearchResult.class);
                startActivity(intent);
            }
        });
    }
}