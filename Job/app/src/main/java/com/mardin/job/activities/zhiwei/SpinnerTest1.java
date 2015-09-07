package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.mardin.job.R;

public class SpinnerTest1 extends Activity {

    private TextView spinnerText;
    private Spinner Spinner1;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc);

        Spinner1 = (Spinner) findViewById(R.id.Spinner1);
        spinnerText = (TextView) findViewById(R.id.spinnerText);

        //将可选内容与ArrayAdapter连接起来
        adapter = ArrayAdapter.createFromResource(this, R.array.area, android.R.layout.simple_spinner_item);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter2 添加到spinner中
        Spinner1.setAdapter(adapter);

        //添加事件Spinner事件监听
        Spinner1.setOnItemSelectedListener(new SpinnerXMLSelectedListener());

        //设置默认值
        Spinner1.setVisibility(View.VISIBLE);

    }

    //使用XML形式操作
    class SpinnerXMLSelectedListener implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            spinnerText.setText(""+adapter.getItem(arg2));
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }
}