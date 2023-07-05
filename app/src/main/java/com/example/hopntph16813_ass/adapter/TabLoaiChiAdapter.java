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

import com.example.hopntph16813_ass.DAO.KhoanThuDAO;
import com.example.hopntph16813_ass.DAO.LoaiChiDAO;
import com.example.hopntph16813_ass.DTO.KhoanThu;
import com.example.hopntph16813_ass.DTO.LoaiChi;
import com.example.hopntph16813_ass.R;

import java.util.ArrayList;

public class TabLoaiChiAdapter extends RecyclerView.Adapter<TabLoaiChiAdapter.TabLoaiChiViewHoldel> {

    private Context context;
    private ArrayList<LoaiChi> arrayList;
    private ArrayList<KhoanThu> khoanThuArrayList = new ArrayList<>();
    LoaiChiDAO loaiChiDAO;
    Toast toast;
    KhoanThuDAO khoanThuDAO;
    public TabLoaiChiAdapter(Context context, ArrayList<LoaiChi> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        loaiChiDAO = new LoaiChiDAO(context);
        khoanThuDAO = new KhoanThuDAO(context);
        toast = new Toast(context);
    }

    @NonNull
    //ham cha ve layout hien thi
    @Override
    public TabLoaiChiViewHoldel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_loai_chi_view_item,parent,false);
        return  new TabLoaiChiViewHoldel(view);
    }
    //ham set du lieu de hien thi len
    @Override
    public void onBindViewHolder(@NonNull  TabLoaiChiAdapter.TabLoaiChiViewHoldel holder, int position) {
        LoaiChi loaiChi = arrayList.get(position);
        holder.tvNoiDungTenLoaichi.setText(loaiChi.getTenLoaiChi());
        //sửa dữ liệu
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_loaichi,null);
                builder.setView(view);
                AlertDialog alertDialog= builder.create();
                //ẩn background của view-item
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                View view_true = LayoutInflater.from(context).inflate(R.layout.custom_toast_edit_true,null);
                View view_false = LayoutInflater.from(context).inflate(R.layout.custom_toast_edit_false,null);
                EditText edSuaLoaiChi = view.findViewById(R.id.ed_sua_tenloaichi);
                edSuaLoaiChi.setText(loaiChi.getTenLoaiChi());
                Button btnSua = view.findViewById(R.id.btn_sua_loaichi);
                Button btnXoa = view.findViewById(R.id.btn_xoa_loaichi);
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loaiChi.setTenLoaiChi(edSuaLoaiChi.getText().toString());
                        long result =  loaiChiDAO.update(loaiChi);
                        if(result>0){
                            //buoc cap nhap lai du lieu
                            arrayList.clear();
                            arrayList.addAll(loaiChiDAO.getAll());
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
                        edSuaLoaiChi.setText("");
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
                        ArrayList<LoaiChi> list  =loaiChiDAO.checkGetIDLoaiChi(loaiChi.getIdTenLoaiChi());
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

                        long result=loaiChiDAO.delete(loaiChi.getIdTenLoaiChi());
                        if(result>0){
                            //buoc cap nhap lai du lieu
                            arrayList.clear();
                            arrayList.addAll(loaiChiDAO.getAll());
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

    public class TabLoaiChiViewHoldel extends RecyclerView.ViewHolder{
        ImageView ivEdit;
        ImageView ivDel;
        TextView tvNoiDungTenLoaichi;

        public TabLoaiChiViewHoldel(@NonNull View itemView) {
            super(itemView);
            ivDel = itemView.findViewById(R.id.iv_delete);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            tvNoiDungTenLoaichi = itemView.findViewById(R.id.tv_noidung_tenloaichi);

        }

    }

}
