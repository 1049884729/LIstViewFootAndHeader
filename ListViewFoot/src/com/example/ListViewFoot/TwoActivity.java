package com.example.ListViewFoot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import com.example.ListViewFoot.sercices.StartAppServer;

/**
 * Created by xff on 14-2-12.
 */
public class TwoActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent();
            intent.setClass(TwoActivity.this,StartAppServer.class);
            stopService(intent);

        }
        return super.onKeyDown(keyCode, event);
    }
}