package com.example.hopntph16813_ass.fragment;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.hopntph16813_ass.DAO.KhoanChiDAO;
import com.example.hopntph16813_ass.DAO.KhoanThuDAO;
import com.example.hopntph16813_ass.DAO.LoaiChiDAO;
import com.example.hopntph16813_ass.DTO.KhoanChi;
import com.example.hopntph16813_ass.DTO.KhoanThu;
import com.example.hopntph16813_ass.DTO.LoaiChi;
import com.example.hopntph16813_ass.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class ThongKeFragment extends Fragment {
    private Button btnNgaybatdau;
    private Button btnNgayketthuc;
    private Button btnKetqua;
    private TextView tvTongthu;
    private TextView tvSotientongthu;
    private TextView tvTongchi;
    private TextView tvSotientongchi;
    KhoanChiDAO khoanChiDAO;
    KhoanThuDAO khoanThuDAO;
    float tongthu=0,tongchi=0;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        khoanChiDAO = new KhoanChiDAO(getContext());
        khoanThuDAO = new KhoanThuDAO(getContext());
        btnNgaybatdau = (Button) view.findViewById(R.id.btn_ngaybatdau);
        btnNgayketthuc = (Button) view.findViewById(R.id.btn_ngayketthuc);
        btnKetqua = (Button) view.findViewById(R.id.btn_ketqua);
        tvTongthu = (TextView) view.findViewById(R.id.tv_tongthu);
        tvSotientongthu = (TextView) view.findViewById(R.id.tv_sotientongthu);
        tvTongchi = (TextView) view.findViewById(R.id.tv_tongchi);
        tvSotientongchi = (TextView) view.findViewById(R.id.tv_sotientongchi);
        //tông thu chi
        ArrayList<KhoanChi> listchi =khoanChiDAO.getAll();
        for (int i= 0;i<listchi.size();i++){
            tongchi = tongchi + listchi.get(i).getSoTien();
        }
        tvSotientongchi.setText(tongchi+" VNĐ");


        ArrayList<KhoanThu> listthu =khoanThuDAO.getAll();
        for (int i= 0;i<listthu.size();i++){
            tongthu = tongthu + listthu.get(i).getSoTien();
        }
        tvSotientongthu.setText(tongthu+" VNĐ");

        //chọn ngày
        btnNgaybatdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnNgaybatdau.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btnNgayketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnNgayketthuc.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        //ket qua

        btnKetqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //Tính tiền theo ngày
                float chi_THEONGAY = 0;
                float thu_THEONGAY = 0;
                String ngaybatdau=btnNgaybatdau.getText().toString();
                String ngayketthuc=btnNgayketthuc.getText().toString();

                //chi
                for (int i= 0;i<listchi.size();i++){
                    try {
                        if (listchi.get(i).getNgayChi().compareTo(simpleDateFormat.parse(ngaybatdau))>=0 && listchi.get(i).getNgayChi().compareTo(simpleDateFormat.parse(ngayketthuc))<=0 ){
                            chi_THEONGAY = chi_THEONGAY + listchi.get(i).getSoTien();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                //thu
                for (int i= 0;i<listthu.size();i++){
                    try {
                        if (listthu.get(i).getNgayThu().compareTo(simpleDateFormat.parse(ngaybatdau))>=0 && listthu.get(i).getNgayThu().compareTo(simpleDateFormat.parse(ngayketthuc))<=0 ){
                            thu_THEONGAY = thu_THEONGAY + listthu.get(i).getSoTien();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                tvSotientongchi.setText(chi_THEONGAY+" VNĐ");
                tvSotientongthu.setText(thu_THEONGAY+" VNĐ");

            }
        });

        return view;
    }
}