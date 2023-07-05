package com.example.hopntph16813_ass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.hopntph16813_ass.DAO.KhoanChiDAO;
import com.example.hopntph16813_ass.DAO.KhoanThuDAO;
import com.example.hopntph16813_ass.DAO.LoaiChiDAO;
import com.example.hopntph16813_ass.DTO.KhoanChi;
import com.example.hopntph16813_ass.DTO.KhoanThu;
import com.example.hopntph16813_ass.DTO.LoaiChi;
import com.example.hopntph16813_ass.fragment.ChiFragment;
import com.example.hopntph16813_ass.fragment.GioiThieuFragment;
import com.example.hopntph16813_ass.fragment.ThongKeFragment;
import com.example.hopntph16813_ass.fragment.ThuFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //khai bao bien de check fragment hien tai
    private  static final int FRAGMENT_THU=0;
    private  static final int FRAGMENT_CHI=1;
    private  static final int FRAGMENT_THONGKE=2;
    private  static final int FRAGMENT_GIOITHIEU=3;

    //TAO BIEN DE CHECK  FRAGMENT
     int current = FRAGMENT_THU;


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //anh xa

        toolbar =findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        frameLayout=findViewById(R.id.frame_layout);

        //toolbar
        setSupportActionBar(toolbar);

        //Tao icon menu ba gach
        toggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);//dua togle vao drawerlayout
        toggle.syncState();//dong bo hoa

        //navigationView lang nghe su kien
        navigationView.setNavigationItemSelectedListener(this);

        //set fragment hien thi luc dau tien
         replaceFragment(new ThuFragment());
         navigationView.getMenu().findItem(R.id.khoanthu).setChecked(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull  MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.khoanthu){
            setTitle("KHOẢN THU");
            if (current!=FRAGMENT_THU){
                replaceFragment(new ThuFragment());
                current=FRAGMENT_THU;
                navigationView.getMenu().findItem(R.id.khoanthu).setChecked(true);
            }
        }else if (id==R.id.khoanchi){
            setTitle("KHOẢN CHI");
            if (current!=FRAGMENT_CHI){
                replaceFragment(new ChiFragment());
                current=FRAGMENT_CHI;
                navigationView.getMenu().findItem(R.id.khoanchi).setChecked(true);
            }
        }
        else if (id==R.id.thongke){
            setTitle("THỐNG KÊ");
            if (current!=FRAGMENT_THONGKE){
                replaceFragment(new ThongKeFragment());
                current=FRAGMENT_THONGKE;
                navigationView.getMenu().findItem(R.id.thongke).setChecked(true);
            }
        }
        else if (id==R.id.gioithieu){
            setTitle("QUẢN LÝ THU CHI");
            if (current!=FRAGMENT_GIOITHIEU){
                replaceFragment(new GioiThieuFragment());
                current=FRAGMENT_GIOITHIEU;
                navigationView.getMenu().findItem(R.id.gioithieu).setChecked(true);
            }
        }
        else if (id==R.id.thoat) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_thoat,null);
            builder.setView(view);
            AlertDialog alertDialog= builder.create();
            alertDialog.show();
            Button btnThoat = view.findViewById(R.id.btn_thoat);
            Button btnHuy = view.findViewById(R.id.btn_huy_thoat);
            btnThoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        }
        //dong drawerlayout lai
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment).commit();
    }

    //nut back tren dien thoai
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

}