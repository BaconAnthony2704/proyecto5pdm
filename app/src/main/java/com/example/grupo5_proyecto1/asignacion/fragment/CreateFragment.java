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
import com.example.grupo5_proyecto1.models.CatalogoTipoLibro;
import com.example.grupo5_proyecto1.models.DetalleLibro;
import com.example.grupo5_proyecto1.services.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ListResourceBundle;

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
    Button btnGuardar,btnGuardarService;
    MyProgressDialog progreso;
    List<Articulo> articulos;
    List<CatalogoMotivoAsignacion> catalogos;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    List <DetalleLibro> detallelibro;
    List <CatalogoTipoLibro> catalogolibro;


    int idCatalogoMovAsignacion;
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
        catalogos=new ArrayList<>();
        //ALfredo
        detallelibro=new ArrayList<>();
        catalogolibro=new ArrayList<>();
        ///////////
        progreso=new MyProgressDialog(getContext());
        request= Volley.newRequestQueue(getContext());
        toolbar=(Toolbar)getView().findViewById(R.id.toolbar_crear_asignacion);
        btnGuardarService=(Button)getView().findViewById(R.id.btnWebServiceAsginacion);
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

        cargarWebServiceArticulo();
        cargarWebServiceCatalogoMotivoAsignacion();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

//        spinner2=(Spinner) getView().findViewById(R.id.spinner_motivo_asignacion);
//        ArrayAdapter<String>arrayAdapterMotivo=new ArrayAdapter<>(
//                getContext(),
//                android.R.layout.simple_list_item_1,
//                obtenerListaMotivoAsignacion()
//        );
//        spinner2.setAdapter(arrayAdapterMotivo);
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
        btnGuardarService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebSerivceIdMotivoAsignacion(spinner2.getSelectedItem().toString());

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
//        for(CatalogoMotivoAsignacion catalogoMotivoAsignacion:helper.obtenerCatalogoMotivoAsignacion()){
//            lista.add(catalogoMotivoAsignacion.getDescripcion());
//        }
        for(CatalogoMotivoAsignacion catalogoMotivoAsignacion:catalogos){
            lista.add(catalogoMotivoAsignacion.getDescripcion());
        }
        return lista;
    }
