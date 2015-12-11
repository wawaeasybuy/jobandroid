package com.example.ryo.job_employer.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.ryo.job_employer.helper.GlobalProvider;
import com.example.ryo.job_employer.models.Employer;
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
            holder.tuiguang_text= (TextView) convertView.findViewById(R.id.tuiguang_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
            holder.positionName.setText(job.getPositionName());
            holder.updateTime.setText(ConverToString(job.getTimeUpdate()));
            //if(job.getIsRelease()){}else{}

        if(job.getIsRelease()){
            holder.openImg.setImageResource(R.drawable.unlocked);
            holder.openText.setText("已公开");
        }else{
            holder.openImg.setImageResource(R.drawable.locked);
            holder.openText.setText("未公开");
        }

        if(job.getIsTop()||job.getIsUrg()){
            holder.tuiguang_text.setText("已推广");
        }else{
            holder.tuiguang_text.setText("推广");
        }
        if(job.getIsPush()!=null){
            if(job.getIsPush()){
                holder.my_position_limit.setText("可发布");
            }else{
                holder.my_position_limit.setText("不可发布");
            }
        }

           holder.tuiguang.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                       if(!job.getIsPush()){
                           new AlertDialog.Builder(context)
                                   .setMessage("该职位不可发布，暂时不能推广！")
                                   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {

                                       }
                                   }).show();
                           return;
                       }else if(!job.getIsRelease()){
                           new AlertDialog.Builder(context)
                                   .setMessage("该职位还没公开，暂时不能推广！")
                                   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {

                                       }
                                   }).show();
                           return;
                       }
                       Intent intent=new Intent(context, PositionAdActivity.class);
                       intent.putExtra("job",job);
                       ((MyPositionActivity)context).startActivityForResult(intent, Constants.TUIGUANGINTENT);
               }
           });
           holder.open.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(adJustToOpen()){
                       if(!job.getIsPush()){
                           new AlertDialog.Builder(context)
                                   .setMessage("该职位信息还不完善，暂时不能公开！")
                                   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {

                                       }
                                   }).show();
                       }else{
                           if(job.getIsRelease()==null||!job.getIsRelease()){
                               state=0;
                           }else{
                               state=1;
                           }
                           ((MyPositionActivity)context).doRelease(job.get_id(), state);
                       }
                   }else{
                       new AlertDialog.Builder(context)
                               .setMessage("公司信息还未完善，暂时不能公开！")
                               .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               }).show();
                   }
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
            holder.fenxiang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MyPositionActivity)context).doShare(job);
                }
            });
        return convertView;
    }
    public boolean adJustToOpen(){
        if(GlobalProvider.getInstance().employer.getName()==null||GlobalProvider.getInstance().employer.getName().equals("")||GlobalProvider.getInstance().employer.getCompanyname()==null||GlobalProvider.getInstance().employer.getCompanyname().equals("")||GlobalProvider.getInstance().employer.getMainBusiness()==null||GlobalProvider.getInstance().employer.getMainBusiness().equals("")||GlobalProvider.getInstance().employer.getCompanyURL()==null||GlobalProvider.getInstance().employer.getCompanyURL().equals("")||GlobalProvider.getInstance().employer.getProvince()==null||GlobalProvider.getInstance().employer.getCompanyAddress()==null||GlobalProvider.getInstance().employer.getCompanyAddress().equals("")||GlobalProvider.getInstance().employer.getCompanyInfo()==null||GlobalProvider.getInstance().employer.getCompanyInfo().equals("")){
            return false;
        }else{
            return true;
        }
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
        public TextView tuiguang_text;
    }

}
