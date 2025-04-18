package com.example.automarket.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automarket.R;
import com.example.automarket.Utils;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText etNombreRegistro, etCorreoRegistro, etContrasenaRegistro, etRepetirContrasena;
    private Button btnRegistrar, btnSalir;
    private static final String URL_REGISTRO = Utils.IP + "registro.php"; // Ruta del servidor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        etNombreRegistro = findViewById(R.id.etUsuarioRegistro);
        etCorreoRegistro = findViewById(R.id.etCorreoRegistro);
        etContrasenaRegistro = findViewById(R.id.etContrasenaRegistro);
        etRepetirContrasena = findViewById(R.id.etRepetirContrasena);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnSalir = findViewById(R.id.btnSalir);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombreRegistro.getText().toString().trim();
                String correo = etCorreoRegistro.getText().toString().trim();
                String contrasena = etContrasenaRegistro.getText().toString().trim();
                String repetirContrasena = etRepetirContrasena.getText().toString().trim();

                if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || repetirContrasena.isEmpty()) {
                    Toast.makeText(Registro.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else if (!contrasena.equals(repetirContrasena)) {
                    Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                    registrarUsuario(nombre, correo, contrasena);
                }
            }
        });

        btnSalir.setOnClickListener(v -> finish());
    }

    private void registrarUsuario(String nombre, String correo, String contrasenia) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTRO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RegistroResponse", "Respuesta del servidor: " + response);

                        if (response.trim().equalsIgnoreCase("success")) {
                            Toast.makeText(Registro.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registro.this, Inicio_Sesion.class));
                            finish();
                        } else if (response.trim().equalsIgnoreCase("existe")) {
                            Toast.makeText(Registro.this, "El usuario ya está registrado", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("RegistroResponse", "Respuesta del servidor: " + response);
                            Toast.makeText(Registro.this, "Respuesta del servidor: " + response, Toast.LENGTH_LONG).show();
                            //Toast.makeText(Registro.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Error en la petición: " + error.getMessage());
                        Toast.makeText(Registro.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("email", correo);
                params.put("contrasenia", contrasenia);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}