package com.mardin.job.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.models.Job;

import java.util.List;

/**
 * Created by Mardin on 7/13/15.
 */
public class CollectedJobListAdapter extends ArrayAdapter<Job> {
    Context mContext;

    public CollectedJobListAdapter(Context context, List<Job> items) {
        super(context, R.layout.joblistitem, items);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.joblistitem, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            viewHolder.tvCompany = (TextView) convertView.findViewById(R.id.tvCompany);
            viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Job job = getItem(position);
        viewHolder.tvTitle.setText(job.jobname);
        viewHolder.tvDescription.setText(job.description);
        viewHolder.tvCompany.setText(job.companyname + "," + job.district);

        String time_l = DateUtils.getRelativeTimeSpanString(job.createtime.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
        viewHolder.tvTime.setText(time_l);

        return convertView;
    }


    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvCompany;
        TextView tvTime;
    }

}
