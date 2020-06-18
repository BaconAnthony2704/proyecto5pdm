package com.example.grupo5_proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.grupo5_proyecto1.models.Usuario;
import com.example.grupo5_proyecto1.services.MyProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>, Serializable {
    EditText usr,pwd;
    Button btnIngresar;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    MyProgressDialog progreso;
    Usuario usuario=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request= Volley.newRequestQueue(this);
        setContentView(R.layout.activity_login);
        usr=(EditText) findViewById(R.id.txtUsr);
        pwd=(EditText) findViewById(R.id.txtPwd);
        btnIngresar=(Button) findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.dismiss();
        Toast.makeText(getApplicationContext(),"No se pudo consultar "+error,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.dismiss();
        JSONArray json=response.optJSONArray("usuario");
        JSONObject jsonObject=null;
        try{
            jsonObject=json.getJSONObject(0);
            usuario=new Usuario();
            usuario.setIdUsuario(jsonObject.optString("ID_USUARIO"));
            usuario.setNomUsuario(jsonObject.optString("NOMUSUARIO"));
            usuario.setPassword(jsonObject.optString("CLAVE"));
        }catch (JSONException e){
            e.printStackTrace();
        }

        if(!usuario.getNomUsuario().equals("no registra") && !usuario.getPassword().equals("no registra")){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Usuario o Password no valido",Toast.LENGTH_LONG).show();
        }

    }

    private void cargarWebService(){
        try{
            progreso=new MyProgressDialog(this);
            progreso.show();
            String ip=getString(R.string.ip);
            String url=ip+"/ws/obtenerUsuario.php?usuario="+usr.getText().toString().trim()+"&password="+pwd.getText().toString().trim();
            jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            request.add(jsonObjectRequest);

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"No se puede enlazar con la BD remota: "+e,Toast.LENGTH_LONG).show();
            progreso.dismiss();
        }
    }
}
