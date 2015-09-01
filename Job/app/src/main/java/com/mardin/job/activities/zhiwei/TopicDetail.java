package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.fragments.zhiwei.TopicAboutFragment;
import com.mardin.job.fragments.zhiwei.TopicDataFragment;
import com.mardin.job.fragments.zhiwei.TopicHotFragment;

/**
 * Created by Ryo on 2015/8/28.
 */
public class TopicDetail extends Activity  implements View.OnClickListener {

    private LinearLayout topicData;
    private LinearLayout hotTopic;
    private LinearLayout topicAbout;

    private TextView topicDataT;
    private TextView hotTopicT;
    private TextView topicAboutT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topicdetail);



        ImageView turn_left= (ImageView) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        topicDataT= (TextView) findViewById(R.id.topicDataT);
        hotTopicT= (TextView) findViewById(R.id.hotTopicT);
        topicAboutT= (TextView) findViewById(R.id.topicAboutT);


        initView();

        topicData.setOnClickListener(this);
        hotTopic.setOnClickListener(this);
        topicAbout.setOnClickListener(this);

        setSelect(0);
    }


    private void initView() {

        topicData= (LinearLayout) findViewById(R.id.topicData);
        hotTopic= (LinearLayout) findViewById(R.id.hotTopic);
        topicAbout=(LinearLayout)findViewById(R.id.topicAbout);

    }


    public void setSelect(int i){

        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        //hide(transaction);
        switch(i){

            case 0:

                TopicDataFragment topicData= new TopicDataFragment();
                transaction.replace(R.id.topicDetail_content, topicData);

                topicDataT.setTextColor(0xFF0070ee);
                hotTopicT.setTextColor(0xff666666);
                topicAboutT.setTextColor(0xff666666);

                break;
            case 1:

                TopicHotFragment hotTopic=new TopicHotFragment();
                transaction.replace(R.id.topicDetail_content, hotTopic);

                topicDataT.setTextColor(0xff666666);
                hotTopicT.setTextColor(0xFF0070ee);
                topicAboutT.setTextColor(0xff666666);

                break;
            case 2:

                TopicAboutFragment topicAbout=new TopicAboutFragment();
                transaction.replace(R.id.topicDetail_content, topicAbout);

                topicDataT.setTextColor(0xff666666);
                hotTopicT.setTextColor(0xff666666);
                topicAboutT.setTextColor(0xFF0070ee);


                break;
        }
        transaction.commit();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.topicData:

                setSelect(0);

                break;
            case R.id.hotTopic:
                setSelect(1);

                break;
            case R.id.topicAbout:
                setSelect(2);
        }
    }

}
