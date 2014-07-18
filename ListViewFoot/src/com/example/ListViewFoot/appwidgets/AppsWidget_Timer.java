package com.example.ListViewFoot.appwidgets;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
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
import com.example.ListViewFoot.sercices.AppWidget_TimeService;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * use by Time_app_widget
 * Created by xff on 14-3-3.
 */
public class AppsWidget_Timer extends AppWidgetProvider {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    public void onReceive(Context context, Intent intent) {

            super.onReceive(context, intent);
        String action = intent.getAction();
        Log.i("WeatherWidget", "onReceive action = " + action);
        if (action.equals("android.intent.action.USER_PRESENT")) {// 用户唤醒设备时启动服务
            context.startService(new Intent(context, AppWidget_TimeService.class));
        } else if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, AppWidget_TimeService.class));
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Log.i("app_widget:","--------onUpdate------------");//添加 appwidget ，执行该方法second
        Intent intent = new Intent(context, AppWidget_TimeService.class);
        context.startService(intent);
      //  updateTime(context,appWidgetManager,appWidgetIds);
        super.onUpdate(context, appWidgetManager, appWidgetIds);

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
        Intent intent = new Intent(context, AppWidget_TimeService.class);
        context.stopService(intent);


    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i("app_widget:","--------onEnabled------------");//添加 appwidget ，首先执行该方法frist
        Intent intent = new Intent(context, AppWidget_TimeService.class);
        context.startService(intent);

    }
}
