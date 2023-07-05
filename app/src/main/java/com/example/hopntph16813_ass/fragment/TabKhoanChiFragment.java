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

import com.example.hopntph16813_ass.DAO.KhoanChiDAO;
import com.example.hopntph16813_ass.DAO.LoaiChiDAO;
import com.example.hopntph16813_ass.DTO.KhoanChi;
import com.example.hopntph16813_ass.DTO.KhoanThu;
import com.example.hopntph16813_ass.DTO.LoaiChi;
import com.example.hopntph16813_ass.DTO.LoaiThu;
import com.example.hopntph16813_ass.R;
import com.example.hopntph16813_ass.adapter.TabKhoanChiAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.ArrayList;

public class TabKhoanChiFragment extends Fragment {
    RecyclerView rvKhoanChi;
    ArrayList<KhoanChi> arrayListKhoanChi;
    KhoanChiDAO khoanChiDAO;
    TabKhoanChiAdapter adapter;
    FloatingActionButton fabKhoanChi;
    LoaiChiDAO loaiChiDAO;
    ArrayList<LoaiChi> list = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Toast toast;
    public TabKhoanChiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_khoan_chi, container, false);

        rvKhoanChi = view.findViewById(R.id.recyclerView_khoanchi);
        fabKhoanChi = view.findViewById(R.id.fab_addkhoanchi);

        loaiChiDAO = new LoaiChiDAO(getContext());
        toast= new Toast(getContext());

        arrayListKhoanChi = new ArrayList<>();
        khoanChiDAO = new KhoanChiDAO(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvKhoanChi.setLayoutManager(layoutManager);
        arrayListKhoanChi=khoanChiDAO.getAll();
        adapter = new TabKhoanChiAdapter(getContext(),arrayListKhoanChi);
        rvKhoanChi.setAdapter(adapter);

        fabKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();;
            }
        });
        return view;
    }
    public void addDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_khoanchi,null);
        builder.setView(view);
        AlertDialog alertDialog =builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        View view_true = LayoutInflater.from(getContext()).inflate(R.layout.custom_toast_add_true,null);
        View view_false = LayoutInflater.from(getContext()).inflate(R.layout.custom_toast_add_false,null);
        EditText edThemTenKhoanChi = view.findViewById(R.id.ed_them_tenkhoanchi);
        EditText edThemNoiDungKhoanChi= view.findViewById(R.id.ed_them_noidungkhoanchi);
        EditText edThemNgayThuKhoanChi = view.findViewById(R.id.ed_them_ngaythukhoanchi);
        EditText edThemSoTienThuKhoanChi = view.findViewById(R.id.ed_them_sotienkhoanchi);
        Spinner spthemkhoanchi = view.findViewById(R.id.sp_them_loaichi);
        //đổ dữu liệu vào cho spinner
        list = loaiChiDAO.getAll();
        ArrayAdapter adapter_sp = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,list);
        spthemkhoanchi.setAdapter(adapter_sp);
        //chon ngay
        edThemNgayThuKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edThemNgayThuKhoanChi.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        Button btnThem = view.findViewById(R.id.btn_them_khoanchi);
        Button btnHuy = view.findViewById(R.id.btn_huy_khoanchi);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edThemTenKhoanChi.getText().toString().isEmpty() ||
                        edThemNoiDungKhoanChi.getText().toString().isEmpty() ||
                        edThemNgayThuKhoanChi.getText().toString().isEmpty() ||
                        edThemSoTienThuKhoanChi.getText().toString().isEmpty()){
                    Toast.makeText(getContext()," Các Trường Không được để trống",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(list.size()<=0){
                    Toast.makeText(getContext(),"Loại Chi Chưa có dữ liệu!\n Mời bạn thêm Loại Chi Trước",Toast.LENGTH_SHORT).show();
                    return;
                }
                KhoanChi khoanChi = new KhoanChi();
                khoanChi.setTenKhoanChi(edThemTenKhoanChi.getText().toString());
                khoanChi.setNoiDung(edThemNoiDungKhoanChi.getText().toString());
                try {
                    khoanChi.setNgayChi(simpleDateFormat.parse(edThemNgayThuKhoanChi.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                khoanChi.setSoTien(Float.parseFloat(edThemSoTienThuKhoanChi.getText().toString()));
                LoaiChi loaiChi = (LoaiChi) spthemkhoanchi.getSelectedItem();
                khoanChi.setIdTenLoaiChi(loaiChi.getIdTenLoaiChi());
                long result =  khoanChiDAO.insert(khoanChi);
                if(result>0){
                    //buoc cap nhap lai du lieu
                    arrayListKhoanChi.clear();
                    arrayListKhoanChi.addAll(khoanChiDAO.getAll());
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
                edThemNoiDungKhoanChi.setText("");
                edThemTenKhoanChi.setText("");
                edThemSoTienThuKhoanChi.setText("");
                edThemNgayThuKhoanChi.setText("");
            }
        });

    }
}