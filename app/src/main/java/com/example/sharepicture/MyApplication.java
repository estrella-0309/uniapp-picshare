package com.example.sharepicture;

import android.app.Application;

import java.util.HashMap;

public class MyApplication extends Application {

    private static MyApplication app;

    public HashMap<String,String> info = new HashMap<>();
    public static MyApplication getInstance(){
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }
}
