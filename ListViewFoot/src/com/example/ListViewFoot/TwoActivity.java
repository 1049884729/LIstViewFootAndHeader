package com.example.ListViewFoot;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import com.example.ListViewFoot.databases.SqliteDatabases;
import com.example.ListViewFoot.sercices.StartAppServer;

/**
 * Created by xff on 14-2-12.
 */
public class TwoActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        if (Build.VERSION.SDK_INT>10) {//版本2.33以下不支持ActionBar，所以区别对待
            ActionBar bar = getActionBar();
        }
        Button iamge_get= (Button) findViewById(R.id.image_get);
        iamge_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent=new Intent(TwoActivity.this,InterNetGetImage.class);
                startActivity(intent);
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

        if (Build.VERSION.SDK_INT>10) {

            menu.add(0, 0, 0, "exit").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
 menu.add(0, 1, 1, "exit1").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
 menu.add(0, 2, 2, "exit2").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
 menu.add(1, 3, 3, "exit3").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
 menu.add(1, 4, 4, "exit4").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
 menu.add(1, 5, 5, "exit5").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        }else{
            menu.add(0, 0, 0, "exit");
        }

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case 0:
                Intent intent=new Intent();
                intent.setClass(TwoActivity.this,StartAppServer.class);
                stopService(intent);
                 SqliteDatabases.getInstance(getApplicationContext()).deleteTable(SqliteDatabases.TABLE_IMAGEURL);

                         android.os.Process.killProcess(android.os.Process.myPid());
                finish();
                break;
            default:break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}