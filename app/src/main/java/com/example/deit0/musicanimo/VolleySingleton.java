package com.example.deit0.musicanimo;

/**
 * Created by DeIt0 on 28/06/2016.
 */
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Tomas on 28/06/2015.
 */
public class VolleySingleton {

    private static VolleySingleton instance = null;
    private RequestQueue mRequestQueue;
    private VolleySingleton()
    {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static VolleySingleton getInstance()
    {
        if (instance ==null)
        {
            instance = new VolleySingleton();
        }
        return instance;
    }


    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

}