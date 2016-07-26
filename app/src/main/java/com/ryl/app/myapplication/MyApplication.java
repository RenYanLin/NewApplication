package com.ryl.app.myapplication;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ryl.app.utils.ImageLoadController;

/**
 * Created by ryl on 2016/7/25.
 */
public class MyApplication extends Application{
    public RequestQueue myQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoadController.imageload(this);
        myQueue = Volley.newRequestQueue(this);
    }

    public RequestQueue getMyQueue() {
        return myQueue;
    }
}
