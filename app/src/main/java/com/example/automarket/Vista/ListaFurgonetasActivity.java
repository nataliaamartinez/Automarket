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

public class ListaFurgonetasActivity extends AppCompatActivity {
    private static ListView listViewFurgonetas;
    private static TextView tvNoFurgonetas;
    private static List<String> listaFurgonetas;
    private static ArrayAdapter<String> furgonetasAdapter;

    private static final String URL_LISTAR_FURGONETAS = Utils.IP + "listar_furgonetas.php"; // Actualiza la URL para listar furgonetas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_furgonetas); // Asegúrate de tener un layout adecuado

        listViewFurgonetas = findViewById(R.id.listViewFurgonetas);
        tvNoFurgonetas = findViewById(R.id.tvNoFurgonetas);

        listaFurgonetas = new ArrayList<>();
        furgonetasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaFurgonetas);
        listViewFurgonetas.setAdapter(furgonetasAdapter);

        listViewFurgonetas.setVisibility(View.GONE);
        tvNoFurgonetas.setVisibility(View.VISIBLE);

        obtenerFurgonetas();
    }

    void obtenerFurgonetas() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_LISTAR_FURGONETAS, // URL para listar furgonetas
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("ListaFurgonetas", "Respuesta del servidor: " + response.toString());

                        try {
                            listaFurgonetas.clear();

                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject furgonetaJson = response.getJSONObject(i);

                                    String marca = furgonetaJson.getString("marca");
                                    String modelo = furgonetaJson.getString("modelo");
                                    double precio = furgonetaJson.getDouble("precio");

                                    String furgonetaTexto = "Marca: " + marca + "\n" +
                                            "Modelo: " + modelo + "\n" +
                                            "Precio: " + precio + "€";

                                    listaFurgonetas.add(furgonetaTexto);
                                }

                                furgonetasAdapter.notifyDataSetChanged();
                                listViewFurgonetas.setVisibility(View.VISIBLE);
                                tvNoFurgonetas.setVisibility(View.GONE);
                            } else {
                                tvNoFurgonetas.setVisibility(View.VISIBLE);
                                listViewFurgonetas.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListaFurgonetasActivity.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> {
                    Log.e("ListaFurgonetas", "Error al conectar con el servidor: " + error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(ListaFurgonetasActivity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(jsonArrayRequest);
    }
}
