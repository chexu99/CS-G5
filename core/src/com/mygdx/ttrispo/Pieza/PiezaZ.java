package com.mygdx.ttrispo.Pieza;

import com.mygdx.ttrispo.Gestores.GestorRecursos;

public class PiezaZ extends Pieza{

    public PiezaZ(int fila, int columna) {
        super(fila, columna);
        tipo = Z;
        this.textura = GestorRecursos.get("Z.jpg");
        this.imagen = GestorRecursos.get("ZCompleta.png");
    }

    public int [][] getPosicionPieza(){
        int [][] nuevaPosicionBloquesPieza = new int[super.numBlock][2];
        switch (estadoGiro){
            case(1):
                nuevaPosicionBloquesPieza[0][0] = this.fila; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna - 1; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[2][1] = this.columna;

                nuevaPosicionBloquesPieza[3][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna + 1;
                break;
            case(2):
                nuevaPosicionBloquesPieza[0][0] = this.fila + 1; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna + 1;

                nuevaPosicionBloquesPieza[3][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna + 1;
                break;

            case(3):
                nuevaPosicionBloquesPieza[0][0] = this.fila - 1; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna - 1; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna;

                nuevaPosicionBloquesPieza[3][0] = this.fila;
                nuevaPosicionBloquesPieza[3][1] = this.columna + 1;
                break;

            case(4):
                nuevaPosicionBloquesPieza[0][0] = this.fila - 1; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna - 1;

                nuevaPosicionBloquesPieza[3][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna - 1;
                break;
        }
        return nuevaPosicionBloquesPieza;
    }

}