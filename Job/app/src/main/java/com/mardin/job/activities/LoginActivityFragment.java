package com.mardin.job.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mardin.job.R;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {
    private EditText usernameET;
    private EditText passwordET;
    private Button loginBtn;
    private Button regEmployeeBtn;
    private Button regEmployerBtn;
    AsyncHttpClient mClient;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mClient = new AsyncHttpClient();
        usernameET = (EditText)getView().findViewById(R.id.usernameET);
        passwordET = (EditText)getView().findViewById(R.id.passwordET);
        loginBtn = (Button)getView().findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAction(v);
            }
        });
        regEmployeeBtn = (Button)getView().findViewById(R.id.regEmployeeBtn);
        regEmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegEmployee();
            }
        });

        regEmployerBtn = (Button)getView().findViewById(R.id.regEmployerBtn);
        regEmployerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegEmployer();
            }
        });
    }

    public void loginAction(View view) {
        String usernameStr = usernameET.getText().toString();
        String passwordStr = passwordET.getText().toString();

        RequestParams params = new RequestParams();
        params.put("email", usernameStr);
        params.put("password", passwordStr);

        mClient.post(Constants.loginUrlStr, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseLoginResult(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();
            }
        });

        //Toast.makeText(getActivity(), usernameStr , Toast.LENGTH_SHORT).show();
    }

    public void gotoRegEmployee() {
        Intent intent = new Intent(getActivity(), RegisterEmployeeActivity.class);
        getActivity().startActivityForResult(intent, Constants.REGEMPLOYEEINTENT);
    }

    public void gotoRegEmployer() {
        Intent intent = new Intent(getActivity(), RegEmployerActivity.class);
        getActivity().startActivityForResult(intent, Constants.REGEMPLOYERINTENT);
    }

    public void parseLoginResult(String json) {
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonParser jsonParser = jsonFactory.createJsonParser(json);
            JsonNode rootNode =  mapper.readTree(jsonParser);
            JsonNode tokenNode = rootNode.path("token");
            String token = tokenNode.toString();
            token = token.replaceAll("\"", "");
            Constants.setToken(getActivity(), token);
            //Toast.makeText(getActivity(), token, Toast.LENGTH_SHORT).show();
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
