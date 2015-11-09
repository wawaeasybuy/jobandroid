package com.example.ryo.job_employer.adapter;

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

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.activities.EditPositionActivity;
import com.example.ryo.job_employer.activities.MyPositionActivity;
import com.example.ryo.job_employer.activities.PositionAdActivity;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.network.Constants;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/26.
 */
public class PositionAdapter extends BaseAdapter{
    private List<Job> Data;
    private Context context;
    public int state;

    public PositionAdapter(Context context,List data){
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
            holder.delete= (TextView) convertView.findViewById(R.id.delete);
            holder.updateTime= (TextView) convertView.findViewById(R.id.update_time);
            holder.my_position_limit=(TextView) convertView.findViewById(R.id.my_position_limit);
            holder.edit= (LinearLayout) convertView.findViewById(R.id.edit);
            holder.open= (LinearLayout) convertView.findViewById(R.id.open);
            holder.tuiguang= (LinearLayout) convertView.findViewById(R.id.tuiguang);
            holder.fenxiang= (LinearLayout) convertView.findViewById(R.id.fenxiang);
            holder.openImg= (ImageView) convertView.findViewById(R.id.openImg);
            holder.openText= (TextView) convertView.findViewById(R.id.openText);
            holder.tuiguangImg= (ImageView) convertView.findViewById(R.id.tuiguangImg);
            holder.fenxiangImg= (ImageView) convertView.findViewById(R.id.fenxiangImg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.positionName.setText("急求" + job.getPositionName());
            holder.updateTime.setText(ConverToString(job.getTimeUpdate()));
            //if(job.getIsRelease()){}else{}

        if(job.getIsRelease()){
            holder.openImg.setImageResource(R.drawable.locked);
            holder.openText.setText("已公开");
        }else{
            holder.openImg.setImageResource(R.drawable.unlocked);
            holder.openText.setText("公开");
        }

           holder.tuiguang.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(context, PositionAdActivity.class);
                   intent.putExtra("job",job);
                   ((MyPositionActivity)context).startActivityForResult(intent, Constants.TUIGUANGINTENT);
               }
           });
           holder.open.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(job.getIsRelease()==null||!job.getIsRelease()){
                       state=0;
                   }else{
                       state=1;
                   }
                   ((MyPositionActivity)context).doRelease(job.get_id(),state);
               }
           });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MyPositionActivity)context).deletePosition(job.get_id());
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditPositionActivity.class);
                    intent.putExtra("job", job);
                    ((Activity) context).startActivityForResult(intent, Constants.EditJobIntent);
                }
            });
        return convertView;
    }
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }
    class ViewHolder {
        public TextView positionName;
        public TextView delete;
        public TextView updateTime;
        public TextView my_position_limit;
        public LinearLayout edit;
        public LinearLayout open;
        public LinearLayout tuiguang;
        public LinearLayout fenxiang;

        public ImageView openImg;
        public ImageView tuiguangImg;
        public ImageView fenxiangImg;
        public TextView openText;
    }

}
