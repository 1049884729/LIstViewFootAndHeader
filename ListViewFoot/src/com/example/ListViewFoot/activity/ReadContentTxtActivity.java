package com.example.ListViewFoot.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.ListViewFoot.R;

import java.io.*;

/**
 * Created by xff on 14-7-21.
 */
public class ReadContentTxtActivity extends Activity {
    private TextView textView;

    private int currentPage=1;
    private Button pre,next;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readtxt);
         textView = (TextView) findViewById(R.id.result);
          pre= (Button) findViewById(R.id.pre);
        next= (Button) findViewById(R.id.next);
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage--;
                if (currentPage<1)currentPage=1;
                new ReadTxt().execute(getIntent().getStringExtra("path"),currentPage);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                new ReadTxt().execute(getIntent().getStringExtra("path"),currentPage);

            }
        });
        new ReadTxt().execute(getIntent().getStringExtra("path"),currentPage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0,1,0,"下一页");
        menu.add(0,2,0,"上一页");



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 1:
                currentPage++;
                new ReadTxt().execute(getIntent().getStringExtra("path"),currentPage);

                break;

            case 2:
                currentPage--;
                if (currentPage<1)currentPage=1;
                new ReadTxt().execute(getIntent().getStringExtra("path"),currentPage);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class ReadTxt extends AsyncTask<Object,Integer,Object> {
        public String  getFileCode(File fileSrc) throws Exception{
            InputStream inputStream = new FileInputStream(fileSrc);
            byte[] head = new byte[4];
            inputStream.read(head);
            String code="";

            if(head[0] == -1 && head[1] == -2) {
                code = "UTF-16";
            }
            else if(head[0] == -2 && head[1] == -1) {
                code = "Unicode";
            }
            else if(head[0] == -17 && head[1] == -69 && head[2] == -65)
                code = "UTF-8";
            else {
                code = "gb2312";
            }
            return code;
        }
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog=new ProgressDialog(ReadContentTxtActivity.this);
//            dialog.setTitle("请稍候");
//            dialog.setMessage("...");
//            dialog.show();
        }

        @Override
        protected Object doInBackground(Object... params) {
            String path= (String) params[0];
            int  page= Integer.parseInt(params[1].toString());
            File file=new File(path);
            String content="";
            FileInputStream fileInputStream= null;
            InputStreamReader byteArrayInputStream=null;
            BufferedReader br=null;
            int sizeRead=1024;
            if (file.exists()) {
                try {
                    fileInputStream= new FileInputStream(file);
                    String codeFormat=getFileCode(file);
                    byteArrayInputStream = new InputStreamReader(fileInputStream,codeFormat);
                    br=new BufferedReader(byteArrayInputStream);
                    int c=0;
                    int start=(page - 1) * sizeRead;
                     br.skip(start);
                    br.markSupported();
                    while ((c=br.read())!=-1){
                        content+=br.readLine()+"\n";
                        int s=content.getBytes().length;
                        if (s>sizeRead){

                            break;
                        }
                    }

                    return content;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }  catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                     try {
                         if (br!=null) br.close();
                         if (byteArrayInputStream!=null)byteArrayInputStream.close();
                         if (fileInputStream!=null) fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
           // dialog.dismiss();
            if(o!=null)
            textView.setText(o.toString());
        }
    }

}