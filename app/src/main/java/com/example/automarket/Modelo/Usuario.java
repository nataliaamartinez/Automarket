package com.example.automarket.Modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String contranenia;


    public Usuario() {
    }

    public Usuario(int id, String nombre, String email, String contranenia) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contranenia = contranenia;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getcontranenia() {
        return contranenia;
    }
    public void setcontranenia(String contranenia) {
        this.contranenia = contranenia;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", telefono=" + contranenia + "]";
    }

} 