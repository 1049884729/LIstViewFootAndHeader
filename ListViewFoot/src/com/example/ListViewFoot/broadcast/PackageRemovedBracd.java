package com.example.ListViewFoot.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import com.example.ListViewFoot.R;
import com.example.ListViewFoot.activity.LoadingActivity;

/**
 * Created by xff on 14-3-1.
 */
public class PackageRemovedBracd extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)){
            intent.getDataString().toString();
            String packageName = intent.getDataString().toString();
            System.out.println("---------------" + packageName);
            NotificationManager notificationManager= (NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification=new Notification(R.drawable.wen,"Notifiy test",System.currentTimeMillis());
            notification.number=2;
            notification.flags=Notification.FLAG_AUTO_CANCEL;

            notification.sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notification.vibrate=new long[]{1000,1000,1000};
            Intent intent1=new Intent(context, LoadingActivity.class);
            PendingIntent intent2=PendingIntent.getActivity(context.getApplicationContext(),0,intent1,0);
            notification.setLatestEventInfo(context.getApplicationContext(),"biaoti",packageName,intent2);
            notificationManager.notify(1,notification);
        }
    }
}
