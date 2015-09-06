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
        // 实例化一个List,添加数据
        list = new ArrayList<String>();
        list.add("全部");
        list.add("拱墅区");
        list.add("拱墅区");
        adapter = new PositionSpinnerAdapter(this, list);
        textView.setText((CharSequence) adapter.getItem(0));
        spinnerlayout = (LinearLayout) findViewById(R.id.spinnerid);
        // 点击右侧按钮，弹出下拉框
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
        // 设置弹框的宽度为布局文件的宽
        popupWindow.setWidth(spinnerlayout.getWidth());
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击弹框外部，弹框消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setContentView(layout);
        // 设置弹框出现的位置，在v的正下方横轴偏移textview的宽度，为了对齐~纵轴不偏移
        popupWindow.showAsDropDown(position, 0, 0);
        popupWindow.setOnDismissListener(new OnDismissListener(){
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                spinnerlayout.setBackgroundResource(R.drawable.preference_single_item);
            }

        });
        // listView的item点击事件
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                txt.setText(list.get(arg2));// 设置所选的item作为下拉框的标题
                // 弹框消失
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

