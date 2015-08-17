package com.mardin.job.helper;

import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

/**
 * Created by Mardin on 7/9/15.
 */
public interface RequestListener {
    public abstract void onSuccess(int statusCode, Header[] headers, byte[] responseBody);
    public abstract void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error);
    public abstract void onPostProcessResponse(ResponseHandlerInterface instance, HttpResponse response);
}
