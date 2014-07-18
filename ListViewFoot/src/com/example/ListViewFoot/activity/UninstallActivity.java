package com.example.ListViewFoot.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.ListViewFoot.R;

/**
 * 将 要删除的动作转移到该类中
 * Created by xff on 14-3-1.
 */
public class UninstallActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView textView= (TextView) findViewById(R.id.name);
        textView.setText("xiezai");
    }
}