package com.example.hopntph16813_ass.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopntph16813_ass.DAO.KhoanChiDAO;
import com.example.hopntph16813_ass.DAO.LoaiChiDAO;
import com.example.hopntph16813_ass.DTO.KhoanChi;
import com.example.hopntph16813_ass.DTO.LoaiChi;
import com.example.hopntph16813_ass.DTO.LoaiThu;
import com.example.hopntph16813_ass.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TabKhoanChiAdapter extends RecyclerView.Adapter<TabKhoanChiAdapter.TabKhoanChiHolder> {
    Context context;
    ArrayList<KhoanChi> arrayListKhoanChi;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    LoaiChiDAO loaiChiDAO;
    ArrayList<LoaiChi> list = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    LoaiChi loaiChi;
    Toast toast;
    KhoanChiDAO khoanChiDAO;
    public TabKhoanChiAdapter(Context context, ArrayList<KhoanChi> arrayListKhoanChi) {
        this.context = context;
        this.arrayListKhoanChi = arrayListKhoanChi;
        loaiChiDAO = new LoaiChiDAO(context);
        khoanChiDAO = new KhoanChiDAO(context);
        toast = new Toast(context);

    }

    @NonNull
    //ham cha ve layout hien thi
    @Override
    public TabKhoanChiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_khoan_chi_view_item,null);
        return new TabKhoanChiHolder(view);
    }
    //ham set du lieu de hien thi len
    @Override
    public void onBindViewHolder(@NonNull TabKhoanChiAdapter.TabKhoanChiHolder holder, int position) {
        KhoanChi khoanChi = arrayListKhoanChi.get(position);
        holder.tvTenKhoanChi.setText(khoanChi.getTenKhoanChi());
        holder.tvNoiDungKhoanChi.setText("Nội Dung: "+khoanChi.getNoiDung());
        holder.tvNgayChiKhoanChi.setText("Ngày Chi: "+simpleDateFormat.format(khoanChi.getNgayChi()));
        holder.tvSoTienKhoanChi.setText("Số Tiền: "+khoanChi.getSoTien()+" vnđ");
        holder.tvLoaiChiKhoanChi.setText("Loại Chi: "+loaiChiDAO.getTenLoaiChi(khoanChi.getIdTenLoaiChi()));
        //sửa sữ liệu
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_khoanchi,null);
                builder.setView(view);
                AlertDialog alertDialog= builder.create();
                //ẩn background của view-item
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                View view_true = LayoutInflater.from(context).inflate(R.layout.custom_toast_edit_true,null);
                View view_false = LayoutInflater.from(context).inflate(R.layout.custom_toast_edit_false,null);
                EditText edSuaTenKhoanChi = view.findViewById(R.id.ed_sua_tenkhoanchi);
                EditText edSuaNgayThuKhoanChi = view.findViewById(R.id.ed_sua_ngaythukhoanchi);
                EditText edSuaNoiDungKhoanChi = view.findViewById(R.id.ed_sua_noidungkhoanchi);
                EditText edSuaSoTienKhoanChi = view.findViewById(R.id.ed_sua_sotienkhoanchi);
                Spinner spinner = view.findViewById(R.id.sp_sua_loaichi);
                //đổ dữu liệu vào cho spinner
                list = loaiChiDAO.getAll();
                ArrayAdapter adapter_sp = new ArrayAdapter(context, android.R.layout.simple_list_item_1,list);
                spinner.setAdapter(adapter_sp);
                //chon ngay
                edSuaNgayThuKhoanChi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                edSuaNgayThuKhoanChi.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.MONTH));
                        datePickerDialog.show();
                    }
                });
                //set lại dữ liệu khi sửa
                edSuaTenKhoanChi.setText(khoanChi.getTenKhoanChi());
                edSuaNgayThuKhoanChi.setText(simpleDateFormat.format(khoanChi.getNgayChi()));
                edSuaNoiDungKhoanChi.setText(khoanChi.getNoiDung());
                edSuaSoTienKhoanChi.setText(khoanChi.getSoTien()+"");
                int vitri = -1;
                for (int i=0;i<list.size();i++){
                   if (list.get(i).getTenLoaiChi().equalsIgnoreCase(loaiChiDAO.getTenLoaiChi(khoanChi.getIdTenLoaiChi()))){
                       vitri=i;
                       break;
                   }
                }
                spinner.setSelection(vitri);

                Button btnSua = view.findViewById(R.id.btn_sua_khoanchi);
                Button btnXoa = view.findViewById(R.id.btn_xoa_khoanchi);
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        khoanChi.setTenKhoanChi(edSuaTenKhoanChi.getText().toString());
                        khoanChi.setNoiDung(edSuaNoiDungKhoanChi.getText().toString());
                        khoanChi.setSoTien(Float.parseFloat(edSuaSoTienKhoanChi.getText().toString()));
                        try {
                            khoanChi.setNgayChi(simpleDateFormat.parse(edSuaNgayThuKhoanChi.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //set spiner
                        loaiChi = (LoaiChi) spinner.getSelectedItem();
                        khoanChi.setIdTenLoaiChi(loaiChi.getIdTenLoaiChi());
                        long result =  khoanChiDAO.update(khoanChi);
                        if(result>0){
                            //buoc cap nhap lai du lieu
                            arrayListKhoanChi.clear();
                            arrayListKhoanChi.addAll(khoanChiDAO.getAll());
                            notifyDataSetChanged();

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
                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edSuaNgayThuKhoanChi.setText("");
                        edSuaNoiDungKhoanChi.setText("");
                        edSuaSoTienKhoanChi.setText("");
                        edSuaTenKhoanChi.setText("");
                    }
                });
            }
        });
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_delete,null);
                View view_true = LayoutInflater.from(context).inflate(R.layout.custom_toast_delete_true,null);
                View view_false = LayoutInflater.from(context).inflate(R.layout.custom_toast_delete_false,null);
                builder.setView(view);
                AlertDialog alertDialog= builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                Button btn_delete = view.findViewById(R.id.btn_ok_delete);
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        long result=khoanChiDAO.delete(khoanChi.getIdKhoanChi());
                        if(result>0){
                            //buoc cap nhap lai du lieu
                            arrayListKhoanChi.clear();
                            arrayListKhoanChi.addAll(khoanChiDAO.getAll());
                            notifyDataSetChanged();
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListKhoanChi.size();
    }

    public class TabKhoanChiHolder extends RecyclerView.ViewHolder{
        TextView tvTenKhoanChi;
        TextView tvSoTienKhoanChi;
        TextView tvNgayChiKhoanChi;
        TextView tvNoiDungKhoanChi;
        TextView tvLoaiChiKhoanChi;
        ImageView ivEdit;
        ImageView ivDel;
        public TabKhoanChiHolder(@NonNull View itemView) {
            super(itemView);
            tvTenKhoanChi = itemView.findViewById(R.id.tv_tenkhoanchi);
            tvSoTienKhoanChi = itemView.findViewById(R.id.tv_sotien_khoanchi);
            tvNgayChiKhoanChi = itemView.findViewById(R.id.tv_ngaychi_khoanchi);
            tvNoiDungKhoanChi = itemView.findViewById(R.id.tv_noidung_khoanchi);
            tvLoaiChiKhoanChi = itemView.findViewById(R.id.tv_loaichi_khoanchi);
            ivEdit =itemView.findViewById(R.id.iv_edit);
            ivDel =itemView.findViewById(R.id.iv_delete);

        }

    }
}
