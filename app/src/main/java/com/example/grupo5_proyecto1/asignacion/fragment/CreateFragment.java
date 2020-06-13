package com.example.grupo5_proyecto1.asignacion.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.grupo5_proyecto1.R;
import com.example.grupo5_proyecto1.asignacion.fragment.datepicker_fecha.DatePickerFragment;
import com.example.grupo5_proyecto1.controller.SQLite_Helper;
import com.example.grupo5_proyecto1.models.Articulo;
import com.example.grupo5_proyecto1.models.Asignacion;
import com.example.grupo5_proyecto1.models.CatalogoMotivoAsignacion;
import com.example.grupo5_proyecto1.services.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.grupo5_proyecto1.Envirioment.GlobalEnvirioment.BASE_DATOS;
import static com.example.grupo5_proyecto1.Envirioment.GlobalEnvirioment.VERSION;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    //columna_asignacion={"NODOCUMENTO","CODMOTIVOASGINACION","CODIGOARTICULO","DOCENTE","CODARTICULO","DESCRIPCION","FECHAASIGNACION"};
    Spinner spinner,spinner2;
    Toolbar toolbar;
    EditText txtDocente,txtdescripcion,txtfecha;
    SQLite_Helper helper;
    Button btnGuardar;
    MyProgressDialog progreso;
    List<Articulo> articulos;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        helper=new SQLite_Helper(activity);

    }
    public CreateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        articulos=new ArrayList<>();
        request= Volley.newRequestQueue(getContext());
        toolbar=(Toolbar)getView().findViewById(R.id.toolbar_crear_asignacion);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        txtDocente=(EditText) getView().findViewById(R.id.txtEditDocente);
        txtdescripcion=(EditText) getView().findViewById(R.id.txtEditDescripcion);
        txtfecha=(EditText) getView().findViewById(R.id.txtEditFecha);
        txtfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.txtEditFecha:
                        showDatePickerDialog();
                        break;
                }
            }
        });
        cargarWebService();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        spinner2=(Spinner) getView().findViewById(R.id.spinner_motivo_asignacion);
        ArrayAdapter<String>arrayAdapterMotivo=new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_list_item_1,
                obtenerListaMotivoAsignacion()
        );
        spinner2.setAdapter(arrayAdapterMotivo);
        btnGuardar=(Button) getView().findViewById(R.id.btnGuardarAsignacion);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Asignacion asignacion=new Asignacion();
                asignacion.setCodigoArticulo(spinner.getSelectedItem().toString());
                asignacion.setCodMotivoAsignacion(helper.obtenerIdCodMotivoAsignacion(spinner2.getSelectedItem().toString()));
                asignacion.setDescripcion(txtdescripcion.getText().toString());
                asignacion.setDocente(txtDocente.getText().toString());
                asignacion.setFechaAsignacion(txtfecha.getText().toString());
                String progreso=helper.insertar(asignacion);
                Toast.makeText(getView().getContext(),progreso,Toast.LENGTH_LONG).show();
            }
        });


    }
    private void showDatePickerDialog(){
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                txtfecha.setText(selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private List<String> obtenerListaMotivoAsignacion() {
        List<String>lista=new ArrayList<>();
        for(CatalogoMotivoAsignacion catalogoMotivoAsignacion:helper.obtenerCatalogoMotivoAsignacion()){
            lista.add(catalogoMotivoAsignacion.getDescripcion());
        }
        return lista;
    }

    private List<String> obtenerListaArticulo(){
        List<String> list=new ArrayList<>();
        for(Articulo art:articulos){
            list.add(art.getCodigoArticulo());
        }
        return list;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        AlertDialog.Builder mensaje=new AlertDialog.Builder(getContext());
        mensaje.setMessage("No hay articulos");
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.dismiss();
        Articulo articulo=null;
        try{
            if(response.optJSONArray("articulos")!=null){
                JSONArray json=response.optJSONArray("articulos");
                for(int i=0;i<json.length();i++){
                    articulo=new Articulo();
                    JSONObject jsonObject=null;
                    jsonObject=json.getJSONObject(i);
                    articulo.setCodigoArticulo(jsonObject.optString("CODIGOARTICULO"));
                    articulo.setCodTipoArticulo(jsonObject.optString("CODTIPOARTICULO"));
                    articulo.setFecha(jsonObject.getString("FECHAREGISTRO"));
                    articulo.setEstado(jsonObject.getInt("ESTADO"));
                    articulos.add(articulo);
                }
                ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        obtenerListaArticulo()
                );
                spinner=(Spinner) getView().findViewById(R.id.spinner_articulo);
                spinner.setAdapter(arrayAdapter);

            }
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"No se pudo establecer la conexion "+e,Toast.LENGTH_LONG).show();
        }

    }

    private void cargarWebService(){
        progreso=new MyProgressDialog(getContext());
        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ws/obtenerArticulos.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }
}
