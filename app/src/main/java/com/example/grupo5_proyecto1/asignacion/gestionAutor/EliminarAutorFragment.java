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

/**
 * A simple {@link Fragment} subclass.
 */
public class EliminarAutorFragment extends Fragment {
    SQLite_Helper helper;
    Button btn_local,btn_serivce;
    EditText crl;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    MyProgressDialog progreso;


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        helper=new SQLite_Helper(activity);

    }

    public EliminarAutorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progreso=new MyProgressDialog(getContext());
        crl=(EditText) getView().findViewById(R.id.txt_autor_eliminar);
        btn_local=(Button) getView().findViewById(R.id.btn_autor_eliminar_local);
        request= Volley.newRequestQueue(getContext());
        btn_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helper.eliminarAutor(Double.parseDouble(crl.getText().toString()))){
                    Toast.makeText(getContext(),"Eliminado",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"No eliminado o NO existe",Toast.LENGTH_LONG).show();
                }

            }
        });
        btn_serivce=(Button) getView().findViewById(R.id.btn_autor_service_eliminar);
        btn_serivce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarAutorService();

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eliminar_autor, container, false);
    }

    private void eliminarAutorService(){
        try{
            progreso.show();
            String ip=getString(R.string.ip);
            String url=ip+"/ws/eliminarAutor.php?CORLN="+Double.parseDouble(crl.getText().toString().trim());
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progreso.dismiss();
                    JSONArray json=response.optJSONArray("autor");
                    JSONObject jsonObject=null;
                    try {
                        jsonObject=json.getJSONObject(0);
                        if(jsonObject.optString("resultado").equals("1")){
                            Toast.makeText(getContext(),"Elimnado con existo",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"No se puede eliminar por que no existe",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        progreso.dismiss();
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
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
