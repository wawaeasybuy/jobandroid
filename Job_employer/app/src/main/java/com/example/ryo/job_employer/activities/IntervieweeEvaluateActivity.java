package com.example.ryo.job_employer.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.ryo.job_employer.R;

/**
 * Created by Ryo on 2015/9/22.
 */
public class IntervieweeEvaluateActivity extends Activity {

    private RatingBar star_bar1;
    private RatingBar star_bar2;
    private RatingBar star_bar3;
    private RatingBar star_bar4;
    private RatingBar star_bar5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewee_evaluate);

        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        final RatingBar star_bar1 = (RatingBar)findViewById(R.id.star_bar1);
        final RatingBar star_bar2 = (RatingBar)findViewById(R.id.star_bar2);
        final RatingBar star_bar3 = (RatingBar)findViewById(R.id.star_bar3);
        final RatingBar star_bar4 = (RatingBar)findViewById(R.id.star_bar4);
        final RatingBar star_bar5 = (RatingBar)findViewById(R.id.star_bar5);

        star_bar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
               // star_bar1.setRating(rating);
                //star_bar3.setRating(rating);
                Toast.makeText(IntervieweeEvaluateActivity.this, "Rating:" + String.valueOf(rating),
                        Toast.LENGTH_LONG).show();
            }
        });
    }



}
