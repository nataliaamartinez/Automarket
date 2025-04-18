package com.example.automarket.Vista;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.automarket.R;

public class Anuncios extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anuncio);

        // Obtener los datos del Intent
        String marca = getIntent().getStringExtra("marca");
        String modelo = getIntent().getStringExtra("modelo");
        double precio = getIntent().getDoubleExtra("precio", 0.0);
        String descripcion = getIntent().getStringExtra("descripcion");
        // int imagenId = getIntent().getIntExtra("imagen", R.drawable.default_image);  // Imagen por defecto si no se pasa

        // Cargar los datos en las vistas correspondientes
        TextView textViewMarca = findViewById(R.id.textViewMarca);
        TextView textViewModelo = findViewById(R.id.textViewModelo);
        TextView textViewPrecio = findViewById(R.id.textViewPrecio);
        TextView textViewDescripcion = findViewById(R.id.textViewDescripcion);
        ImageView imageViewVehiculo = findViewById(R.id.imageViewVehiculo);

        textViewMarca.setText("Marca: " + marca);
        textViewModelo.setText("Modelo: " + modelo);
        textViewPrecio.setText("Precio: " + precio + "€");
        textViewDescripcion.setText("Descripción: " + descripcion);
        //imageViewVehiculo.setImageResource(imagenId);  // Asumiendo que `imagenId` es el recurso de la imagen

    }
}