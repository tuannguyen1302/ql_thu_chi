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

import com.example.hopntph16813_ass.DAO.LoaiChiDAO;
import com.example.hopntph16813_ass.DTO.LoaiChi;
import com.example.hopntph16813_ass.R;
import com.example.hopntph16813_ass.adapter.TabLoaiChiAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class TabLoaiChiFragment extends Fragment {

TabLoaiChiAdapter adapter;
RecyclerView rvLoaiChi;
FloatingActionButton fabLoaiChi;
ArrayList<LoaiChi> arrayListLoaiChi;
LoaiChiDAO loaiChiDAO;
Toast toast;

    public TabLoaiChiFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tab_loai_chi, container, false);

        rvLoaiChi = view.findViewById(R.id.recyclerView_loaichi);
        fabLoaiChi =view.findViewById(R.id.fab_addloaichii);
        toast = new Toast(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLoaiChi.setLayoutManager(layoutManager);
        arrayListLoaiChi = new ArrayList<>();
        loaiChiDAO = new LoaiChiDAO(getContext());
        arrayListLoaiChi = loaiChiDAO.getAll();
        adapter = new TabLoaiChiAdapter(getContext(),arrayListLoaiChi);
        rvLoaiChi.setAdapter(adapter);

        fabLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });
        return view;
    }
    public void addDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_loaichi,null);
        builder.setView(view);
        AlertDialog alertDialog =builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        View view_true = LayoutInflater.from(getContext()).inflate(R.layout.custom_toast_add_true,null);
        View view_false = LayoutInflater.from(getContext()).inflate(R.layout.custom_toast_add_false,null);
        EditText edThemLoaiChi = view.findViewById(R.id.ed_them_tenloaichi);
        Button btnThem = view.findViewById(R.id.btn_them_loaichi);
        Button btnHuy = view.findViewById(R.id.btn_huy_loaichi);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiChi loaiChi = new LoaiChi();
                loaiChi.setTenLoaiChi(edThemLoaiChi.getText().toString());
                if (edThemLoaiChi.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Không được để trống",Toast.LENGTH_SHORT).show();
                    return;
                }
                long result =  loaiChiDAO.insert(loaiChi);
                if(result>0){
                    //buoc cap nhap lai du lieu
                    arrayListLoaiChi.clear();
                    arrayListLoaiChi.addAll(loaiChiDAO.getAll());
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
                edThemLoaiChi.setText("");
            }
        });

    }
}