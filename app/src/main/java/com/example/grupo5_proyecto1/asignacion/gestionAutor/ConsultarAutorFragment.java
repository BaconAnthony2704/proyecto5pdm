package com.example.grupo5_proyecto1.asignacion.gestionAutor;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.grupo5_proyecto1.R;
import com.example.grupo5_proyecto1.controller.SQLite_Helper;
import com.example.grupo5_proyecto1.models.Autores;
import com.example.grupo5_proyecto1.services.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ConsultarAutorFragment extends Fragment implements Serializable, Response.Listener<JSONObject>,Response.ErrorListener {
    EditText autor;
    TextView nombre,correlativo;
    Button btnLocal,btnService;
    SQLite_Helper helper;
    MyProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Autores autores;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        helper=new SQLite_Helper(activity);

    }


    public ConsultarAutorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progreso=new MyProgressDialog(getContext());
        autor=(EditText) getView().findViewById(R.id.txtConsultarAutor);
        nombre=(TextView)getView().findViewById(R.id.textViewNombreAutor);
        correlativo=(TextView) getView().findViewById(R.id.textViewCorlin);
        btnLocal=(Button) getView().findViewById(R.id.btnConsultarAutorLocal);
        btnService=(Button)getView().findViewById(R.id.btnConsultaAutorService);
        request= Volley.newRequestQueue(getContext());
        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Autores autores=helper.obtenerAutor(autor.getText().toString());
                correlativo.setText(String.valueOf(autores.getCorlin()));
                nombre.setText(autores.getNombre());
            }
        });

        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consultar_autor, container, false);
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
        progreso.dismiss();
        JSONArray json=response.optJSONArray("autor");
        JSONObject jsonObject=null;
        try {
            autores =new Autores();
            jsonObject=json.getJSONObject(0);
            autores.setCodigoArticulo(jsonObject.optString("CODIGOARTICULO"));
            autores.setCorlin(Double.parseDouble(jsonObject.optString("CORLN")));
            autores.setNombre(jsonObject.optString("NOOMBRE"));
            //Setear los valores
            correlativo.setText(String.valueOf(autores.getCorlin()));
            nombre.setText(autores.getNombre());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void cargarWebService(){
        try{

            //progreso.drawableHotspotChanged(0,0);
            progreso.show();
            String ip=getString(R.string.ip);
            String url=ip+"/ws/obtenerAutor.php?corlin="+Double.parseDouble(autor.getText().toString().trim());
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);
        }catch (Exception e){
            Toast.makeText(getContext(),"No se pudo enlazar la BD "+e,Toast.LENGTH_LONG).show();
            progreso.dismiss();
        }
    }
}
