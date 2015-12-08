package com.example.ryo.job_employer.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.ryo.job_employer.activities.LoginActivity;
import com.example.ryo.job_employer.models.Employer;
import com.example.ryo.job_employer.models.Http.AsyncHttpClient;
import com.example.ryo.job_employer.models.Http.AsyncHttpResponseHandler;
import com.example.ryo.job_employer.models.Http.RequestParams;
import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;
import com.example.ryo.job_employer.models.Job;
import com.example.ryo.job_employer.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/8.
 */
public class GlobalProvider {
    private static GlobalProvider instance;
    private AsyncHttpClient client;
    public Employer employer=new Employer();
    public Job job=new Job();
    public String employerId="";
    public Boolean isAllowToTalent=false;
    public List<Job> jobList=new ArrayList<Job>();

    private GlobalProvider() {
        client = new AsyncHttpClient();
    }
    public static GlobalProvider getInstance() {
        if(instance == null) {
            instance = new GlobalProvider();
        }
        return instance;
    }

    public void setToken(String token) {
        client.addHeader("Authorization", token);
    }

    public void addHeaderToken(Context context) {
        if ( context != null && Constants.getToken(context) != null ) {
            client.addHeader("Authorization", Constants.getToken(context));
            Log.v("token", Constants.getToken(context));

        }
    }
    public void get(final Context context,String url,final RequestListener listener) {
        addHeaderToken(context);
        client.get(url,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                baseFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }
    public void get(final Context context,String url, RequestParams params, final RequestListener listener) {

        addHeaderToken(context);
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                baseFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }
    public void doLog(final Context context, String url, HttpEntity entity, String contentType, final RequestListener listener) {

        addHeaderToken(context);
        client.post(context, url, entity, contentType, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //doFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }
    public void post(final Context context, String url, HttpEntity entity, String contentType, final RequestListener listener) {

        addHeaderToken(context);
        client.post(context, url, entity, contentType, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                baseFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }
    public void post(final Context context, String url, RequestParams params, final RequestListener listener) {

        addHeaderToken(context);
        client.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                baseFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }
    public void delete(final Context context, String url, final RequestListener listener) {

        addHeaderToken(context);
        client.delete(context, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                baseFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }
    public void delete(final Context context, String url, RequestParams params,final RequestListener listener) {

        addHeaderToken(context);
        client.delete(context, url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                baseFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }
    public void put(final Context context, String url, HttpEntity entity,String contentType, final RequestListener listener) {
        addHeaderToken(context);

        client.put(context, url, entity,contentType, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                baseFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                listener.onPostProcessResponse(instance, response);
            }
        });
    }
    public void put(final Context context, String url, RequestParams params, final RequestListener listener) {
        addHeaderToken(context);

        client.put(context, url, params,  new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                baseFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                listener.onPostProcessResponse(instance, response);
            }
        });
    }
    public void baseFail(final Context context ,int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        if (statusCode == 401) {
            //doSomething
            Intent intent = new Intent(context, LoginActivity.class);
            ((Activity)context).startActivityForResult(intent, Constants.LoginIntent);
        }
    }

}
