package com.example.ListViewFoot.sercices;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.*;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.RemoteViews;
import com.example.ListViewFoot.R;
import com.example.ListViewFoot.appwidgets.AppsWidget_Timer;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**程序用于 更新 appWidget 的时间
 * Created by xff on 14-2-12.
 */
public class AppWidget_TimeService extends Service implements Runnable{
    public IBinder onBind(Intent intent) {
        return null;
    }

    private SimpleDateFormat sdf =new SimpleDateFormat("HH:mm:ss \n yyyy-MM-dd \n EEEE", Locale.CHINESE);

    public static String UPDATE_TIME="com.example.listView.app_UPTIME";

    private AppWidgetManager appWidgetManager;
    private RemoteViews remoteViews=null;
    // update Time

    private void updateTime(){
        remoteViews.setTextViewText(R.id.timer,sdf.format(System.currentTimeMillis()));
        appWidgetManager.updateAppWidget(appids, remoteViews);
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            updateTime();

        }
    };
    private Context context;
    private    int[] appids;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
         appWidgetManager=AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(getApplication(),
                AppsWidget_Timer.class);//AppsWidget_Timer 是 组建的类，不是服务类
         appids=appWidgetManager.getAppWidgetIds(componentName);
        int length=appids.length;

        remoteViews=new RemoteViews(context.getPackageName(), R.layout.appwidgets_timer);

        // 时间 appWidget 的点击 事件
        Intent intent=new Intent("main.activity");
        PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.timer,pendingIntent);
        //更新 appWidget 时间
        try {
            for(int i=0;i<length;i++){
                int id=appids[i];
                updateTime();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread thread=new Thread(this);
        thread.start();
    }



    int stopFlag=0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateTime();
        stopFlag= super.onStartCommand(intent, flags, startId);
        return stopFlag;
    }

    @Override
    public void run() {
        while (true){
        try {
            Thread.sleep(1000);

           //handler.sendEmptyMessage(0);
            updateTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }

    private BroadcastReceiver updateTimeBroadcast =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTime();
        }
    };

    //监听系统时间 变更的问题
    private void registBroadCast(){
        IntentFilter updateIntent=new IntentFilter();
      //  updateIntent.addAction("android.intent.action.TIME_TICK");
        updateIntent.addAction("android.intent.action.TIME_SET");
        updateIntent.addAction("android.intent.action.DATE_CHANGED");
        updateIntent.addAction("android.intent.action.TIMEZONE_CHANGED");
        registerReceiver(updateTimeBroadcast,updateIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (updateTimeBroadcast!=null){
            unregisterReceiver(updateTimeBroadcast);
        }
        if (stopFlag==Service.START_STICKY){
            startService(new Intent("com.example.listview.app_time.server"));
        }
    }
}
