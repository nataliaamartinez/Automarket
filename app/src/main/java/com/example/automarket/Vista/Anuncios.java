package com.example.automarket.Vista;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.automarket.R;

public class Anuncios extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anuncio);

        // Configuración del Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Cargar información en la pantalla
        ImageView imageViewAnuncio = findViewById(R.id.imageViewVehiculo);
        TextView textViewNombre = findViewById(R.id.textViewUserNombre);
        TextView textViewDescripcion = findViewById(R.id.textViewVehiculoInfo);

        //ejemplo:
        textViewNombre.setText("Juan Pérez");
        textViewDescripcion.setText("Se vende coche en excelente estado, color rojo, año 2020.");

        //  cargar la imagen desde recursos
        imageViewAnuncio.setImageResource(R.drawable.logo);
    }
}

