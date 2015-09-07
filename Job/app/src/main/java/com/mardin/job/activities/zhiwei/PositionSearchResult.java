package com.mardin.job.activities.zhiwei;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mardin.job.R;

/**
 * Created by Ryo on 2015/9/1.
 */
public class PositionSearchResult extends Activity {

    private TextView spinnerText1;
    private Spinner spinner1;
    private ArrayAdapter adapter1;

    private TextView spinnerText2;
    private Spinner spinner2;
    private ArrayAdapter adapter2;

    //private TextView spinnerText2;
    private Spinner spinner3;
    private ArrayAdapter adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_search_result);

        LinearLayout turn_left= (LinearLayout) findViewById(R.id.turn_left);
        turn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        ImageView position_header_search= (ImageView) findViewById(R.id.position_header_search);
        position_header_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PositionSearchResult.this, PositionSearchActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout position1= (LinearLayout) findViewById(R.id.position1);
        position1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PositionSearchResult.this, PositionDetail.class);
                startActivity(intent);
            }
        });

        LinearLayout position2= (LinearLayout) findViewById(R.id.position2);
        position2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PositionSearchResult.this, PositionDetail.class);
                startActivity(intent);
            }
        });

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinnerText1= (TextView) findViewById(R.id.spinnerText1);

        //attach selected_content to ArrayAdapter
        adapter1 = ArrayAdapter.createFromResource(this, R.array.position_area, android.R.layout.simple_spinner_item);

        //set the style of DropDownView
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //add adapter1 to spinner
        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(new SpinnerXMLSelectedListener());

        //setVisibility
        spinner1.setVisibility(View.VISIBLE);


        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinnerText2= (TextView) findViewById(R.id.spinnerText2);

        //attach selected_content to ArrayAdapter
        adapter2 = ArrayAdapter.createFromResource(this, R.array.position_category, android.R.layout.simple_spinner_item);

        //set the style of DropDownView
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //add adapter1 to spinner
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new SpinnerXMLSelectedListener());


        spinner3 = (Spinner) findViewById(R.id.spinner3);
       // spinnerText1= (TextView) findViewById(R.id.spinnerText1);

        //attach selected_content to ArrayAdapter
        adapter3 = ArrayAdapter.createFromResource(this, R.array.position_more_choice, android.R.layout.simple_spinner_item);

        //set the style of DropDownView
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //add adapter1 to spinner
        spinner3.setAdapter(adapter3);

        spinner3.setOnItemSelectedListener(new SpinnerXMLSelectedListener());

        //setVisibility
        spinner3.setVisibility(View.VISIBLE);

    }

    //use XML
    class SpinnerXMLSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {


            spinnerText1.setText(""+adapter1.getItem(arg2));
            spinnerText2.setText(""+adapter2.getItem(arg2));
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }

//use XML

    /*
    class SpinnerXMLSelectedListener1 implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        spinnerText2.setText(""+adapter2.getItem(arg2));
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

  }
  */

}