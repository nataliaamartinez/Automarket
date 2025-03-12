package com.example.automarket.Vista;

import android.content.DialogInterface;
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
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
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
    private int selectedItemPosition = -1;

    private static final String URL_LISTAR_FAV = Utils.IP + "listar_fav.php";
    private static final String URL_BORRAR_FAV = Utils.IP + "borrar_fav.php"; // Asegúrate de que esta URL sea correcta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritos);

        listViewFavoritos = findViewById(R.id.listViewFavoritos);
        tvNoFavoritos = findViewById(R.id.tvNoFavoritos);

        listaFavoritos = new ArrayList<>();
        favoritosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaFavoritos);
        listViewFavoritos.setAdapter(favoritosAdapter);

        listViewFavoritos.setVisibility(View.GONE);
        tvNoFavoritos.setVisibility(View.VISIBLE);

        obtenerFavoritos();
    }

    private void obtenerFavoritos() {
        String usuarioId = "1"; // Cambia esto por el ID real del usuario

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String urlConParametros = URL_LISTAR_FAV + "?vendedor_id=" + usuarioId;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                urlConParametros,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Favoritos", "Respuesta del servidor: " + response.toString());

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
                error -> {
                    Log.e("Favoritos", "Error al conectar con el servidor: " + error.getMessage());
                    error.printStackTrace();
                    Toast.makeText(Favoritos.this, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favoritos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_borrar) {
            if (selectedItemPosition != -1) {  // Verificar si se ha seleccionado algo
                String selectedItem = listaFavoritos.get(selectedItemPosition);
                mostrarConfirmacionBorrado(selectedItem, selectedItemPosition);
            } else {
                Toast.makeText(this, "Debes seleccionar un anuncio", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarConfirmacionBorrado(String favorito, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Estás seguro de que quieres borrar este favorito?")
                .setPositiveButton("Sí", (dialog, which) -> borrarFavorito(position))
                .setNegativeButton("No", null)
                .show();
    }

    private void borrarFavorito(int position) {
        String favoritoTexto = listaFavoritos.get(position);
        String usuarioId = "1"; // Cambia esto por el ID real del usuario
        String marca = favoritoTexto.split("\n")[0].split(":")[1].trim();
        String modelo = favoritoTexto.split("\n")[1].split(":")[1].trim();

        String urlBorrarFavorito = URL_BORRAR_FAV + "?favorito_id=" + position + "&usuario_id=" + usuarioId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBorrarFavorito,
                response -> {
                    if ("success".equals(response)) {
                        listaFavoritos.remove(position);
                        favoritosAdapter.notifyDataSetChanged();
                        Toast.makeText(Favoritos.this, "Favorito eliminado correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Favoritos.this, "Error al eliminar el favorito", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(Favoritos.this, "Error de conexión", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(stringRequest);
    }
}
