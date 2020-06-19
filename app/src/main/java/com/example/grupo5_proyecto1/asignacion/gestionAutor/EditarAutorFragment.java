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
import com.example.grupo5_proyecto1.models.Asignacion;
import com.example.grupo5_proyecto1.models.Autores;
import com.example.grupo5_proyecto1.services.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditarAutorFragment extends Fragment {
    Button buscarLocal,buscarService,btnEditarLocal,btnEditarService;
    EditText crln,autor;
    Spinner spinner;
    SQLite_Helper helper;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    MyProgressDialog progreso;
    ArrayAdapter<String> arrayAdapterService;
    String art;

    public EditarAutorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        helper=new SQLite_Helper(activity);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progreso=new MyProgressDialog(getContext());
        request= Volley.newRequestQueue(getContext());
        crln=(EditText) getView().findViewById(R.id.txtCorl_autor_editarr);
        autor=(EditText) getView().findViewById(R.id.txtAutor_editar);
        spinner=(Spinner) getView().findViewById(R.id.spinner_autor_art_editar);
        buscarLocal=(Button) getView().findViewById(R.id.btn_buscar_autor_editar);
        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                obtenerListaArticulo()
        );
        buscarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valor=-1;
                Autores autores=helper.obtenerAutor(crln.getText().toString());
                autor.setText(autores.getNombre());
                spinner.setAdapter(arrayAdapter);
                for(int i=0;i<helper.obtenerArticulo().size();i++){
                    if(autores.getCodigoArticulo().equals(helper.obtenerArticulo().get(i).getCodigoArticulo())){
                        valor=i;
                    }
                }
                spinner.setSelection(valor);
            }
        });
        btnEditarLocal=(Button) getView().findViewById(R.id.btn_editar_autor);
        btnEditarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Autores autores=new Autores();
                autores.setCorlin(Double.parseDouble(crln.getText().toString()));
                autores.setNombre(autor.getText().toString());
                autores.setCodigoArticulo(spinner.getSelectedItem().toString());
                if(helper.actualizarAutor(autores)){
                    Toast.makeText(getContext(),"Actualizado con exito",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"Problemas al actualizar",Toast.LENGTH_LONG).show();
                }
                limipiar();
            }
        });


        buscarService=(Button) getView().findViewById(R.id.btn_buscar_autor_editar_service);
        buscarService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarAutorService();
            }
        });

        btnEditarService=(Button) getView().findViewById(R.id.btn_editar_autor_service);
        btnEditarService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarAutorService();
                limipiar();
            }
        });

    }

    private void limipiar() {
        crln.setText("");
        autor.setText("");
        spinner.setAdapter(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_autor, container, false);
    }

    private List<String> obtenerListaArticulo(){
        List<String> list=new ArrayList<>();
        for(Articulo art:helper.obtenerArticulo()){
            list.add(art.getCodigoArticulo());
        }
        return list;
    }

    private List<String> obtenerListaArticuloService(List<Articulo> articulos){
        List<String> list=new ArrayList<>();
        for(Articulo art:articulos){
            list.add(art.getCodigoArticulo());
        }
        return list;
    }

    private void buscarAutorService(){
        try{
            progreso.show();
            String ip=getString(R.string.ip);
            String url=ip+"/ws/obtenerAutor.php?corlin="+Double.parseDouble(crln.getText().toString().trim());
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progreso.dismiss();
                    JSONArray json=response.optJSONArray("autor");
                    JSONObject jsonObject=null;
                    try {
                        Autores autores =new Autores();
                        jsonObject=json.getJSONObject(0);
                        autores.setCodigoArticulo(jsonObject.optString("CODIGOARTICULO"));
                        autores.setCorlin(Double.parseDouble(jsonObject.optString("CORLN")));
                        autores.setNombre(jsonObject.optString("NOOMBRE"));
                        //Setear los valores
                        art=jsonObject.optString("CODIGOARTICULO");
                        autor.setText(autores.getNombre());
                        cargarArticulos();


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
        }catch (Exception e){
            Toast.makeText(getContext(),"No se pudo conectar con la BD remota",Toast.LENGTH_LONG).show();
        }
    }


    private void cargarArticulos(){
        final List<Articulo> articulosService=new ArrayList<>();

        try{
            progreso.show();
            String ip=getString(R.string.ip);
            String url=ip+"/ws/obtenerArticulos.php";
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.optJSONArray("articulos") != null) {
                        progreso.dismiss();
                        JSONArray json3 = response.optJSONArray("articulos");
                        JSONObject jsonObject = null;
                        for (int i = 0; i < json3.length(); i++) {
                            try {
                                jsonObject = json3.getJSONObject(i);
                                Articulo articulo=new Articulo();
                                articulo.setCodigoArticulo(jsonObject.optString("CODIGOARTICULO"));
                                articulo.setCodTipoArticulo(jsonObject.optString("CODTIPOARTICULO"));
                                articulo.setFecha(jsonObject.optString("FECHAREGISTRO"));
                                articulo.setEstado(jsonObject.optInt("ESTADO"));
                                articulosService.add(articulo);


                            } catch (JSONException e) {
                                progreso.dismiss();
                                e.printStackTrace();
                            }
                        }
                        arrayAdapterService=new ArrayAdapter<String>(
                                getContext(),
                                android.R.layout.simple_list_item_1,
                                obtenerListaArticuloService(articulosService)
                        );
                        spinner.setAdapter(arrayAdapterService);

                        for(int i=0;i<articulosService.size();i++){
                            if(art.equals(articulosService.get(i).getCodigoArticulo())){
                                spinner.setSelection(i);
                            }
                        }

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
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void editarAutorService(){
        try{
            progreso.show();
            String ip=getString(R.string.ip);
            String url=ip+"/ws/editarAutor.php?CODIGOARTICULO="+spinner.getSelectedItem().toString()+"&CORLN="+
                    Integer.parseInt(crln.getText().toString())+"&AUTOR="+autor.getText().toString();
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progreso.dismiss();
                    Toast.makeText(getContext(),"No se pudo actualizar "+response,Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progreso.dismiss();
                    Toast.makeText(getContext(),"Actualizado con exito",Toast.LENGTH_LONG).show();
                }
            });
            request.add(jsonObjectRequest);
        }catch (Exception e){

        }
    }
}
