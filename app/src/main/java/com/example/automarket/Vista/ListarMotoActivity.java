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


public class ListarMotoActivity extends AppCompatActivity {
    private static ListView listViewMotos;
    private static TextView tvNoMotos;
    private static List<String> listaMotos;
    private static ArrayAdapter<String> motosAdapter;

    private static final String URL_LISTAR_MOTOS = Utils.IP + "listar_motos.php"; // Actualiza la URL para listar furgonetas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_motos); // Asegúrate de tener un layout adecuado

        listViewMotos = findViewById(R.id.listViewMotos);
        tvNoMotos = findViewById(R.id.tvNoMotos);

        listaMotos = new ArrayList<>();
        motosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaMotos);
        listViewMotos.setAdapter(motosAdapter);

        listViewMotos.setVisibility(View.GONE);
        tvNoMotos.setVisibility(View.VISIBLE);

        obtenerFurgonetas();
    }

    void obtenerFurgonetas() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                URL_LISTAR_MOTOS, // URL para listar motos
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("ListaMotos", "Respuesta del servidor: " + response.toString());

                        try {
                            listaMotos.clear();

                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject furgonetaJson = response.getJSONObject(i);

                                    String marca = furgonetaJson.getString("marca");
                                    String modelo = furgonetaJson.getString("modelo");
                                    double precio = furgonetaJson.getDouble("precio");

                                    String furgonetaTexto = "Marca: " + marca + "\n" +
                                            "Modelo: " + modelo + "\n" +
                                            "Precio: " + precio + "€";

                                    listaMotos.add(furgonetaTexto);
                                }

                                motosAdapter.notifyDataSetChanged();
                                listViewMotos.setVisibility(View.VISIBLE);
                                tvNoMotos.setVisibility(View.GONE);
                            } else {
                                tvNoMotos.setVisibility(View.VISIBLE);
                                listViewMotos.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListarMotoActivity.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                error -> {
                    Log.e("ListaMotos", "Error al conectar con el servidor: " + error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(ListarMotoActivity.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(jsonArrayRequest);
    }
}
