package com.example.automarket.Vista;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.automarket.R;

public class Aviso_Legal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aviso_legal);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
} 