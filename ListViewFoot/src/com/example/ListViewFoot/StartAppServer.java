package com.example.ListViewFoot;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by xff on 14-2-12.
 */
public class StartAppServer extends Service {
    public IBinder onBind(Intent intent) {

        return new MyIBinder();
    }
    class  MyIBinder extends Binder {
        public void startIntent(){
            Intent i=new Intent();
            i.setAction("entry.to.main.broad");
            getApplicationContext().sendBroadcast(i);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
       new initThread().start();
    }
class  initThread extends Thread{
    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent i=new Intent();
        i.setAction("entry.to.main.broad");
        getApplicationContext().sendBroadcast(i);
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
