package com.example.automarket.Vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automarket.R;
import com.example.automarket.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pantalla_Principal extends AppCompatActivity {

    private ImageButton btnUsuario, /*btnMensaje,*/ btnFavoritos, btnFacebook, btnInstagram, btnTwitter;
    private TextView tvNoCoches, tvAvisoLegal, tvContactanos, tvMapa, tvNombreUsuario;
    private ListView listViewCoches;
    private SearchView searchView;

    private ArrayList<String> listaVehiculos;
    private ArrayList<JSONObject> datosVehiculos;
    private ArrayAdapter<String> vehiculosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        inicializarVistas();

        listaVehiculos = new ArrayList<>();
        datosVehiculos = new ArrayList<>();
        vehiculosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaVehiculos);
        listViewCoches.setAdapter(vehiculosAdapter);

        cargarVehiculos();

        listViewCoches.setOnItemClickListener((parent, view, position, id) -> {
            try {
                JSONObject vehiculo = datosVehiculos.get(position);
                String email = vehiculo.getString("email");
                String asunto = "Consulta sobre " + vehiculo.getString("marca") + " " + vehiculo.getString("modelo");
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
                startActivity(emailIntent);
            } catch (Exception e) {
                Toast.makeText(this, "Error al abrir correo", Toast.LENGTH_SHORT).show();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<String> filtrados = new ArrayList<>();
                for (String v : listaVehiculos) {
                    if (v.toLowerCase().contains(newText.toLowerCase())) {
                        filtrados.add(v);
                    }
                }
                listViewCoches.setAdapter(new ArrayAdapter<>(Pantalla_Principal.this, android.R.layout.simple_list_item_1, filtrados));
                return true;
            }
        });

        String usuario = getIntent().getStringExtra("usuario");
        if (usuario != null && !usuario.isEmpty()) {
            tvNombreUsuario.setText(usuario);
            Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();
        }

        configurarBotones();
    }

    private void inicializarVistas() {
        btnUsuario = findViewById(R.id.btnUsuario);
        // btnMensaje = findViewById(R.id.btnMensaje); // Comentado porque el botón ya no existe
        btnFavoritos = findViewById(R.id.btnFavoritos);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnInstagram = findViewById(R.id.btnInstagram);
        btnTwitter = findViewById(R.id.btnTwitter);
        tvNoCoches = findViewById(R.id.tvNoCoches);
        tvAvisoLegal = findViewById(R.id.tvAvisoLegal);
        tvContactanos = findViewById(R.id.tvContactanos);
        tvMapa = findViewById(R.id.tvMapa);
        tvNombreUsuario = findViewById(R.id.tvUsuarioNombre);
        listViewCoches = findViewById(R.id.listViewCoches);
        searchView = findViewById(R.id.searchView);
    }

    private void configurarBotones() {
        btnUsuario.setOnClickListener(v -> startActivity(new Intent(this, Panel_Control_User.class)));
        // btnMensaje.setOnClickListener(v -> abrirAppMensajes()); // Eliminado porque ya no existe el botón
        btnFavoritos.setOnClickListener(v -> startActivity(new Intent(this, Favoritos.class)));
        btnFacebook.setOnClickListener(v -> abrirRedSocial("https://www.facebook.com/automarket"));
        btnInstagram.setOnClickListener(v -> abrirRedSocial("https://www.instagram.com/automarket"));
        btnTwitter.setOnClickListener(v -> abrirRedSocial("https://www.twitter.com/automarket"));
        tvAvisoLegal.setOnClickListener(v -> startActivity(new Intent(this, Aviso_Legal.class)));
        tvContactanos.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+34666777888"))));
        tvMapa.setOnClickListener(v -> abrirMapa());
    }

    private void abrirRedSocial(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(this, "No se pudo abrir la red social", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "No se pudo abrir el mapa", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarVehiculos() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Utils.IP + "listar_anuncios.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            datosVehiculos.add(obj);
                            String info = obj.getString("marca") + " " + obj.getString("modelo") + " - " + obj.getString("precio") + "€";
                            listaVehiculos.add(info);
                        }
                        vehiculosAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(this, "Error al cargar anuncios", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error de red", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}
