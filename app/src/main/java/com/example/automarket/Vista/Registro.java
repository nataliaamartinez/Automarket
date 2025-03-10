package com.example.automarket.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Registro extends AppCompatActivity {
    private EditText etUsuarioRegistro;
    private EditText etCorreoRegistro;
    private EditText etContrasenaRegistro;
    private EditText etRepetirContrasena;
    private Button btnRegistrar;
    private Button btnSalir;

    private static final String DB_URL = "jdbc:mysql://http://localhost/phpmyadmin/automarket";
    private static final String DB_USER = "tu_usuario";
    private static final String DB_PASSWORD = "tu_contraseña";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        etUsuarioRegistro = findViewById(R.id.etUsuarioRegistro);
        etCorreoRegistro = findViewById(R.id.etCorreoRegistro);
        etContrasenaRegistro = findViewById(R.id.etContrasenaRegistro);
        etRepetirContrasena = findViewById(R.id.etRepetirContrasena);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnSalir = findViewById(R.id.btnSalir);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuarioRegistro.getText().toString();
                String correo = etCorreoRegistro.getText().toString();
                String contrasena = etContrasenaRegistro.getText().toString();
                String repetirContrasena = etRepetirContrasena.getText().toString();

                if (usuario.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || repetirContrasena.isEmpty()) {
                    Toast.makeText(Registro.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else if (!contrasena.equals(repetirContrasena)) {
                    Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {
                    registrarUsuario(usuario, correo, contrasena);
                }
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void registrarUsuario(String nombre, String correo, String contrasena) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    String query = "INSERT INTO Usuario (nombre, email, telefono) VALUES (?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, nombre);
                    pstmt.setString(2, correo);
                    pstmt.setString(3, ""); // Puedes agregar otro campo como teléfono si lo deseas

                    int filasInsertadas = pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (filasInsertadas > 0) {
                                Toast.makeText(Registro.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Registro.this, Inicio_Sesion.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Registro.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Registro.this, "Error de conexión a la base de datos", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
