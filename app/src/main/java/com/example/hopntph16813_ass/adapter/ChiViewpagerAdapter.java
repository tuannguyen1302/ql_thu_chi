package com.example.hopntph16813_ass.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.hopntph16813_ass.DTO.LoaiChi;
import com.example.hopntph16813_ass.fragment.TabKhoanChiFragment;
import com.example.hopntph16813_ass.fragment.TabLoaiChiFragment;

public class ChiViewpagerAdapter extends FragmentStatePagerAdapter {
    private int count = 2;
    public ChiViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch (position){
           case 0:
               return  new TabLoaiChiFragment();
           case 1:
               return new TabKhoanChiFragment();
           default:
               return new TabLoaiChiFragment();
       }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position) {
            case 0:
                title = "Loại Chi";
                break;
            case 1:
                title = "Khoản Chi";
                break;
        }
        return title;
    }
}
