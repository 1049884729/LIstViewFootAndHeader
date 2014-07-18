package com.example.ListViewFoot.sercices;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.*;
import android.os.*;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.ListViewFoot.R;
import com.example.ListViewFoot.appwidgets.AppsWidget_Timer;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**程序用于 更新 appWidget 的时间
 * Created by xff on 14-2-12.
 */
public class AppWidget_TimeService extends Service{
    public IBinder onBind(Intent intent) {
        return null;
    }

    private SimpleDateFormat sdf =new SimpleDateFormat("HH:mm:ss \n yyyy-MM-dd \n EEEE", Locale.CHINESE);

    public static String UPDATE_TIME="com.example.listView.app_UPTIME";

    private AppWidgetManager appWidgetManager;
    private RemoteViews remoteViews=null;
    // update Time

    private void updateTime(int id){

        remoteViews.setTextViewText(R.id.timer,sdf.format(System.currentTimeMillis()));

        appWidgetManager.updateAppWidget(id, remoteViews);
        System.gc();
    }

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
        registBroadCast();
        PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.timer,pendingIntent);
        //更新 appWidget 时间
        try {
            for(int i=0;i<length;i++){
                int id=appids[i];
                updateTime(id);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    int stopFlag=0;

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        for(int i=0;i<appids.length;i++){
            int id=appids[i];
            updateTime(id);

        }

        stopFlag= super.onStartCommand(intent, flags, startId);
        return stopFlag;
    }



    private BroadcastReceiver updateTimeBroadcast =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           // updateTime();
            for(int i=0;i<appids.length;i++){
                int id=appids[i];
                updateTime(id);

            }
        }
    };

    //监听系统时间 变更的问题
    private void registBroadCast(){
        IntentFilter updateIntent=new IntentFilter();
      //  updateIntent.addAction("android.intent.action.TIME_TICK");
        updateIntent.addAction("android.intent.action.TIME_SET");
        updateIntent.addAction("android.intent.action.DATE_CHANGED");

        updateIntent.addAction(Intent.ACTION_TIME_TICK);
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
        startService(new Intent("com.example.listview.app_time.server"));
    }



}
