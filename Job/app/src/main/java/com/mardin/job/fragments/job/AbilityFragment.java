package com.mardin.job.fragments.job;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.adapters.job.AdviceListAdapter;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Candidate;
import com.mardin.job.models.Resume;
import com.mardin.job.models.Skills;
import com.mardin.job.models.Talent;
import com.mardin.job.models.TalentList;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ryo on 2015/9/27.
 */
public class AbilityFragment extends Fragment  {

    private LinearLayout changeone;
    private LinearLayout changetwo;

    private ImageView change1;
    private ImageView change2;
    private RatingBar star_bar1;
    private RatingBar star_bar2;
    private RatingBar star_bar3;
    private RatingBar star_bar4;
    private RatingBar star_bar5;
    private TextView adviceText;
    public TextView companyName;
    public TextView evaluationTime;

    public List<TalentList> mItems;
    public AdviceListAdapter adapter;
    public ListView lv;
    public LinearLayout main;
    public LinearLayout noEvalue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_function_score, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        star_bar1 = (RatingBar)getActivity().findViewById(R.id.star_bar1);
        star_bar2 = (RatingBar)getActivity().findViewById(R.id.star_bar2);
        star_bar3 = (RatingBar)getActivity().findViewById(R.id.star_bar3);
        star_bar4 = (RatingBar)getActivity().findViewById(R.id.star_bar4);
        star_bar5 = (RatingBar)getActivity().findViewById(R.id.star_bar5);
        lv= (ListView) getActivity().findViewById(R.id.lv);
        main= (LinearLayout) getActivity().findViewById(R.id.main);
       // noEvalue= (LinearLayout) getActivity().findViewById(R.id.noEvalue);
//        main.setVisibility(View.VISIBLE);
//        noEvalue.setVisibility(View.GONE);
//        adviceText= (TextView) getActivity().findViewById(R.id.adviceText);
//        companyName= (TextView) getActivity().findViewById(R.id.companyName);
//        evaluationTime= (TextView) getActivity().findViewById(R.id.evaluationTime);
        mItems=new ArrayList<TalentList>();
        adapter=new AdviceListAdapter(getActivity(),mItems);
        lv.setAdapter(adapter);
        getTalent();
    }
    public void getTalent(){
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        String Url=Constants.skillUrlStr+"/"+GlobalProvider.getInstance().candidate.get_id();
        globalProvider.get(getActivity(), Url, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseInfo(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                star_bar1.setEnabled(false);
                star_bar2.setEnabled(false);
                star_bar3.setEnabled(false);
                star_bar4.setEnabled(false);
                star_bar5.setEnabled(false);
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }
    public void parseInfo(String json){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            Skills skills = (Skills) objectMapper.readValue(jsonParser, Skills.class);
//            if(skills.advices.size()==0&&skills.getProfessionalLevel()==0&&skills.getAnalysis()==0&&skills.getExpression()==0&&skills.getCompression()==0&&skills.getAttitude()==0){
//               main.setVisibility(View.GONE);
//               noEvalue.setVisibility(View.VISIBLE);
//            }else{
//                main.setVisibility(View.VISIBLE);
//                noEvalue.setVisibility(View.GONE);
                star_bar1.setRating(skills.getProfessionalLevel());
                star_bar2.setRating(skills.getAnalysis());
                star_bar3.setRating(skills.getExpression());
                star_bar4.setRating(skills.getCompression());
                star_bar5.setRating(skills.getAttitude());

                star_bar1.setEnabled(false);
                star_bar2.setEnabled(false);
                star_bar3.setEnabled(false);
                star_bar4.setEnabled(false);
                star_bar5.setEnabled(false);
                this.mItems.clear();
                this.mItems.addAll(skills.advices);
                adapter.notifyDataSetChanged();
            //}

//            if(talent.getAdviceText()!=null){adviceText.setText(talent.getAdviceText());}else{adviceText.setText("暂无任何公司评价");}
//            if(talent.getCompanyName()!=null){companyName.setText(talent.getCompanyName());}
//            if(talent.getEvaluationTime()!=null){evaluationTime.setText(ConverToString(talent.getEvaluationTime()));}
//            adapter.notifyDataSetChanged();
            //do something
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(date);
    }
}
