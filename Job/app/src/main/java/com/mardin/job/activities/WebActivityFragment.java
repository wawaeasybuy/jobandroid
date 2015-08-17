package com.mardin.job.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.mardin.job.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class WebActivityFragment extends Fragment {

    private WebView mWebView;
    private String mUrlStr;
    private WebActivity mContext;

    public WebActivityFragment() {
    }

    public void onAttach(Activity mActivity) {
        super.onAttach(mActivity);
        mContext = (WebActivity)mActivity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mWebView = (WebView)getView().findViewById(R.id.wv);
        mUrlStr = mContext.getMUrlStr();
        mWebView.loadUrl(mUrlStr);

    }


}
