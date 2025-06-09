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

public class Publicar_Coche extends AppCompatActivity {

    private EditText etMarca, etModelo, etAnio, etPrecio, etKilometraje, etCarroceria, etDescripcion;
    private static final String URL_PUBLICAR = Utils.IP + "publicar_coche.php";
    private String vendedorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_coche);

        // Obtener el ID del usuario desde SharedPreferences
        SharedPreferences prefs = getSharedPreferences("Usuario", MODE_PRIVATE);
        vendedorId = prefs.getString("id_usuario", null);

        if (vendedorId == null) {
            Toast.makeText(this, "Error: usuario no autenticado", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Obtener las vistas por ID
        etMarca = findViewById(R.id.et_marca);
        etModelo = findViewById(R.id.et_modelo);
        etAnio = findViewById(R.id.et_anio);
        etKilometraje = findViewById(R.id.et_kilometraje);
        etCarroceria = findViewById(R.id.et_carroceria);
        etPrecio = findViewById(R.id.et_precio);
        etDescripcion = findViewById(R.id.et_descripcion);

        // Botón Publicar
        Button btnPublicar = findViewById(R.id.btn_publicar);
        btnPublicar.setOnClickListener(v -> publicarCoche());

        // Botón Cancelar
        Button btnCancelar = findViewById(R.id.btn_cancelar);
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void publicarCoche() {
        String marca = etMarca.getText().toString().trim();
        String modelo = etModelo.getText().toString().trim();
        String anio = etAnio.getText().toString().trim();
        String kilometraje = etKilometraje.getText().toString().trim();
        String carroceria = etCarroceria.getText().toString().trim();
        String precio = etPrecio.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();

        if (marca.isEmpty() || modelo.isEmpty() || anio.isEmpty() || kilometraje.isEmpty()
                || carroceria.isEmpty() || precio.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar en Log los datos antes de enviar
        Log.d("PublicarCoche", "Enviando: " + marca + ", " + modelo + ", " + anio + ", " +
                kilometraje + ", " + carroceria + ", " + precio + ", " + descripcion +
                ", vendedor_id=" + vendedorId);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PUBLICAR,
                response -> {
                    Log.d("RESPUESTA_PUBLICAR", response);  // Ver el resultado en Logcat

                    if (response.trim().equalsIgnoreCase("success")) {
                        Toast.makeText(this, "Coche publicado con éxito", Toast.LENGTH_SHORT).show();
                        finish(); // Cierra la pantalla
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
                params.put("carroceria", carroceria);
                params.put("precio", precio);
                params.put("descripcion", descripcion);
                params.put("vendedor_id", vendedorId);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}