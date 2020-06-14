package com.example.grupo5_proyecto1.prestamo.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo5_proyecto1.R;
import com.example.grupo5_proyecto1.asignacion.fragment.datepicker_fecha.DatePickerFragment;
import com.example.grupo5_proyecto1.controller.SQLite_Helper;
import com.example.grupo5_proyecto1.models.Articulo;
import com.example.grupo5_proyecto1.models.Asignacion;
import com.example.grupo5_proyecto1.models.CatalogoMotivoAsignacion;
import com.example.grupo5_proyecto1.models.Prestamos;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class CrearPrestamoFragment extends Fragment {

    Spinner spArticulo;
    Toolbar toolbar;
    EditText txtDocente,txtDuracion,txtMateria, txtActividad;
    SQLite_Helper helper;
    Button btnGuardar;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        helper=new SQLite_Helper(activity);

    }
    public CrearPrestamoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crear_prestamo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        //Inicializando los valores
        toolbar=getView().findViewById(R.id.toolbar_crear_prestamo);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        txtActividad = getView().findViewById(R.id.txtActividad);
        txtDocente = getView().findViewById(R.id.txtDocente);
        txtDuracion = getView().findViewById(R.id.txtDuracion);
        txtActividad = getView().findViewById(R.id.txtActividad);
        spArticulo = getView().findViewById(R.id.spArticulo);

        btnGuardar = getView().findViewById(R.id.btnGuardarPrestamo);

        ArrayAdapter<String>arrayAdapterArticulos=new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                obtenerListaArticuloDisponible()
        );

        spArticulo.setAdapter(arrayAdapterArticulos);

    //Creando los Listeners

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtActividad.getText().toString().equals("") ||
                        txtDocente.getText().toString().equals("") ||
                        txtDuracion.getText().toString().equals("") ||
                        txtMateria.getText().toString().equals("") ||
                        spArticulo.getSelectedItem() == null) {
                    Toast.makeText(getView().getContext(),"Debe completar todos los campos",Toast.LENGTH_SHORT);
                    return;
                }
                Log.i("Llegamos ","Hasta aqui");
                Log.i("mensaje",txtActividad.getText().toString());
            }
        });


    }



    private List<String> obtenerListaArticuloDisponible() {
        List<String> list=new ArrayList<>();
        Log.i("mensaje", "Estamos en Obtener articulos");
        for(Articulo art:helper.obtenerArticuloEstado(11)){
            list.add(art.getCodigoArticulo());
        }
        return list;
    }


}
