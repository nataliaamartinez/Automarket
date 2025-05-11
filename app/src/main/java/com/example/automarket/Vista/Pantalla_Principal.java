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

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;
import com.example.automarket.Utils;

import java.util.ArrayList;

public class Pantalla_Principal extends AppCompatActivity {

    private ImageButton btnUsuario, btnMensaje, btnFavoritos;
    private Button btnPublicar, btnBuscar, btnCoche, btnFurgoneta, btnMoto;
    private EditText etBuscar;
    private ImageButton btnFacebook, btnInstagram, btnTwitter;
    private ListView listViewCoches;
    private TextView tvNoCoches, tvAvisoLegal, tvContactanos, tvMapa, tvNombreUsuario;

    private static final String URL_LISTAR_COCHES = Utils.IP + "listar_coches.php";
    private static final String URL_LISTAR_FURGONETAS = Utils.IP + "listar_furgonetas.php";
    private static final String URL_PUBLICAR = Utils.IP + "publicar_coche.php";

    private ArrayList<String> listaVehiculos;
    private ArrayAdapter<String> vehiculosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        // Inicializar vistas
        inicializarVistas();

        // Inicializar adaptador de lista
        listaVehiculos = new ArrayList<>();
        vehiculosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaVehiculos);
        listViewCoches.setAdapter(vehiculosAdapter);

        // Configurar listeners
        configurarBotones();

        // Mostrar nombre de usuario si se recibió desde otra actividad
        String usuario = getIntent().getStringExtra("usuario");
        if (usuario != null && !usuario.isEmpty()) {
            tvNombreUsuario.setText(usuario);
            Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();
        }
    }

    private void inicializarVistas() {
        btnUsuario = findViewById(R.id.btnUsuario);
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
        tvNombreUsuario = findViewById(R.id.tvUsuarioNombre);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnInstagram = findViewById(R.id.btnInstagram);
        btnTwitter = findViewById(R.id.btnTwitter);
    }

    private void configurarBotones() {
        btnUsuario.setOnClickListener(v -> startActivity(new Intent(this, Panel_Control_User.class)));

        btnMensaje.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                startActivity(intent);
            } catch (Exception e) {
                mostrarError("No se encontró una aplicación de mensajes", e);
            }
        });

        btnFavoritos.setOnClickListener(v -> {
            try {
                startActivity(new Intent(this, Favoritos.class));
            } catch (Exception e) {
                mostrarError("Error al abrir favoritos", e);
            }
        });

        btnPublicar.setOnClickListener(v -> {
            try {
                startActivity(new Intent(this, Categorias.class));
            } catch (Exception e) {
                mostrarError("Error al abrir pantalla de publicación", e);
            }
        });

        btnBuscar.setOnClickListener(v -> {
            String busqueda = etBuscar.getText().toString().trim();
            Toast.makeText(this, "Buscando: " + busqueda, Toast.LENGTH_SHORT).show();
            // Aquí podrías agregar la lógica real de búsqueda
        });

        btnCoche.setOnClickListener(v -> startActivity(new Intent(this, ListaCochesActivity.class)));
        btnFurgoneta.setOnClickListener(v -> startActivity(new Intent(this, ListaFurgonetasActivity.class)));
        btnMoto.setOnClickListener(v -> startActivity(new Intent(this, ListarMotoActivity.class)));

        btnFacebook.setOnClickListener(v -> abrirRedSocial("https://www.facebook.com/automarket"));
        btnInstagram.setOnClickListener(v -> abrirRedSocial("https://www.instagram.com/automarket"));
        btnTwitter.setOnClickListener(v -> abrirRedSocial("https://www.twitter.com/automarket"));

        tvAvisoLegal.setOnClickListener(v -> {
            try {
                startActivity(new Intent(this, Aviso_Legal.class));
            } catch (Exception e) {
                mostrarError("Error al abrir el aviso legal", e);
            }
        });

        tvContactanos.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+34666777888"));
                startActivity(intent);
            } catch (Exception e) {
                mostrarError("No se pudo abrir la aplicación de llamadas", e);
            }
        });

        tvMapa.setOnClickListener(v -> abrirMapa());
    }

    private void abrirRedSocial(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            mostrarError("No se pudo abrir la red social", e);
        }
    }

    private void abrirMapa() {
        try {
            String direccion = "Calle de Embajadores, 181, 28045 Madrid";
            String uri = "geo:0,0?q=" + Uri.encode(direccion);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                String mapsUrl = "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(direccion);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl)));
            }
        } catch (Exception e) {
            mostrarError("No se pudo abrir el mapa", e);
        }
    }

    private void mostrarError(String mensaje, Exception e) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }
}
