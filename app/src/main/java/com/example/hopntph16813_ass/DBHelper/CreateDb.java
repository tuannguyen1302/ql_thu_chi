package com.example.hopntph16813_ass.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDb extends SQLiteOpenHelper {
    public static final String DB_NAME = "asmquanlythuchi.db";
    public static final int DB_VERSION = 1;

    public CreateDb( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TABLE LOAI CHI
        String sql_cr_tb_loai_chi = "CREATE TABLE tb_loaichi (idTenLoaiChi INTEGER PRIMARY KEY AUTOINCREMENT, tenLoaiChi TEXT NOT NULL)";
        db.execSQL(sql_cr_tb_loai_chi);
        //TABLE LOAI THU
        String sql_cr_tb_loai_thu = "CREATE TABLE tb_loaithu (idTenLoaiThu INTEGER PRIMARY KEY AUTOINCREMENT, tenLoaiThu TEXT NOT NULL)";
        db.execSQL(sql_cr_tb_loai_thu);
        //TABLE KHOAN CHI
        String sql_cr_tb_khoan_chi = "CREATE TABLE tb_khoanchi (idKhoanChi INTEGER PRIMARY KEY AUTOINCREMENT, idTenLoaiChi INTEGER NOT NULL REFERENCES tb_loaichi(idTenLoaiChi), tenKhoanChi TEXT NOT NULL, noiDung TEXT NOT NULl, soTien FLOAT, ngayChi DATE)";
        db.execSQL(sql_cr_tb_khoan_chi);
        //TABLE KHOAN THU
        String sql_cr_tb_khoan_thu = "CREATE TABLE tb_khoanthu (idKhoanThu INTEGER PRIMARY KEY AUTOINCREMENT, idTenLoaiThu INTEGER NOT NULL REFERENCES tb_loaithu(idTenLoaiThu), tenKhoanThu TEXT NOT NULL, noiDung TEXT NOT NULl, soTien FLOAT, ngayThu DATE)";
        db.execSQL(sql_cr_tb_khoan_thu);
        //TABLE TAIKHOAN DANG NHAP
        String sql_cr_tb_account = "CREATE TABLE tb_account (taikhoan text PRIMARY KEY, passwrod text)";
        db.execSQL(sql_cr_tb_account);

        //them mot so du lieu de test
        sql_cr_tb_loai_chi = "INSERT INTO tb_loaichi VALUES(NULL, 'Du Lich')";
        db.execSQL(sql_cr_tb_loai_chi);
        sql_cr_tb_loai_thu = "INSERT INTO tb_loaithu VALUES(NULL, 'Lương')";
        db.execSQL(sql_cr_tb_loai_thu);
        sql_cr_tb_khoan_chi = "INSERT INTO tb_khoanchi VALUES(NULL, 1, 'Đi chơi', 'Đi chơi tháng 8', '2200', '2021-08-05')";
        db.execSQL(sql_cr_tb_khoan_chi);
        sql_cr_tb_khoan_thu = "INSERT INTO tb_khoanthu VALUES(NULL, 1, 'Lương Vợ', 'Lương Của vợ tháng 7', '110', '2021-07-02')";
        db.execSQL(sql_cr_tb_khoan_thu);

        sql_cr_tb_account = "INSERT INTO  tb_account VALUES('admin', '123')";
        db.execSQL(sql_cr_tb_account);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String drop_tb_loai_chi = "drop table if exists tb_loaichi";
        db.execSQL(drop_tb_loai_chi);
        String drop_tb_loai_thu = "drop table if exists tb_loaithu";
        db.execSQL(drop_tb_loai_thu);
        String drop_tb_khoan_chi = "drop table if exists tb_khoanchi";
        db.execSQL(drop_tb_khoan_chi);
        String drop_tb_khoan_thu = "drop table if exists tb_khoanthu";
        db.execSQL(drop_tb_khoan_thu);
        String drop_tb_account = "drop table if exists tb_account";
        db.execSQL(drop_tb_account);
        onCreate(db);
    }
}
