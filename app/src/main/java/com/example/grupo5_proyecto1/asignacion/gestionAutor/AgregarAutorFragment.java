package com.example.grupo5_proyecto1.asignacion.gestionAutor;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.grupo5_proyecto1.controller.SQLite_Helper;
import com.example.grupo5_proyecto1.models.Articulo;
import com.example.grupo5_proyecto1.models.Autores;
import com.example.grupo5_proyecto1.services.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgregarAutorFragment extends Fragment implements Serializable, Response.ErrorListener,Response.Listener<JSONObject> {
    Spinner spinner;
    SQLite_Helper helper;
    Button btnGuardarLocal,btnGuardarService;
    EditText corln,nombre;
    MyProgressDialog progreso;
    List<Articulo> articulos;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        helper=new SQLite_Helper(activity);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        articulos=new ArrayList<>();
        progreso=new MyProgressDialog(getContext());
        request= Volley.newRequestQueue(getContext());
        btnGuardarLocal=(Button) getView().findViewById(R.id.btn_ingresar_autor);
        corln=(EditText) getView().findViewById(R.id.txtCorl_autor_ingresar);
        nombre=(EditText) getView().findViewById(R.id.txtAutor_ingresar);
        cargarWebSerivceArticulo();

        btnGuardarLocal=(Button) getView().findViewById(R.id.btn_ingresar_autor);
        btnGuardarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Autores autores=new Autores();
                autores.setCodigoArticulo(spinner.getSelectedItem().toString());
                autores.setCorlin(Double.parseDouble(corln.getText().toString()));
                autores.setNombre(nombre.getText().toString());
                String texto=helper.insertar(autores);
                Toast.makeText(getContext(),texto,Toast.LENGTH_LONG).show();
            }
        });

        btnGuardarService=(Button) getView().findViewById(R.id.btn_ingresar_autor_service);
        btnGuardarService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebServiceAutor();
            }
        });
    }

    public AgregarAutorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_agregar_autor, container, false);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        Toast.makeText(getContext(),"No se pudo consultar "+error,
                Toast.LENGTH_LONG).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Articulo articulo=null;
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
                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        obtenerListaArticulo()
                );
                spinner=(Spinner) getView().findViewById(R.id.spinner_autor_art);
                spinner.setAdapter(arrayAdapter);
                //Catalogo motivo asignacion

            }
        }catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(getContext(),"No se pudo establecer la conexion "+e,Toast.LENGTH_LONG).show();
        }
    }
    private void cargarWebSerivceArticulo(){
        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ws/obtenerArticulos.php";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    private void cargarWebServiceAutor(){

        progreso.show();
        String ip=getString(R.string.ip);
        String url=ip+"/ws/insertarAutor.php?CODIGOARTICULO="+spinner.getSelectedItem().toString()+"&CORLN="+corln.getText().toString()+"&NOOMBRE="+nombre.getText().toString();
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.dismiss();
                try {
                    JSONArray json = response.optJSONArray("autores");
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(0);
                    String codigo = jsonObject.optString("CODIGOARTICULO");
                    if (!codigo.equals("NO registra")) {
                        Toast.makeText(getContext(), "Autor ingresado con exito", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "No se pudo ingresar auto, verifique integridad", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.dismiss();
                Toast.makeText(getContext(),"No se pudo consultar "+error,
                        Toast.LENGTH_LONG).show();
                Log.i("ERROR", error.toString());
            }
        });
        request.add(jsonObjectRequest);
    }

    private List<String> obtenerListaArticulo(){
        List<String> list=new ArrayList<>();
        for(Articulo art:articulos){
            list.add(art.getCodigoArticulo());
        }
        return list;
    }


}
