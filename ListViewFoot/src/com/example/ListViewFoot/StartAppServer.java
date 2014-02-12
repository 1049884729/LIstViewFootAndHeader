package com.example.ListViewFoot;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import java.util.logging.Logger;

/**
 * Created by xff on 14-2-12.
 */
public class StartAppServer extends Service {

    private boolean isOnCreate=false;//线程耗时时，services执行到startCommend方法，
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

        new initThread().start();//初始化耗时任务必须放在线程中，完成后通过广播启动完成初始化的任务
    }
class  initThread extends Thread{
    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(3000);
            Looper.prepare();
            startMainPage();//执行完第一次之后再改变isOnCreate的值，防止该方法执行两次
            isOnCreate=true;

            Looper.loop();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    synchronized void startMainPage(){
        Log.i("aaaa","dddddddddddddd");
        Intent i=new Intent();
        i.setAction("entry.to.main.broad");
        getApplicationContext().sendBroadcast(i);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
             if (isOnCreate)startMainPage();
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
