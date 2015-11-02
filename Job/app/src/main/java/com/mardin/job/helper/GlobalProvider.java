package com.mardin.job.helper;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.mardin.job.models.Resume;
import com.mardin.job.network.Constants;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

/**
 * Created by Mardin on 7/9/15.
 */
public class GlobalProvider {
    private static GlobalProvider instance;
    private AsyncHttpClient client;
    public Resume resume=new Resume();
    public Boolean isLoging;

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
        if (Constants.getToken(context) != null ) {
            client.addHeader("Authorization", Constants.getToken(context));

        }
    }

    public void get(final Context context,String url,final RequestListener listener) {
        addHeaderToken(context);
        client.get(url, new AsyncHttpResponseHandler() {
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
    public void get(final Context context,String url,RequestParams params,final RequestListener listener) {
        addHeaderToken(context);
        client.get(url, params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //baseFail(context, statusCode, headers, responseBody, error);
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
                //baseFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                listener.onPostProcessResponse(instance, response);
            }


        });
    }

    public void post(final Context context, String url, RequestParams params, final RequestListener listener) {
        if (context != null ){
            addHeaderToken(context);
        }

        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (context != null) {
                    //baseFail(context, statusCode, headers, responseBody, error);
                }

                listener.onFailure(statusCode, headers, responseBody, error);
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                listener.onPostProcessResponse(instance, response);
            }
        });
    }

    public void put(final Context context, String url, HttpEntity entity, String contentType, final RequestListener listener) {
        addHeaderToken(context);

        client.put(context, url, entity, contentType, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                listener.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //baseFail(context, statusCode, headers, responseBody, error);
                listener.onFailure(statusCode, headers, responseBody, error);
            }

            @Override
            public void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response) {
                listener.onPostProcessResponse(instance, response);
            }
        });
    }

    /*
    base fail like authorization
     */
    public boolean baseFail(final Context context ,int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        if (statusCode == 401) {
            isLoging=false;
        }else if (statusCode == 0) {
            Toast.makeText(context, "Timeout, please wait and try again", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
//    public void gotoLogin(Context context) {
//        Intent intent = new Intent(context, LoginActivity.class);
//        //context.startActivity(intent);
//        ((Activity)context).startActivityForResult(intent, Constants.LoginIntent);
//    }


}
