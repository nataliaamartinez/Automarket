package com.example.automarket.Vista;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;
import com.example.automarket.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ModificarCredencialesActivity extends AppCompatActivity {

    private EditText etNombreUsuario, etCorreoUsuario, etNuevaContrasena, etConfirmarContrasena;
    private Button btnGuardarCambios;
    private String nombreUsuario;
    private String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_credenciales);

        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        etCorreoUsuario = findViewById(R.id.etCorreoUsuario);
        etNuevaContrasena = findViewById(R.id.etNuevaContrasena);
        etConfirmarContrasena = findViewById(R.id.etConfirmarContrasena);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);

        // Bloquear edición en nombre y correo
        etNombreUsuario.setEnabled(false);
        etCorreoUsuario.setEnabled(false);

        // Cargar datos del usuario desde SharedPreferences
        SharedPreferences prefs = getSharedPreferences("Usuario", MODE_PRIVATE);
        nombreUsuario = prefs.getString("nombre_usuario", "");
        correoUsuario = prefs.getString("correo_usuario", "");

        etNombreUsuario.setText(nombreUsuario);
        etCorreoUsuario.setText(correoUsuario);

        btnGuardarCambios.setOnClickListener(v -> {
            String nuevaContrasena = etNuevaContrasena.getText().toString().trim();
            String confirmar = etConfirmarContrasena.getText().toString().trim();

            if (nuevaContrasena.isEmpty() || confirmar.isEmpty()) {
                Toast.makeText(this, "Por favor, completa ambos campos de contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!nuevaContrasena.equals(confirmar)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            new GuardarCambiosTask().execute(nombreUsuario, correoUsuario, nuevaContrasena);
        });
    }

    private class GuardarCambiosTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(Utils.IP + "modificar_credenciales.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String data = "nombre=" + params[0] + "&email=" + params[1] + "&contrasenia=" + params[2];

                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                conn.disconnect();
                return response.toString();
            } catch (Exception e) {
                Log.e("GuardarCambiosTask", "Error en conexión: " + e.getMessage());
                return "{\"resultado\": \"0\", \"error\": \"Error de conexión.\"}";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                if ("1".equals(json.optString("resultado"))) {
                    Toast.makeText(ModificarCredencialesActivity.this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String error = json.optString("error", "Error desconocido");
                    Toast.makeText(ModificarCredencialesActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(ModificarCredencialesActivity.this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
