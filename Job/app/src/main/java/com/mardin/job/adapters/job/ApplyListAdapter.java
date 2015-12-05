package com.mardin.job.adapters.job;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.models.Apply;
import com.mardin.job.models.Job;
import com.mardin.job.models.PublicResume;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/11/27.
 */
public class ApplyListAdapter extends BaseAdapter{
    private List<PublicResume> Apply;
    private Context context;
    public ApplyListAdapter(Context context,List apply){
        this.Apply=apply;
        this.context=context;
    }
    @Override
    public int getCount() {
        return Apply.size();
    }

    @Override
    public Object getItem(int position) {
        return Apply.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final PublicResume pub=Apply.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_apply_listitem, null);
            holder = new ViewHolder();
            holder.firmName= (TextView) convertView.findViewById(R.id.firmName);
            holder.positionName= (TextView) convertView.findViewById(R.id.positionName);
            holder.city= (TextView) convertView.findViewById(R.id.city);
            holder.salary=(TextView) convertView.findViewById(R.id.salary);
            holder.blackLine= (ImageView) convertView.findViewById(R.id.blackLine);
            holder.blackLine2= (ImageView) convertView.findViewById(R.id.blackLine2);
            holder.success= (TextView) convertView.findViewById(R.id.success);
            holder.date= (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(pub.getCompanyName()!=null){  holder.firmName.setText(pub.getCompanyName());}
        if(pub._job!=null){holder.city.setText(pub.get_job().getCity()+"/"+pub.get_job().getRegion());}
        if(pub._job!=null){if(pub._job.getPositionName()!=null){holder.positionName.setText(pub._job.getPositionName());}}
        if(pub._job!=null){holder.salary.setText("底薪"+pub.get_job().getSalary());}
        if(pub.isRead){
            holder.success.setText("已查看");
        }else{
            holder.success.setText("投递成功");
        }

        holder.date.setText(ConverToString(pub.getDelieverTime()));

        return convertView;
    }

    class ViewHolder {
        public TextView firmName;
        public TextView positionName;
        public TextView city;
        public TextView salary;
        public ImageView blackLine;
        public ImageView blackLine2;
        public TextView success;
        public TextView date;

    }
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }
}
