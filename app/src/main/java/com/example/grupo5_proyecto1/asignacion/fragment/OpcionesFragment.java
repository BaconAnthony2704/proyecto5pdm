package com.example.grupo5_proyecto1.asignacion.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.grupo5_proyecto1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpcionesFragment extends Fragment {

    public OpcionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_opciones, container, false);
    }
}
