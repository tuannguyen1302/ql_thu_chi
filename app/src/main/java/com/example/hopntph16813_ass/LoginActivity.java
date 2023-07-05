package com.example.hopntph16813_ass;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hopntph16813_ass.DAO.AccontDAO;
import com.example.hopntph16813_ass.DTO.User;

public class LoginActivity extends AppCompatActivity {
    private  long Pressed;
    Toast mToast;
    private EditText txtUsername;
    private EditText txtPassword;
    private CheckBox checkRemeber;
    private Button btnDangnhap;
    private Button btnDangky;
    AccontDAO accontDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accontDAO = new AccontDAO(LoginActivity.this);
        //anh xa
        init();
        //load data
        loadData();
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usser = txtUsername.getText().toString();
                String pass = txtPassword.getText().toString();
                Boolean chk = checkRemeber.isChecked();//lay ra trang thai cua chk
                User user = new User(usser,pass);
                if (accontDAO.checkLogin(user)){
                    luuData(usser,pass,chk);
                    Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(LoginActivity.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,RegisterActivity.RE);//phương thức truyền dữ liệu qua lại giwuxa các activity

            }
        });


    }




    //anh xa
    public void init(){
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        checkRemeber = (CheckBox) findViewById(R.id.checkRemeber);
        btnDangnhap = (Button) findViewById(R.id.btn_dangnhap);
        btnDangky = (Button) findViewById(R.id.btn_dangky);
    }

    //luu du lieu
    public void luuData(String user, String pass, Boolean check){
        SharedPreferences sharedPreferences =getSharedPreferences("DataLogin.dat",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (check==true){
            editor.putString("username",user);
            editor.putString("passwword",pass);
            editor.putBoolean("checkk",check);
        }else {
            editor.clear();
        }
        editor.commit();//xac nhan viec luu thong tin
    }


    //lay du lieu ra
    public void loadData(){
        SharedPreferences sharedPreferences =getSharedPreferences("DataLogin.dat",MODE_PRIVATE);
         Boolean chk= sharedPreferences.getBoolean("checkk",false);
         if (chk){
            txtUsername.setText(sharedPreferences.getString("username",""));
            txtPassword.setText(sharedPreferences.getString("passwword",""));
            checkRemeber.setChecked(chk);
         }
    }
    //hàm lấy lại data từ register

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RegisterActivity.RE){
            String u = data.getStringExtra("tk");
            String p = data.getStringExtra("mk");
            txtUsername.setText(u);
            txtPassword.setText(p);
        }
    }

    @Override
    public void onBackPressed() {
        if (Pressed+2000>System.currentTimeMillis()){
            mToast.cancel();
            super.onBackPressed();
        }else{
            mToast=Toast.makeText(getApplicationContext(),"ấn 2 lần để thoát ứng dụng",Toast.LENGTH_SHORT);
            mToast.show();
        }
        Pressed=System.currentTimeMillis();
    }
}