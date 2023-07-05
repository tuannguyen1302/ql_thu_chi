package com.example.hopntph16813_ass.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hopntph16813_ass.DAO.LoaiThuDAO;
import com.example.hopntph16813_ass.DTO.LoaiChi;
import com.example.hopntph16813_ass.DTO.LoaiThu;
import com.example.hopntph16813_ass.R;
import com.example.hopntph16813_ass.adapter.TabLoaiThuAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TabLoaiThuFragment extends Fragment {
    RecyclerView rvLoaiThu;
    TabLoaiThuAdapter adapter;
    LoaiThuDAO loaiThuDAO;
    ArrayList<LoaiThu> arrayListLoaiThu;
    FloatingActionButton fabLoaiThu;
    Toast toast;
    public TabLoaiThuFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_loai_thu, container, false);
        rvLoaiThu =view.findViewById(R.id.recyclerView_loaithu);
        fabLoaiThu =view.findViewById(R.id.fab_addloaithu);
        toast =new Toast(getContext());
        loaiThuDAO = new LoaiThuDAO(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLoaiThu.setLayoutManager(layoutManager);
        arrayListLoaiThu = new ArrayList<>();
        arrayListLoaiThu = loaiThuDAO.getAll();
        adapter= new TabLoaiThuAdapter(getContext(),arrayListLoaiThu);
        rvLoaiThu.setAdapter(adapter);
        fabLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });
        return view;
    }
    public void addDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_loaithu,null);
        builder.setView(view);
        AlertDialog alertDialog =builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        View view_true = LayoutInflater.from(getContext()).inflate(R.layout.custom_toast_add_true,null);
        View view_false = LayoutInflater.from(getContext()).inflate(R.layout.custom_toast_add_false,null);
        EditText edThemLoaiThu = view.findViewById(R.id.ed_them_tenloaithu);
        Button btnThem = view.findViewById(R.id.btn_them_loaithu);
        Button btnHuy = view.findViewById(R.id.btn_huy_loaithu);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiThu loaiThu = new LoaiThu();
                loaiThu.setTenLoaiThu(edThemLoaiThu.getText().toString());
                if (edThemLoaiThu.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Không được để trống",Toast.LENGTH_SHORT).show();
                    return;
                }
                long result =  loaiThuDAO.insert(loaiThu);
                if(result>0){
                    //buoc cap nhap lai du lieu
                    arrayListLoaiThu.clear();
                    arrayListLoaiThu.addAll(loaiThuDAO.getAll());
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
                edThemLoaiThu.setText("");
            }
        });

    }
}