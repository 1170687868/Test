package com.example.administrator.recipes.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.recipes.utils.User_Cpflxq;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/12.
 */

public class MySqliteHelper {
    MySqlite mySqlite = null;//声明数据库对象
    SQLiteDatabase sqLiteDatabase = null;//声明数据库帮助类对象
    public MySqliteHelper(Context context) {
        mySqlite = new MySqlite(context);
        sqLiteDatabase = mySqlite.getReadableDatabase();
    }
    public void addUser(String title,String content,String imgurl){//添加数据的方法
//        Log.e("size",getDBLeng()+"");
        String sql = "insert into my_collection values('"+title+"','"+content+"','"+imgurl+"')";
        sqLiteDatabase.execSQL(sql);//执行sql语句
    }
    public void deleteUser(String title){//删除数据的方法
        String sql = "delete from my_collection where title = '"+title+"'";
        sqLiteDatabase.execSQL(sql);
    }
    public ArrayList<User_Cpflxq> findUser() {//查询数据的方法
        ArrayList<User_Cpflxq> arrayList = new ArrayList<User_Cpflxq>();
        String sql = "select * from my_collection";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {//
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String imgurl = cursor.getString(cursor.getColumnIndex("imgurl"));
            arrayList.add(new User_Cpflxq(title,content,imgurl));
//            Log.e("name",name);
            cursor.moveToNext();
        }
//        Log.e("findUser",cursor.getCount()+"");
        cursor.close();
        return arrayList;
    }
}
