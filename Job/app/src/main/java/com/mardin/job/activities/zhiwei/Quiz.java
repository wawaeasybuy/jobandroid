package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.mardin.job.R;
import com.mardin.job.fragments.zhiwei.QuizLeftFragment;
import com.mardin.job.fragments.zhiwei.QuizMiddleFragment;
import com.mardin.job.fragments.zhiwei.QuizRightFragment;

/**
 * Created by Ryo on 2015/8/28.
 */
public class Quiz extends Activity implements View.OnClickListener{

    private LinearLayout quiz_question;
    private LinearLayout quiz_describe;
    private LinearLayout quiz_topic;

    private TextView quiz_questionT;
    private TextView quiz_describeT;
    private TextView quiz_topicT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ImageView turn_left= (ImageView) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        quiz_questionT= (TextView) findViewById(R.id.quiz_questionT);
        quiz_describeT= (TextView) findViewById(R.id.quiz_describeT);
        quiz_topicT= (TextView) findViewById(R.id.quiz_topicT);


        initView();

        quiz_question.setOnClickListener(this);
        quiz_describe.setOnClickListener(this);
        quiz_topic.setOnClickListener(this);

        setSelect(0);

    }

    private void initView() {

        quiz_question= (LinearLayout) findViewById(R.id.quiz_question);
        quiz_describe= (LinearLayout) findViewById(R.id.quiz_describe);
        quiz_topic=(LinearLayout)findViewById(R.id.quiz_topic);

    }

    public void setSelect(int i){

        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        //hide(transaction);
        switch(i){

            case 0:

                QuizLeftFragment quiz_question= new QuizLeftFragment();
                transaction.replace(R.id.quizTab_content, quiz_question);

                quiz_questionT.setTextColor(0xFF0000FF);
                quiz_describeT.setTextColor(0xff666666);
                quiz_topicT.setTextColor(0xff666666);

                break;
            case 1:

                QuizMiddleFragment quiz_describe=new QuizMiddleFragment();
                transaction.replace(R.id.quizTab_content, quiz_describe);

                quiz_questionT.setTextColor(0xff666666);
                quiz_describeT.setTextColor(0xFF0000FF);
                quiz_topicT.setTextColor(0xff666666);

                break;
            case 2:

                QuizRightFragment quiz_topic=new QuizRightFragment();
                transaction.replace(R.id.quizTab_content, quiz_topic);

                quiz_questionT.setTextColor(0xff666666);
                quiz_describeT.setTextColor(0xff666666);
                quiz_topicT.setTextColor(0xFF0000FF);


                break;
        }
        transaction.commit();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.quiz_question:

                setSelect(0);

                break;
            case R.id.quiz_describe:
                setSelect(1);

                break;
            case R.id.quiz_topic:
                setSelect(2);
        }
    }
}
