package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mardin.job.R;
import com.mardin.job.fragments.zhiwei.PersonalFragment;

/**
 * Created by Ryo on 2015/9/8.
 */
public class PersonalMyQuizActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_my_quiz);

        LinearLayout turn_left = (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        TextView personal_question_title= (TextView) findViewById(R.id.personal_question_title);
        personal_question_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PersonalMyQuizActivity.this, Question.class);
                startActivity(intent);
            }
        });


        ImageView  delete=(ImageView)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                new AlertDialog.Builder(PersonalMyQuizActivity.this)

                        .setMessage("Do You Want To Delete This Quiz?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                            @Override

                            public void onClick(DialogInterface dialog, int which) {

                              //  new AlertDialog.Builder(PersonalMyQuizActivity.this)

                                       // .setMessage("Finished!");

                                  finish();

                            }

                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {



                        Log.i("alertdialog", " Please Save The Data!");

                    }

                }).show();



            }

        });
    }
}

