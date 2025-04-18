package com.example.automarket.Vista;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private EditText etNombreUsuario;
    private EditText etCorreoUsuario;
    private EditText etNuevaContrasena;
    private EditText etConfirmarContrasena;
    private Button btnGuardarCambios;
    private String nombreUsuario;
    private String correoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_credenciales);

        // Inicializar vistas
        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        etCorreoUsuario = findViewById(R.id.etCorreoUsuario);
        etNuevaContrasena = findViewById(R.id.etNuevaContrasena);
        etConfirmarContrasena = findViewById(R.id.etConfirmarContrasena);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);

        // Obtener los datos del usuario desde la actividad anterior (Pantalla_Principal)
        nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        correoUsuario = getIntent().getStringExtra("correoUsuario");

        // Mostrar los datos del usuario en los campos EditText
        etNombreUsuario.setText(nombreUsuario);
        etCorreoUsuario.setText(correoUsuario);

        // Listener para guardar los cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los nuevos valores
                String nuevoNombre = etNombreUsuario.getText().toString();
                String nuevoCorreo = etCorreoUsuario.getText().toString();
                String nuevaContrasena = etNuevaContrasena.getText().toString();
                String confirmarContrasena = etConfirmarContrasena.getText().toString();

                // Verificar que las contraseñas coinciden
                if (!nuevaContrasena.equals(confirmarContrasena)) {
                    Toast.makeText(ModificarCredencialesActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;  // No continuar si las contraseñas no coinciden
                }

                // Llamar a la tarea para guardar los cambios en el servidor
                new GuardarCambiosTask().execute(nuevoNombre, nuevoCorreo, nuevaContrasena);
            }
        });
    }

    // Clase AsyncTask para hacer la solicitud HTTP en segundo plano
    private class GuardarCambiosTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String resultado = "";
            try {
                // URL del archivo PHP en tu servidor
                URL url = new URL(Utils.IP + "modificar_credenciales.php");

                // Abrir una conexión HTTP
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Obtener los datos enviados desde la actividad
                String nuevoNombre = params[0];
                String nuevoCorreo = params[1];
                String nuevaContrasena = params[2];

                // Crear los parámetros para la solicitud POST
                String data = "nombre=" + nuevoNombre + "&email=" + nuevoCorreo + "&contrasenia=" + nuevaContrasena;

                // Escribir los datos en el OutputStream
                OutputStream os = connection.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                // Verificar el código de respuesta HTTP
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new Exception("Error en la conexión: " + responseCode);
                }

                // Leer la respuesta del servidor
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Obtener la respuesta del servidor
                resultado = response.toString();

                // Cerrar la conexión
                connection.disconnect();

                // Log de la respuesta completa para depuración
                Log.d("Server Response", resultado);

            } catch (Exception e) {
                Log.e("Error", "Error al hacer la solicitud HTTP", e);
                resultado = "{\"resultado\": \"0\", \"error\": \"Error en la conexión o en la consulta.\"}";
            }
            return resultado;
        }

        @Override
        protected void onPostExecute(String result) {
            // Analizar el resultado JSON
            try {
                // Log de la respuesta completa
                Log.d("Response Data", result);

                // Verificar si la respuesta es un JSON válido
                JSONObject jsonObject = new JSONObject(result);
                String resultado = jsonObject.getString("resultado");

                if ("1".equals(resultado)) {
                    // Cambios guardados correctamente
                    Toast.makeText(ModificarCredencialesActivity.this, "Credenciales actualizadas", Toast.LENGTH_SHORT).show();
                    finish();  // Finalizar la actividad
                } else {
                    // Hubo un error
                    String error = jsonObject.optString("error", "Error desconocido");
                    Toast.makeText(ModificarCredencialesActivity.this, "Error al actualizar credenciales: " + error, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // Error al procesar la respuesta
                Toast.makeText(ModificarCredencialesActivity.this, "Error al procesar la respuesta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("JSON Error", "Error al analizar la respuesta JSON", e);
            }
        }

    }
}