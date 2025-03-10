package com.example.automarket.Vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;

public class Pantalla_Principal extends AppCompatActivity {

    private ImageButton btnMensaje;
    private ImageButton btnFavoritos;
    private Button btnPublicar;
    private Button btnBuscar;
    private Button btnCoche;
    private Button btnFurgoneta;
    private Button btnMoto;
    private EditText etBuscar;
    private ImageView tvLogo;
    private TextView tvQueBuscar;
    private TextView tvAnuncio1;
    private TextView tvAnuncio2;
    private TextView tvAvisoLegal;
    private TextView tvContactanos;
    private TextView tvMapa;
    private TextView tvCopyright;
    private ImageButton btnFacebook;
    private ImageButton btnInstagram;
    private ImageButton btnTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.pantalla_principal);

            // Inicializar vistas
            btnMensaje = findViewById(R.id.btnMensaje);
            btnFavoritos = findViewById(R.id.btnFavoritos);
            btnPublicar = findViewById(R.id.btnPublicar);
            btnBuscar = findViewById(R.id.btnBuscar);
            btnCoche = findViewById(R.id.btnCoche);
            btnFurgoneta = findViewById(R.id.btnFurgoneta);
            btnMoto = findViewById(R.id.btnMoto);
            etBuscar = findViewById(R.id.etBuscar);
            tvLogo = findViewById(R.id.tvLogo);
            tvQueBuscar = findViewById(R.id.tvQueBuscar);
            tvAnuncio1 = findViewById(R.id.tvAnuncio1);
            tvAnuncio2 = findViewById(R.id.tvAnuncio2);
            tvAvisoLegal = findViewById(R.id.tvAvisoLegal);
            tvContactanos = findViewById(R.id.tvContactanos);
            tvMapa = findViewById(R.id.tvMapa);
            tvCopyright = findViewById(R.id.tvCopyright);

            // Inicializar botones de redes sociales
            btnFacebook = findViewById(R.id.btnFacebook);
            btnInstagram = findViewById(R.id.btnInstagram);
            btnTwitter = findViewById(R.id.btnTwitter);

            // Configurar listeners
            btnMensaje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(Pantalla_Principal.this,
                                "No se encontró una aplicación de mensajes",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

            btnFavoritos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Pantalla_Principal.this, Favoritos.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(Pantalla_Principal.this,
                                "Error al abrir favoritos",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

            btnPublicar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Pantalla_Principal.this, Categorias.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(Pantalla_Principal.this,
                                "Error al abrir pantalla de publicación",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

            btnBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String busqueda = etBuscar.getText().toString();
                    Toast.makeText(Pantalla_Principal.this, "Buscando: " + busqueda, Toast.LENGTH_SHORT).show();
                }
            });

            // Configurar el click listener para Aviso Legal
            tvAvisoLegal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Pantalla_Principal.this, Aviso_Legal.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(Pantalla_Principal.this,
                                "Error al abrir el aviso legal",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

            // Configurar el click listener para Contáctanos
            tvContactanos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String phoneNumber = "tel:+34666777888"; // Número de ejemplo
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(phoneNumber));
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(Pantalla_Principal.this,
                                "No se pudo abrir la aplicación de llamadas",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

            // Configurar listeners para redes sociales
            btnFacebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirRedSocial("https://www.facebook.com/automarket");
                }
            });

            btnInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirRedSocial("https://www.instagram.com/automarket");
                }
            });

            btnTwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirRedSocial("https://www.twitter.com/automarket");
                }
            });

            // Configurar el click listener para Mapa
            tvMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // Dirección de ejemplo: IES Juan de la Cierva
                        String direccion = "Calle de Embajadores, 181, 28045 Madrid";
                        String uri = "geo:0,0?q=" + Uri.encode(direccion);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");

                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        } else {
                            // Si Google Maps no está instalado, abrir en el navegador
                            String mapsUrl = "https://www.google.com/maps/search/?api=1&query="
                                    + Uri.encode(direccion);
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl));
                            startActivity(browserIntent);
                        }
                    } catch (Exception e) {
                        Toast.makeText(Pantalla_Principal.this,
                                "No se pudo abrir el mapa",
                                Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });

            // Recibir el usuario si fue pasado
            String usuario = getIntent().getStringExtra("usuario");
            if (usuario != null && !usuario.isEmpty()) {
                Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al iniciar la pantalla principal", Toast.LENGTH_LONG).show();
            // Volver a la pantalla de inicio de sesión
            Intent intent = new Intent(this, Inicio_Sesion.class);
            startActivity(intent);
            finish();
        }
    }

    private void abrirRedSocial(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this,
                    "No se pudo abrir la red social",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
