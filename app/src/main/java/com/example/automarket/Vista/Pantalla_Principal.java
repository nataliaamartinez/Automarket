package com.example.automarket.Vista;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;
import com.example.automarket.Utils;

import java.util.ArrayList;

public class Pantalla_Principal extends AppCompatActivity {

    private ImageButton btnUsuario;
    private ImageButton btnMensaje;
    private ImageButton btnFavoritos;
    private Button btnPublicar;
    private Button btnBuscar;
    private Button btnCoche;
    private Button btnFurgoneta;
    private Button btnMoto;
    private EditText etBuscar;
    private ImageButton btnFacebook;
    private ImageButton btnInstagram;
    private ImageButton btnTwitter;
    private ListView listViewCoches;
    private TextView tvNoCoches;
    private TextView tvAvisoLegal;
    private TextView tvContactanos;
    private TextView tvMapa;

    // Barra superior - Modificar Credenciales y Cerrar sesión
    private TextView tvNombreUsuario;


    // URL para listar los coches y furgonetas
    private static final String URL_LISTAR_COCHES = Utils.IP + "listar_coches.php";
    private static final String URL_LISTAR_FURGONETAS = Utils.IP + "listar_furgonetas.php";
    private static final String URL_PUBLICAR = Utils.IP + "publicar_coche.php";

    // Variables de la lista de coches y furgonetas
    private ArrayList<String> listaVehiculos;
    private ArrayAdapter<String> vehiculosAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        // Inicializar vistas
        btnUsuario=findViewById(R.id.btnUsuario);
        btnMensaje = findViewById(R.id.btnMensaje);
        btnFavoritos = findViewById(R.id.btnFavoritos);
        btnPublicar = findViewById(R.id.btnPublicar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnCoche = findViewById(R.id.btnCoche);
        btnFurgoneta = findViewById(R.id.btnFurgoneta);
        btnMoto = findViewById(R.id.btnMoto);
        etBuscar = findViewById(R.id.etBuscar);
        tvNoCoches = findViewById(R.id.tvNoCoches);
        listViewCoches = findViewById(R.id.listViewCoches);
        tvAvisoLegal = findViewById(R.id.tvAvisoLegal);
        tvContactanos = findViewById(R.id.tvContactanos);
        tvMapa = findViewById(R.id.tvMapa);

        // Barra superior - Modificar Credenciales y Cerrar sesión
        tvNombreUsuario = findViewById(R.id.tvUsuarioNombre);


        // Inicializar lista de vehículos (coches y furgonetas)
        listaVehiculos = new ArrayList<>();
        vehiculosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaVehiculos);
        listViewCoches.setAdapter(vehiculosAdapter);

        // Inicializar botones de redes sociales
        btnFacebook = findViewById(R.id.btnFacebook);
        btnInstagram = findViewById(R.id.btnInstagram);
        btnTwitter = findViewById(R.id.btnTwitter);



        btnUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pantalla_Principal.this, Panel_Control_User.class);
                startActivity(intent);

            }
        });

        // Resto de los listeners ya existentes para otros botones

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

        btnCoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pantalla_Principal.this, ListaCochesActivity.class);
                startActivity(intent);
            }

        });

        btnFurgoneta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pantalla_Principal.this, ListaFurgonetasActivity.class);
                startActivity(intent);
            }
        });
        btnMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pantalla_Principal.this, ListarMotoActivity.class);
                startActivity(intent);
            }
        });

        // Configurar redes sociales
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
            tvNombreUsuario.setText(usuario);  // Mostrar nombre de usuario en la barra superior
            Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();
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
