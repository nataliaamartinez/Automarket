package com.example.automarket.Modelo;

public class Moto { private int id;
    private String marca;
    private String modelo;
    private int anio;
    private double cilindrada;

    // Constructor
    public Moto(int id, String marca, String modelo, int anio, double cilindrada) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.cilindrada = cilindrada;
    }

    // Métodos getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(double cilindrada) {
        this.cilindrada = cilindrada;
    }

    // Método toString para mostrar la moto
    @Override
    public String toString() {
        return "Moto [ID=" + id + ", Marca=" + marca + ", Modelo=" + modelo + ", anio=" + anio + ", Cilindrada=" + cilindrada + "]";
    }
}
