package com.mygdx.ttrispo.Pieza;

import com.mygdx.ttrispo.Gestores.GestorRecursos;

public class PiezaS extends Pieza{

    public PiezaS(int fila, int Columna) {
        super(fila, Columna);
        tipo = S;
        this.textura = GestorRecursos.get("S.jpg");
        this.imagen = GestorRecursos.get("SCompleta.png");
    }

    public int [][] getPosicionPieza(){
        int [][] nuevaPosicionBloquesPieza = new int[super.numBlock][4];
        switch (estadoGiro){
            case(1):
                nuevaPosicionBloquesPieza[0][0] = this.fila + 1; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna - 1; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna;

                nuevaPosicionBloquesPieza[3][0] = this.fila;
                nuevaPosicionBloquesPieza[3][1] = this.columna + 1;
                break;

            case(2):
                nuevaPosicionBloquesPieza[0][0] = this.fila - 1; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna + 1;

                nuevaPosicionBloquesPieza[3][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna + 1;
                break;

            case(3):
                nuevaPosicionBloquesPieza[0][0] = this.fila; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna - 1; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[2][1] = this.columna;

                nuevaPosicionBloquesPieza[3][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna + 1;
                break;

            case(4):
                nuevaPosicionBloquesPieza[0][0] = this.fila - 1; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna - 1; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna - 1;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna;

                nuevaPosicionBloquesPieza[3][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna;
                break;
        } 
        return nuevaPosicionBloquesPieza;
    }
}
