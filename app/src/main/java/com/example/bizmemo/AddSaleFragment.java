package com.example.bizmemo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSaleFragment extends Fragment {



    public AddSaleFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View AddSaleView=inflater.inflate(R.layout.fragment_add_sale, container, false);



        return AddSaleView;
    }
}