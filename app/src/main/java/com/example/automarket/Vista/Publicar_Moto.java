package com.example.automarket.Vista;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;

public class Publicar_Moto extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crear ScrollView para manejar el contenido desplazable
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));

        // Crear un LinearLayout principal para los elementos
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        layout.setPadding(32, 32, 32, 32);

        // Crear Título
        TextView title = new TextView(this);
        title.setText("Publicar Moto");
        title.setTextSize(24);
        title.setTextColor(Color.BLACK);
        title.setPadding(0, 0, 0, 24);
        layout.addView(title);

        // Crear campos de entrada
        EditText etId = createEditText("ID", layout);
        EditText etMarca = createEditText("Marca", layout);
        EditText etModelo = createEditText("Modelo", layout);
        EditText etAnio = createEditText("Año", layout);
        EditText etPrecio = createEditText("Precio", layout);
        EditText etKilometraje = createEditText("Kilometraje", layout);
        EditText etCilindrada = createEditText("Cilindrada", layout);

        // Crear botones Publicar y Cancelar
        Button btnPublicar = new Button(this);
        btnPublicar.setText("Publicar");
        btnPublicar.setBackgroundColor(Color.RED);
        btnPublicar.setTextColor(Color.WHITE);
        btnPublicar.setPadding(16, 16, 16, 16);
        btnPublicar.setOnClickListener(v -> {
            // Acciones al publicar
            String datos = "Publicando:\n" +
                    "ID: " + etId.getText().toString() + "\n" +
                    "Marca: " + etMarca.getText().toString() + "\n" +
                    "Modelo: " + etModelo.getText().toString() + "\n" +
                    "Año: " + etAnio.getText().toString() + "\n" +
                    "Precio: " + etPrecio.getText().toString() + "\n" +
                    "Kilometraje: " + etKilometraje.getText().toString() + "\n" +
                    "Cilindrada: " + etCilindrada.getText().toString();
            Toast.makeText(this, datos, Toast.LENGTH_LONG).show();
        });
        layout.addView(btnPublicar);

        Button btnCancelar = new Button(this);
        btnCancelar.setText("Cancelar");
        btnCancelar.setBackgroundColor(Color.GRAY);
        btnCancelar.setTextColor(Color.WHITE);
        btnCancelar.setPadding(16, 16, 16, 16);
        btnCancelar.setOnClickListener(v -> {
            finish();
        });
        layout.addView(btnCancelar);

        // Agregar el layout principal al ScrollView
        scrollView.addView(layout);

        // Establecer el ScrollView como contenido de la actividad
        setContentView(scrollView);
    }

    // Método auxiliar para crear campos de entrada
    private EditText createEditText(String hint, LinearLayout layout) {
        EditText editText = new EditText(this);
        editText.setHint(hint);
        editText.setHintTextColor(Color.GRAY);
        editText.setTextColor(Color.BLACK);
        editText.setPadding(16, 16, 16, 16);
        editText.setBackgroundResource(android.R.drawable.edit_text);
        editText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        layout.addView(editText);
        return editText;
    }
}
