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
import com.example.ryo.job_employer.activities.PositionFitActivity;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.models.Resume;
import com.example.ryo.job_employer.network.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class PositionFitAllAdapter extends BaseAdapter{
    private List<Resume> Data;
    private List<Job> list;
    private Context context;
    public PositionFitAllAdapter(Context context,List Data,List list){
        this.context=context;
        this.Data=Data;
        this.list=list;
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
            holder.positionName= (TextView) convertView.findViewById(R.id.positionName);
            holder.testValue= (TextView) convertView.findViewById(R.id.testValue);
            holder.schoolName= (TextView) convertView.findViewById(R.id.schoolName);
            holder.chakan= (TextView) convertView.findViewById(R.id.chakan);
            holder.professional= (TextView) convertView.findViewById(R.id.professional);
            holder.ignore= (TextView) convertView.findViewById(R.id.ignore);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//            for(int i=1;i<list.size();i++){
//                if(resume.getExpectedPosition().equals(list.get(i).getPositionCategory())){
//                    holder.positionName.setText("急求"+list.get(i).getPositionName());
//                }
//            }
            if(resume.getExpectedPosition()!=null){holder.positionName.setText(resume.getExpectedPosition());}
            if(resume.getName()!=null)holder.name.setText(resume.getName());
            holder.testValue.setText("职业测评分 ："+resume._candidate.getTestValue());
            if(resume.getSchoolName()!=null){holder.schoolName.setText(resume.getSchoolName());}
            if(resume.getProfessional()!=null){holder.professional.setText(resume.getProfessional());}
            holder.chakan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((PositionFitActivity)context).PositionFitUpdate(resume);
                    }
                });
            holder.ignore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((PositionFitActivity)context).PositionFitIgnore(resume.get_id());
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
        public TextView positionName;
        public TextView testValue;
        public TextView schoolName;
        public TextView chakan;
        public TextView professional;
        public TextView ignore;
    }
}
