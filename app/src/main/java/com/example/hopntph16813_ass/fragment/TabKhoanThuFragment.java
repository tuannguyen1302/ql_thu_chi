package com.example.hopntph16813_ass.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hopntph16813_ass.DAO.KhoanThuDAO;
import com.example.hopntph16813_ass.DAO.LoaiThuDAO;
import com.example.hopntph16813_ass.DTO.KhoanThu;
import com.example.hopntph16813_ass.DTO.LoaiThu;
import com.example.hopntph16813_ass.R;
import com.example.hopntph16813_ass.adapter.TabKhoanThuAdapter;
import com.example.hopntph16813_ass.adapter.TabLoaiThuAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;


public class TabKhoanThuFragment extends Fragment {
    RecyclerView rvKhoanThu;
    TabKhoanThuAdapter adapter;
    KhoanThuDAO khoanThuDAO;
    ArrayList<KhoanThu> arrayListKhoanThus;
    FloatingActionButton fabKhoanThu;
    ArrayList<LoaiThu> list = new ArrayList<>();
    LoaiThuDAO loaiThuDAO;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Toast toast;
    DatePickerDialog datePickerDialog;
    public TabKhoanThuFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_khoan_thu, container, false);

        rvKhoanThu =view.findViewById(R.id.recyclerView_khoanthu);
        fabKhoanThu = view.findViewById(R.id.fab_addkhoanthu);

        khoanThuDAO = new KhoanThuDAO(getContext());
        loaiThuDAO = new LoaiThuDAO(getContext());
        toast = new Toast(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvKhoanThu.setLayoutManager(layoutManager);
        arrayListKhoanThus = new ArrayList<>();
        arrayListKhoanThus = khoanThuDAO.getAll();
        adapter= new TabKhoanThuAdapter(getContext(),arrayListKhoanThus);
        rvKhoanThu.setAdapter(adapter);


        fabKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });
        return view;
    }
    public void addDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_khoanthu,null);
        builder.setView(view);
        AlertDialog alertDialog =builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        View view_true = LayoutInflater.from(getContext()).inflate(R.layout.custom_toast_add_true,null);
        View view_false = LayoutInflater.from(getContext()).inflate(R.layout.custom_toast_add_false,null);
        EditText edThemTenKhoanThu = view.findViewById(R.id.ed_them_tenkhoanthu);
        EditText edThemNoiDungKhoanThu = view.findViewById(R.id.ed_them_noidungkhoanthu);
        EditText edThemNgayThuKhoanThu = view.findViewById(R.id.ed_them_ngaythukhoanthu);
        EditText edThemSoTienThuKhoanThu = view.findViewById(R.id.ed_them_sotienkhoanthu);
        Spinner spthemkhoanthu = view.findViewById(R.id.sp_them_loaithu);
        //đổ dữu liệu vào cho spinner
        list = loaiThuDAO.getAll();
        ArrayAdapter adapter_sp = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,list);
        spthemkhoanthu.setAdapter(adapter_sp);
        //chon ngay
        edThemNgayThuKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edThemNgayThuKhoanThu.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        Button btnThem = view.findViewById(R.id.btn_them_khoanthu);
        Button btnHuy = view.findViewById(R.id.btn_huy_khoanthu);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edThemTenKhoanThu.getText().toString().isEmpty() ||
                        edThemNoiDungKhoanThu.getText().toString().isEmpty() ||
                        edThemNgayThuKhoanThu.getText().toString().isEmpty() ||
                        edThemSoTienThuKhoanThu.getText().toString().isEmpty()){
                    Toast.makeText(getContext()," Các Trường Không được để trống",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(list.size()<=0){
                    Toast.makeText(getContext(),"Loại Thu Chưa có dữ liệu!\n Mời bạn thêm Loại Thu Trước",Toast.LENGTH_SHORT).show();
                    return;
                }
                KhoanThu khoanThu = new KhoanThu();
                khoanThu.setTenKhoanThu(edThemTenKhoanThu.getText().toString());
                khoanThu.setNoiDung(edThemNoiDungKhoanThu.getText().toString());
                try {
                    khoanThu.setNgayThu(simpleDateFormat.parse(edThemNgayThuKhoanThu.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                khoanThu.setSoTien(Float.parseFloat(edThemSoTienThuKhoanThu.getText().toString()));
                LoaiThu loaiThu = (LoaiThu) spthemkhoanthu.getSelectedItem();
                khoanThu.setIdTenLoaiThu(loaiThu.getIdTenLoaiThu());

                long result =  khoanThuDAO.insert(khoanThu);
                if(result>0){
                    //buoc cap nhap lai du lieu
                    arrayListKhoanThus.clear();
                    arrayListKhoanThus.addAll(khoanThuDAO.getAll());
                    adapter.notifyDataSetChanged();

                    //thời gian hiển thị
                    toast.setDuration(Toast.LENGTH_SHORT);
                    //thong bao len
                    toast.setView(view_true);
                    toast.show();
                    alertDialog.dismiss();
                }else {
                    //thời gian hiển thị
                    toast.setDuration(Toast.LENGTH_SHORT);
                    //thong bao len
                    toast.setView(view_false);
                    toast.show();
                    alertDialog.dismiss();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edThemNoiDungKhoanThu.setText("");
                edThemTenKhoanThu.setText("");
                edThemSoTienThuKhoanThu.setText("");
                edThemNgayThuKhoanThu.setText("");
            }
        });

    }


}