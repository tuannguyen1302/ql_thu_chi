package com.example.hopntph16813_ass.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hopntph16813_ass.DAO.LoaiThuDAO;
import com.example.hopntph16813_ass.DTO.LoaiChi;
import com.example.hopntph16813_ass.DTO.LoaiThu;
import com.example.hopntph16813_ass.R;

import java.util.ArrayList;

public class TabLoaiThuAdapter extends RecyclerView.Adapter<TabLoaiThuAdapter.TabLoaiThuViewHoldel> {

    private Context context;
    private ArrayList<LoaiThu> arrayList;
    Toast toast;
    LoaiThuDAO loaiThuDAO;
    public TabLoaiThuAdapter(Context context, ArrayList<LoaiThu> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        toast = new Toast(context);
        loaiThuDAO = new LoaiThuDAO(context);
    }

    @NonNull
    //ham cha ve layout hien thi
    @Override
    public TabLoaiThuViewHoldel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_loai_thu_view_item,parent,false);
        return  new TabLoaiThuViewHoldel(view);
    }
    //ham set du lieu de hien thi len
    @Override
    public void onBindViewHolder(@NonNull  TabLoaiThuAdapter.TabLoaiThuViewHoldel holder, int position) {
        LoaiThu loaiThu = arrayList.get(position);
        holder.tvNoiDungTenLoaiThu.setText(loaiThu.getTenLoaiThu());
        //Sửa dữ liệu
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_loaithu,null);
                builder.setView(view);
                AlertDialog alertDialog= builder.create();
                //ẩn background của view-item
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                View view_true = LayoutInflater.from(context).inflate(R.layout.custom_toast_edit_true,null);
                View view_false = LayoutInflater.from(context).inflate(R.layout.custom_toast_edit_false,null);
                EditText edSuaLoaiThu = view.findViewById(R.id.ed_sua_tenloaithu);
                edSuaLoaiThu.setText(loaiThu.getTenLoaiThu());
                Button btnSua = view.findViewById(R.id.btn_sua_loaithu);
                Button btnXoa = view.findViewById(R.id.btn_xoa_loaithu);
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loaiThu.setTenLoaiThu(edSuaLoaiThu.getText().toString());
                        long result =  loaiThuDAO.update(loaiThu);
                        if(result>0){
                            //buoc cap nhap lai du lieu
                            arrayList.clear();
                            arrayList.addAll(loaiThuDAO.getAll());
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
                        edSuaLoaiThu.setText("");
                    }
                });
            }
        });
        //Xóa Dữ Liệu
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
                        //check sem dữ liệu con đã bị xóa chưa
                        ArrayList<LoaiThu> list  =loaiThuDAO.checkGetIDLoaiThu(loaiThu.getIdTenLoaiThu());
                        Log.e("xxx", "onClick: "+list.size() );
                        if (list.size()>0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            View view = LayoutInflater.from(context).inflate(R.layout.dialog_e,null);
                            View view_true = LayoutInflater.from(context).inflate(R.layout.custom_toast_delete_true,null);
                            View view_false = LayoutInflater.from(context).inflate(R.layout.custom_toast_delete_false,null);
                            builder.setView(view);
                            AlertDialog alertDialog2= builder.create();
                            alertDialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            alertDialog2.show();
                            Button btn_e = view.findViewById(R.id.btn_e);
                            btn_e.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog2.dismiss();
                                    alertDialog.dismiss();
                                }
                            });
                            return;
                        }

                        long result=loaiThuDAO.delete(loaiThu.getIdTenLoaiThu());
                        if(result>0){
                            //buoc cap nhap lai du lieu
                            //buoc cap nhap lai du lieu
                            arrayList.clear();
                            arrayList.addAll(loaiThuDAO.getAll());
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
    //tra ve so luong
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class TabLoaiThuViewHoldel extends RecyclerView.ViewHolder{
        ImageView ivEdit;
        ImageView ivDel;
        TextView tvNoiDungTenLoaiThu;

        public TabLoaiThuViewHoldel(@NonNull View itemView) {
            super(itemView);
            ivDel = itemView.findViewById(R.id.iv_delete);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            tvNoiDungTenLoaiThu = itemView.findViewById(R.id.tv_noidung_tenloaithu);
        }

    }

}
