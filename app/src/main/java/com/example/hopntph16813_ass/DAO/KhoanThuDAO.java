package com.example.hopntph16813_ass.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hopntph16813_ass.DBHelper.CreateDb;
import com.example.hopntph16813_ass.DTO.KhoanThu;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class KhoanThuDAO {
    //khai bao
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    CreateDb createDb;
    public KhoanThuDAO(Context context) {
        createDb = new CreateDb(context);
    }

    //lay danh sach
    public ArrayList<KhoanThu> getAll()  {
        SQLiteDatabase db = createDb.getReadableDatabase();
        ArrayList<KhoanThu> khoanThuArrayList = new ArrayList<>();
        String SELECT = "SELECT * FROM " + KhoanThu.TB_NAME;
        Cursor cursor =db.rawQuery(SELECT,null);
        //dua con tro ve dau ket qua
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id1 = cursor.getInt(0);
            int id2 = cursor.getInt(1);
            String ten = cursor.getString(2);
            String noiDung = cursor.getString(3);
            float soTien = cursor.getFloat(4);
            Date ngay = null;
            try {
                ngay = format.parse(cursor.getString(5));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            KhoanThu khoanThu = new KhoanThu(id2,id1,ten,noiDung,soTien,ngay);
            khoanThuArrayList.add(khoanThu);
            cursor.moveToNext();
        }
        cursor.close();//dong con tro
        db.close();//dong csdl
        return khoanThuArrayList;
    }
    //Them
    public long insert(KhoanThu khoanThu){
        SQLiteDatabase db = createDb.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KhoanThu.COL_NAME_ID_FK,khoanThu.getIdTenLoaiThu());
        contentValues.put(KhoanThu.COL_NAME_TEN,khoanThu.getTenKhoanThu());
        contentValues.put(KhoanThu.COL_NAME_NOIDUNG,khoanThu.getNoiDung());
        contentValues.put(KhoanThu.COL_NAME_SOTIEN,khoanThu.getSoTien());
        contentValues.put(KhoanThu.COL_NAME_NGAYTHU,format.format(khoanThu.getNgayThu()));
        long result=db.insert(KhoanThu.TB_NAME,null,contentValues);
        return result;
    }
    //Sua
    public long update(KhoanThu khoanThu){
        SQLiteDatabase db = createDb.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KhoanThu.COL_NAME_ID_FK,khoanThu.getIdTenLoaiThu());
        contentValues.put(KhoanThu.COL_NAME_TEN,khoanThu.getTenKhoanThu());
        contentValues.put(KhoanThu.COL_NAME_NOIDUNG,khoanThu.getNoiDung());
        contentValues.put(KhoanThu.COL_NAME_SOTIEN,khoanThu.getSoTien());
        contentValues.put(KhoanThu.COL_NAME_NGAYTHU,format.format(khoanThu.getNgayThu()));
        long result=db.update(KhoanThu.TB_NAME,contentValues,"idKhoanThu =? ",new String[]{String.valueOf(khoanThu.getIdKhoanThu())});
        return result;
    }

    //xoa
    public long delete(int id){
        SQLiteDatabase db = createDb.getWritableDatabase();
        long result = db.delete(KhoanThu.TB_NAME,"idKhoanThu = " + id,null);
        return result;
    }


    public ArrayList<KhoanThu> getKhoanThuTheoDK(String sql, String... a) {

        SQLiteDatabase db = createDb.getReadableDatabase();
        ArrayList<KhoanThu> khoanThuArrayList = new ArrayList<>();
        Cursor cursor =db.rawQuery(sql,a);
        //dua con tro ve dau ket qua
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id1 = cursor.getInt(0);
            int id2 = cursor.getInt(1);
            String ten = cursor.getString(2);
            String noiDung = cursor.getString(3);
            float soTien = cursor.getFloat(4);
            Date ngay = null;
            try {
                ngay = format.parse(cursor.getString(5));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            KhoanThu khoanThu = new KhoanThu(id2,id1,ten,noiDung,soTien,ngay);
            khoanThuArrayList.add(khoanThu);
            cursor.moveToNext();
        }
        cursor.close();//dong con tro
        db.close();//dong csdl
        return khoanThuArrayList;
    }



}
