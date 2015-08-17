package com.mardin.job.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.activities.JobDetailActivityFragment;
import com.mardin.job.models.Job;
import com.mardin.job.models.JobDetail;

import org.w3c.dom.Text;

/**
 * Created by Mardin on 7/11/15.
 */
public class JobDetailAdapter extends RecyclerView.Adapter<JobDetailAdapter.ViewHolder> {

    private Fragment mFragment;
    private JobDetail mJob;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //CardView cardView;
        /*
        first header
         */
        TextView companyTv;
        TextView districtTv;
        TextView timeTv;
        TextView headerquarterTv;

        /*
        description
         */
        TextView descriptionTv;

        /*
        third section
         */
        TextView sizeTv;
        TextView foundedTv;
        TextView revenueTv;
        TextView typeTv;
        Button collectBtn;
        Button weblinkBtn;

        ViewHolder(View itemView) {
            super(itemView);
            //cardView = (CardView)itemView.findViewById(R.id.cv);
            companyTv = (TextView)itemView.findViewById(R.id.tvJobCompany);
            districtTv = (TextView)itemView.findViewById(R.id.tvDistrict);
            timeTv = (TextView)itemView.findViewById(R.id.tvTime);

            descriptionTv = (TextView)itemView.findViewById(R.id.tvJobDescription);

            headerquarterTv = (TextView)itemView.findViewById(R.id.tvHeaderquarter);
            sizeTv = (TextView)itemView.findViewById(R.id.tvSize);
            foundedTv = (TextView)itemView.findViewById(R.id.tvFounded);
            revenueTv = (TextView)itemView.findViewById(R.id.tvRevenue);
            typeTv = (TextView)itemView.findViewById(R.id.tvType);
            collectBtn = (Button)itemView.findViewById(R.id.btnsave);
            weblinkBtn = (Button)itemView.findViewById(R.id.btnLink);
        }
    }

    public JobDetailAdapter(JobDetail job, Fragment fragment) {
        mFragment = fragment;
        mJob = job;
    }

    public void setMJob(JobDetail job) {
        mJob = job;
    }

    @Override
    public JobDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_detailjob_first, parent, false);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_detailjob_second, parent, false);
                break;
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_detailjob_third, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_detailjob_first, parent, false);
                break;
        }

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.jobNameTv.setText(mJob.jobname);

        switch (position) {
            case 0:
                if (mJob.companyname != null)
                    holder.companyTv.setText(mJob.companyname);
                if (mJob.district != null)
                    holder.districtTv.setText(mJob.district);
                if (mJob.createtime != null) {
                    String time_l = DateUtils.getRelativeTimeSpanString(mJob.createtime.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
                    holder.timeTv.setText(time_l);
                }
                holder.collectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((JobDetailActivityFragment)mFragment).actionCollect();
                    }
                });

                if (mJob.applylink == null || mJob.applylink.equals(""))  {
                    holder.weblinkBtn.setVisibility(View.GONE);
                }else {
                    holder.weblinkBtn.setVisibility(View.VISIBLE);
                    holder.weblinkBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((JobDetailActivityFragment)mFragment).gotoWeb();
                        }
                    });
                }

                break;
            case 1:
                if (mJob.description != null)
                    holder.descriptionTv.setText(mJob.description);
                break;
            case 2:
                if (mJob.companyname != null)
                    holder.companyTv.setText(mJob.companyname);
                if (mJob.district != null)
                    holder.headerquarterTv.setText(mJob.district);
                if (mJob._employer != null && mJob._employer.get_employerProfile() != null
                        && mJob._employer._employerProfile.type != null) {
                    holder.sizeTv.setText(mJob._employer.get_employerProfile().size);
                }
                if (mJob._employer != null && mJob._employer.get_employerProfile() != null
                        && mJob._employer._employerProfile.founded != null) {
                    String time_l = DateUtils.getRelativeTimeSpanString(mJob._employer._employerProfile.founded.getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
                    holder.foundedTv.setText(time_l);
                }

                if (mJob._employer != null && mJob._employer.get_employerProfile() != null
                        && mJob._employer._employerProfile.revenue != null) {
                    holder.revenueTv.setText(mJob._employer._employerProfile.revenue);
                }

                if (mJob._employer != null && mJob._employer.get_employerProfile() != null
                        && mJob._employer._employerProfile.type != null) {
                    holder.typeTv.setText(mJob._employer._employerProfile.type);
                }

                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
