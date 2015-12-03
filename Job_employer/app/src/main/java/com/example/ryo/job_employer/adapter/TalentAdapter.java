package com.example.ryo.job_employer.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ryo.job_employer.R;
import com.example.ryo.job_employer.activities.IntervieweeActivity;
import com.example.ryo.job_employer.activities.IntervieweeEvaluateActivity;
import com.example.ryo.job_employer.activities.PositionFitActivity;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.models.Resume;
import com.example.ryo.job_employer.models.Talent;
import com.example.ryo.job_employer.network.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/30.
 */
public class TalentAdapter extends BaseAdapter{
    private List<Talent> Data;
    private List<Job> data;
    private Context context;
    public TalentAdapter(Context context,List Data,List data){
        this.context=context;
        this.Data=Data;
        this.data=data;
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
        final Talent talent=Data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.talent_list_item, null);
            holder = new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.testValue= (TextView) convertView.findViewById(R.id.testValue);
            holder.schoolName= (TextView) convertView.findViewById(R.id.schoolName);
            holder.needEvalueOrNo= (TextView) convertView.findViewById(R.id.needEvalueOrNo);
            holder.positionName= (TextView) convertView.findViewById(R.id.positionName);
            holder.go_to_evaluate= (TextView) convertView.findViewById(R.id.go_to_evaluate);
            holder.professional= (TextView) convertView.findViewById(R.id.professional);
            holder.adjustToView= (LinearLayout) convertView.findViewById(R.id.adjustToView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        for(int i=0;i<data.size();i++){
            if(Data.get(position).get_job().equals(data.get(i).get_id())){
                if(data.get(i).getPositionName()!=null) {
                    holder.positionName.setText("急求"+data.get(i).getPositionName());
                }
            }
        }
        if(talent.getName()!=null){holder.name.setText(talent.getName());}
        holder.testValue.setText("职业测评分 ："+talent._candidate.getTestValue());
        if(talent.getSchoolName()!=null){holder.schoolName.setText(talent.getSchoolName());}
        if(talent.getProfessional()!=null){holder.professional.setText(talent.getProfessional());}
        //holder.schoolName.setText(talent.getSchoolName());
       // holder.professional.setText(talent.getProfessional());
        if(!talent.getIsInterview()){
            holder.go_to_evaluate.setPadding(5, 5, 5, 5);
            holder.go_to_evaluate.setBackgroundColor(0xff0080fe);
            holder.go_to_evaluate.setTextColor(0xffffffff);
            holder.go_to_evaluate.setText("去评价");
            holder.needEvalueOrNo.setText("待评价");
        }
        else{
            holder.go_to_evaluate.setPadding(0,0,0,0);
            holder.go_to_evaluate.setBackgroundColor(0xffffffff);
            holder.go_to_evaluate.setTextColor(0xff666666);
            holder.go_to_evaluate.setText("看评价");
            holder.needEvalueOrNo.setText("已评价");
        }
        holder.go_to_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doEvaluate(talent);
            }
        });
        return convertView;
    }
    public void doEvaluate(Talent talent){
        Intent intent=new Intent(context,IntervieweeEvaluateActivity.class);
        intent.putExtra("talent",talent);
        ((IntervieweeActivity)context).startActivityForResult(intent, Constants.EVALUATEINTENT);
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
        public TextView needEvalueOrNo;
        public TextView professional;
        public TextView positionName;
        public TextView go_to_evaluate;
        public LinearLayout adjustToView;
    }
}
