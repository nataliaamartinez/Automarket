package com.example.automarket.Modelo;

import java.util.Date;

public class Favorito {
    private int id;
    private int usuarioId;
    private int anuncioId;
    private Date fechaAgregado;
    private Anuncio anuncio;  // Relación con el anuncio
    private Usuario usuario;   // Relación con el usuario

    // Constructor vacío
    public Favorito() {
    }

    // Constructor con parámetros
    public Favorito(int usuarioId, int anuncioId) {
        this.usuarioId = usuarioId;
        this.anuncioId = anuncioId;
        this.fechaAgregado = new Date();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getAnuncioId() {
        return anuncioId;
    }

    public void setAnuncioId(int anuncioId) {
        this.anuncioId = anuncioId;
    }

    public Date getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(Date fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Favorito [id=" + id + ", usuarioId=" + usuarioId + ", anuncioId=" + anuncioId + ", fechaAgregado="
                + fechaAgregado + ", anuncio=" + anuncio + ", usuario=" + usuario + "]";
    }
    
}
