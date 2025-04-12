package com.example.automarket.Vista;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.automarket.R;

public class Inicio_Sesion extends AppCompatActivity {
    private EditText etUsuario;
    private EditText etContrasena;
    private Button btnIniciarSesion;
    private Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString().trim();
                String contrasena = etContrasena.getText().toString().trim();

                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(Inicio_Sesion.this, 
                        "Por favor, complete todos los campos", 
                        Toast.LENGTH_SHORT).show();
                    return;
                }

                // Aquí iría la lógica de autenticación
                // Por ahora, solo simulamos un inicio de sesión exitoso
                Intent intent = new Intent(Inicio_Sesion.this, Pantalla_Principal.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Inicio_Sesion.this, 
                    "Funcionalidad de registro en desarrollo", 
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
}