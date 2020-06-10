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
import com.example.grupo5_proyecto1.models.Autores;
import com.example.grupo5_proyecto1.models.CatalogoArticulo;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpcionesFragment extends Fragment {
    Toolbar toolbar;
    ListView listView;
    SQLite_Helper helper;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        helper=new SQLite_Helper(activity);

    }

    public OpcionesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar=(Toolbar) getView().findViewById(R.id.toolbar_autores);
        listView=(ListView)getView().findViewById(R.id.listViewAutores);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,
                ListaAutores());
        listView.setAdapter(arrayAdapter);
    }

    private List<String> ListaAutores() {
        List<String> lista=new ArrayList<>();
        List<Autores> autoresList=helper.obtenerAutores();
        CatalogoArticulo catalogoArticulo;
        Articulo articulo;
        for(Autores a:autoresList){
            articulo=helper.obtenerArticulo(a.getCodigoArticulo());
            catalogoArticulo=helper.obtenerCatalogo(articulo.getCodTipoArticulo());

            String estado;
            if(articulo.getEstado()==1){
                estado="Disponible";
            }else{
                estado="Ocupado";
            }
            String autor="";
            autor+="Codigo articulo: "+a.getCodigoArticulo()+"\n"+
                    "Descripcion de articulo: "+catalogoArticulo.getDescripcion()+"\n"+
                    "Fecha de registro: "+articulo.getFecha()+"\n"+
                    "Estado: "+estado+"\n"+
                    "Corlin: "+a.getCorlin()+"\n"+
                    "Nombre: "+a.getNombre()+"\n";
            lista.add(autor);
        }
        return lista;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_opciones, container, false);
    }
}
