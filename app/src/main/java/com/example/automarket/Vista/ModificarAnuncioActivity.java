package com.example.automarket.Vista;

import android.os.Bundle;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;

public class ModificarAnuncioActivity extends AppCompatActivity {
    private EditText etAnuncio;
    private String anuncioOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_anuncio);

        // Inicializar vistas
        etAnuncio = findViewById(R.id.etAnuncio);

        // Obtener el anuncio pasado desde la actividad anterior
        anuncioOriginal = getIntent().getStringExtra("anuncio");

        // Si hay un anuncio pasado, mostrarlo en el campo de texto
        if (anuncioOriginal != null && !anuncioOriginal.isEmpty()) {
            etAnuncio.setText(anuncioOriginal);
        }

        // Configurar el listener para el botón de guardar
        findViewById(R.id.btnGuardarAnuncio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nuevoAnuncio = etAnuncio.getText().toString();

                if (nuevoAnuncio.isEmpty()) {
                    Toast.makeText(ModificarAnuncioActivity.this, "El anuncio no puede estar vacío", Toast.LENGTH_SHORT).show();
                } else {
                    // Aquí se puede guardar el nuevo anuncio o modificar el existente
                    // Por ahora, solo mostramos un mensaje
                    Toast.makeText(ModificarAnuncioActivity.this, "Anuncio guardado", Toast.LENGTH_SHORT).show();
                    finish();  // Cerrar la actividad
                }
            }
        });
    }
}