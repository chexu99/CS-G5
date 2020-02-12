package com.mygdx.ttrispo.BaseDeDatos;

import java.util.ArrayList;

/**
 * Esta interfaz actua como listener para cuando termine de procesar los datos del servidor
 * de la base de datos de Firebase, me permita trabajar con el ArrayList para el ranking.
 * Para ello, habra que llamar al metodo que contenga como parametro esta misma interfaz, y crear
 * un new FirebaseCallback, siendo asi que dentro del metodo onCallback, se podra jugar con el
 * ArrayList. NO HAY OTRO METODO!!!
 */
public interface FirebaseCallback {
    void onCallback(ArrayList<Jugador> lista);
}
