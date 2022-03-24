package com.android.afla.UI.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    private static final String PREF_email = "email";
    private static final String PREF_userType = "userType";

//    private static final String PREF_userIndex = "userIndex";
//    private static final String PREF_userID = "userId";
//    private static final String PREF_userName = "userName";
//    private static final String PREF_isAdmin = "isAdmin";
//    private static final String PREF_location = "location";
//    private static final String PREF_clientId = "clientId";
//    private static final String PREF_notificationCount = "notificationCount";
//    private static final String PREF_isAdmin = "isAdmin";


    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    //get values

//    public static String getPREF_clientId(Context context) {
//        return getSharedPreferences(context).getString(PREF_clientId, "");
//    }
//
//    public static String getPREF_userID(Context context) {
//        return getSharedPreferences(context).getString(PREF_userID, "");
//    }
//
//    public static String getPREF_userIndex(Context context) {
//        return getSharedPreferences(context).getString(PREF_userIndex, "");
//    }
//
//    public static String getPREF_userName(Context context) {
//        return getSharedPreferences(context).getString(PREF_userName, "");
//    }

    public static String getPREF_email(Context context) {
        return getSharedPreferences(context).getString(PREF_email, "");
    }

    public static String getPREF_userType(Context context) {
        return getSharedPreferences(context).getString(PREF_userType, "");
    }


    //set values of shared preference
    public static void setValues(Context ctx, String UserEmail, String UserType) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_email, UserEmail);
        editor.putString(PREF_userType, UserType);
        editor.apply();
    }

    //remove values from shared preference
    public static void removeValues(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(PREF_email);
        editor.remove(PREF_userType);
        editor.apply();
    }

}

