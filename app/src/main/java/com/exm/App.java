package com.exm;

import android.app.Application;
import android.content.Context;

/**
 * Created by Lee on 2018/11/5.
 */
public class App extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
