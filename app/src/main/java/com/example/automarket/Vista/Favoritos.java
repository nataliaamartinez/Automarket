package com.example.automarket.Vista;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automarket.R;
import com.example.automarket.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Favoritos extends AppCompatActivity {

    private ListView listViewFavoritos;
    private TextView tvNoFavoritos;
    private List<String> listaFavoritos;
    private List<Integer> favoritosIds;
    private ArrayAdapter<String> favoritosAdapter;

    private int selectedItemPosition = -1;
    private String usuarioId;

    private static final String URL_LISTAR_FAV = Utils.IP + "listar_fav.php";
    private static final String URL_BORRAR_FAV = Utils.IP + "borrar_fav.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritos);

        listViewFavoritos = findViewById(R.id.listViewFavoritos);
        tvNoFavoritos = findViewById(R.id.tvNoFavoritos);

        listaFavoritos = new ArrayList<>();
        favoritosIds = new ArrayList<>();
        favoritosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, listaFavoritos);
        listViewFavoritos.setAdapter(favoritosAdapter);

        // ✅ Recibe el ID correctamente
        usuarioId = getIntent().getStringExtra("usuario_id");
        if (usuarioId == null) usuarioId = "1";

        Log.d("Favoritos", "ID recibido: " + usuarioId);

        listViewFavoritos.setOnItemClickListener((parent, view, position, id) -> selectedItemPosition = position);

        obtenerFavoritos();
    }

    private void obtenerFavoritos() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = URL_LISTAR_FAV + "?comprador_id=" + usuarioId;

        Log.d("Favoritos", "URL: " + url);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    listaFavoritos.clear();
                    favoritosIds.clear();

                    try {
                        if (response.length() == 0) {
                            tvNoFavoritos.setVisibility(View.VISIBLE);
                            listViewFavoritos.setVisibility(View.GONE);
                            return;
                        }

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            String favorito = "Marca: " + obj.getString("marca") + "\n" +
                                    "Modelo: " + obj.getString("modelo") + "\n" +
                                    "Precio: " + obj.getDouble("precio") + "€\n" +
                                    "Descripción: " + obj.getString("descripcion");

                            listaFavoritos.add(favorito);
                            favoritosIds.add(obj.getInt("anuncio_id"));
                        }

                        favoritosAdapter.notifyDataSetChanged();
                        tvNoFavoritos.setVisibility(View.GONE);
                        listViewFavoritos.setVisibility(View.VISIBLE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error al procesar datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(request);
    }

    private void borrarFavorito(int position) {
        int anuncioId = favoritosIds.get(position);
        String url = URL_BORRAR_FAV + "?anuncio_id=" + anuncioId + "&comprador_id=" + usuarioId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    if ("success".equalsIgnoreCase(response.trim())) {
                        listaFavoritos.remove(position);
                        favoritosIds.remove(position);
                        favoritosAdapter.notifyDataSetChanged();
                        selectedItemPosition = -1;
                        Toast.makeText(this, "Favorito eliminado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error de red", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }

    private void mostrarConfirmacionBorrado() {
        if (selectedItemPosition == -1) {
            Toast.makeText(this, "Selecciona un favorito para borrar", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Eliminar favorito")
                .setMessage("¿Deseas eliminar este anuncio de tus favoritos?")
                .setPositiveButton("Sí", (dialog, which) -> borrarFavorito(selectedItemPosition))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favoritos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_borrar) {
            mostrarConfirmacionBorrado();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
