package com.example.automarket.Vista;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class Panel_Control_User extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crear el ConstraintLayout principal
        ConstraintLayout mainLayout = new ConstraintLayout(this);
        mainLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));
        mainLayout.setBackgroundColor(Color.WHITE);

        // Barra superior
        LinearLayout topBar = new LinearLayout(this);
        topBar.setOrientation(LinearLayout.HORIZONTAL);
        topBar.setBackgroundColor(Color.RED);
        topBar.setPadding(16, 16, 16, 16);
        topBar.setId(View.generateViewId());

        TextView userText = new TextView(this);
        userText.setText("Usuario");
        userText.setTextColor(Color.WHITE);
        userText.setTextSize(18);
        LinearLayout.LayoutParams userTextParams = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        );
        userText.setLayoutParams(userTextParams);

        Button addButton = new Button(this);
        addButton.setText("Añadir +");
        addButton.setBackgroundColor(Color.WHITE);
        addButton.setTextColor(Color.BLACK);

        topBar.addView(userText);
        topBar.addView(addButton);

        // Agregar la barra superior al layout principal
        mainLayout.addView(topBar);

        // Lista de anuncios
        LinearLayout adList = new LinearLayout(this);
        adList.setOrientation(LinearLayout.VERTICAL);
        adList.setId(View.generateViewId());
        adList.setPadding(16, 16, 16, 16);

        for (int i = 1; i <= 3; i++) {
            // Crear contenedor horizontal para cada anuncio
            LinearLayout adRow = new LinearLayout(this);
            adRow.setOrientation(LinearLayout.HORIZONTAL);
            adRow.setPadding(8, 8, 8, 8);
            adRow.setGravity(Gravity.CENTER_VERTICAL);

            // Imagen del anuncio
            ImageView adImage = new ImageView(this);
            adImage.setBackgroundColor(Color.LTGRAY);
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(100, 100);
            adImage.setLayoutParams(imageParams);

            // Texto del anuncio
            TextView adText = new TextView(this);
            adText.setText("Anuncio " + i);
            adText.setPadding(16, 0, 0, 0);
            adText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

            // Botones Borrar y Modificar
            Button deleteButton = new Button(this);
            deleteButton.setText("Borrar");
            Button modifyButton = new Button(this);
            modifyButton.setText("Modificar");

            // Agregar elementos al contenedor del anuncio
            adRow.addView(adImage);
            adRow.addView(adText);
            adRow.addView(deleteButton);
            adRow.addView(modifyButton);

            // Agregar anuncio a la lista
            adList.addView(adRow);
        }

        // Agregar la lista de anuncios al layout principal
        mainLayout.addView(adList);

        // Barra inferior
        LinearLayout bottomBar = new LinearLayout(this);
        bottomBar.setOrientation(LinearLayout.HORIZONTAL);
        bottomBar.setBackgroundColor(Color.RED);
        bottomBar.setPadding(16, 16, 16, 16);
        bottomBar.setId(View.generateViewId());

        // Sección izquierda (Aviso Legal, Redes Sociales, CopyRight)
        LinearLayout leftSection = new LinearLayout(this);
        leftSection.setOrientation(LinearLayout.VERTICAL);
        leftSection.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        TextView legalText = new TextView(this);
        legalText.setText("Aviso Legal");
        legalText.setTextColor(Color.WHITE);

        TextView socialText = new TextView(this);
        socialText.setText("Redes sociales");
        socialText.setTextColor(Color.WHITE);

        TextView copyrightText = new TextView(this);
        copyrightText.setText("CopyRight");
        copyrightText.setTextColor(Color.WHITE);

        leftSection.addView(legalText);
        leftSection.addView(socialText);
        leftSection.addView(copyrightText);

        // Sección derecha (Contáctanos y Mapa)
        LinearLayout rightSection = new LinearLayout(this);
        rightSection.setOrientation(LinearLayout.VERTICAL);
        rightSection.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        rightSection.setGravity(Gravity.CENTER);

        TextView contactText = new TextView(this);
        contactText.setText("Contáctanos");
        contactText.setTextColor(Color.WHITE);

        View mapView = new View(this);
        mapView.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams mapParams = new LinearLayout.LayoutParams(200, 200);
        mapView.setLayoutParams(mapParams);

        rightSection.addView(contactText);
        rightSection.addView(mapView);

        bottomBar.addView(leftSection);
        bottomBar.addView(rightSection);

        // Agregar la barra inferior al layout principal
        mainLayout.addView(bottomBar);

        // Configuración de ConstraintLayout
        ConstraintSet set = new ConstraintSet();
        set.clone(mainLayout);

        set.connect(topBar.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        set.connect(topBar.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        set.connect(topBar.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        set.connect(adList.getId(), ConstraintSet.TOP, topBar.getId(), ConstraintSet.BOTTOM);
        set.connect(adList.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        set.connect(adList.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        set.connect(adList.getId(), ConstraintSet.BOTTOM, bottomBar.getId(), ConstraintSet.TOP);

        set.connect(bottomBar.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        set.connect(bottomBar.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        set.connect(bottomBar.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        set.applyTo(mainLayout);

        // Establecer el layout principal como contenido
        setContentView(mainLayout);
    }
}
