package com.mardin.job.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.models.User;
import com.mardin.job.network.Constants;

/**
 * Created by Mardin on 7/12/15.
 */
public class MeAdapter extends BaseAdapter {

    private User mUser;
    private LayoutInflater mInflater;
    private Context mContext;

    public MeAdapter(Context context, User user) {
        mUser = user;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setMUser(User user) {
        mUser = user;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.melistitem, null);
            holder = new ViewHolder();
            holder.tv = (TextView)convertView.findViewById(R.id.tv);
            holder.tvTag = (TextView)convertView.findViewById(R.id.tvtag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if(mUser == null) {
            return convertView;
        }else {
            switch (position){
                case 0:
                    if (mUser.role.equals(Constants.candidate)) {
                        holder.tvTag.setText(R.string.label_your_name);
                        if (mUser._candidateProfile.name != null)
                            holder.tv.setText(mUser._candidateProfile.name);
                    }else {
                        holder.tvTag.setText(R.string.companyname);
                        if (mUser._employerProfile.companyname != null)
                            holder.tv.setText(mUser._employerProfile.companyname);
                    }
                        break;
                case 1:
                    holder.tvTag.setText(R.string.label_your_email);
                    holder.tv.setText(mUser.email);
                    break;
                case 2:
                    holder.tvTag.setText(R.string.phone);
                    if(mUser.phone != null)
                        holder.tv.setText(mUser.phone);
                    break;
                case 3:
                    if (mUser.role.equals(Constants.candidate))
                        holder.tvTag.setText(R.string.collectedjobs);
                    else
                        holder.tvTag.setText(R.string.postjobs);
                    break;
                case 4:
                    if (Constants.getToken(mContext) == null || Constants.getToken(mContext).equals("")) {
                        holder.tvTag.setText(R.string.title_login);
                    }else {
                        holder.tvTag.setText(R.string.action_logout);
                    }
                    break;

                default:
                    break;
            }

            return convertView;
        }


    }

    public class ViewHolder {
        public TextView tv;
        public TextView tvTag;
    }

}
