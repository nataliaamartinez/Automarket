package com.example.automarket.Vista;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;

public class Favoritos extends AppCompatActivity {
    private ListView listViewFavoritos;
    private TextView tvNoFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoritos);

        listViewFavoritos = findViewById(R.id.listViewFavoritos);
        tvNoFavoritos = findViewById(R.id.tvNoFavoritos);

        // Por ahora mostraremos el mensaje de no favoritos
        listViewFavoritos.setVisibility(View.GONE);
        tvNoFavoritos.setVisibility(View.VISIBLE);
    }
}
