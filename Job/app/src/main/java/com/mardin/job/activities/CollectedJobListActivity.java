package com.mardin.job.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mardin.job.R;
import com.mardin.job.models.Job;
import com.mardin.job.models.User;

import java.util.List;

public class CollectedJobListActivity extends ActionBarActivity {

    private List<Job> mItems;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collected_job_list);

        Intent intent = this.getIntent();

//        mUser = (User)intent.getParcelableExtra("user");
//        if (mUser._candidateProfile._collectjobs != null)
//            mItems = mUser._candidateProfile._collectjobs;
        //mItems = (List<Job>)intent.getParcelableArrayListExtra("jobs");
        mItems = (List<Job>) intent.getSerializableExtra("jobs");
        this.setTitle(R.string.collectedjobs);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collected_job_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<Job> getJobs() {
        return mItems;
    }
}
