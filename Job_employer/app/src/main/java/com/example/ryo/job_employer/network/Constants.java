package com.example.ryo.job_employer.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.ryo.job_employer.helper.GlobalProvider;

/**
 * Created by Administrator on 2015/10/8.
 */
public class Constants {
    //static public String baseUrlStr = "http://192.168.1.102:9000/";
    static public String baseUrlStr = "http://54.169.214.156:8080/";//≤‚ ‘URL
    //static public String baseUrlStr = "http://192.168.1.103:8080/";
    static public String personalInfo = baseUrlStr + "api/employers";
    static public String RegisterStr=baseUrlStr+"api/candidates";
    static public String PositionStr=baseUrlStr+"api/jobs";
    static public String DeleteJobStr=baseUrlStr+"api/jobs";
    static public String UpdateJobStr=baseUrlStr+"api/jobs";
    static public String CreateJobStr=baseUrlStr+"api/jobs";
    static public String PositionFitStr=baseUrlStr+"api/resumes";
    static public String PositionFitUpdateStr=baseUrlStr+"api/resumes";
    static public String PositionFitIgnoreStr=baseUrlStr+"api/resumes";
    static public String ApplyPersonStr=baseUrlStr+"api/publicresumes";
    static public String ApplyPersonIgnoreStr=baseUrlStr+"api/publicresumes";
    static public String TalentStr=baseUrlStr+"api/talents";
    static public String loginUrlStr = baseUrlStr + "auth/local";

    static public String tokenStr = "token";

    public static final int LoginIntent = 90;
    public static final int UpdateInfoIntent=91;
    public static final int EditJobIntent=92;
    public static final int EVALUATEINTENT=93;
    public static  final int TUIGUANGINTENT=94;
    public static  final int RELEASEINTENT=95;
    public static  final int SCOREINTENT=96;
    public static  final int LOGOUT=97;

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
