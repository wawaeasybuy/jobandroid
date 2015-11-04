package com.mardin.job.adapters.job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.models.Job;

import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class AddressSelectListAdapter extends BaseAdapter{
    public List<String> Data;
    public Context context;
    public AddressSelectListAdapter(Context context,List data){
        this.Data=data;
        this.context=context;
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
        final String name=Data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.select_address_list_item, null);
            holder = new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.name.setText(name);
        return convertView;
    }
    class ViewHolder {
        public TextView name;
    }
}
