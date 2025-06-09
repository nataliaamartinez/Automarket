package com.example.automarket.Vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.automarket.Controlador.AnuncioAdapter;
import com.example.automarket.Modelo.Anuncio;
import com.example.automarket.R;
import com.example.automarket.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Panel_Control_User extends AppCompatActivity {

    private TextView tvUsuario;
    private RecyclerView recyclerAnuncios;
    private String usuarioId;
    private String nombreUsuario;
    private AnuncioAdapter adapter;
    private List<Anuncio> listaAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_control_user);

        SharedPreferences prefs = getSharedPreferences("Usuario", MODE_PRIVATE);
        usuarioId = prefs.getString("id_usuario", null);
        nombreUsuario = prefs.getString("nombre_usuario", null);

        tvUsuario = findViewById(R.id.tvUsuario);
        Button btnAnadir = findViewById(R.id.btnAnadir);
        Button btnModificarCredenciales = findViewById(R.id.btnModificarCredenciales);
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        Button btnFavoritos = findViewById(R.id.btnFavoritos);
        recyclerAnuncios = findViewById(R.id.recyclerAnuncios);

        if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
            tvUsuario.setText("Hola, " + nombreUsuario);
        } else {
            tvUsuario.setText("Usuario no identificado");
            Toast.makeText(this, "Nombre no disponible. Inicia sesión de nuevo.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        recyclerAnuncios.setLayoutManager(new LinearLayoutManager(this));
        listaAnuncios = new ArrayList<>();
        adapter = new AnuncioAdapter(this, listaAnuncios, usuarioId);
        recyclerAnuncios.setAdapter(adapter);

        btnAnadir.setOnClickListener(v -> {
            Intent intent = new Intent(this, Categorias.class);
            startActivity(intent);
        });

        btnModificarCredenciales.setOnClickListener(v -> {
            Intent intent = new Intent(this, ModificarCredencialesActivity.class);
            startActivity(intent);
        });

        btnCerrarSesion.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, Inicio_Sesion.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnFavoritos.setOnClickListener(v -> {
            Intent intent = new Intent(this, Favoritos.class);
            intent.putExtra("usuario_id", usuarioId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarAnunciosDelUsuario(); // ✅ Recarga cada vez que vuelves a esta pantalla
    }

    private void cargarAnunciosDelUsuario() {
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            Toast.makeText(this, "Nombre de usuario no disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Utils.IP + "get_anuncios_por_nombre.php?nombre=" + nombreUsuario;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    listaAnuncios.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Anuncio anuncio = new Anuncio(
                                    obj.getInt("id"),
                                    obj.getString("marca"),
                                    obj.getString("modelo"),
                                    obj.getInt("año"),
                                    obj.getInt("kilometraje"),
                                    obj.getDouble("precio"),
                                    obj.getString("descripcion")
                            );
                            listaAnuncios.add(anuncio);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error al cargar anuncios", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}
