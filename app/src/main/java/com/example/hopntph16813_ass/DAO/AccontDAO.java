package com.example.hopntph16813_ass.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.hopntph16813_ass.DBHelper.CreateDb;
import com.example.hopntph16813_ass.DTO.User;

import java.util.ArrayList;
import java.util.List;

public class AccontDAO {
    CreateDb createDb;
    public AccontDAO(Context context) {
        createDb = new CreateDb(context);
    }
    public boolean checkLogin(User user){
        SQLiteDatabase db = createDb.getReadableDatabase();
        String SELECT = "SELECT * FROM tb_account WHERE taikhoan=? AND passwrod=?";
        Cursor cursor = db.rawQuery(SELECT,new String[]{user.getUsername(), user.getPassword()});
        cursor.moveToFirst();
        if (cursor.getCount()<=0){
            return false;
        }else {
            return true;
        }
    }
    public long themtk(User user){
        SQLiteDatabase db = createDb.getWritableDatabase();
        ContentValues contentValues =new ContentValues();
        contentValues.put("taikhoan",user.getUsername());
        contentValues.put("passwrod",user.getPassword());
        long result = db.insert("tb_account",null,contentValues);
        return result;

    }
    public List<User> getAll(){
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = createDb.getReadableDatabase();
        String SELECT = "SELECT * FROM tb_account";
        Cursor cursor = db.rawQuery(SELECT,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String u = cursor.getString(0);
            String p = cursor.getString(1);
            User user =  new User(u,p);
            list.add(user);
            cursor.moveToNext();

        }
        cursor.close();
        db.close();
        return  list;
    }



}
