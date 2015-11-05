package com.mardin.job.adapters.job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.models.Job;

import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class AddressSelectListAdapter extends BaseAdapter{
    public String[] Data;
    public Context context;
    private int i;

    public interface  OnItemClickListener{

        void onItemClick(View view,int position);
    }

    private OnItemClickListener mOnItemClickListener;


    public  void setOnItemClickListener(OnItemClickListener mOnItemClickListener){

        this.mOnItemClickListener=mOnItemClickListener;

    }
    public AddressSelectListAdapter(Context context,String[] data){
        this.Data=data;
        this.context=context;
    }
    @Override
    public int getCount() {
        return Data.length;
    }

    @Override
    public Object getItem(int position) {
        return  Data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final String name=Data[position];
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.select_address_list_item, null);
            holder = new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder= (ViewHolder) convertView.getTag();
        }
//        if(i==position){
//            holder.name.setTextColor(0xffee2400);
//           // myViewHoder.tv.setBackgroundColor(0xffffffff);
//
//        }else{
//
//            holder.name.setTextColor(0xff666666);
//            //myViewHoder.tv.setBackgroundColor(0xfff8f8f8);
//        }
        holder.name.setText(name);
        if(mOnItemClickListener!=null){

            final ViewHolder finalHolder = holder;
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(finalHolder.name,position);
                    setPosition(position);
                }
            });

        }
        return convertView;
    }
    public void setPosition(int position){

        this.i=position;
        this.notifyDataSetChanged();

    }
    class ViewHolder {
        public TextView name;
    }
}
