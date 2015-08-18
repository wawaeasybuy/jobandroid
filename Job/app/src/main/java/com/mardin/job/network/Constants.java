package com.mardin.job.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mardin.job.helper.GlobalProvider;

/**
 * Created by Mardin on 7/7/15.
 */
public class Constants {
    static public String baseUrlStr = "http://54.169.214.156/";
    static public String jobListUrlStr = baseUrlStr + "api/jobs";
    static public String loginUrlStr = baseUrlStr + "auth/local";
    static public String addJobUrlStr = baseUrlStr + "api/jobs";
    static public String jobDetailUrlStr = baseUrlStr + "api/jobs";
    static public String jobCollectUrlStr = baseUrlStr + "api/jobs";
    static public String meUrlStr = baseUrlStr + "api/users/me";
    static public String updateEmployerStr = baseUrlStr + "api/employers";
    static public String updateCanStr = baseUrlStr + "api/candidates";
    static public String regCanStr = baseUrlStr + "api/candidates";
    static public String regEmpStr = baseUrlStr + "api/employers";


    static public String employer = "employer";
    static public String candidate = "candidate";
    static public String tokenStr = "token";


    static public boolean needReflesh = false;

    public static final int LoginIntent = 90;
    public static final int UPEMPLOYER_INTENT = 91;
    public static final int UPECANDIDATE_INTENT = 92;
    public static final int REGEMPLOYEEINTENT = 93;
    public static final int REGEMPLOYERINTENT = 94;

    public static void setToken(Context context,String token) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        GlobalProvider globalProvider = GlobalProvider.getInstance();
        if (token.equals("")) {
            editor.putString(tokenStr, token);
            globalProvider.setToken(token);
        }else {
            editor.putString(tokenStr, "Bearer " + token);
            globalProvider.setToken("Bearer " + token);
        }

        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        String auth_token_string = settings.getString(tokenStr, ""/*default value is ""*/);
        return auth_token_string;
    }

}
