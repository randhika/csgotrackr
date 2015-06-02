/*
 * Copyright (c) 2015. Juraj Palaščák
 * All rights Reserved
 */

package com.example.android.cstogo.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cstogo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentF extends Fragment {


    public FragmentF() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_f, container, false);
    }


}
