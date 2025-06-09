package com.example.automarket.Vista;

import android.os.Bundle;
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

public class ModificarAnuncioActivity extends AppCompatActivity {

    private EditText etMarca, etModelo, etAnio, etKilometraje, etDescripcion, etPrecio;
    private int anuncioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_anuncio);

        // Inicialización de vistas
        etMarca = findViewById(R.id.etMarca);
        etModelo = findViewById(R.id.etModelo);
        etAnio = findViewById(R.id.etAnio);
        etKilometraje = findViewById(R.id.etKilometraje);
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecio = findViewById(R.id.etPrecio);
        Button btnModificar = findViewById(R.id.btnModificar);

        // Obtener los datos del intent
        anuncioId = getIntent().getIntExtra("anuncio_id", -1);
        if (anuncioId == -1) {
            Toast.makeText(this, "ID del anuncio no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etMarca.setText(getIntent().getStringExtra("marca"));
        etModelo.setText(getIntent().getStringExtra("modelo"));
        etAnio.setText(String.valueOf(getIntent().getIntExtra("anio", 0)));
        etKilometraje.setText(String.valueOf(getIntent().getIntExtra("kilometraje", 0)));
        etDescripcion.setText(getIntent().getStringExtra("descripcion"));
        etPrecio.setText(String.valueOf(getIntent().getDoubleExtra("precio", 0.0)));

        btnModificar.setOnClickListener(v -> modificarAnuncio());
    }

    private void modificarAnuncio() {
        String marca = etMarca.getText().toString().trim();
        String modelo = etModelo.getText().toString().trim();
        String anio = etAnio.getText().toString().trim();
        String kilometraje = etKilometraje.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String precio = etPrecio.getText().toString().trim();

        // Validación básica
        if (marca.isEmpty() || modelo.isEmpty() || anio.isEmpty() || kilometraje.isEmpty() || descripcion.isEmpty() || precio.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validación numérica
        try {
            Integer.parseInt(anio);
            Integer.parseInt(kilometraje);
            Double.parseDouble(precio);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Año, Kilometraje y Precio deben ser números válidos", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Utils.IP + "modificar_anuncio.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    if (response.trim().equalsIgnoreCase("success")) {
                        Toast.makeText(this, "Anuncio modificado con éxito", Toast.LENGTH_SHORT).show();
                        finish(); // Volver al panel
                    } else {
                        Toast.makeText(this, "Error al modificar: " + response, Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(anuncioId));
                params.put("marca", marca);
                params.put("modelo", modelo);
                params.put("anio", anio);
                params.put("kilometraje", kilometraje);
                params.put("descripcion", descripcion);
                params.put("precio", precio);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
