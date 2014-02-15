package com.example.ListViewFoot.databases;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xff on 14-2-14.
 */
public class SqliteDatabases extends SQLiteOpenHelper {
    public static String TABLE_WEBURL="weburl";
    public static String TABLE_IMAGEURL="imageurl";
    public SqliteDatabases(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    private static SqliteDatabases mInstance;
    public synchronized static SqliteDatabases getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SqliteDatabases(context,"imagedbs",null,1);
        }

        return mInstance;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE imageurl (id INTEGER PRIMARY KEY, url TEXT, isuse NUMERIC)");
        sqLiteDatabase.execSQL("CREATE TABLE weburl (id INTEGER PRIMARY KEY, url TEXT, isuse NUMERIC)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public  void addUrl(String tableName,String url){

        try {
            SQLiteDatabase db =getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("url",url);
            values.put("isuse",0);//未使用
            db.insertOrThrow(tableName,null,values);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List selectUrls(String tableName){
        SQLiteDatabase db =getReadableDatabase();

        Cursor cursor= db.query(tableName, new String[]{"url", "isuse","id"}, "  isuse=? ", new String[]{0+""}, null, null, null);
        List list=new ArrayList();

        UrlBean bean=null;
        try {
            while (cursor.moveToNext()){
                bean=new UrlBean();
                bean.url=cursor.getString(0);
                bean.isuse=cursor.getInt(1);
                bean.id=cursor.getInt(2);

                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally
        {
            cursor.close();
        }

        return list;
    }
    public  Boolean deleteTable(String tableName){
        SQLiteDatabase db=getWritableDatabase();

        int i=db.delete(tableName,null,null);


        if (i==1)return true;
        return false;
    }
    public void update(String tableName ,UrlBean bean){
        SQLiteDatabase db=getWritableDatabase();

        try {

            String sql="delete from "+tableName +" where id="+bean.id;
          int i=  db.delete(tableName,"id=?",new String[]{bean.id+""});
            System.out.print("开始下载:"+"删除"+i);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("开始下载:"+"删除");
        }

    }
}
