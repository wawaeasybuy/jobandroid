package com.mardin.job.activities.zhiwei;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.adapters.zhiwei.PositionSpinnerAdapter;


public class SpinnerTest1 extends Activity {
    private ArrayList<String> list;
    //private ImageView imgView;
    private TextView textView;
    private LinearLayout layout;
    private ListView lv;
    private PositionSpinnerAdapter adapter;
    private PopupWindow popupWindow;
    private LinearLayout spinnerlayout;
    int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abc);
        textView = (TextView) findViewById(R.id.textView2);
       // imgView = (ImageView) findViewById(R.id.imageView1);
        // ʵ����һ��List,�������
        list = new ArrayList<String>();
        list.add("ȫ��");
        list.add("������");
        list.add("������");
        adapter = new PositionSpinnerAdapter(this, list);
        textView.setText((CharSequence) adapter.getItem(0));
        spinnerlayout = (LinearLayout) findViewById(R.id.spinnerid);
        // ����Ҳఴť������������
        imgView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if(list.size()>0){
                    spinnerlayout.setBackgroundResource(R.drawable.preference_first_item);
                }
                showWindow(spinnerlayout, textView);

            }
        });
    }
    protected void onResume(){
        super.onResume();

    }
    public void showWindow(View position, final TextView txt) {

        layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.mypinner_dropdown, null);
        listView = (ListView) layout.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        popupWindow = new PopupWindow(position);
        // ���õ���Ŀ��Ϊ�����ļ��Ŀ�
        popupWindow.setWidth(spinnerlayout.getWidth());
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        // ����һ��͸���ı�������Ȼ�޷�ʵ�ֵ�������⣬������ʧ
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // ���õ�������ⲿ��������ʧ
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(layout);
        // ���õ�����ֵ�λ�ã���v�����·�����ƫ��textview�Ŀ�ȣ�Ϊ�˶���~���᲻ƫ��
        popupWindow.showAsDropDown(position, 0, 0);
        popupWindow.setOnDismissListener(new OnDismissListener(){
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                spinnerlayout.setBackgroundResource(R.drawable.preference_single_item);
            }

        });
        // listView��item����¼�
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                txt.setText(list.get(arg2));// ������ѡ��item��Ϊ������ı���
                // ������ʧ
                popupWindow.dismiss();
                popupWindow = null;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}

