package com.mygdx.ttrispo.BaseDeDatos;

public class Jugador {
    private String nombre;
    private long puntuacion;
    private String imagen;

    public Jugador(String nombre, long puntuacion, String imagen) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(long puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
