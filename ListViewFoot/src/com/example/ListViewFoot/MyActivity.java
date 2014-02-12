package com.example.ListViewFoot;

import android.app.Activity;
import android.app.Service;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;

public class MyActivity extends Activity {
    private EnterToTwoActivtyNotify broadcast;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        broadcast=new EnterToTwoActivtyNotify();

        IntentFilter broadIntent=new IntentFilter();
        broadIntent.addAction("entry.to.main.broad");
        registerReceiver(broadcast, broadIntent);
        Intent intent=new Intent();
        intent.setClass(MyActivity.this,StartAppServer.class);
        startService(intent);
        System.out.print(false);


    }


    @Override
    protected void onResume() {
        super.onResume();
        //广播一次注册的问题


        //bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(broadcast);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // unbindService(conn);
    }

    class EnterToTwoActivtyNotify extends BroadcastReceiver{


         @Override
         public void onReceive(Context context, Intent intent) {

             intent.setAction("main.activity");
             context.startActivity(intent);
             finish();


         }

     }
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ( (StartAppServer.MyIBinder)iBinder).startIntent();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

}
