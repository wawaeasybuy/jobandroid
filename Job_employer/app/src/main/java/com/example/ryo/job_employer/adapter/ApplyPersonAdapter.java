package com.example.ryo.job_employer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.activities.ApplyPersonActivity;
import com.example.ryo.job_employer.activities.PersonalResumeActivity;
import com.example.ryo.job_employer.activities.PositionFitActivity;
import com.example.ryo.job_employer.models.Resume;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public class ApplyPersonAdapter extends BaseAdapter{
    private List<Resume> Data;
    private Context context;
    public ApplyPersonAdapter(Context context,List Data){
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
        final Resume resume=Data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.position_fit_all_list_item, null);
            holder = new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.testValue= (TextView) convertView.findViewById(R.id.testValue);
            holder.schoolName= (TextView) convertView.findViewById(R.id.schoolName);
            holder.chakan= (TextView) convertView.findViewById(R.id.chakan);
            holder.professional= (TextView) convertView.findViewById(R.id.professional);
            holder.ignore= (TextView) convertView.findViewById(R.id.ignore);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(resume.getName());
        holder.testValue.setText("ְҵ������ ��"+resume.getTestValue());
        holder.schoolName.setText(resume.getSchoolName());
        holder.professional.setText(resume.getProfessional());
        holder.chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,PersonalResumeActivity.class);
                intent.putExtra("resume",resume);
                context.startActivity(intent);
                //((PositionFitActivity) context).PositionFitUpdate(resume);
            }
        });
        holder.ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ApplyPersonActivity)context).ApplyPersonIgnore(resume.get_id());
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
        public TextView name;
        public TextView testValue;
        public TextView schoolName;
        public TextView chakan;
        public TextView professional;
        public TextView ignore;
    }
}
