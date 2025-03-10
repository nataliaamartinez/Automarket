package com.example.automarket.Modelo;

public class Anuncio {
    private int id;
    private String titulo;
    private String descripcion;
    private double precio;
    private String categoria;
    private int usuario_id;
    private String fecha;
    private String uriFoto;

    public Anuncio() {
    }

    public Anuncio(int id, String descripcion, String titulo, double precio, String categoria, int usuario_id, String fecha, String uriFoto) {
        this.id = id;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.precio = precio;
        this.categoria = categoria;
        this.usuario_id = usuario_id;
        this.fecha = fecha;
        this.uriFoto = uriFoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUriFoto() {
        return uriFoto;
    }

    public void setUriFoto(String uriFoto) {
        this.uriFoto = uriFoto;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", categoria='" + categoria + '\'' +
                ", usuario_id=" + usuario_id +
                ", fecha='" + fecha + '\'' +
                ", uriFoto='" + uriFoto + '\'' +
                '}';
    }

}