package com.mardin.job.adapters.job;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.models.Job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class JobListAdapter extends BaseAdapter {
    private List<Job> Data;
    private Context context;

    public JobListAdapter(Context context,List data){
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
        final Job job=Data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.position_list_item, null);
            holder = new ViewHolder();
            holder.positionName= (TextView) convertView.findViewById(R.id.positionName);
            holder.timeUpdate= (TextView) convertView.findViewById(R.id.timeUpdate);
            holder.city= (TextView) convertView.findViewById(R.id.city);
            holder.salary=(TextView) convertView.findViewById(R.id.salary);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(job.getPositionName()!=null){holder.positionName.setText(job.getPositionName());}
        if(job.getCompanyAddress()!=null){holder.city.setText(job.getCompanyAddress());}
        holder.salary.setText(job.getSalary()+"");
        if(job.getTimeUpdate()!=null){holder.timeUpdate.setText(ConverToString(job.getTimeUpdate()));}
        return convertView;
    }
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }
    class ViewHolder {
        public TextView positionName;
        public TextView timeUpdate;
        public TextView city;
        public TextView salary;

    }
}
