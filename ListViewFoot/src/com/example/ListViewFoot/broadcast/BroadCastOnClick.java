package com.example.ListViewFoot.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by xff on 14-3-1.
 */
public class BroadCastOnClick extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"通知按钮点击 success",0).show();
    }
}
