package com.example.automarket.Vista;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Pantalla de inicio de sesión para los usuarios registrados en AUTOMARKET.
 * Envía los datos al servidor PHP usando Volley y valida la respuesta.
 */
public class Inicio_Sesion extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasena;
    private Button btnAcceder;
    private Button btnRegistrarse;

    // URL del archivo login.php en el servidor (Utils.IP debe terminar en "/")
    private static final String URL_LOGIN = Utils.IP + "login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);

        // Enlazar elementos de la vista
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnAcceder = findViewById(R.id.btnAcceder);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        // Botón para iniciar sesión
        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString().trim();
                String contrasena = etContrasena.getText().toString().trim();

                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(Inicio_Sesion.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    verificarCredenciales(usuario, contrasena);
                }
            }
        });

        // Botón para ir a la pantalla de registro
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio_Sesion.this, Registro.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Envía los datos del usuario al servidor para validar el login.
     * @param usuario nombre ingresado
     * @param contrasena contraseña ingresada
     */
    private void verificarCredenciales(final String usuario, final String contrasena) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                int usuarioId = jsonObject.getInt("usuario_id"); // <- Este campo debe venir del JSON
                                Toast.makeText(Inicio_Sesion.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Inicio_Sesion.this, Pantalla_Principal.class);
                                intent.putExtra("usuario", usuario); // Nombre
                                intent.putExtra("usuario_id", usuarioId + ""); // ID como String
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(Inicio_Sesion.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Inicio_Sesion.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Inicio_Sesion.this, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Usar los mismos nombres que espera login.php
                Map<String, String> params = new HashMap<>();
                params.put("nombre", usuario);
                params.put("contrasenia", contrasena);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
