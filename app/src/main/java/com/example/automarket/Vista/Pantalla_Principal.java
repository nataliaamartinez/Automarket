package com.example.automarket.Vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    private TextView tvUsuario, tvNoCoches, tvNombreUsuario;
    private ListView listViewCoches;
    private SearchView searchView;
    private Button btnAnadir, btnPanelUsuario;
    private Button btnCoche, btnFurgoneta, btnMoto;

    private ArrayList<String> listaVehiculos;
    private ArrayList<JSONObject> datosVehiculos;
    private ArrayAdapter<String> vehiculosAdapter;

    private String usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.pantalla_principal);
            inicializarVistas();

            // ✅ Leer nombre desde SharedPreferences
            SharedPreferences prefs = getSharedPreferences("Usuario", MODE_PRIVATE);
            String nombreUsuario = prefs.getString("nombre_usuario", "Usuario");

            if (tvNombreUsuario != null) {
                tvNombreUsuario.setText("Bienvenido, " + nombreUsuario);
            }

            listaVehiculos = new ArrayList<>();
            datosVehiculos = new ArrayList<>();
            vehiculosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaVehiculos);
            listViewCoches.setAdapter(vehiculosAdapter);

            configurarBotones();
            cargarVehiculos();
            configurarBusqueda();

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
        btnCoche = findViewById(R.id.btnCoche);
        btnFurgoneta = findViewById(R.id.btnFurgoneta);
        btnMoto = findViewById(R.id.btnMoto);
        btnPanelUsuario = findViewById(R.id.btnPanelUsuario);
    }

    private void configurarBotones() {
        btnPanelUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(Pantalla_Principal.this, Panel_Control_User.class);
            startActivity(intent);
        });

        btnAnadir.setOnClickListener(v -> {
            Intent intent = new Intent(this, Categorias.class);
            startActivity(intent);
        });

        btnCoche.setOnClickListener(v -> cargarVehiculosPorTipo("coche"));
        btnFurgoneta.setOnClickListener(v -> cargarVehiculosPorTipo("furgoneta"));
        btnMoto.setOnClickListener(v -> cargarVehiculosPorTipo("moto"));
    }

    private void cargarVehiculos() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Utils.IP + "listar_anuncio.php";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        listaVehiculos.clear();
                        datosVehiculos.clear();

                        if (array.length() == 0) {
                            tvNoCoches.setText("No hay coches disponibles.");
                            tvNoCoches.setVisibility(View.VISIBLE);
                            return;
                        }

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            datosVehiculos.add(obj);

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

                Intent detalleIntent = new Intent(Pantalla_Principal.this, DetalleAnuncioActivity.class);
                detalleIntent.putExtra("anuncio_id", vehiculo.optInt("id")); // ✅ ID del anuncio
                detalleIntent.putExtra("marca", vehiculo.optString("marca"));
                detalleIntent.putExtra("modelo", vehiculo.optString("modelo"));
                detalleIntent.putExtra("precio", vehiculo.optString("precio"));
                detalleIntent.putExtra("descripcion", vehiculo.optString("descripcion"));
                detalleIntent.putExtra("email", vehiculo.optString("email")); // suponiendo que ya lo tienes
                startActivity(detalleIntent);


            } catch (Exception e) {
                Log.e("INTENT_ERROR", "Error al abrir detalle: " + e.getMessage(), e); // ⬅️ más detallado
                Toast.makeText(this, "No se pudo abrir el detalle", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarVehiculosPorTipo(String tipo) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Utils.IP + "listar_por_tipo.php?tipo=" + tipo;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        listaVehiculos.clear();
                        datosVehiculos.clear();

                        if (array.length() == 0) {
                            tvNoCoches.setText("No hay resultados para " + tipo);
                            tvNoCoches.setVisibility(View.VISIBLE);
                            vehiculosAdapter.notifyDataSetChanged();
                            return;
                        }

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            datosVehiculos.add(obj);

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
                for (JSONObject obj : datosVehiculos) {
                    String marca = obj.optString("marca", "").toLowerCase();
                    String modelo = obj.optString("modelo", "").toLowerCase();
                    String text = marca + " " + modelo;

                    if (text.contains(newText.toLowerCase())) {
                        String info = marca + " " + modelo + " - " + obj.optString("precio") + "€";
                        filtrados.add(info);
                    }
                }

                vehiculosAdapter.clear();
                vehiculosAdapter.addAll(filtrados);
                vehiculosAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}
