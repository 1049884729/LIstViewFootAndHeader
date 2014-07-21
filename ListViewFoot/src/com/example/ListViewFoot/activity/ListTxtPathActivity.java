package com.example.ListViewFoot.activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.ListViewFoot.R;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xff on 14-7-18.
 */
public class ListTxtPathActivity extends ListActivity{
    private final String READ_RENCENT="rencent_path";  TextView textView;

    private ListView listView;
    private Context context;

    private boolean isCHooseItem=false;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.readtxt);
        listView=getListView();
        context=this;
        // textView= (TextView) findViewById(R.id.result);
        new GetTxtPath().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String path= (String) parent.getAdapter().getItem(position);

              Intent intent=new Intent(context,ReadContentTxtActivity.class);
                intent.putExtra("path",path);
                context.startActivity(intent);

            }
        });
    }






    class GetTxtPath extends AsyncTask<Object,Integer,Object>{

        @Override
        protected Object doInBackground(Object... params) {
            File file= new File(Environment.getExternalStorageDirectory().getPath());
            List<String> listFile=new ArrayList<String>();
            isDir(listFile,file);
            if (listFile.size()>0){
                return listFile;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            List<String> listFile= (List<String>) o;
            listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, listFile));
        }
        private void isText(List<String> list,File file){
            if (file.isFile()){
                if (file.getName().endsWith(".txt")||file.getName().endsWith(".TXT")){
                    list.add(file.getPath());
                }
            }else if(file.isDirectory()){
                isDir(list,file);
            }
        }
        private void isDir(List<String> list,File file){
            File[] listResult=   file.listFiles();
            if (listResult!=null&&listResult.length>0)
                for (File f:listResult){
                    if(f.isDirectory()){
                        isDir(list, f);
                    }else if (f.isFile()){
                        isText(list,f);
                    }
                }

        }
    }

    @Override
    public void onBackPressed() {
        if (isCHooseItem){
            setContentView(listView);
            isCHooseItem=false;
            return;
        }
        super.onBackPressed();

    }
}