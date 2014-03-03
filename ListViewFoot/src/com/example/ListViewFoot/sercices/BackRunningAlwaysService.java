package com.example.ListViewFoot.sercices;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

/**程序一旦安装后，一直在后台默默运行，用于接收推送消息或者进行离线下载
 * Created by xff on 14-2-12.
 */
public class BackRunningAlwaysService extends Service implements Runnable{
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Thread thread=new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
