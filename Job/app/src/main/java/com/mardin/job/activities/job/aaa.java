package com.mardin.job.activities.job;

import android.app.Activity;


import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.adapters.job.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryo on 2015/9/28.
 */
public class aaa extends Activity{

    private ViewPager mPager;//ҳ������
    private List<View> listViews; // Tabҳ���б�
    //private List<Fragment> listFragment;
    private ImageView cursor;// ����ͼƬ
    private TextView t1, t2, t3;// ҳ��ͷ��
    private int offset = 0;// ����ͼƬƫ����
    private int currIndex = 0;// ��ǰҳ�����
    private int bmpW;// ����ͼƬ���

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa);


    }

    /**
     * ��ʼ��ͷ��
     */

    private void InitTextView() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);

        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
    }

    /**
     * ͷ��������
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };

    /**
     * ��ʼ��ViewPager
     */
    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.search_viewpager);
        listViews = new ArrayList<View>();
        LayoutInflater mInflater = getLayoutInflater();
        //JobListFragment  JobListFragment=new JobListFragment();
        //listFragment.add(JobListFragment)  ;
        listViews.add(mInflater.inflate(R.layout.aab, null));
        listViews.add(mInflater.inflate(R.layout.aac, null));
        listViews.add(mInflater.inflate(R.layout.aad, null));
        mPager.setAdapter(new MyPagerAdapter(listViews));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }


    /**
     * ��ʼ������
     */
    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.slide_blue)
                .getWidth();// ��ȡͼƬ���
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
        offset = (screenW / 3 - bmpW) / 2;// ����ƫ����
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
    }

    /**
     * ҳ���л�����
     */
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
        int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

}
