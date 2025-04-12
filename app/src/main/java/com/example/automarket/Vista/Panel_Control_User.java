package com.example.automarket.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;

public class Panel_Control_User extends AppCompatActivity {
    private TextView tvUsuario;
    private TextView tvNombreUsuario;
    private Button btnAnadir;
    private Button btnModificarCredenciales;
    private Button btnCerrarSesion;
    private Button btnBorrarAnuncio1;
    private Button btnModificarAnuncio1;
    private TextView tvAvisoLegal;
    private TextView tvContactanos;
    private TextView tvRedesSociales;
    private TextView tvMapa;
    private TextView tvCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_control_user);

        // Inicializar vistas
        tvUsuario = findViewById(R.id.tvUsuario);
        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        btnAnadir = findViewById(R.id.btnAnadir);
        btnModificarCredenciales = findViewById(R.id.btnModificarCredenciales);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        btnBorrarAnuncio1 = findViewById(R.id.btnBorrarAnuncio1);
        btnModificarAnuncio1 = findViewById(R.id.btnModificarAnuncio1);
        tvAvisoLegal = findViewById(R.id.tvAvisoLegal);
        tvContactanos = findViewById(R.id.tvContactanos);
        tvRedesSociales = findViewById(R.id.tvRedesSociales);
        tvMapa = findViewById(R.id.tvMapa);
        tvCopyright = findViewById(R.id.tvCopyright);

        // Obtener el nombre de usuario de la actividad anterior
        String nombreUsuario = getIntent().getStringExtra("usuario");
        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            tvNombreUsuario.setText(nombreUsuario);
        }

        // Configurar listeners
        btnModificarCredenciales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Panel_Control_User.this, ModificarCredencialesActivity.class);
                intent.putExtra("usuario", nombreUsuario);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Panel_Control_User.this, Inicio_Sesion.class);
                startActivity(intent);
                finish();
            }
        });

        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Panel_Control_User.this, Categorias.class);
                startActivity(intent);
            }
        });

        btnBorrarAnuncio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Panel_Control_User.this, "Anuncio borrado", Toast.LENGTH_SHORT).show();
            }
        });

        btnModificarAnuncio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Panel_Control_User.this, "Modificar anuncio", Toast.LENGTH_SHORT).show();
            }
        });

        tvAvisoLegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Panel_Control_User.this, Aviso_Legal.class);
                startActivity(intent);
            }
        });

        tvContactanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String phoneNumber = "tel:+34666777888";
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(android.net.Uri.parse(phoneNumber));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(Panel_Control_User.this,
                            "No se pudo abrir la aplicación de llamadas",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String direccion = "Calle de Embajadores, 181, 28045 Madrid";
                    String uri = "geo:0,0?q=" + android.net.Uri.encode(direccion);
                    Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        String mapsUrl = "https://www.google.com/maps/search/?api=1&query="
                                + android.net.Uri.encode(direccion);
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(mapsUrl));
                        startActivity(browserIntent);
                    }
                } catch (Exception e) {
                    Toast.makeText(Panel_Control_User.this,
                            "No se pudo abrir el mapa",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
