package com.mardin.job.adapters.zhiwei;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/9/6.
 */
public class PositionSpinnerAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList<String> list;

    public PositionSpinnerAdapter(Context context, ArrayList<String> list) {
        super();
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_position_spinner_item, null);
            viewHolder = new ViewHolder();
            viewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.spinner_dropdown_layout);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.spinner_dropdown_item);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(list.size() == position+1){
            viewHolder.layout.setBackgroundResource(R.drawable.finish_background);
        }else{
            viewHolder.layout.setBackgroundResource(R.drawable.selectable_item_background);
        }
        viewHolder.textView.setText(list.get(position));
        return convertView;
    }

    public class ViewHolder {
        RelativeLayout layout;
        TextView textView;
    }

    public void refresh(List<String> l) {
        this.list.clear();
        list.addAll(l);
        notifyDataSetChanged();
    }

    public void add(String str) {
        list.add(str);
        notifyDataSetChanged();
    }

    public void add(ArrayList<String> str) {
        list.addAll(str);
        notifyDataSetChanged();

    }
}