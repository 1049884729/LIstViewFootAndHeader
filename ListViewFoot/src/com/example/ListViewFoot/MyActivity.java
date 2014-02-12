package com.example.ListViewFoot;

import android.app.Activity;
import android.app.Service;
import android.content.*;
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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                System.out.print(false);
                Intent intent=new Intent();
                intent.setClass(MyActivity.this,StartAppServer.class);
                startService(intent);
                bindService(intent,conn, Service.BIND_AUTO_CREATE);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter broadIntent=new IntentFilter();
        broadIntent.addAction("entry.to.main.broad");
        registerReceiver(broadcast, broadIntent);
        Intent intent=new Intent();
        intent.setClass(MyActivity.this,StartAppServer.class);

        bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcast);

        unbindService(conn);
    }

    class EnterToTwoActivtyNotify extends BroadcastReceiver{

         @Override
         public void onReceive(Context context, Intent intent) {
             intent.setAction("main.activity");
             context.startActivity(intent);
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
