package com.example.automarket.Modelo;

public class Anuncio {
    private int id;
    private String marca;
    private String modelo;
    private int anio;
    private int kilometraje;
    private double precio;
    private String descripcion;

    public Anuncio(int id, String marca, String modelo, int anio, int kilometraje, double precio, String descripcion) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.kilometraje = kilometraje;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnio() {
        return anio;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
