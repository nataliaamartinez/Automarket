package com.example.automarket.Vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Inicio_Sesion extends AppCompatActivity {

    private EditText etUsuario, etContrasena;
    private Button btnAcceder, btnRegistrarse;
    private static final String URL_LOGIN = Utils.IP + "login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnAcceder = findViewById(R.id.btnAcceder);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        btnAcceder.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString().trim();
            String contrasena = etContrasena.getText().toString().trim();

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(Inicio_Sesion.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                verificarCredenciales(usuario, contrasena);
            }
        });

        btnRegistrarse.setOnClickListener(v -> {
            Intent intent = new Intent(Inicio_Sesion.this, Registro.class);
            startActivity(intent);
        });
    }

    private void verificarCredenciales(final String usuario, final String contrasena) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            int usuarioId = jsonObject.getInt("usuario_id");
                            String nombreUsuario = jsonObject.getString("nombre");
                            String correo = jsonObject.getString("email");
                            SharedPreferences prefs = getSharedPreferences("Usuario", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("id_usuario", String.valueOf(usuarioId));
                            editor.putString("nombre_usuario", nombreUsuario);
                            editor.putString("correo_usuario", correo);
                            editor.apply();

                            Toast.makeText(Inicio_Sesion.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Inicio_Sesion.this, Pantalla_Principal.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Inicio_Sesion.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Inicio_Sesion.this, "Error en el servidor", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(Inicio_Sesion.this, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", usuario);
                params.put("contrasenia", contrasena);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
