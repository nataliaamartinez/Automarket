package com.example.automarket.Vista;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.automarket.R;
import com.example.automarket.Utils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetalleAnuncioActivity extends AppCompatActivity {

    private TextView tvMarcaModelo, tvPrecio, tvDescripcion;
    private Button btnContactar, btnFavorito, btnVolver;

    private String emailVendedor, marca, modelo;
    private int anuncioId = -1;  // Se usará para añadir a favoritos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_anuncio);

        tvMarcaModelo = findViewById(R.id.tvMarcaModelo);
        tvPrecio = findViewById(R.id.tvPrecio);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        btnContactar = findViewById(R.id.btnContactar);
        btnFavorito = findViewById(R.id.btnFavorito);
        btnVolver = findViewById(R.id.btnVolver);

        // Obtener datos del Intent
        Intent intent = getIntent();
        if (intent != null) {
            marca = intent.getStringExtra("marca");
            modelo = intent.getStringExtra("modelo");
            String precio = intent.getStringExtra("precio");
            String descripcion = intent.getStringExtra("descripcion");
            emailVendedor = intent.getStringExtra("email");
            anuncioId = intent.getIntExtra("anuncio_id", -1);  // ✅ importante

            tvMarcaModelo.setText(marca + " " + modelo);
            tvPrecio.setText("Precio: " + precio + " €");
            tvDescripcion.setText(descripcion);
        }

        // Contactar
        btnContactar.setOnClickListener(v -> {
            if (emailVendedor != null && !emailVendedor.isEmpty()) {
                String asunto = "Consulta sobre " + marca + " " + modelo;
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailVendedor));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
                startActivity(emailIntent);
            } else {
                Toast.makeText(this, "Email no disponible", Toast.LENGTH_SHORT).show();
            }
        });

        // Añadir a favoritos
        btnFavorito.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("Usuario", MODE_PRIVATE);
            String usuarioId = prefs.getString("id_usuario", null);

            if (usuarioId == null || anuncioId == -1) {
                Toast.makeText(this, "No se pudo obtener usuario o anuncio", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = Utils.IP + "añadir_favorito.php";

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getBoolean("success")) {
                                Toast.makeText(this, "Anuncio añadido a favoritos", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("usuario_id", usuarioId);
                    params.put("anuncio_id", String.valueOf(anuncioId));
                    return params;
                }
            };

            Volley.newRequestQueue(this).add(request);
        });

        // Volver
        btnVolver.setOnClickListener(v -> finish());
    }
}
