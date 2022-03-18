package com.example.voicechange.FloatThings;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;


public class MyFloatIntentService extends IntentService {



    public MyFloatIntentService() {
        super("MyFloatIntentService");
    }





    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("myIntentService", "thread is " + Thread.currentThread().getName());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("myIntentService", "onDestroy: ");
    }
}