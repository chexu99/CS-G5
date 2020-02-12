package com.mygdx.ttrispo.Pieza;

import com.mygdx.ttrispo.Gestores.GestorRecursos;

public class PiezaO extends Pieza {

    public PiezaO(int fila, int columna) {
        super(fila, columna);
        tipo = O;
        this.textura = GestorRecursos.get("O.jpg");
        this.imagen = GestorRecursos.get("OCompleta.png");
    }

    public int[][] getPosicionPieza() {
        int[][] nuevaPosicionBloquesPieza = new int[super.numBlock][2];
        switch (estadoGiro) {
            case (1): // 0º -> 90º  (orientacion 1 a orientacion 2)
                nuevaPosicionBloquesPieza[0][0] = this.fila; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna - 1; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[2][1] = this.columna - 1;

                nuevaPosicionBloquesPieza[3][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna;
                break;
            case (2): // 90º -> 180º  (orientacion 2 a orientacion 3)
                nuevaPosicionBloquesPieza[0][0] = this.fila; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[2][1] = this.columna - 1;

                nuevaPosicionBloquesPieza[3][0] = this.fila;
                nuevaPosicionBloquesPieza[3][1] = this.columna - 1;
                break;
            case (3): // 180º -> 270º  (orientacion 3 a orientacion 4)
                nuevaPosicionBloquesPieza[0][0] = this.fila + 1; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[1][1] = this.columna - 1;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna - 1;

                nuevaPosicionBloquesPieza[3][0] = this.fila;
                nuevaPosicionBloquesPieza[3][1] = this.columna;
                break;
            case (4): // 270º -> 0º  (orientacion 4 a orientacion 1)
                nuevaPosicionBloquesPieza[0][0] = this.fila + 1; // Fila
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
