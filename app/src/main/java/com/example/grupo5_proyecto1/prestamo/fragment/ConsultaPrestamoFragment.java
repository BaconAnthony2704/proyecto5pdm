package com.example.grupo5_proyecto1.prestamo.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.grupo5_proyecto1.R;
import com.example.grupo5_proyecto1.controller.SQLite_Helper;
import com.example.grupo5_proyecto1.models.Prestamos;

import java.util.ArrayList;
import java.util.List;


public class ConsultaPrestamoFragment extends Fragment {
    Toolbar toolbar;
    ListView listView;
    SQLite_Helper helper;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        helper=new SQLite_Helper(activity);

    }


    public ConsultaPrestamoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar=(Toolbar) getView().findViewById(R.id.toolbar_consultar_prestamo);
        listView=(ListView) getView().findViewById(R.id.listViewPrestamos);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,
                obtenerPrestamos());
        listView.setAdapter(arrayAdapter);
    }

    public List<String> obtenerPrestamos ()  {
        List<String> lista = new ArrayList();
        List <Prestamos> lstPrestamos = helper.obtenerPrestamos();
        for(Prestamos p : lstPrestamos) {
            lista.add(p.toString());
        }

    return lista;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consulta_prestamos, container, false);
    }
}
