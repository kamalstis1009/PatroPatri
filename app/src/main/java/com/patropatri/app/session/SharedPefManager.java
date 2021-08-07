package com.patropatri.app.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.patropatri.app.models.UserWithToken;

public class SharedPefManager {

    private Context mContext;
    private static SharedPefManager mManager;

    private SharedPefManager(Context context) {
        mContext = context;
    }

    public static SharedPefManager getInstance(Context context) {
        if (mManager == null) {
            mManager = new SharedPefManager(context);
        }
        return mManager;
    }

    private static final String SHARED_PREF_NAME = "shared_preferences";

    //===============================================| User
    public void saveUser(UserWithToken model){
        SharedPreferences pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("UserWithToken", new Gson().toJson(model));
        editor.apply();
        editor.commit(); //for old version
    }
    public UserWithToken getUser(){
        SharedPreferences pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Gson().fromJson(pref.getString("UserWithToken", null), UserWithToken.class);
    }

    //===============================================| Login Screen
    public void setSignIn(boolean isSignIn) {
        SharedPreferences pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("IS_SING_IN", isSignIn);
        editor.apply();
        editor.commit(); //for old version
    }

    public boolean isSignIn() {
        SharedPreferences pref = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean("IS_SING_IN", false);
    }

}
