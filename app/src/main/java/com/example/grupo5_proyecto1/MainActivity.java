package com.example.grupo5_proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grupo5_proyecto1.asignacion.AsignacionMainActivity;
import com.example.grupo5_proyecto1.controller.SQLite_Helper;
import com.example.grupo5_proyecto1.prestamo.PrestamoActivity;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    SQLite_Helper helper=new SQLite_Helper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista=(ListView) findViewById(R.id.mainListView);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.main_interface));
        lista.setAdapter(arrayAdapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(MainActivity.this, PrestamoActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent=new Intent(MainActivity.this, AsignacionMainActivity.class);
                        startActivity(intent);
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        String texto;
                        texto=helper.llenarBaseDatos();
                        Toast.makeText(getApplicationContext(),texto,Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }
}
