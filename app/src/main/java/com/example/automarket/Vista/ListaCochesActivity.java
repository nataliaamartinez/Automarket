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

public class ListaCochesActivity extends AppCompatActivity {
    private static ListView listViewCoches;
    private static TextView tvNoCoches;
    private static List<String> listaCoches;
    private static ArrayAdapter<String> cochesAdapter;

    private static final String URL_LISTAR_COCHES = Utils.IP + "listar_coches.php"; // Asegúrate de que esta URL esté correcta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_coches);

        listViewCoches = findViewById(R.id.listViewCoches);
        tvNoCoches = findViewById(R.id.tvNoCoches);

        listaCoches = new ArrayList<>();
        cochesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCoches);
        listViewCoches.setAdapter(cochesAdapter);

        listViewCoches.setVisibility(View.GONE);
        tvNoCoches.setVisibility(View.VISIBLE);

        obtenerCoches();
    }

    void obtenerCoches() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_LISTAR_COCHES,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("ListaCoches", "Respuesta del servidor: " + response.toString());

                        try {
                            listaCoches.clear();

                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject cocheJson = response.getJSONObject(i);

                                    String marca = cocheJson.getString("marca");
                                    String modelo = cocheJson.getString("modelo");
                                    double precio = cocheJson.getDouble("precio");

                                    String cocheTexto = "Marca: " + marca + "\n" +
                                            "Modelo: " + modelo + "\n" +
                                            "Precio: " + precio + "€";

                                    listaCoches.add(cocheTexto);
                                }

                                cochesAdapter.notifyDataSetChanged();
                                listViewCoches.setVisibility(View.VISIBLE);
                                tvNoCoches.setVisibility(View.GONE);
                            } else {
                                tvNoCoches.setVisibility(View.VISIBLE);
                                listViewCoches.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListaCochesActivity.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> {
                    Log.e("ListaCoches", "Error al conectar con el servidor: " + error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(ListaCochesActivity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(jsonArrayRequest);
    }
}
