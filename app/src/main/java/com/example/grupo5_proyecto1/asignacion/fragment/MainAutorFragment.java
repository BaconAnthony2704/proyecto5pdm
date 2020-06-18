package com.example.grupo5_proyecto1.asignacion.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.grupo5_proyecto1.R;
import com.example.grupo5_proyecto1.asignacion.gestionAutor.AgregarAutorFragment;
import com.example.grupo5_proyecto1.asignacion.gestionAutor.ConsultarAutorFragment;
import com.example.grupo5_proyecto1.controller.SQLite_Helper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainAutorFragment extends Fragment {
    ListView listaAutor;
    Toolbar toolbar;
    SQLite_Helper helper;
    private void mostrarFragmentSeleccionado(Fragment fragment){
       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_asignacion,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        helper=new SQLite_Helper(activity);

    }

    public MainAutorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_autor, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listaAutor=(ListView) getView().findViewById(R.id.listViewAutores);
        toolbar=(Toolbar) getView().findViewById(R.id.toolbar_autorMain);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.main_autores));
        listaAutor.setAdapter(arrayAdapter);
        listaAutor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mostrarFragmentSeleccionado(new OpcionesFragment());
                        break;
                    case 1:
                        mostrarFragmentSeleccionado(new ConsultarAutorFragment());
                        break;
                    case 2:
                        mostrarFragmentSeleccionado(new AgregarAutorFragment());
                        break;
                }

            }
        });
    }


}
