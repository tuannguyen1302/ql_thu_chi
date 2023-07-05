package com.example.hopntph16813_ass.fragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hopntph16813_ass.R;
import com.example.hopntph16813_ass.adapter.ChiViewpagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class ChiFragment extends Fragment {
TabLayout tabLayout;
ViewPager viewPager;
ChiViewpagerAdapter adapter;

    public ChiFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_chi, container, false);

        viewPager = view.findViewById(R.id.viewpager_chi);
        tabLayout = view.findViewById(R.id.tablayout_chi);
        adapter = new ChiViewpagerAdapter(getActivity().getSupportFragmentManager());
        //set adapter cho viewpager
        viewPager.setAdapter(adapter);
        //set viewpager vao tablayout
        tabLayout.setupWithViewPager(viewPager);


        return view;

    }
}