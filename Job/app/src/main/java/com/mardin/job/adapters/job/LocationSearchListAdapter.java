package com.mardin.job.adapters.job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mardin.job.R;
import com.mardin.job.models.City;

import java.util.List;

/**
 * Created by Administrator on 2015/12/17.
 */
public class LocationSearchListAdapter extends BaseAdapter{
    public Context context;
    public List<City> Data;

    public LocationSearchListAdapter(Context context, List Data){
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.position_fit_header_list_item, null);
            holder = new ViewHolder();
            holder.job= (TextView) convertView.findViewById(R.id.job);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //if(position==0){holder.job.setText("全部职位");}
        holder.job.setText(Data.get(position).getC_city());
        return convertView;
    }
    class ViewHolder {
        public TextView job;

    }
}
