package com.example.automarket.Vista;

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
    // Elementos de la barra superior
    private TextView tvUsuario;
    private Button btnAnadir;
    private Button btnModificarCredenciales;
    private Button btnCerrarSesion;
    // Elementos de los anuncios
    private Button btnBorrarAnuncio1, btnModificarAnuncio1;
    private ImageView imageAnuncio1;
    private TextView tvAnuncio1;

    // Elementos del footer
    private TextView tvAvisoLegal, tvContactanos, tvMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_control_user);

        // Inicializar vistas de la barra superior
        tvUsuario = findViewById(R.id.tvUsuario);
        btnAnadir = findViewById(R.id.btnAnadir);
        btnModificarCredenciales = findViewById(R.id.btnModificarCredenciales);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Inicializar vistas de los anuncios
        imageAnuncio1 = findViewById(R.id.imageAnuncio1);
        tvAnuncio1 = findViewById(R.id.tvAnuncio1);
        btnBorrarAnuncio1 = findViewById(R.id.btnBorrarAnuncio1);
        btnModificarAnuncio1 = findViewById(R.id.btnModificarAnuncio1);

        // Inicializar vistas del footer
        tvAvisoLegal = findViewById(R.id.tvAvisoLegal);
        tvContactanos = findViewById(R.id.tvContactanos);
        tvMapa = findViewById(R.id.tvMapa);

        // Configurar el botón de "Añadir +"
        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes poner la acción que deseas cuando se haga clic en "Añadir +"
                Intent intent = new Intent(Panel_Control_User.this, Categorias.class);  // Suponiendo que tienes una actividad llamada AnadirAnuncioActivity
                startActivity(intent);
            }
        });
        btnModificarCredenciales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Panel_Control_User.this, ModificarCredencialesActivity.class);
                startActivity(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Panel_Control_User.this, Inicio_Sesion.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // Cierra esta actividad para evitar volver con el botón "Atrás"
            }
        });
        // Configurar el botón de "Borrar" en el Anuncio 1
        btnBorrarAnuncio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para borrar el anuncio 1
                Toast.makeText(Panel_Control_User.this, "Anuncio 1 borrado", Toast.LENGTH_SHORT).show();
                // Aquí deberías borrar el anuncio en tu base de datos o lista
            }
        });

        // Configurar el botón de "Modificar" en el Anuncio 1
        btnModificarAnuncio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para modificar el anuncio 1
                Intent intent = new Intent(Panel_Control_User.this, ModificarAnuncioActivity.class);  // Suponiendo que tienes una actividad llamada ModificarAnuncioActivity
                startActivity(intent);
            }
        });

        // Configurar el clic en "Aviso Legal"
        tvAvisoLegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para abrir el Aviso Legal
                Intent intent = new Intent(Panel_Control_User.this, Aviso_Legal.class);  // Suponiendo que tienes una actividad llamada AvisoLegalActivity
                startActivity(intent);
            }
        });

        // Configurar el clic en "Contáctanos"
        tvContactanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para abrir la aplicación de llamadas con un número
                String phoneNumber = "tel:+34666777888";  // Número de ejemplo
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phoneNumber));
                startActivity(intent);
            }
        });

        // Configurar el clic en "Mapa"
        tvMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción para abrir el mapa con una ubicación
                String direccion = "Calle de Embajadores, 181, 28045 Madrid";  // Dirección de ejemplo
                String uri = "geo:0,0?q=" + Uri.encode(direccion);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // Si Google Maps no está instalado, abrir en el navegador
                    String mapsUrl = "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(direccion);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl));
                    startActivity(browserIntent);
                }
            }
        });
    }
}

