package com.example.grupo5_proyecto1.asignacion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.grupo5_proyecto1.R;
import com.example.grupo5_proyecto1.asignacion.fragment.ConsultaFragment;
import com.example.grupo5_proyecto1.asignacion.fragment.CreateFragment;
import com.example.grupo5_proyecto1.asignacion.fragment.OpcionesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AsignacionMainActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion_main);

        mostrarFragmentSeleccionado(new ConsultaFragment());
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottomNavigationAsignacion);
        toolbar=(Toolbar) findViewById(R.id.toolbar_asignacion);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.menu_create){
                    mostrarFragmentSeleccionado(new CreateFragment());
                }
                if(item.getItemId()==R.id.menu_search){
                    mostrarFragmentSeleccionado(new ConsultaFragment());
                }
                if(item.getItemId()==R.id.menu_configurar){
                    mostrarFragmentSeleccionado(new OpcionesFragment());
                }
                return true;
            }
        });
    }
    private void mostrarFragmentSeleccionado(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_asignacion,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
