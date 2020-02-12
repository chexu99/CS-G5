package com.mygdx.ttrispo.Pieza;

import com.mygdx.ttrispo.Gestores.GestorRecursos;

public class PiezaJ extends Pieza{

    public PiezaJ(int fila,int columna) {
        super(fila, columna);
        tipo = J;
        this.textura = GestorRecursos.get("J.jpg");
        this.imagen = GestorRecursos.get("JCompleta.png");
    }

    public int [][] getPosicionPieza(){ // ¿Deberia llamarse giroPiezaLDerecha, el metodo?
        int [][] nuevaPosicionBloquesPieza = new int[super.numBlock][2]; // Cada fila del array representa las coordenadas de uno de los bloques que forman la pieza
        switch (estadoGiro){
            case(1):
                nuevaPosicionBloquesPieza[0][0] = this.fila; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna - 1; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna + 1;

                nuevaPosicionBloquesPieza[3][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna - 1;
                // estadoGiro = 4; // ¿Actualizar la nueva "orientacion" de la pieza?
                break;

            case(2): // 270º -> 0º  (orientacion 4 a orientacion 1)
                nuevaPosicionBloquesPieza[0][0] = this.fila - 1; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna + 1; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila;
                nuevaPosicionBloquesPieza[2][1] = this.columna;

                nuevaPosicionBloquesPieza[3][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna;
                //  estadoGiro = 1; // ¿Actualizar la nueva "orientacion" de la pieza?
                break;

            case(3): // 0º -> 90º  (orientacion 1 a orientacion 2)
                nuevaPosicionBloquesPieza[0][0] = this.fila - 1; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna - 1; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila - 1;
                nuevaPosicionBloquesPieza[2][1] = this.columna + 1;

                nuevaPosicionBloquesPieza[3][0] = this.fila;
                nuevaPosicionBloquesPieza[3][1] = this.columna + 1;
                // estadoGiro = 2; // ¿Actualizar la nueva "orientacion" de la pieza?
                break;

            case(4):
                // 90º -> 180º  (orientacion 2 a orientacion 3)
                nuevaPosicionBloquesPieza[0][0] = this.fila - 1; // Fila
                nuevaPosicionBloquesPieza[0][1] = this.columna; // Columna

                nuevaPosicionBloquesPieza[1][0] = this.fila;
                nuevaPosicionBloquesPieza[1][1] = this.columna;

                nuevaPosicionBloquesPieza[2][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[2][1] = this.columna;

                nuevaPosicionBloquesPieza[3][0] = this.fila + 1;
                nuevaPosicionBloquesPieza[3][1] = this.columna - 1;
                // estadoGiro = 3; // ¿Actualizar la nueva "orientacion" de la pieza?
                break;
        }
        return nuevaPosicionBloquesPieza;
    }

}
