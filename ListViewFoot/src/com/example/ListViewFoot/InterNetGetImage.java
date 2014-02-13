package com.example.ListViewFoot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.*;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import com.example.ListViewFoot.util.CatchImage;

import java.util.List;

/**
 * Created by xff on 14-2-13.
 */
public class InterNetGetImage extends Activity {
    private  String[] URLS = new String[]{
            "wap.baidu.com","www.03jjj.com","wap.so.com","www.google.com",
            "http://wap.baidu.com","http://www.03jjj.com","http://wap.so.com","http://www.google.com"
    };
    private RelativeLayout relativeLayout;
    private Button buttonOk;
    private EditText saveUrlE;
    private AutoCompleteTextView urlE;
    private WebView webView;
    private  CatchImage cm = new CatchImage();
    private    String saveString=null;//保存路径值
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_get_activity);
        relativeLayout= (RelativeLayout) findViewById(R.id.setting);
        buttonOk= (Button) findViewById(R.id.ok);
        urlE= (AutoCompleteTextView) findViewById(R.id.set_url);
        webView= (WebView) findViewById(R.id.webView);
        saveUrlE= (EditText) findViewById(R.id.set_saveurl);
        saveUrlE.setInputType(InputType.TYPE_NULL);
        saveUrlE.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(InterNetGetImage.this, GetSdcardActivity.class);
                    startActivityForResult(intent, 101);//requestCode 不能填RESULT_OK
                }

                return false;
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, URLS);

        urlE.setAdapter(adapter);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings set=webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setSupportZoom(true);
        set.setBuiltInZoomControls(true);
        set.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 saveString=saveUrlE.getText().toString().trim();
                String url=urlE.getText().toString().trim();
                if(url==null||url.length()==0){
                    urlE.setHint("不能为空");
                    return;
                }
                if(saveString==null||saveString.length()==0){
                    saveUrlE.setHint("不能为空");
                    return;
                }
                 //webView.loadUrl("http://image.baidu.com/");
                 // webView.loadUrl("http://www.03jjj.com");
                if (url.startsWith("http://"))    webView.loadUrl(url);
                else
                  webView.loadUrl("http://"+url);

                //获得html文本内容

                relativeLayout.setVisibility(View.GONE);
            }
        });
    }
     class InJavaScriptLocalObj {
        public void showSource(String html) {
            Log.d("HTML", html);
        }
    }
    class ThreadDown extends Thread{
        String url;
        ThreadDown(String url){
            this.url=url;
        }
        @Override
        public void run() {
            super.run();
            try {
                cm.dealDown(url,saveString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     class MyWebViewClient extends WebViewClient{
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
           new  ThreadDown(url).start();
            return true;
        }
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }
        public void onPageFinished(WebView view, String url) {
            new  ThreadDown(url).start();
            view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
                    "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
            super.onPageFinished(view, url);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK)
        saveUrlE.setText(data.getStringExtra("file"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (Build.VERSION.SDK_INT>10){
        menu.add(0,0,0,"set").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }else{
            menu.add(0,0,0,"set");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId()==0){
            if( relativeLayout.getVisibility()==View.GONE){
                relativeLayout.setVisibility(View.VISIBLE);
            }else {
                relativeLayout.setVisibility(View.GONE);
            }

        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (webView.canGoBack()){
                webView.goBack();
                return false;
            }else{
                Toast.makeText(InterNetGetImage.this,"继续会退出？",0).show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}