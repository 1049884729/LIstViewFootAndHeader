package com.example.ListViewFoot.sercices;

import android.app.Service;
import android.content.Intent;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.example.ListViewFoot.databases.SqliteDatabases;
import com.example.ListViewFoot.databases.UrlBean;
import org.apache.http.client.HttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


/**
 * Created by xff on 14-2-12.
 */
public class StartAppServer extends Service implements Runnable{

    private boolean isOnCreate=false;//线程耗时时，services执行到startCommend方法，

    private SqliteDatabases jdbc=null;
    private String savePath=null;
    private  MyIBinder myIBinder=new MyIBinder();
    public IBinder onBind(Intent intent) {

        return myIBinder;
    }

    @Override
    public void run() {
        jdbc= SqliteDatabases.getInstance(getApplicationContext());
        jdbc.deleteTable(SqliteDatabases.TABLE_IMAGEURL);
        List<UrlBean> lists=null;
        while (true){
            lists=null;
            lists=jdbc.selectUrls(SqliteDatabases.TABLE_IMAGEURL);
            if (lists.size()>0&&savePath!=null){

                Download(lists,savePath);

            }else{

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    /***
     * 下载图片
     *
     * @param listImgSrc
     */
    private void Download(List<UrlBean>  listImgSrc,String savePath) {
        try {
            int totalSize=listImgSrc.size();
            File file=null;
            for (int i=0;i<totalSize;i++ ) {
                UrlBean bean= null;
                try {
                    bean = listImgSrc.get(i);
                    String url=bean.url;
                    String imageName = url.substring(url.lastIndexOf("/") + 1, url.length());
                    file=new File(savePath+"/"+imageName);
                    if (!file.exists()){
                    URL uri = new URL(url);

                    InputStream in = uri.openStream();
                    FileOutputStream fo = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    int length = 0;
                    System.out.println("开始下载:"+i+"url:" + url);
                    while ((length = in.read(buf, 0, buf.length)) != -1) {
                        fo.write(buf, 0, length);
                    }
                    in.close();
                    fo.close();
                    System.out.println(imageName + "下载完成"+i+":z"+totalSize);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                jdbc.update(SqliteDatabases.TABLE_IMAGEURL,bean);
            }
            System.out.println("开始下载--all over");


        } catch (Exception e) {
            System.out.println("下载失败");
        }
    }
    private ServiceToActivtyMethod serviceMethod;
    public class  MyIBinder extends Binder {
        public void startIntent(){
            Intent i=new Intent();
            i.setAction("entry.to.main.broad");
            getApplicationContext().sendBroadcast(i);
        }
        public void getActivityMethod(ServiceToActivtyMethod serviceToActivtyMethod){
            serviceMethod=serviceToActivtyMethod;
           //
            //serviceToActivtyMethod.getActivityMethod();
        }

    }
 private Thread thread=null;
    @Override
    public void onCreate() {
        super.onCreate();

      //初始化耗时任务必须放在线程中，完成后通过广播启动完成初始化的任务
        new initThread().start();
         thread=new Thread(this);
        thread.start();
    }
class  initThread extends Thread{

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(4000);
            Looper.prepare();

            startMainPage();//执行完第一次之后再改变isOnCreate的值，防止该方法执行两次
            isOnCreate=true;

            Looper.loop();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
    synchronized void startMainPage(){
        Log.i("aaaa","dddddddddddddd");
        if (serviceMethod!=null)
            serviceMethod.getActivityMethod();
//        Intent i=new Intent();
//        i.setAction("entry.to.main.broad");
//
//        getApplicationContext().sendBroadcast(i);

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

           if (intent!=null){
            int flagd=intent.getIntExtra("flag",0);
            switch (flagd){
                case 0:
                    if (isOnCreate)startMainPage();
                    break;
                case 1:
                    savePath=intent.getStringExtra("saveString");

                    break;
                default:
                    break;
            }
           }


        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (thread!=null){
            stopSelf();

        }
    }
}
