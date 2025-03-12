package com.example.automarket.Modelo;

public class Furgoneta {

        private int id;                  // ID de la furgoneta
        private String marca;            // Marca de la furgoneta
        private String modelo;           // Modelo de la furgoneta
        private int año;                 // Año de la furgoneta
        private double capacidadCarga;   // Capacidad de carga de la furgoneta

        // Constructor
        public Furgoneta(int id, String marca, String modelo, int año, double capacidadCarga) {
            this.id = id;
            this.marca = marca;
            this.modelo = modelo;
            this.año = año;
            this.capacidadCarga = capacidadCarga;
        }

        // Getters y Setters
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

        public int getAño() {
            return año;
        }

        public void setAño(int año) {
            this.año = año;
        }

        public double getCapacidadCarga() {
            return capacidadCarga;
        }

        public void setCapacidadCarga(double capacidadCarga) {
            this.capacidadCarga = capacidadCarga;
        }

        // Método para obtener la información de la furgoneta en un formato de texto
        public String getFurgonetaInfo() {
            return "Marca: " + marca + "\n" +
                    "Modelo: " + modelo + "\n" +
                    "Año: " + año + "\n" +
                    "Capacidad de carga: " + capacidadCarga + " kg";
        }
}
