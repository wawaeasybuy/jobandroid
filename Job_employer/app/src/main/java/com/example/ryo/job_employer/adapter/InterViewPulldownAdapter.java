package com.example.ryo.job_employer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.models.Job;

import java.util.List;

/**
 * Created by Administrator on 2015/12/9.
 */
public class InterViewPulldownAdapter extends BaseAdapter {
    public Context context;
    public String[] Data;

    public InterViewPulldownAdapter(Context context,String[] Data){
        this.context=context;
        this.Data=Data;
    }
    @Override
    public int getCount() {
        return Data.length;
    }

    @Override
    public Object getItem(int position) {
        return  Data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.position_fit_header_list_item, null);
            holder = new ViewHolder();
            holder.job= (TextView) convertView.findViewById(R.id.job);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //if(position==0){holder.job.setText("全部职位");}
        holder.job.setText(Data[position]);
        return convertView;
    }
    class ViewHolder {
        public TextView job;

    }
}