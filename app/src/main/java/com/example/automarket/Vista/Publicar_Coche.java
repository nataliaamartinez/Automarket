package com.example.automarket.Vista;

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
    private static final String URL_PUBLICAR = Utils.IP + "publicar_coche.php"; // Cambia la URL por la correcta
    private String vendedorId = "1"; // TODO: Reemplaza con el ID del usuario autenticado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_coche); // Inflar el XML directamente

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

        // Verificar que todos los campos estén completos
        if (marca.isEmpty() || modelo.isEmpty() || anio.isEmpty() || kilometraje.isEmpty() || carroceria.isEmpty() || precio.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Imprimir en log los valores que se van a enviar
        Log.d("PublicarCoche", "Marca: " + marca + ", Modelo: " + modelo + ", Kilometraje: " + kilometraje + ", Descripcion: " + descripcion + ", Vendedor ID: " + vendedorId);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PUBLICAR,
                response -> {
                    Toast.makeText(Publicar_Coche.this, response, Toast.LENGTH_SHORT).show();
                    if (response.trim().equalsIgnoreCase("success")) {
                        Toast.makeText(Publicar_Coche.this, "Coche publicado con éxito", Toast.LENGTH_SHORT).show();
                        finish(); // Cierra la actividad después de publicar
                    } else {
                        Toast.makeText(Publicar_Coche.this, "Error al publicar: " + response, Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(Publicar_Coche.this, "Error de conexión: " + error.toString(), Toast.LENGTH_SHORT).show()
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