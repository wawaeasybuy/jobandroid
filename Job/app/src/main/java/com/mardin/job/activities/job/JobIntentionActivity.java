package com.mardin.job.activities.job;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.models.Resume;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ByteArrayEntity;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by Ryo on 2015/9/15.
 */
public class JobIntentionActivity extends Activity {
    public EditText expectedIndustry;
    public EditText expectedPosition;
    public EditText expectedAddress;
    public TextView save;
     public Resume resume;
    //public static String employerId="5628474a583221f00507653b";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_job_intention);
        expectedIndustry= (EditText) findViewById(R.id.expectedIndustry);
        expectedPosition= (EditText) findViewById(R.id.expectedPosition);
        expectedAddress= (EditText) findViewById(R.id.expectedAddress);
        save= (TextView) findViewById(R.id.save);

        this.resume=GlobalProvider.getInstance().resume;
        if(resume.getExpectedAddress()!=null){expectedAddress.setText(resume.getExpectedAddress());}
        if(resume.getExpectedIndustry()!=null){expectedIndustry.setText(resume.getExpectedIndustry());}
        if(resume.getExpectedPosition()!=null){expectedPosition.setText(resume.getExpectedPosition());}
        save.setOnClickListener(new View.OnClickListener() {
         @Override
          public void onClick(View v) {
           //doCreate();
             GlobalProvider.getInstance().resume.setExpectedIndustry(expectedIndustry.getText().toString());
             GlobalProvider.getInstance().resume.setExpectedAddress(expectedAddress.getText().toString());
             GlobalProvider.getInstance().resume.setExpectedPosition(expectedPosition.getText().toString());
             setResult(Activity.RESULT_OK);
             finish();
             }
});
        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

//    private void doCreate() {
//        Resume resume=new Resume();
//        resume.setExpectedIndustry(expect.getText().toString());
//        JsonFactory jsonFactory = new JsonFactory();
//        ObjectMapper objectMapper = new ObjectMapper();
//        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
//        String json = "";
//        try {
//            json = ow.writeValueAsString(resume);
//            ByteArrayEntity entity= new ByteArrayEntity(json.getBytes("UTF-8"));
//            GlobalProvider globalProvider = GlobalProvider.getInstance();
//            String Url=Constants.createResumeStr+"/"+employerId;
//            globalProvider.post(this,Url, entity, "application/json", new RequestListener() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                    Toast.makeText(JobIntentionActivity.this, "success to create!", Toast.LENGTH_SHORT).show();
//                    //parseLoginResult(new String(responseBody));
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                    //Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
