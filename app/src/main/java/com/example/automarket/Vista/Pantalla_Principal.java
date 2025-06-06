package com.example.automarket.Vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

    //private ImageButton btnFavoritos, btnFacebook, btnInstagram, btnTwitter;
    private TextView tvUsuario, tvNoCoches, tvAvisoLegal, tvContactanos, tvMapa, tvNombreUsuario;
    private ListView listViewCoches;
    private SearchView searchView;
    private Button btnAnadir, btnPanelUsuario;

    private ArrayList<String> listaVehiculos;
    private ArrayList<JSONObject> datosVehiculos;
    private ArrayAdapter<String> vehiculosAdapter;

    private String usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.pantalla_principal);
            Log.d("DEBUG", "Layout cargado correctamente");

            inicializarVistas();
            Log.d("DEBUG", "Vistas inicializadas");

            listaVehiculos = new ArrayList<>();
            datosVehiculos = new ArrayList<>();
            vehiculosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaVehiculos);
            listViewCoches.setAdapter(vehiculosAdapter);

            usuarioActual = getIntent().getStringExtra("usuario");
            if (usuarioActual != null && !usuarioActual.isEmpty()) {
                if (tvNombreUsuario != null) {
                    tvNombreUsuario.setText(usuarioActual);
                }
            }

            cargarVehiculos();
            configurarBusqueda();
            configurarBotones();

        } catch (Exception e) {
            Log.e("CRASH_DEBUG", "Error en onCreate: " + e.getMessage());
            Toast.makeText(this, "Error en la pantalla principal", Toast.LENGTH_LONG).show();
        }
    }


    private void inicializarVistas() {
        tvUsuario = findViewById(R.id.tvUsuario);
        tvNoCoches = findViewById(R.id.tvNoCoches);
        tvNombreUsuario = findViewById(R.id.tvUsuarioNombre);
        listViewCoches = findViewById(R.id.listViewCoches);
        searchView = findViewById(R.id.searchView);
        btnAnadir = findViewById(R.id.btnAnadir);
        btnPanelUsuario = findViewById(R.id.btnPanelUsuario);
        if (btnPanelUsuario == null) {
            Toast.makeText(this, "btnPanelUsuario es NULL", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "btnPanelUsuario OK", Toast.LENGTH_SHORT).show();
        }
    }


    private void configurarBotones() {
        btnPanelUsuario.setOnClickListener(v -> {
            try {
                Log.d("BOTON_PANEL", "Click en Panel Usuario");
                Intent intent = new Intent(Pantalla_Principal.this, Panel_Control_User.class);
                intent.putExtra("usuario", usuarioActual);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(Pantalla_Principal.this,
                        "ERROR al abrir Panel: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });

        btnAnadir.setOnClickListener(v -> {
            Intent intent = new Intent(this, Categorias.class);
            intent.putExtra("usuario", usuarioActual);
            startActivity(intent);
        });

        /*btnFavoritos.setOnClickListener(v -> {
            Intent intent = new Intent(this, Favoritos.class);
            intent.putExtra("usuario", usuarioActual);
            startActivity(intent);
        });*/


    }

    private void cargarVehiculos() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Utils.IP + "listar_anuncio.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("RESPUESTA", response);

                    try {
                        JSONArray array = new JSONArray(response);

                        listaVehiculos.clear();
                        datosVehiculos.clear();

                        if (array.length() == 0) {
                            tvNoCoches.setText("No hay coches disponibles.");
                            tvNoCoches.setVisibility(TextView.VISIBLE);
                            return;
                        }

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            datosVehiculos.add(obj);

                            // Asegúrate de que los campos existen
                            String marca = obj.optString("marca", "Desconocido");
                            String modelo = obj.optString("modelo", "Modelo");
                            String precio = obj.optString("precio", "0.00");

                            String info = marca + " " + modelo + " - " + precio + "€";
                            listaVehiculos.add(info);
                        }

                        vehiculosAdapter.notifyDataSetChanged();
                        tvNoCoches.setVisibility(View.GONE);

                    } catch (Exception e) {
                        Log.e("JSON_ERROR", "Error procesando JSON: " + e.getMessage());
                        Toast.makeText(this, "Error al procesar los anuncios", Toast.LENGTH_SHORT).show();
                        tvNoCoches.setText("Error cargando anuncios.");
                        tvNoCoches.setVisibility(View.VISIBLE);
                    }
                },
                error -> {
                    Log.e("VOLLEY_ERROR", "Error de red: " + error.toString());
                    Toast.makeText(this, "Error de red al cargar vehículos", Toast.LENGTH_SHORT).show();
                    tvNoCoches.setText("No se pudo conectar con el servidor.");
                    tvNoCoches.setVisibility(View.VISIBLE);
                });

        queue.add(request);

        listViewCoches.setOnItemClickListener((parent, view, position, id) -> {
            try {
                JSONObject vehiculo = datosVehiculos.get(position);
                String email = vehiculo.optString("email", "");
                String marca = vehiculo.optString("marca", "");
                String modelo = vehiculo.optString("modelo", "");

                if (!email.isEmpty()) {
                    String asunto = "Consulta sobre " + marca + " " + modelo;
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
                    startActivity(emailIntent);
                } else {
                    Toast.makeText(this, "No hay correo para este anuncio", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("EMAIL_ERROR", "Error al abrir correo: " + e.getMessage());
                Toast.makeText(this, "Error al abrir correo", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void configurarBusqueda() {
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

                listViewCoches.setAdapter(new ArrayAdapter<>(Pantalla_Principal.this,
                        android.R.layout.simple_list_item_1, filtrados));
                return true;
            }
        });
    }
}