//Alfredo
private List<String> obtenerCatalogoTipoLibro() {
    List<String>lista=new ArrayList<>();

    for(CatalogoTipoLibro catalogoTipoLibro:catalogolibro){
        lista.add(catalogoTipoLibro.getDescripcion());
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
//Alfredo
private List<String> obtenerDetalleLibro(){
    List<String> list=new ArrayList<>();
    for(DetalleLibro lib: detallelibro){
        list.add(lib.getCodigoArticulo());
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

        Articulo articulo=null;
        CatalogoMotivoAsignacion catalogoMotivoAsignacion=null;
        DetalleLibro detallelib=null;
        CatalogoTipoLibro catalogoTipoLib=null;
        try{
            if(response.optJSONArray("articulos")!=null){
                progreso.dismiss();
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
                //Catalogo motivo asignacion

            }

            if(response.optJSONArray("catmovasignacion")!=null){
                progreso.dismiss();
                JSONArray json2=response.optJSONArray("catmovasignacion");
                for(int i=0;i<json2.length();i++){
                    catalogoMotivoAsignacion=new CatalogoMotivoAsignacion();
                    JSONObject jsonObject=null;
                    jsonObject=json2.getJSONObject(i);
                    catalogoMotivoAsignacion.setCodMotivoAsignacion(jsonObject.optInt("CODMOTIVOASGINACION"));
                    catalogoMotivoAsignacion.setDescripcion(jsonObject.optString("DESCRIPCION"));
                    catalogos.add(catalogoMotivoAsignacion);
                }
                spinner2=(Spinner) getView().findViewById(R.id.spinner_motivo_asignacion);
                ArrayAdapter<String>arrayAdapterMotivo=new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        obtenerListaMotivoAsignacion()
                );
                spinner2.setAdapter(arrayAdapterMotivo);
            }
//Alfredo
            if(response.optJSONArray("detallelib")!=null){
                progreso.dismiss();
                JSONArray json=response.optJSONArray("detallelib");
                for(int i=0;i<json.length();i++){
                    detallelib = new DetalleLibro();
                    JSONObject jsonObject=null;
                    jsonObject=json.getJSONObject(i);
                    detallelib.setCodigoArticulo(jsonObject.optString("CODIGOARTICULO"));
                    detallelib.setIsbn(jsonObject.optString("ISBN"));
                    detallelib.setIdioma(jsonObject.optString("IDIOMA"));
                    detallelib.setCodTipoLibro(jsonObject.getInt("CODTIPOLIBRO"));
                    detallelib.setTitulo(jsonObject.getString("TITULO"));
                    detallelibro.add(detallelib);
                }
                ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        obtenerDetalleLibro()
                );
                spinner=(Spinner) getView().findViewById(R.id.spinner_articulo);
                spinner.setAdapter(arrayAdapter);

            if(response.optJSONArray("catTipoLibro")!=null){
                    progreso.dismiss();
                    JSONArray json2=response.optJSONArray("catTipoLibro");
                    for(int i=0;i<json2.length();i++){
                        catalogoTipoLib=new  CatalogoTipoLibro();
                        JSONObject jsonObject=null;
                        jsonObject=json2.getJSONObject(i);
                        catalogoTipoLib.setCodTipoLibro(jsonObject.optInt("CODTIPOLIBRO"));
                        catalogoTipoLib.setDescripcion(jsonObject.optString("DESCRIPCION"));
                        catalogolibro.add(catalogoTipoLib);
                    }
                    spinner2=(Spinner) getView().findViewById(R.id.spinner_motivo_asignacion);
                    ArrayAdapter<String>arrayAdapterMotivo=new ArrayAdapter<>(
                            getContext(),
                            android.R.layout.simple_list_item_1,
                            obtenerListaMotivoAsignacion()
                    );
                    spinner2.setAdapter(arrayAdapterMotivo);
                }
      ////////////

            }
            progreso.dismiss();
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(),"No se pudo establecer la conexion "+e,Toast.LENGTH_LONG).show();
        }

    }
    //Alfredo
    private void cargarWebServiceDetalleLibro(){

        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ws/obtenerDetalleLibro.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    private void cargarWebServiceCatalogoTipoLibro(){
        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ws/obtenerCatTipoLibro.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    ///////////
    private void cargarWebServiceArticulo(){

        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ws/obtenerArticulos.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);



    }

    private void cargarWebSerivceIdMotivoAsignacion(String descripcion){
        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ws/obtenerIdCatMovAsignacion.php?DESCRIPCION="+descripcion;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.optJSONArray("catmovasig") != null) {
                        progreso.dismiss();
                        JSONArray json3 = response.optJSONArray("catmovasig");
                        JSONObject jsonObject = null;
                        for (int i = 0; i < json3.length(); i++) {
                            jsonObject = json3.getJSONObject(i);
                            idCatalogoMovAsignacion = jsonObject.getInt("CODMOTIVOASGINACION");
                        }
                        Asignacion asignacion=new Asignacion();
                        asignacion.setCodigoArticulo(spinner.getSelectedItem().toString());
                        asignacion.setCodMotivoAsignacion(idCatalogoMovAsignacion);
                        asignacion.setDocente(txtDocente.getText().toString());
                        asignacion.setDescripcion(txtdescripcion.getText().toString());
                        asignacion.setFechaAsignacion(txtfecha.getText().toString());
                        cargarWebServiceIngresarAsginacion(asignacion);
                        Toast.makeText(getContext(),"Guardado con exito",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.dismiss();
            }
        });
        request.add(jsonObjectRequest);

    }
    private void cargarWebServiceCatalogoMotivoAsignacion(){
        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ws/obtenerCatMovAsignaciones.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    private void cargarWebServiceIngresarAsginacion(Asignacion asignacion){
        try{
            progreso.show();
            String ip=getString(R.string.ip);
            String url=ip+"/ws/insertarAsignacion.php?CODMOTIVOASGINACION="+asignacion.getCodMotivoAsignacion()+
                    "&CODIGOARTICULO="+asignacion.getCodigoArticulo()+"&DOCENTE="+asignacion.getDocente()+
                    "&DESCRIPCION="+asignacion.getDescripcion();
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);
        }catch (Exception e){
            Toast.makeText(getContext()," No se pudo enlazar con BD remota: "+e,Toast.LENGTH_LONG).show();
        }
    }

}
