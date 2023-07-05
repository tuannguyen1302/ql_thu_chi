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

import com.example.hopntph16813_ass.DAO.KhoanThuDAO;
import com.example.hopntph16813_ass.DAO.LoaiThuDAO;
import com.example.hopntph16813_ass.DTO.KhoanThu;
import com.example.hopntph16813_ass.DTO.LoaiThu;
import com.example.hopntph16813_ass.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TabKhoanThuAdapter extends RecyclerView.Adapter<TabKhoanThuAdapter.TabKhoanThuViewHoldel> {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Context context;
    private ArrayList<KhoanThu> arrayListKhoanThu;
    LoaiThuDAO loaiThuDAO;
    KhoanThuDAO khoanThuDAO;
    DatePickerDialog datePickerDialog;
    LoaiThu loaiThu;
    Toast toast;
    ArrayList<LoaiThu> list = new ArrayList<>();
    public TabKhoanThuAdapter(Context context, ArrayList<KhoanThu> arrayListKhoanThu) {
        this.context = context;
        this.arrayListKhoanThu = arrayListKhoanThu;
        loaiThuDAO = new LoaiThuDAO(context);
        khoanThuDAO = new KhoanThuDAO(context);
        toast = new Toast(context);
    }

    @NonNull
    //ham cha ve layout hien thi
    @Override
    public TabKhoanThuViewHoldel onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_khoan_thu_view_item,null);
        return new TabKhoanThuViewHoldel(view);
    }
    //ham set du lieu de hien thi len
    @Override
    public void onBindViewHolder(@NonNull  TabKhoanThuAdapter.TabKhoanThuViewHoldel holder, int position) {
        KhoanThu khoanThu = khoanThuDAO.getAll().get(position);
        holder.tvTenKhoanThu.setText(khoanThu.getTenKhoanThu());
        holder.tvSoTienKhoanThu.setText("Số Tiền: "+khoanThu.getSoTien()+" vnđ");
        holder.tvNgayThuKhoanThu.setText("Ngày Thu: "+simpleDateFormat.format(khoanThu.getNgayThu()));
        holder.tvNoiDungKhoanThu.setText("Nội Dung: "+khoanThu.getNoiDung());
        holder.tvLoaiThuKhoanThu.setText("Loại Thu: "+loaiThuDAO.getTenLoaiThu(khoanThu.getIdTenLoaiThu()));
        //Sửa dữ liệu
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_khoanthu,null);
                builder.setView(view);
                AlertDialog alertDialog= builder.create();
                //ẩn background của view-item
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                View view_true = LayoutInflater.from(context).inflate(R.layout.custom_toast_edit_true,null);
                View view_false = LayoutInflater.from(context).inflate(R.layout.custom_toast_edit_false,null);
                EditText edSuaTenKhoanThu = view.findViewById(R.id.ed_sua_tenkhoanthu);
                EditText edSuaNgayThuKhoanThu = view.findViewById(R.id.ed_sua_ngaythukhoanthu);
                EditText edSuaNoiDungKhoanThu = view.findViewById(R.id.ed_sua_noidungkhoanthu);
                EditText edSuaSoTienKhoanThu = view.findViewById(R.id.ed_sua_sotienkhoanthu);
                Spinner spinner = view.findViewById(R.id.sp_sua_loaithu);
                //đổ dữu liệu vào cho spinner
                list = loaiThuDAO.getAll();
                ArrayAdapter adapter_sp = new ArrayAdapter(context, android.R.layout.simple_list_item_1,list);
                spinner.setAdapter(adapter_sp);
                //chon ngay
                edSuaNgayThuKhoanThu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                edSuaNgayThuKhoanThu.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.MONTH));
                        datePickerDialog.show();
                    }
                });
                //set lại dữ liệu khi sửa
                edSuaTenKhoanThu.setText(khoanThu.getTenKhoanThu());
                edSuaNgayThuKhoanThu.setText(simpleDateFormat.format(khoanThu.getNgayThu()));
                edSuaNoiDungKhoanThu.setText(khoanThu.getNoiDung());
                edSuaSoTienKhoanThu.setText(khoanThu.getSoTien()+"");
                int vitri = -1;
                for (int i=0;i<list.size();i++){
                  if (list.get(i).getTenLoaiThu().equalsIgnoreCase(loaiThuDAO.getTenLoaiThu(khoanThu.getIdTenLoaiThu()))){
                      vitri=i;
                      break;
                  }
                }
                spinner.setSelection(vitri);

                Button btnSua = view.findViewById(R.id.btn_sua_khoanthu);
                Button btnXoa = view.findViewById(R.id.btn_xoa_khoanthu);
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        khoanThu.setTenKhoanThu(edSuaTenKhoanThu.getText().toString());
                        khoanThu.setNoiDung(edSuaNoiDungKhoanThu.getText().toString());
                        khoanThu.setSoTien(Float.parseFloat(edSuaSoTienKhoanThu.getText().toString()));
                        try {
                            khoanThu.setNgayThu(simpleDateFormat.parse(edSuaNgayThuKhoanThu.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        //set spiner
                        loaiThu = (LoaiThu) spinner.getSelectedItem();
                        khoanThu.setIdTenLoaiThu(loaiThu.getIdTenLoaiThu());
                        long result =  khoanThuDAO.update(khoanThu);
                        if(result>0){
                            //buoc cap nhap lai du lieu
                            arrayListKhoanThu.clear();
                            arrayListKhoanThu.addAll(khoanThuDAO.getAll());
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
                        edSuaNgayThuKhoanThu.setText("");
                        edSuaNoiDungKhoanThu.setText("");
                        edSuaSoTienKhoanThu.setText("");
                        edSuaTenKhoanThu.setText("");
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
                        long result=khoanThuDAO.delete(khoanThu.getIdKhoanThu());
                        if(result>0){
                            //buoc cap nhap lai du lieu
                            arrayListKhoanThu.clear();
                            arrayListKhoanThu.addAll(khoanThuDAO.getAll());
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
        return arrayListKhoanThu.size();
    }

    public class TabKhoanThuViewHoldel extends RecyclerView.ViewHolder{
        TextView tvTenKhoanThu;
        TextView tvSoTienKhoanThu;
        TextView tvNgayThuKhoanThu;
        TextView tvNoiDungKhoanThu;
        TextView tvLoaiThuKhoanThu;
        ImageView ivEdit;
        ImageView ivDel;
        public TabKhoanThuViewHoldel( View itemView) {
            super(itemView);
            tvTenKhoanThu = itemView.findViewById(R.id.tv_tenkhoanthu);
            tvSoTienKhoanThu = itemView.findViewById(R.id.tv_sotien_khoanthu);
            tvNgayThuKhoanThu = itemView.findViewById(R.id.tv_ngaythu_khoanthu);
            tvNoiDungKhoanThu = itemView.findViewById(R.id.tv_noidung_khoanthu);
            tvLoaiThuKhoanThu = itemView.findViewById(R.id.tv_loaithu_khoanthu);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivDel = itemView.findViewById(R.id.iv_delete);
        }
    }
}
