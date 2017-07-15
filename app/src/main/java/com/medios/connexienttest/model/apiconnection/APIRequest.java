package com.medios.connexienttest.model.apiconnection;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

/**
 * Created by Camilo on 7/15/17.
 */

public class APIRequest {

    private static APIRequest instance = null;
    private OkHttpClient client;

    /**
     * Obtain the data from a web service
     * @param url The url where to request the info from
     * @param callback A handler to get the info returned by the server
     */
    public void fetchDataFrom(String url, Callback callback) {
        if (client == null)
            client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * Get only one instance of APIRequest
     * @return
     */
    public static synchronized APIRequest getInstance() {
        if (instance == null) {
            instance = new APIRequest();
        }
        return instance;
    }

    /**
     * This checks if the internet connection is available
     * @param context Current context, will be used to access resources
     * @return true if connected to internet
     */
    public boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            // if no network is available networkInfo will be null
            // otherwise check if we are connected
            return (networkInfo != null && networkInfo.isConnected());
        } catch (Exception ex) {
            return false;
        }
    }


}
