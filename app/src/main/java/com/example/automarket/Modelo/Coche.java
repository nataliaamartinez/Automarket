package com.example.automarket.Modelo;

public class Coche {
    private String marca;
    private String modelo;
    private double precio;
    private String anio;
    private String descripcion;
    private int imagenId;  // ID de imagen (puedes utilizar una imagen de recursos)

    // Constructor
    public Coche(String marca, String modelo, double precio, String descripcion, int imagenId) {
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagenId = imagenId;
    }

    // Getters y setters
    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getImagenId() {
        return imagenId;
    }
}
