package com.example.ryo.job_employer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.models.EmpMessage;
import com.example.ryo.job_employer.models.Job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/11.
 */
public class MessageAdapter extends BaseAdapter{
    public Context context;
    public List<EmpMessage> Data;

    public MessageAdapter(Context context,List Data){
        this.context=context;
        this.Data=Data;
    }
    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int position) {
        return  Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        EmpMessage message=Data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_list_item, null);
            holder = new ViewHolder();

            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.date= (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(message.getIndex()==0){
            holder.name.setText("收到" + message.getName() + "的新简历");
        }else if(message.getIndex()==1){
            holder.name.setText("成功将"+Data.get(position).getName()+"职位置顶");
        }else if(message.getIndex()==2){
            holder.name.setText("成功将"+Data.get(position).getName()+"职位加急");
        }
        if(message.getDate()!=null) {
            holder.date.setText(ConverToString(message.getDate()));
        }
        //if(position==0){holder.job.setText("全部职位");}
        return convertView;
    }
    class ViewHolder {
        public TextView name;
        public TextView date;

    }
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }
}
