package com.example.ListViewFoot.activity;


import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import com.example.ListViewFoot.R;
import com.example.ListViewFoot.databases.SqliteDatabases;
import com.example.ListViewFoot.sercices.StartAppServer;
import com.example.ListViewFoot.util.DeviceDetailInfo;
import com.example.ListViewFoot.util.SharePrefrenceUtil;

/**
 * Created by xff on 14-2-12.
 */
public class MainActivity extends ListActivity {


    private Context context;

    public String[] strings={"网站图片抓取","生成条形码","Drawmenu测试","txt文本阅读"};
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;

        if (Build.VERSION.SDK_INT > 10) {//版本2.33以下不支持ActionBar，所以区别对待
            ActionBar bar = getActionBar();
        }
        getListView().setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strings));
//        Button iamge_get = (Button) findViewById(R.id.image_get);
//        iamge_get.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, InterNetGetImage.class);
//                startActivity(intent);
//            }
//        });
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, InterNetGetImage.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intentMenu = new Intent(MainActivity.this, DrawMenuActivity.class);
                        startActivity(intentMenu);
                        break;
                    case 3:
                        Intent intentRead = new Intent(MainActivity.this, ListTxtPathActivity.class);
                        startActivity(intentRead);
                        break;
                }
            }
        });
    }

    //private long currentTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode==KeyEvent.KEYCODE_BACK){
//            if (currentTime==0){
//                currentTime=System.currentTimeMillis();
//                return false;
//            }else{
//                if (System.currentTimeMillis()-currentTime<3000){
//
//                }
//            }
//
//
//        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (Build.VERSION.SDK_INT > 10) {

            menu.add(0, 0, 0, "exit").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            menu.add(0, 1, 1, "普通广播").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(0, 2, 2, "进度条广播").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(1, 3, 3, "exit3").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(1, 4, 4, "exit4").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menu.add(1, 5, 5, "exit5").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        } else {
            menu.add(0, 0, 0, "exit");
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, StartAppServer.class);
                stopService(intent);
                SqliteDatabases.getInstance(getApplicationContext()).deleteTable(SqliteDatabases.TABLE_IMAGEURL);
                 SharePrefrenceUtil.saveBoolean(SharePrefrenceUtil.CACHE_MANAGER, context, getString(R.string.app_name) + DeviceDetailInfo.getAppVersion(context), false);

                android.os.Process.killProcess(android.os.Process.myPid());
                finish();
                break;
            case 1://自定义广播View的
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new Notification(R.drawable.wen, "Notifiy test", System.currentTimeMillis());
                notification.number = 2;
                notification.defaults = Notification.DEFAULT_ALL;
                notification.flags = Notification.FLAG_AUTO_CANCEL;
//                notification.sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                notification.vibrate=new long[]{1000,1000,1000};
                Intent intent1 = new Intent(MainActivity.this, LoadingActivity.class);
                PendingIntent intent2 = PendingIntent.getActivity(getApplicationContext(), 0, intent1, 0);
                notification.setLatestEventInfo(getApplicationContext(), "biaoti", "conetnet", intent2);
                notificationManager.notify(1, notification);
                break;
            case 2://含有进度条的广播
                NotificationManager nfm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                Notification nf = new Notification(R.drawable.wen, "Notifiy test", System.currentTimeMillis());
                // nf.number=2;
                //nf.defaults=Notification.DEFAULT_ALL;
                nf.flags = Notification.FLAG_ONGOING_EVENT;
//                notification.sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                notification.vibrate=new long[]{1000,1000,1000};
                Intent inf = new Intent(MainActivity.this, LoadingActivity.class);
                PendingIntent intentnfm = PendingIntent.getActivity(getApplicationContext(), 0, inf, 0);
                nf.contentView = new RemoteViews(this.getPackageName(), R.layout.notify_layout);
                nf.contentView.setImageViewResource(R.id.notify_name, android.R.drawable.stat_notify_chat);
                nf.contentView.setTextViewText(R.id.notify_des, "进度：");
                nf.contentView.setProgressBar(R.id.notify_pro, 100, 0, false);
                nf.contentIntent=intentnfm;
                new MyThreadUpdateNotifyBar(nfm, nf, intentnfm).start();
                nfm.notify(33, nf);



                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * 更新广播进度条中的数值
     */
    class MyThreadUpdateNotifyBar extends Thread {
        NotificationManager nfm;
        Notification nf;
        PendingIntent intentnfm;

        MyThreadUpdateNotifyBar(NotificationManager nfm,
                                Notification nf,
                                PendingIntent intentnfm) {
            this.nf = nf;
            this.nfm = nfm;
            this.intentnfm = intentnfm;

        }

        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    nf.contentView.setImageViewResource(R.id.notify_name, android.R.drawable.stat_notify_chat);
                    nf.contentView.setTextViewText(R.id.notify_des, "进度：");
                    nf.contentView.setProgressBar(R.id.notify_pro, 100, msg.arg1, false);
                    nfm.notify(33, nf);
                } else {
                    nfm.cancel(33);
                }
            }
        };

        @Override
        public void run() {
            super.run();
            int i = 0;

            while (i < 100) {
                try {
                    Thread.sleep(200);
                    i++;
                    Message msg = handler.obtainMessage();
                    msg.arg1 = i;
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message msg = handler.obtainMessage();
            msg.what = 2;
            handler.sendMessage(msg);
        }
    }
}