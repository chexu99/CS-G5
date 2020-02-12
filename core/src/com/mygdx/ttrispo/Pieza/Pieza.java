package com.mygdx.ttrispo.Pieza;

import com.badlogic.gdx.graphics.Texture;

public class Pieza {

    public static final int T = 1;
    public static final int S = 2;
    public static final int Z = 3;
    public static final int I = 4;
    public static final int O = 5;
    public static final int L = 6;
    public static final int J = 7;

    public static final int VACIA = 0;

    protected int fila, columna, numBlock;
    protected int tipo, estadoGiro;
    protected Texture textura;
    protected Texture imagen;

    public Pieza(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.numBlock = 4;
        this.estadoGiro = 1;
    }

    public Texture getTextura() {
        return textura;
    }

    public int[][] getPosicionPieza() {
        int[][] coordenadasPieza = new int[numBlock][2];
        return coordenadasPieza;
    }

    public int[][] getPosicionAbajo() {
        int[][] coordenadasPieza = this.getPosicionPieza();
        for (int i = 0; i < coordenadasPieza.length; i++) {
            coordenadasPieza[i][0]++;
        }
        return coordenadasPieza;
    }

    public int[][] getPosicionDerecha() {
        int[][] coordenadasPieza = this.getPosicionPieza();
        for (int i = 0; i < coordenadasPieza.length; i++) {
            coordenadasPieza[i][1]++;
        }
        return coordenadasPieza;
    }

    public int[][] getPosicionIzquierda() {
        int[][] coordenadasPieza = this.getPosicionPieza();
        for (int i = 0; i < coordenadasPieza.length; i++) {
            coordenadasPieza[i][1]--;
        }
        return coordenadasPieza;
    }

    public void bloquear() {
        this.fila = 0;
        this.columna = 5;
        this.estadoGiro = 1;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setEstadoGiro(int estadoGiro) {
        this.estadoGiro += estadoGiro;
    }

    public int getEstadoGiro() {
        return estadoGiro;
    }

    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    public void girarDer() {
        if (estadoGiro < 4) {
            estadoGiro++;
        } else {
            estadoGiro = 1;
        }
    }

    public void girarIz() {
        if (estadoGiro > 1) {
            estadoGiro--;
        } else {
            estadoGiro = 4;
        }
    }

    public int getColumna() {
        return this.columna;
    }

    public int getFila() {
        return this.fila;
    }

    public Texture getImagen() {
        return this.imagen;
    }
}
