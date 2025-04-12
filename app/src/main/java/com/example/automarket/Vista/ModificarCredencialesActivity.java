package com.example.automarket.Vista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;

public class ModificarCredencialesActivity extends AppCompatActivity {

    private EditText etUsuarioActual;
    private EditText etContrasenaActual;
    private EditText etNuevoUsuario;
    private EditText etNuevaContrasena;
    private Button btnGuardarCambios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_credenciales);

        etUsuarioActual = findViewById(R.id.etUsuarioActual);
        etContrasenaActual = findViewById(R.id.etContrasenaActual);
        etNuevoUsuario = findViewById(R.id.etNuevoUsuario);
        etNuevaContrasena = findViewById(R.id.etNuevaContrasena);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios);

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuarioActual = etUsuarioActual.getText().toString().trim();
                String contrasenaActual = etContrasenaActual.getText().toString().trim();
                String nuevoUsuario = etNuevoUsuario.getText().toString().trim();
                String nuevaContrasena = etNuevaContrasena.getText().toString().trim();

                if (usuarioActual.isEmpty() || contrasenaActual.isEmpty() || 
                    nuevoUsuario.isEmpty() || nuevaContrasena.isEmpty()) {
                    Toast.makeText(ModificarCredencialesActivity.this,
                            "Por favor, complete todos los campos",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aquí iría la lógica para modificar las credenciales
                Toast.makeText(ModificarCredencialesActivity.this,
                        "Credenciales modificadas exitosamente",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}