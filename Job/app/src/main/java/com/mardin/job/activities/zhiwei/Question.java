package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/8/28.
 */
public class Question extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_main);

        ImageView turn_left= (ImageView) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        ImageView question_header_add= (ImageView) findViewById(R.id.question_header_add);
        question_header_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Question.this, Quiz.class);
                startActivity(intent);
            }
        });

        ImageView question_header_search= (ImageView) findViewById(R.id.question_header_search);
        question_header_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Question.this, JobMarketSearch.class);
                startActivity(intent);
            }
        });

        TextView question_comment= (TextView) findViewById(R.id.question_comment);
        question_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Question.this, Comment.class);
                startActivity(intent);
            }
        });

        TextView question_ID= (TextView) findViewById(R.id.question_ID);
        question_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Question.this, Personal.class);
                startActivity(intent);
            }
        });

    }
}
