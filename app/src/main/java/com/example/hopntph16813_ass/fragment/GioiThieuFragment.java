package com.example.hopntph16813_ass.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.hopntph16813_ass.R;


public class GioiThieuFragment extends Fragment {

    private WebView webview;




    public GioiThieuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gioi_thieu, container, false);
        webview = (WebView) view.findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/gioithieu.html");
        return view;


    }



}