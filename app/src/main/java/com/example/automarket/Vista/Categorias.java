package com.example.automarket.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;

public class Categorias extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categorias);

        Button buttonCoche = findViewById(R.id.buttonCoche);
        Button buttonMoto = findViewById(R.id.buttonMoto);
        Button buttonFurgoneta = findViewById(R.id.buttonFurgoneta);

        // Acciones al presionar el botón Coche
        buttonCoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a una actividad o mostrar información relacionada con Coche
                Intent intent = new Intent(Categorias.this, Publicar_Coche.class);
                startActivity(intent);
            }
        });

        // Acciones al presionar el botón Moto
        buttonMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a una actividad o mostrar información relacionada con Moto
                Intent intent = new Intent(Categorias.this, Publicar_Moto.class);
                startActivity(intent);
            }
        });

        // Acciones al presionar el botón Furgoneta
        buttonFurgoneta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirigir a una actividad o mostrar información relacionada con Furgoneta
                Intent intent = new Intent(Categorias.this, Publicar_Furgoneta.class);
                startActivity(intent);
            }
        });
    }
}
