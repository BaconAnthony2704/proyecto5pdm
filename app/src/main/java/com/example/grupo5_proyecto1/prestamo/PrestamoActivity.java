package com.example.grupo5_proyecto1.prestamo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.grupo5_proyecto1.R;

import com.example.grupo5_proyecto1.prestamo.fragment.CrearPrestamoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PrestamoActivity extends AppCompatActivity {
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamo);
        mostrarFragmentSeleccionado(new CrearPrestamoFragment());

        toolbar=(Toolbar) findViewById(R.id.toolbar_prestamo);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationPrestamo);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_create:
                        mostrarFragmentSeleccionado(new CrearPrestamoFragment());
                    case R.id.menu_search:
                        Toast.makeText(PrestamoActivity.this, "menu_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_devolver:
                        Toast.makeText(PrestamoActivity.this, "menu_devolver", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    private void mostrarFragmentSeleccionado(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container_prestamo,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}

