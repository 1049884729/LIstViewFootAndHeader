package com.example.ListViewFoot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by xff on 14-2-13.
 */
public class GetSdcardActivity extends Activity {
    private ListView listView;
    private Context context;
    private File pathNew=null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdcard_list);
        context=GetSdcardActivity.this;
        listView= (ListView) findViewById(R.id.listView);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                boolean isExitSdcard= Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
                if (isExitSdcard){
                    pathNew=Environment.getExternalStorageDirectory();
                   new  AsyncCheckSdcard().execute(pathNew);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                 String str= (String) adapterView.getAdapter().getItem(0);
                    new  AsyncCheckSdcard().execute(new File(backFile(str)));

                }else{
                    String str= (String) adapterView.getAdapter().getItem(i);
                    new  AsyncCheckSdcard().execute(new File(str));

                }
            }
        });
    }


    FileFilter ff = new FileFilter() {

        public boolean accept(File pathname) {


            return pathname.isFile()||!pathname.isHidden();//过滤隐藏文件和文件，只显示文件夹
        }
    };
    android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final File path= (File) msg.obj;
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("是否选择该目录？");
            builder.setMessage(path.getAbsolutePath());
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new  AsyncCheckSdcard().execute(new File(backFile(path.getPath())));

                }
            });
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent a = new Intent( GetSdcardActivity.this, InterNetGetImage.class);
                    a.putExtra("file",path.getPath());
                    GetSdcardActivity.this.setResult(Activity.RESULT_OK, a); //这理有2个参数(int resultCode, Intent intent)
                    finish();
                }
            });
            builder.create().show();
        }
    };
    class AsyncCheckSdcard extends AsyncTask<Object,Integer,Object>{

        private File path;
        @Override
        protected Object doInBackground(Object... objects) {
            path= (File) objects[0];
            File[] result=path.listFiles(ff);
            ArrayList<String> arrayList=new ArrayList<String>();
            arrayList.add(path.getPath());
            for (File str:result){

                try {
                    if (str.isDirectory()){
                        arrayList.add(str.getAbsolutePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return   arrayList;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            ArrayList<String> arrayList= (ArrayList<String>) o;
            if(arrayList.size()==1){
              Message msg=handler.obtainMessage();
                msg.obj=path;
                handler.sendMessage(msg);
                return;
            }
            if (listView.getAdapter()==null){
                listView.setAdapter(new FilePathList(arrayList));
            }else {
                ((FilePathList)listView.getAdapter()).notifiNew(arrayList);
            }

        }
    }

  String backFile(String str){
      return str.substring(0,str.lastIndexOf("/"));
  }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (Build.VERSION.SDK_INT>10){
            menu.add(0,0,0,"Create").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }else{
            menu.add(0,0,0,"Create");
        }
        return super.onCreateOptionsMenu(menu);
    }

    private Dialog dialog;
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (item.getItemId()==0){
         AlertDialog.Builder buider=new AlertDialog.Builder(context);

            buider.setTitle("创建文件夹");
            View v=LayoutInflater.from(context).inflate(R.layout.create_file,null);
            final EditText nameE= (EditText) v.findViewById(R.id.filename);

            Button cancel = (Button) v.findViewById(R.id.cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    dialog.dismiss();
                }
            });  Button ok = (Button) v.findViewById(R.id.ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String strName=nameE.getText().toString().trim();
                    if (strName==null||strName.length()==0){
                        strName="新建文件夹";
                    }
                    FilePathList fileAdatper= (FilePathList) listView.getAdapter();
                    fileAdatper.addMkdir(strName);
                    dialog.dismiss();
                }
            });
            buider.setView(v);
            buider.create();
             dialog =  buider.show();
        }
        return super.onMenuItemSelected(featureId, item);
    }
    class FilePathList extends BaseAdapter{
        ArrayList<String>  listStrings;
        FilePathList( ArrayList<String>  listStrings){
            this.listStrings=listStrings;
        }
        @Override
        public int getCount() {
            return listStrings.size();
        }

        @Override
        public Object getItem(int i) {
            return listStrings.get(i);
        }
        public void notifiNew(ArrayList<String> strs){
            this.listStrings=strs;
            this.notifyDataSetChanged();
        }
        public void addMkdir(String strs){
            final String pathStr=listStrings.get(0)+"/"+strs;
            listStrings.add(1,pathStr);
             new Thread(){
                 @Override
                 public void run() {
                     super.run();
                     File f=new File(pathStr);
                     if(!f.exists()){
                         f.mkdirs();
                     }
                 }
             }.start();
            this.notifyDataSetChanged();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
           view= LayoutInflater.from(context).inflate(R.layout.sdcard_item,null);
            TextView name= (TextView) view.findViewById(R.id.name);
            name.setText(listStrings.get(i).substring(listStrings.get(i).lastIndexOf("/")+1));
            return view;
        }
    }
}