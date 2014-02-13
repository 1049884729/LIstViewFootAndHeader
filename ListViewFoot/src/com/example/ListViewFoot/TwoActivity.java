package com.example.ListViewFoot;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
                finish();
                break;
            default:break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}