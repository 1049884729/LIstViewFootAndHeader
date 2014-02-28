package com.example.ListViewFoot;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import com.example.ListViewFoot.sercices.SercicesFlag;
import com.example.ListViewFoot.sercices.ServiceToActivtyMethod;
import com.example.ListViewFoot.sercices.StartAppServer;

public class MyActivity extends Activity  {
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
        broadIntent.setPriority(222222);
        registerReceiver(broadcast, broadIntent);
        Intent intent=new Intent();
        intent.putExtra("flag", SercicesFlag.APP_START);

        intent.setClass(MyActivity.this,StartAppServer.class);
        startService(intent);
        System.out.print(false);

         bindService(intent,conn,Context.BIND_AUTO_CREATE);


    }
private ServiceToActivtyMethod serviceToActivtyMethod=new ServiceToActivtyMethod() {
    @Override
    public void getActivityMethod() {
        Toast.makeText(MyActivity.this,"success",0).show();
        Intent intent=new Intent();
        intent.setAction("main.activity");
        startActivity(intent);
        finish();
    }
};


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
            unbindService(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // unbindService(conn);
    }



    class EnterToTwoActivtyNotify extends BroadcastReceiver{


         @Override
         public void onReceive(Context context, Intent intent) {

//             intent.setAction("main.activity");
//             context.startActivity(intent);
//             finish();


         }

     }
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ( (StartAppServer.MyIBinder)iBinder).getActivityMethod(serviceToActivtyMethod);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

}
