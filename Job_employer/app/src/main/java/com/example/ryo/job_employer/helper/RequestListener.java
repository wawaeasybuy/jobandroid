package com.example.ryo.job_employer.helper;

import com.example.ryo.job_employer.models.Http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

/**
 * Created by Administrator on 2015/7/10.
 */
public interface RequestListener {
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody);
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error);

    void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response);
}