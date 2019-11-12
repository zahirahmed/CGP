package com.eleganzit.cgp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.eleganzit.cgp.HomeActivity;

import java.util.HashMap;

/**
 * Created by eleganz on 2/11/18.
 */

public class UserLoggedInSession {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;
    Activity activity;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "cgp";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_FIRST = "IsFIRST";


    // User name (make variable public to access from outside)

    public static final String USER_ID= "user_id";
    public static final String GINNING_NAME= "ginning_name";
    public static final String STATE= "state";
    public static final String AREA= "area";
    public static final String BUSINESS = "business";

    public static final String EMAIL = "email";
    public static final String MOBILE = "mobile";
    public static final String FROM = "from";

    public UserLoggedInSession(Context context){
        this._context = context;

        this.activity = (Activity) context;

        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String user_id,String ginning_name,String state, String area, String email, String mobile,String from){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putBoolean(IS_FIRST, true);

        // Storing name in pref   ,
        editor.putString(USER_ID, user_id);
        editor.putString(GINNING_NAME, ginning_name);

        editor.putString(STATE, state);

        editor.putString(AREA, area);
        editor.putString(EMAIL, email);
        editor.putString(MOBILE, mobile);
        editor.putString(FROM, from);

        // commit changes
        editor.commit();
        Intent i = new Intent(_context, HomeActivity.class)
                .putExtra("from","register");

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(i);
        activity.finish();
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void createLoginSession(String user_id,String ginning_name,String state, String area, String email, String mobile,String from, String expance,String expance_unit){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        editor.putBoolean(IS_FIRST, true);

        // Storing name in pref   ,
        editor.putString(USER_ID, user_id);
        editor.putString(GINNING_NAME, ginning_name);

        editor.putString(STATE, state);

        editor.putString(AREA, area);
        editor.putString(EMAIL, email);
        editor.putString(MOBILE, mobile);
        editor.putString(FROM, from);

        // commit changes
        editor.commit();
        Intent i = new Intent(_context, HomeActivity.class)
                .putExtra("from","login")
                .putExtra("expance",""+expance)
                .putExtra("expance_unit",""+expance_unit);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(i);
        activity.finish();
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void updateLoginSession(String user_id,String ginning_name,String state, String area, String email, String mobile){
        // Storing login value as TRUE

        // Storing name in pref   ,
        editor.putString(USER_ID, user_id);
        editor.putString(GINNING_NAME, ginning_name);

        editor.putString(STATE, state);

        editor.putString(AREA, area);
        editor.putString(EMAIL, email);
        editor.putString(MOBILE, mobile);

        // commit changes
        editor.commit();

    }

    public void updateStateAndArea(String state, String area){
        // Storing login value as TRUE

        editor.putString(STATE, state);
        editor.putString(AREA, area);

        // commit changes
        editor.commit();

    }

   /* public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, HistoryActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

*/
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(USER_ID, pref.getString(USER_ID, null));
        user.put(GINNING_NAME, pref.getString(GINNING_NAME, null));

        user.put(EMAIL, pref.getString(EMAIL, null));
        user.put(MOBILE, pref.getString(MOBILE, null));
        user.put(STATE, pref.getString(STATE, null));
        user.put(BUSINESS, pref.getString(BUSINESS, null));
        user.put(AREA, pref.getString(AREA, null));
        user.put(FROM, pref.getString(FROM, null));

        // return user
        return user;
    }


/*

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MeasureActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Staring Login Activity
        _context.startActivity(i);
        activity.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
*/

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isFirst(){
        return pref.getBoolean(IS_FIRST, false);
    }


}
