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
import android.widget.RatingBar;
import android.widget.TextView;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Candidate;
import com.mardin.job.models.Resume;
import com.mardin.job.models.Talent;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        adviceText= (TextView) getActivity().findViewById(R.id.adviceText);
        companyName= (TextView) getActivity().findViewById(R.id.companyName);
        evaluationTime= (TextView) getActivity().findViewById(R.id.evaluationTime);

        getTalent();
    }
    public void getTalent(){
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        String Url=Constants.talentStr+"/"+GlobalProvider.getInstance().candidate.get_id();
        globalProvider.get(getActivity(), Url, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseInfo(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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
            Talent talent = (Talent) objectMapper.readValue(jsonParser, Talent.class);
            star_bar1.setRating(talent.getProfessionalLevel());
            star_bar2.setRating(talent.getAnalysis());
            star_bar3.setRating(talent.getExpression());
            star_bar4.setRating(talent.getCompression());
            star_bar5.setRating(talent.getAttitude());
            if(talent.getAdviceText()!=null){adviceText.setText(talent.getAdviceText());}else{adviceText.setText("暂无任何公司评价");}
            if(talent.getCompanyName()!=null){companyName.setText(talent.getCompanyName());}
            if(talent.getEvaluationTime()!=null){evaluationTime.setText(ConverToString(talent.getEvaluationTime()));}
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
