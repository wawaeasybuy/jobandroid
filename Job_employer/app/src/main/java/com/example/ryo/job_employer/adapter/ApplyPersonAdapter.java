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
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.models.PublicResume;
import com.example.ryo.job_employer.models.Resume;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public class ApplyPersonAdapter extends BaseAdapter{
    private List<PublicResume> Data;
    private Context context;
   // public List<Job> data;
    public ApplyPersonAdapter(Context context,List Data){
        this.context=context;
        this.Data=Data;
        //this.data=data;
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
        final PublicResume resume=Data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.position_fit_all_list_item, null);
            holder = new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.testValue= (TextView) convertView.findViewById(R.id.testValue);
            holder.schoolName= (TextView) convertView.findViewById(R.id.schoolName);
            holder.chakan= (TextView) convertView.findViewById(R.id.chakan);
            holder.professional= (TextView) convertView.findViewById(R.id.professional);
            holder.ignore= (TextView) convertView.findViewById(R.id.ignore);
            holder.positionName= (TextView) convertView.findViewById(R.id.positionName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        for(int i=0;i<data.size();i++){
//            if(Data.get(position).get_job().equals(data.get(i).get_id())){
//                if(data.get(i).getPositionName()!=null) {
//                    holder.positionName.setText("急求"+data.get(i).getPositionName());
//                }
//            }
//        }
        if(resume._job!=null){
            if(resume._job.getPositionName()!=null){
                holder.positionName.setText(resume._job.getPositionName());
            }
        }
        if(resume.getName()!=null){holder.name.setText(resume.getName());}
        holder.testValue.setText("职业测评分 ："+resume._candidate.getTestValue());
        if(resume.getSchoolName()!=null){holder.schoolName.setText(resume.getSchoolName());}
        if(resume.getProfessional()!=null) {holder.professional.setText(resume.getProfessional());}
        holder.chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalProvider.getInstance().isAllowToTalent=true;
                Intent intent=new Intent(context,PersonalResumeActivity.class);
                intent.putExtra("Pubresume", resume);
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
        public TextView positionName;
    }
}
