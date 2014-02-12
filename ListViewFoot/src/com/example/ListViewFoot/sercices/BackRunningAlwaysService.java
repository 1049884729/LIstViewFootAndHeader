package com.example.ListViewFoot.sercices;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**程序一旦安装后，一直在后台默默运行，用于接收推送消息或者进行离线下载
 * Created by xff on 14-2-12.
 */
public class BackRunningAlwaysService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
