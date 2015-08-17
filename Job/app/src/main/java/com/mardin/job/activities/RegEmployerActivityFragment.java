package com.mardin.job.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.R;
import com.mardin.job.helper.GlobalProvider;
import com.mardin.job.helper.RequestListener;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class RegEmployerActivityFragment extends Fragment {

    private EditText usernameET;
    private EditText passwordET;
    private EditText nameET;
    private Button registerBtn;

    public RegEmployerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reg_employer, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.title_regemployer);
        usernameET = (EditText)getView().findViewById(R.id.usernameET);
        passwordET = (EditText)getView().findViewById(R.id.passwordET);
        nameET = (EditText)getView().findViewById(R.id.nameET);
        registerBtn = (Button)getView().findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAction();
            }
        });
    }

    public void registerAction() {
        String nameStr = nameET.getText().toString();
        String usernameStr = usernameET.getText().toString();
        String passwordStr = passwordET.getText().toString();

        RequestParams params = new RequestParams();
        params.put("companyname", nameStr );
        params.put("email", usernameStr);
        params.put("password", passwordStr);

        GlobalProvider globalProvider = GlobalProvider.getInstance();
        globalProvider.post(getActivity(), Constants.regEmpStr, params, new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                parseregResult(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {

            }
        });
    }

    public void parseregResult(String json) {
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
