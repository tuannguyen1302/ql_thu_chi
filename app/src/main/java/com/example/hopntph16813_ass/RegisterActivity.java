package com.example.hopntph16813_ass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hopntph16813_ass.DAO.AccontDAO;
import com.example.hopntph16813_ass.DTO.User;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private EditText edNhapUsername;
    private EditText edNhapPassword;
    private EditText edNhaplaiPassword;
    private Button btnDangkyRegister;
    private Button btnNhaplai;
    AccontDAO accontDAO;
    public  static  int RE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        accontDAO = new AccontDAO(RegisterActivity.this);
        edNhapUsername = (EditText) findViewById(R.id.ed_nhap_Username);
        edNhapPassword = (EditText) findViewById(R.id.ed_nhap_Password);
        edNhaplaiPassword = (EditText) findViewById(R.id.ed_nhaplai_Password);
        btnDangkyRegister = (Button) findViewById(R.id.btn_dangky_register);
        btnNhaplai = (Button) findViewById(R.id.btn_nhaplai);

        List<User> list =  accontDAO.getAll();

        btnDangkyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userr = edNhapUsername.getText().toString();
                String pass = edNhapPassword.getText().toString();
                String re_pass = edNhaplaiPassword.getText().toString();
                User user = new User(userr, pass);
                Boolean check_trung_user = true;
                for (int i=0;i<list.size();i++){
                    if (userr.matches(list.get(i).getUsername())){
                        check_trung_user=false;
                        break;
                    }
                }
                if (userr.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Tài khoản or mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (!pass.matches(re_pass)) {
                        Toast.makeText(RegisterActivity.this, "Mật khẩu nhập lại không khớp nhau", Toast.LENGTH_SHORT).show();
                    }else {
                        if (check_trung_user==false){
                            Toast.makeText(RegisterActivity.this, "Tài Khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        }else{
                            accontDAO.themtk(user);
                            Toast.makeText(RegisterActivity.this, "Tạo Tài Khoản Thành Công", Toast.LENGTH_SHORT).show();
                            //gửi trả lại dữ liệu cho login
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            intent.putExtra("tk",userr);
                            intent.putExtra("mk",pass);
                            setResult(RegisterActivity.RE,intent);
                            finish();
                        }
                    }
                }

            }
        });
        btnNhaplai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edNhapUsername.setText("");
                edNhaplaiPassword.setText("");
            }
        });
    }
}