package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.helper.RequestListener;
import com.example.ryo.job_employer.models.EvaluateBody;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.Talent;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by Ryo on 2015/9/22.
 */
public class IntervieweeEvaluateActivity extends Activity implements View.OnClickListener {

    public LinearLayout turn_left;
    public TextView save;
    public Talent talent;
    public EditText adviceText;

    private RatingBar star_bar1;
    private RatingBar star_bar2;
    private RatingBar star_bar3;
    private RatingBar star_bar4;
    private RatingBar star_bar5;
    public EvaluateBody body=new EvaluateBody();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewee_evaluate);
        initView();
        initAction();
        Intent intent=this.getIntent();
        talent= (Talent) intent.getSerializableExtra("talent");
        final RatingBar star_bar1 = (RatingBar)findViewById(R.id.star_bar1);
        final RatingBar star_bar2 = (RatingBar)findViewById(R.id.star_bar2);
        final RatingBar star_bar3 = (RatingBar)findViewById(R.id.star_bar3);
        final RatingBar star_bar4 = (RatingBar)findViewById(R.id.star_bar4);
        final RatingBar star_bar5 = (RatingBar)findViewById(R.id.star_bar5);

        if(talent.getAdviceText()!=null){adviceText.setText(talent.getAdviceText());}

        star_bar1.setRating(talent.getProfessionalLevel());
        star_bar2.setRating(talent.getAnalysis());
        star_bar3.setRating(talent.getExpression());
        star_bar4.setRating(talent.getCompression());
        star_bar5.setRating(talent.getAttitude());

        body.setProfessionalLevel(talent.getProfessionalLevel());
        body.setAnalysis(talent.getAnalysis());
        body.setExpression(talent.getExpression());
        body.setCompression(talent.getCompression());
        body.setAttitude(talent.getAttitude());
        if(talent.isInterview){
            star_bar1.setEnabled(false);
            star_bar2.setEnabled(false);
            star_bar3.setEnabled(false);
            star_bar4.setEnabled(false);
            star_bar5.setEnabled(false);
            adviceText.setEnabled(false);
            save.setVisibility(View.INVISIBLE);
        }else{
            star_bar1.setEnabled(true);
            star_bar2.setEnabled(true);
            star_bar3.setEnabled(true);
            star_bar4.setEnabled(true);
            star_bar5.setEnabled(true);
            adviceText.setEnabled(true);
            save.setVisibility(View.VISIBLE);
        }

        star_bar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
//                Toast.makeText(IntervieweeEvaluateActivity.this, "Rating:" + String.valueOf(rating),
//                        Toast.LENGTH_LONG).show();
                body.setProfessionalLevel(rating);
            }
        });
        star_bar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                Toast.makeText(IntervieweeEvaluateActivity.this, "Rating:" + String.valueOf(rating),
//                        Toast.LENGTH_LONG).show();
                body.setAnalysis(rating);
            }
        });
        star_bar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                Toast.makeText(IntervieweeEvaluateActivity.this, "Rating:" + String.valueOf(rating),
//                        Toast.LENGTH_LONG).show();
                body.setExpression(rating);
            }
        });
        star_bar4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                Toast.makeText(IntervieweeEvaluateActivity.this, "Rating:" + String.valueOf(rating),
//                        Toast.LENGTH_LONG).show();
                body.setCompression(rating);
            }
        });
        star_bar5.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                Toast.makeText(IntervieweeEvaluateActivity.this, "Rating:" + String.valueOf(rating),
//                        Toast.LENGTH_LONG).show();
                body.setAttitude(rating);
            }
        });
    }

    private void initAction() {
        turn_left.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void initView() {
        turn_left = (LinearLayout) findViewById(R.id.turn_left);
        save= (TextView) findViewById(R.id.save);
        adviceText= (EditText) findViewById(R.id.adviceText);
    }
    @Override
    public void onClick(View v) {
         switch (v.getId()){
           case R.id.turn_left:
            this.setResult(Activity.RESULT_OK);
            this.finish();
            break;
             case R.id.save:
                doEvaluete();
            break;
      }
    }
    public void doEvaluete(){
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = "";
        body.setAdviceText(adviceText.getText().toString());
        if(GlobalProvider.getInstance().employer.getCompanyname()!=null){body.setCompanyName(GlobalProvider.getInstance().employer.getCompanyname());}
        try {
            json = ow.writeValueAsString(body);
            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
            GlobalProvider globalProvider = GlobalProvider.getInstance();
            String URL= Constants.TalentStr+"/"+talent.get_id();
            globalProvider.put(this, URL, entity, "application/json", new RequestListener() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Toast.makeText(IntervieweeEvaluateActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK);
                    finish();
                    //doResult();
                    //parseLoginResult(new String(responseBody));
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void doResult(){
//
//    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {


            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
                v.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {

                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
