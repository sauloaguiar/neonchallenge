package com.sauloaguiar.neonapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.sauloaguiar.neonapplication.network.NeonEndpoint;

/**
 * Created by sauloaguiar on 11/27/16.
 */

public class NeonApplication extends Application {

    protected NeonEndpoint.NeonEndpointInterface endpoint;

    private static NeonApplication application = null;

    private final String TOKEN = "token";
    private SharedPreferences mPreferences;

    public static synchronized NeonApplication getInstance(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public String getApplicationToken(){
        return mPreferences.getString(TOKEN, "");
    }

    public void saveApplicationToken(String token){
        mPreferences.edit().putString(TOKEN, token).commit();
    }

    public boolean isConnected(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

}
