package com.example.automarket.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.Modelo.Anuncio;
import com.example.automarket.R;

import java.util.ArrayList;

public class ListaAnunciosActivity extends AppCompatActivity {
    private ListView listViewAnuncios;
    private Button btnNuevoAnuncio;
    private ArrayList<String> listaAnuncios;
    private ArrayAdapter<String> anunciosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_anuncios);

        // Inicializar vistas
        listViewAnuncios = findViewById(R.id.listViewAnuncios);
        btnNuevoAnuncio = findViewById(R.id.btnNuevoAnuncio);

        // Inicializar la lista de anuncios
        listaAnuncios = new ArrayList<>();
        listaAnuncios.add("Anuncio 1");
        listaAnuncios.add("Anuncio 2");
        listaAnuncios.add("Anuncio 3"); // Añadir más anuncios de ejemplo

        // Crear un adaptador para la lista
        anunciosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaAnuncios);
        listViewAnuncios.setAdapter(anunciosAdapter);

        // Configurar el click listener para la lista
        listViewAnuncios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el anuncio seleccionado
                String anuncioSeleccionado = listaAnuncios.get(position);

                // Aquí puedes abrir una actividad para modificar el anuncio
                Intent intent = new Intent(ListaAnunciosActivity.this, ModificarAnuncioActivity.class);
                intent.putExtra("anuncio", anuncioSeleccionado); // Pasar el anuncio seleccionado
                startActivity(intent);
            }
        });

        // Configurar el listener para el botón de añadir nuevo anuncio
        btnNuevoAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes abrir una actividad para agregar un nuevo anuncio
                Intent intent = new Intent(ListaAnunciosActivity.this, ModificarAnuncioActivity.class);
                intent.putExtra("anuncio", ""); // Pasar un valor vacío para indicar un nuevo anuncio
                startActivity(intent);
            }
        });
    }
}
