package com.example.automarket.Vista;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automarket.R;
import com.example.automarket.Utils;

import java.util.HashMap;
import java.util.Map;

public class Publicar_Moto extends AppCompatActivity {

    private EditText etMarca, etModelo, etAnio, etPrecio, etKilometraje, etCilindrada, etDescripcion;
    private static final String URL_PUBLICAR = Utils.IP + "publicar_moto.php";
    private String vendedorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_moto);

        // Obtener ID del usuario
        SharedPreferences prefs = getSharedPreferences("Usuario", MODE_PRIVATE);
        vendedorId = prefs.getString("id_usuario", null);

        if (vendedorId == null) {
            Toast.makeText(this, "Error: usuario no autenticado", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Obtener vistas
        etMarca = findViewById(R.id.et_marca);
        etModelo = findViewById(R.id.et_modelo);
        etAnio = findViewById(R.id.et_anio);
        etKilometraje = findViewById(R.id.et_kilometraje);
        etCilindrada = findViewById(R.id.et_cilindrada);
        etPrecio = findViewById(R.id.et_precio);
        etDescripcion = findViewById(R.id.et_descripcion);

        // Botón Publicar
        Button btnPublicar = findViewById(R.id.btn_publicar);
        btnPublicar.setOnClickListener(v -> publicarMoto());

        // Botón Cancelar
        Button btnCancelar = findViewById(R.id.btn_cancelar);
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void publicarMoto() {
        String marca = etMarca.getText().toString().trim();
        String modelo = etModelo.getText().toString().trim();
        String anio = etAnio.getText().toString().trim();
        String kilometraje = etKilometraje.getText().toString().trim();
        String cilindrada = etCilindrada.getText().toString().trim();
        String precio = etPrecio.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        if (marca.isEmpty() || modelo.isEmpty() || anio.isEmpty() || kilometraje.isEmpty()
                || cilindrada.isEmpty() || precio.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("PublicarMoto", "Enviando: " + marca + ", " + modelo + ", " + anio + ", " +
                kilometraje + ", " + cilindrada + ", " + precio + ", " + descripcion +
                ", vendedor_id=" + vendedorId);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PUBLICAR,
                response -> {
                    Log.d("RESPUESTA_PUBLICAR", response);
                    if (response.trim().equalsIgnoreCase("success")) {
                        Toast.makeText(this, "Moto publicada con éxito", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Error al publicar: " + response, Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("marca", marca);
                params.put("modelo", modelo);
                params.put("anio", anio);
                params.put("kilometraje", kilometraje);
                params.put("cilindrada", cilindrada);
                params.put("precio", precio);
                params.put("descripcion", descripcion);
                params.put("vendedor_id", vendedorId);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
