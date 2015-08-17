package com.mardin.job.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Job;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class JobDetailActivity extends ActionBarActivity {

    private Job mJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);


        Intent intent = this.getIntent();
        mJob = (Job)intent.getSerializableExtra("job");
        this.setTitle(mJob.jobname);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_detail, menu);
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
            removecollect();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Job getmJob() {
        return mJob;
    }

    public void setmJob(Job mJob) {
        this.mJob = mJob;
    }

    public void removecollect() {
        String collectStr = Constants.jobCollectUrlStr + "/" + mJob._id + "/recollect" ;

        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.get(this, collectStr, null, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                if (statusCode == 200) {
                    Constants.needReflesh = true;
                    Toast.makeText(JobDetailActivity.this, "removed successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(JobDetailActivity.this, "fail to remove", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(JobDetailActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }


}
