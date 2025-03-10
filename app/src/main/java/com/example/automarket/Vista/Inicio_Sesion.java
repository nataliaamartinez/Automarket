package com.example.automarket.Vista;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.automarket.Controlador.ConexionPHP;
import com.example.automarket.R;
import com.example.automarket.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Inicio_Sesion extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etContrasena;
    private Button btnAcceder;
    private Button btnRegistrarse;

    private static final String URL_LOGIN = Utils.IP + "login.php";// esto es para almacenar la ruta login.php

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);

        // Inicializar vistas
        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnAcceder = findViewById(R.id.btnAcceder);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString().trim();
                String contrasena = etContrasena.getText().toString().trim();

                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(Inicio_Sesion.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    new login().execute("Juan Pérez","1234");
                    //verificarCredenciales(usuario, contrasena);
                }
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio_Sesion.this, Registro.class);
                startActivity(intent);
            }
        });
    }

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
                                Toast.makeText(Inicio_Sesion.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Inicio_Sesion.this, Pantalla_Principal.class);
                                intent.putExtra("usuario", usuario);
                                startActivity(intent);
                                finish();
                            } else {
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
                        Toast.makeText(Inicio_Sesion.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", usuario);
                params.put("contrasena", contrasena);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private class login extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            //aqui es donde se hace la llmada
            Map<String, String> parametros = new HashMap<>();
            parametros.put("nombre",strings[0]);
            parametros.put("contrasenia",strings[1]);
            return ConexionPHP.enviarPeticion(URL_LOGIN,"POST",parametros);
        }
        @Override
        protected void onPostExecute(String result) {
        //aqui es donde se reciben los resultados de la peticion post atraves del parametro result
            try {
                JSONArray jsonArray = new JSONArray(result);
                JSONObject usuario = jsonArray.getJSONObject(0);
                String user = usuario.getString("nombre");
                if (!user.isEmpty()) {
                    Toast.makeText(Inicio_Sesion.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Inicio_Sesion.this, Pantalla_Principal.class);
                    intent.putExtra("usuario", user);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Inicio_Sesion.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}