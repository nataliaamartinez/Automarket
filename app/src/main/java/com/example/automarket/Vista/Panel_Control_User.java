package com.example.automarket.Vista;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;

public class Panel_Control_User extends AppCompatActivity {
    private TextView tvUsuario, tvAnuncio1;
    private Button btnAnadir, btnModificarCredenciales, btnCerrarSesion;
    private Button btnBorrarAnuncio1, btnModificarAnuncio1;

    private String usuario;  // ahora como String

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_control_user);

        // Obtener el usuario (String) desde el Intent
        usuario = getIntent().getStringExtra("usuario");

        // Inicializar vistas
        tvUsuario = findViewById(R.id.tvUsuario);
        btnAnadir = findViewById(R.id.btnAnadir);
        btnModificarCredenciales = findViewById(R.id.btnModificarCredenciales);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        tvAnuncio1 = findViewById(R.id.tvAnuncio1);
        btnBorrarAnuncio1 = findViewById(R.id.btnBorrarAnuncio1);
        btnModificarAnuncio1 = findViewById(R.id.btnModificarAnuncio1);

        if (usuario != null && !usuario.isEmpty()) {
            tvUsuario.setText("Usuario: " + usuario);
        } else {
            tvUsuario.setText("Usuario no identificado");
        }

        btnAnadir.setOnClickListener(v -> {
            Intent intent = new Intent(this, Categorias.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        });

        btnModificarCredenciales.setOnClickListener(v -> {
            Intent intent = new Intent(this, ModificarCredencialesActivity.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        });

        btnCerrarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(this, Inicio_Sesion.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnBorrarAnuncio1.setOnClickListener(v -> {
            Toast.makeText(this, "Anuncio 1 borrado", Toast.LENGTH_SHORT).show();
        });

        btnModificarAnuncio1.setOnClickListener(v -> {
            Intent intent = new Intent(this, ModificarAnuncioActivity.class);
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        });
    }
}