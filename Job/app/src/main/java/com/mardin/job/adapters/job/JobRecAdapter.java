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
public class JobRecAdapter extends BaseAdapter {
    private List<Job> Data;
    private Context context;

    public JobRecAdapter(Context context,List data){
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

        final boolean[] ishowing = {false};
        ViewHolder holder = null;
        final Job job=Data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.job_rec_list_item, null);
            holder = new ViewHolder();
            holder.positionName= (TextView) convertView.findViewById(R.id.positionName);
            holder.timeUpdate= (TextView) convertView.findViewById(R.id.timeUpdate);
            holder.city= (TextView) convertView.findViewById(R.id.city);
            holder.salary=(TextView) convertView.findViewById(R.id.salary);
//            holder.job_character_pull= (LinearLayout) convertView.findViewById(R.id.job_character_pull);
//            holder.turn_img= (ImageView) convertView.findViewById(R.id.turn_img);
//            holder.job_character_layout= (LinearLayout) convertView.findViewById(R.id.job_character_layout);
//            holder.job_character= (TextView) convertView.findViewById(R.id.job_character);
            holder.top= (TextView) convertView.findViewById(R.id.top);
            holder.urg= (TextView) convertView.findViewById(R.id.urg);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ViewHolder finalHolder = holder;
        final ViewHolder finalHolder1 = holder;
//        holder.job_character_pull.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!ishowing[0]){
//                    finalHolder.job_character_layout.setVisibility(View.VISIBLE);
//                    finalHolder1.turn_img.setImageResource(R.drawable.turn_up_red);
//                }else{
//                    finalHolder.job_character_layout.setVisibility(View.GONE);
//                    finalHolder1.turn_img.setImageResource(R.drawable.turn_down_red);
//                }
//                ishowing[0] =!ishowing[0];
//            }
//        });

        if(job.getIsTop()){holder.top.setVisibility(View.VISIBLE);}else{holder.top.setVisibility(View.GONE);}
        if(job.getIsUrg()){holder.urg.setVisibility(View.VISIBLE);}else{holder.urg.setVisibility(View.GONE);}
//        holder.job_character_layout.setVisibility(View.GONE);
        if(job.getPositionName()!=null){holder.positionName.setText(job.getPositionName());}
        if(job.getCompanyAddress()!=null){holder.city.setText(job.getCompanyAddress());}
        holder.salary.setText("底薪"+job.getSalary()+"");
        if(job.getTimeUpdate()!=null){holder.timeUpdate.setText(ConverToString(job.getTimeUpdate()));}
        //if(job.getPositionCharacter()!=null&&!job.getPositionCharacter().equals("")){holder.job_character.setText(job.getPositionCharacter());}else{holder.job_character.setText("暂无职位特色");}
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


        public TextView top;
        public TextView urg;

    }
}
