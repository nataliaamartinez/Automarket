package com.example.automarket.Vista;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
import java.util.List;

public class Favoritos extends AppCompatActivity {
    private ListView listViewFavoritos;
    private TextView tvNoFavoritos;
    private List<String> listaFavoritos;
    private ArrayAdapter<String> favoritosAdapter;

    // Asegúrate de que la URL esté correcta, y que `Utils.IP` esté bien configurada.
    private static final String URL_LISTAR_FAV = Utils.IP + "listar_fav.php"; // Asegúrate de que esta URL esté bien definida

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritos);

        listViewFavoritos = findViewById(R.id.listViewFavoritos);
        tvNoFavoritos = findViewById(R.id.tvNoFavoritos);

        // Inicializar la lista de favoritos
        listaFavoritos = new ArrayList<>();
        favoritosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaFavoritos);
        listViewFavoritos.setAdapter(favoritosAdapter);

        // Ocultar la lista hasta que se carguen los favoritos
        listViewFavoritos.setVisibility(View.GONE);
        tvNoFavoritos.setVisibility(View.VISIBLE);

        // Llamar a la función para obtener los favoritos
        obtenerFavoritos();
    }

    private void obtenerFavoritos() {
        // Obtener el ID del usuario (esto lo puedes obtener al iniciar sesión, por ejemplo)
        String usuarioId = "1"; // Cambia esto por el ID real del usuario

        // Crear una cola de solicitudes (RequestQueue)
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Crear la URL con el ID del usuario como parámetro
        String urlConParametros = URL_LISTAR_FAV + "?vendedor_id=" + usuarioId;

        // Crear la solicitud JSON
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                urlConParametros,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Favoritos", "Respuesta del servidor: " + response.toString());  // Log de la respuesta

                        try {
                            listaFavoritos.clear();

                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject favoritoJson = response.getJSONObject(i);

                                    String marca = favoritoJson.getString("marca");
                                    String modelo = favoritoJson.getString("modelo");
                                    String descripcion = favoritoJson.getString("descripcion");
                                    double precio = favoritoJson.getDouble("precio");

                                    String favoritoTexto = "Marca: " + marca + "\n" +
                                            "Modelo: " + modelo + "\n" +
                                            "Precio: " + precio + "€\n" +
                                            "Descripción: " + descripcion;

                                    listaFavoritos.add(favoritoTexto);
                                }

                                favoritosAdapter.notifyDataSetChanged();
                                listViewFavoritos.setVisibility(View.VISIBLE);
                                tvNoFavoritos.setVisibility(View.GONE);
                            } else {
                                tvNoFavoritos.setVisibility(View.VISIBLE);
                                listViewFavoritos.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Favoritos.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(com.android.volley.VolleyError error) {
                        // Log para ver los detalles del error de la conexión
                        Log.e("Favoritos", "Error al conectar con el servidor: " + error.getMessage());  // Log de error
                        error.printStackTrace();
                        Toast.makeText(Favoritos.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola
        requestQueue.add(jsonArrayRequest);
    }
}
