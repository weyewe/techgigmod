package weyewe.com.techgig;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import weyewe.com.techgig.database.DBApp;

/**
 * Created by willy on 21/07/15.
 */




public class TechgigApplication extends Application
{
    private static TechgigApplication mInstance;
    private static DBApp mDatabase;  // from DBMovies




    public TechgigApplication()
    {
        mInstance = this;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }


    @Override
    public void onCreate()
    {
        super.onCreate();

        mDatabase = new DBApp(this);
    }


    public static Context getContext()
    {
        return mInstance;
    }

    public synchronized static DBApp getWritableDatabase() {
        if (mDatabase == null) {
            Log.d("theWritableDatabase: ",  "it is NULL");
            mDatabase = new DBApp(getAppContext());
        }else{
            Log.d("theWritableDatabase: ", "it is NOT NULL");
        }
        return mDatabase;
    }


}
