package com.example.ListViewFoot.appwidgets;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.ListViewFoot.R;
import com.example.ListViewFoot.sercices.BackRunningAlwaysService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xff on 14-3-3.
 */
public class AppsWidget_Timer extends AppWidgetProvider {

    private SimpleDateFormat sdf =new SimpleDateFormat("HH:mm:ss \n yyyy-MM-dd \n EEEE", Locale.CHINESE);

    public static String UPDATE_TIME="com.example.listView.app_UPTIME";

    private AppWidgetManager appWidgetManager;
   private   RemoteViews remoteViews=null;
    public void onReceive(Context context, Intent intent) {

            super.onReceive(context, intent);
        if (UPDATE_TIME.equals(intent.getAction())){
            //Update app_widgets
            updateTime(context);

        }
    }
    // update Time
    private void updateTime (Context context){
        AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
        ComponentName componentName=new ComponentName(context,AppsWidget_Timer.class);
        int[] ids=appWidgetManager.getAppWidgetIds(componentName);
        updateTime(context,appWidgetManager,ids);
    }
    private void updateTime(Context context,AppWidgetManager appWidgetManager, final int[] appids){
        int length=appids.length;
        if (remoteViews==null){
         remoteViews=new RemoteViews(context.getPackageName(), R.layout.appwidgets_timer);
        }
        for(int i=0;i<length;i++){
            int id=appids[i];
             handler.post(new Runnable() {
                 @Override
                 public void run() {
                     Message msg=handler.obtainMessage();
                     msg.obj=appids;
                     handler.sendMessage(msg);
                     handler.postDelayed(this,1000);
                 }
             });

        }

    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            remoteViews.setTextViewText(R.id.timer,sdf.format(System.currentTimeMillis()));
            appWidgetManager.updateAppWidget((int[]) msg.obj,remoteViews);
        }
    };
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.appWidgetManager=appWidgetManager;
        Log.i("app_widget:","--------onUpdate------------");//添加 appwidget ，执行该方法second

        updateTime(context,appWidgetManager,appWidgetIds);
    }

    public AppsWidget_Timer() {
        super();
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.i("app_widget:","--------onDeleted------------");//删除 appwidget ，首先执行该方法frist
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.i("app_widget:","--------onDisabled------------");//删除 appwidget ，首先执行该方法second


    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i("app_widget:","--------onEnabled------------");//添加 appwidget ，首先执行该方法frist


    }
}
