package com.mardin.job.adapters.job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mardin.job.R;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class PositionFitHeaderAdapter extends BaseAdapter{
    public Context context;
    public String[] Data;

    public PositionFitHeaderAdapter(Context context, String[] Data){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.position_pull_down_list_item, null);
            holder = new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position==0){holder.name.setTextColor(0xffee2400);}
        holder.name.setText(Data[position]);
        return convertView;
    }
    class ViewHolder {
      public TextView name;

    }
}
