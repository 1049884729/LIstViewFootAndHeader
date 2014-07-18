package com.example.ListViewFoot.activity;

import android.app.Activity;
import android.os.*;
import android.util.Log;
import android.view.MenuInflater;
import android.widget.TextView;
import com.example.ListViewFoot.ParentActivity;
import com.example.ListViewFoot.R;
import com.example.ListViewFoot.util.Logger;

import java.io.*;
import java.util.List;

/**
 * Created by xff on 14-7-18.
 */
public class ReadTxtActivity extends ParentActivity {
    private final String READ_RENCENT="rencent_path";  TextView textView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readtxt);
         textView= (TextView) findViewById(R.id.result);
        new GetTxtPath().execute();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            File[] list= (File[]) msg.obj;
            String str="";
            if (list!=null){
                textView.setText( msg.obj.toString());
               // for (File file:list){
                    try {

                        InputStreamReader isr=new InputStreamReader(new FileInputStream(list[4]),"UTF-8");

                        BufferedReader br=new BufferedReader(isr);

                        while (br.read()!=-1){
                            str+=br.readLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
              //  }
                textView.setText(str);
            }

        }
    };
    public String  FileReader(String from, String to) throws Exception{
        File f1 = new File(from);
        File f2 = new File(to);
        String content;
        FileWriter fw = new FileWriter(f2,true);
        FileReader fr = new FileReader(f1);
        BufferedReader br = new BufferedReader(fr);
        BufferedWriter bw = new BufferedWriter(fw);
        while((content=br.readLine())!= null){
            System.out.println(content);
            bw.write(content);
            bw.newLine();
            bw.flush();
        }
        br.close();
        fr.close();
        bw.close();
        fw.close();
        return content;
    }

    FileFilter ff = new FileFilter() {

        public boolean accept(File pathname) {


            return pathname.isFile()&&!pathname.isHidden()&&pathname.getName().endsWith(".txt");//过滤隐藏文件和文件，只显示文件夹
        }
    };
    class GetTxtPath extends AsyncTask<Object,Integer,Object>{
        @Override
        protected Object doInBackground(Object... params) {
            String urlPath = "";
            File file= new File(Environment.getExternalStorageDirectory().getPath());
           File[] list= file.listFiles(ff);


            if (list.length>0){
                for (File file1:list){
                    Logger.error("text:file1"+file1);

                }
                return list;

            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Message message=handler.obtainMessage();
            message.obj=o;
            handler.sendMessage(message);
        }
    }
}