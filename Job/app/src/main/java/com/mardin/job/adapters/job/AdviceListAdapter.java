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
import com.mardin.job.models.Job;
import com.mardin.job.models.Talent;
import com.mardin.job.models.TalentList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
public class AdviceListAdapter extends BaseAdapter{
    public List<TalentList> Data;
    public Context context;

    public AdviceListAdapter(Context context,List Data){
        this.context=context;
        this.Data=Data;
    }
    @Override
    public int getCount() {
        return Data.size();
    }

    @Override
    public Object getItem(int position) {
        return Data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        TalentList talent=Data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.advice_list_item, null);
            holder = new ViewHolder();
            holder.adviceText= (TextView) convertView.findViewById(R.id.adviceText);
            holder.companyName= (TextView) convertView.findViewById(R.id.companyName);
            holder.evaluationTime= (TextView) convertView.findViewById(R.id.evaluationTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(talent._talent!=null){if(talent._talent.getAdviceText()!=null){holder.adviceText.setText(talent._talent.getAdviceText());}}
        if(talent._talent!=null){if(talent._talent.getCompanyName()!= null){holder.companyName.setText(talent._talent.getCompanyName());}}
        if(talent._talent!=null){if(talent._talent.getEvaluationTime()!=null){holder.evaluationTime.setText(ConverToString(talent._talent.getEvaluationTime()));}}
        return convertView;
    }
    public static String ConverToString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }
    class ViewHolder {
        public TextView adviceText;
        public TextView companyName;
        public TextView evaluationTime;

    }
}
