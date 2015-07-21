package weyewe.com.techgig.network;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import weyewe.com.techgig.TechgigApplication;

/**
 * Created by willy on 21/07/15.
 */

public class VolleySingleton {
    private static VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;
    private VolleySingleton(){
        mRequestQueue= Volley.newRequestQueue(TechgigApplication.getAppContext());

    }
    public static VolleySingleton getInstance(){
        if(sInstance==null)
        {
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
}
