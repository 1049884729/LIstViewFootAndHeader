package com.example.ListViewFoot;

import android.app.Application;
import android.content.Intent;
import com.example.ListViewFoot.sercices.SercicesFlag;
import com.example.ListViewFoot.sercices.StartAppServer;

/**
 * Created by xff on 14-7-18.
 */
public class GloabAppLication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Intent intent = new Intent();
        intent.putExtra("flag", SercicesFlag.APP_START);
        intent.setClass(getApplicationContext(), StartAppServer.class);
        startService(intent);
    }
}
