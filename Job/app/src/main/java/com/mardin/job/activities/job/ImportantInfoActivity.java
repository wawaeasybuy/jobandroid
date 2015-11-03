package com.mardin.job.activities.job;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.models.Resume;

/**
 * Created by Ryo on 2015/9/15.
 */
public class ImportantInfoActivity extends Activity {
    public EditText selfEvaluation;
    public EditText experience;
    public EditText works;
    public TextView save;
    public Resume resume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_important_info);

        selfEvaluation= (EditText) findViewById(R.id.selfEvaluation);
        experience= (EditText) findViewById(R.id.experience);
        works= (EditText) findViewById(R.id.works);

        save= (TextView) findViewById(R.id.save);

        this.resume= GlobalProvider.getInstance().resume;
        if(resume.getSelfEvaluation()!=null){selfEvaluation.setText(resume.getSelfEvaluation());}
        if(resume.getExperience()!=null){experience.setText(resume.getExperience());}
        if(resume.getWorks()!=null){works.setText(resume.getWorks());}


        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalProvider.getInstance().resume.setSelfEvaluation(selfEvaluation.getText().toString());
                GlobalProvider.getInstance().resume.setExperience(experience.getText().toString());
                GlobalProvider.getInstance().resume.setWorks(works.getText().toString());
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
