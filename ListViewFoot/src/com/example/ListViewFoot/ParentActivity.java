package com.example.ListViewFoot;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by xff on 14-7-18.
 */
public class ParentActivity extends Activity {
    public Context context;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
    }
}