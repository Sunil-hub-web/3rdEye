package com.example.a3rdeye;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.a3rdeye.subscriber.Login_ModelClass;
import com.example.a3rdeye.subscriber.signin.SignIn_OTPVerification;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_id = "keyid";
    private static final String KEY_first_name = "keyfirst_name";
    private static final String KEY_last_name = "keylast_name";
    private static final String KEY_mobile_number = "keymobile_number";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user register
    //this method will store the user data in shared preferences
    public void userLogin(Login_ModelClass login_modelClass) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString(KEY_id,                login_modelClass.getId ());
        editor.putString(KEY_first_name,        login_modelClass.getFname ());
        editor.putString(KEY_last_name,         login_modelClass.getLname ());
        editor.putString(KEY_mobile_number,     login_modelClass.getMobileno ());

        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_id, null) != null;
    }

    //this method will give the logged in user
    public Login_ModelClass getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Login_ModelClass(


                sharedPreferences.getString(KEY_id, null),
                sharedPreferences.getString(KEY_first_name, null),
                sharedPreferences.getString(KEY_last_name, null),
                sharedPreferences.getString(KEY_mobile_number, null)


        );

    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent (mCtx, MainActivity.class));
    }



}
