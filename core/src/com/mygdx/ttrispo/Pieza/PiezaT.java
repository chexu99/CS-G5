package com.mygdx.ttrispo.Pieza;

import com.mygdx.ttrispo.Gestores.GestorRecursos;

public class PiezaT extends Pieza {

    public PiezaT(int fila, int columna) {
        super(fila, columna);
        tipo = T;
        this.textura = GestorRecursos.get("T.jpg");
        this.imagen = GestorRecursos.get("TCompleta.png");
    }

    public int[][] getPosicionPieza() {
        int[][] nuevaPosicionBloquesPieza = new int[super.numBlock][2];
        switch (estadoGiro) {
            case (1):
                //Bloque 1
                nuevaPosicionBloquesPieza[0][0] = this.fila; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna - 1; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna + 1;

                nuevaPosicionBloquesPieza[3][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna;
                break;
            case (2):
                nuevaPosicionBloquesPieza[0][0] = this.fila; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[2][1] = this.columna;

                nuevaPosicionBloquesPieza[3][0] = this.fila;
                nuevaPosicionBloquesPieza[3][1] = this.columna - 1;
                break;
            case (3):
                nuevaPosicionBloquesPieza[0][0] = this.fila; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna - 1;

                nuevaPosicionBloquesPieza[2][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[2][1] = this.columna;

                nuevaPosicionBloquesPieza[3][0] = this.fila;
                nuevaPosicionBloquesPieza[3][1] = this.columna + 1;
                break;
            case (4):
                nuevaPosicionBloquesPieza[0][0] = this.fila; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna + 1;

                nuevaPosicionBloquesPieza[3][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna;
                break;
        }
        return nuevaPosicionBloquesPieza;
    }
}