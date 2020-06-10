package com.example.grupo5_proyecto1.asignacion.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.grupo5_proyecto1.R;
import com.example.grupo5_proyecto1.controller.SQLite_Helper;
import com.example.grupo5_proyecto1.models.Articulo;
import com.example.grupo5_proyecto1.models.Asignacion;
import com.example.grupo5_proyecto1.models.CatalogoArticulo;

import java.util.ArrayList;
import java.util.List;


public class ConsultaFragment extends Fragment {
    Toolbar toolbar;
    ListView listView;
    SQLite_Helper helper;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        helper=new SQLite_Helper(activity);

    }
    public ConsultaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar=(Toolbar) getView().findViewById(R.id.toolbar_consultar_asignacion);
        listView=(ListView) getView().findViewById(R.id.listViewAsignacion);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,
                ListaAsignaciones());
        listView.setAdapter(arrayAdapter);
    }

    private List<String> ListaAsignaciones() {
        List<String> lista=new ArrayList<>();
        Articulo articulo;
        CatalogoArticulo catalogoArticulo;
        List<Asignacion> lasignacion=new ArrayList<>();
        lasignacion=helper.MostrarAsignaciones();
        for(Asignacion a:lasignacion){
            articulo=helper.obtenerArticulo(a.getCodigoArticulo());
            catalogoArticulo=helper.obtenerCatalogo(articulo.getCodTipoArticulo());
            String estado;
            if(articulo.getEstado()==1){
                estado="Diponible";
            }else{
                estado="No disponible";
            }
            String asignacion="";
            asignacion+="Fecha: ".toUpperCase()+a.getFechaAsignacion()+"\n"+
                        "Motivo: ".toUpperCase()+helper.obtenerMotivoAsignacion(a.getCodMotivoAsignacion())+"\n"+
                        "Codigo articulo: ".toUpperCase()+a.getCodigoArticulo()+"\n"+
                        "Tipo articulo: ".toUpperCase()+articulo.getCodTipoArticulo()+"\n"+
                        "Descripcion articulo: ".toUpperCase()+catalogoArticulo.getDescripcion()+"\n"+
                        "Fecha registro: ".toUpperCase()+articulo.getFecha()+"\n"+
                        "Estado: ".toUpperCase()+estado+"\n"+
                        "Descripcion: ".toUpperCase()+a.getDescripcion()+"\n"+
                        "Docente: ".toUpperCase()+a.getDocente()+"\n";
            lista.add(asignacion);
        }
        return lista;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consulta, container, false);
    }
}
