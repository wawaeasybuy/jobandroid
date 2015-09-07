package com.mardin.job.fragments.zhiwei;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mardin.job.R;
import com.mardin.job.activities.zhiwei.PersonalSelfIntroduceActivity;

/**
 * Created by Ryo on 2015/8/26.
 */
public class PersonalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_personal_header, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       /*
        EditText introduce= (EditText) getActivity().findViewById(R.id.introduce);
        introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PersonalSelfIntroduceActivity.class);
                startActivity(intent);
            }
        });
        */


    }
}