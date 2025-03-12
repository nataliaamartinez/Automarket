package com.example.automarket.Vista;

import android.annotation.SuppressLint;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.automarket.R;
import com.example.automarket.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Pantalla_Principal extends AppCompatActivity {

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

    // URL para listar los coches y furgonetas
    private static final String URL_LISTAR_COCHES = Utils.IP + "listar_coches.php";
    private static final String URL_LISTAR_FURGONETAS = Utils.IP + "listar_furgonetas.php"; // URL para listar furgonetas
    private static final String URL_PUBLICAR = Utils.IP + "publicar_coche.php"; // Cambia la URL por la correcta

    // Variables de la lista de coches y furgonetas
    private ArrayList<String> listaVehiculos;
    private ArrayAdapter<String> vehiculosAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        tvNoCoches = findViewById(R.id.tvNoCoches);
        listViewCoches = findViewById(R.id.listViewCoches);

        // Inicializar botones de redes sociales
        btnFacebook = findViewById(R.id.btnFacebook);
        btnInstagram = findViewById(R.id.btnInstagram);
        btnTwitter = findViewById(R.id.btnTwitter);

        // Inicializar lista de vehículos (coches y furgonetas)
        listaVehiculos = new ArrayList<>();
        vehiculosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaVehiculos);
        listViewCoches.setAdapter(vehiculosAdapter);

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

        btnCoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pantalla_Principal.this, ListaCochesActivity.class);
                startActivity(intent); // Esto ya inicia la nueva pantalla y se ejecuta obtenerCoches() automáticamente
            }

        });

        btnFurgoneta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pantalla_Principal.this, ListaFurgonetasActivity.class);
                startActivity(intent); // Esto ya inicia la nueva pantalla y se ejecuta obtenerCoches() automáticamente
            }
        });
        btnMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pantalla_Principal.this, ListarMotoActivity.class);
                startActivity(intent); // Esto ya inicia la nueva pantalla y se ejecuta obtenerCoches() automáticamente
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

        // Recibir el usuario si fue pasado
        String usuario = getIntent().getStringExtra("usuario");
        if (usuario != null && !usuario.isEmpty()) {
            Toast.makeText(this, "Bienvenido " + usuario, Toast.LENGTH_SHORT).show();
        }
    }


    // Método para listar furgonetas
    private void listarFurgonetas() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_LISTAR_FURGONETAS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            listaVehiculos.clear();

                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject furgonetaJson = response.getJSONObject(i);

                                    String marca = furgonetaJson.getString("marca");
                                    String modelo = furgonetaJson.getString("modelo");
                                    String descripcion = furgonetaJson.getString("descripcion");
                                    double precio = furgonetaJson.getDouble("precio");

                                    String furgonetaTexto = "Marca: " + marca + "\n" +
                                            "Modelo: " + modelo + "\n" +
                                            "Precio: " + precio + "€\n" +
                                            "Descripción: " + descripcion;

                                    listaVehiculos.add(furgonetaTexto);
                                }

                                vehiculosAdapter.notifyDataSetChanged();
                                listViewCoches.setVisibility(View.VISIBLE);
                                tvNoCoches.setVisibility(View.GONE);
                            } else {
                                tvNoCoches.setVisibility(View.VISIBLE);
                                listViewCoches.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Pantalla_Principal.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(Pantalla_Principal.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
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