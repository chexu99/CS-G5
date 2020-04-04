package com.mygdx.ttrispo.Gestores;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.ttrispo.Pantalla.PantallaAjustes;
import com.mygdx.ttrispo.Pantalla.Partida;
import com.mygdx.ttrispo.Pieza.Pieza;
import com.mygdx.ttrispo.Pieza.PiezaI;
import com.mygdx.ttrispo.Pieza.PiezaJ;
import com.mygdx.ttrispo.Pieza.PiezaL;
import com.mygdx.ttrispo.Pieza.PiezaO;
import com.mygdx.ttrispo.Pieza.PiezaS;
import com.mygdx.ttrispo.Pieza.PiezaT;
import com.mygdx.ttrispo.Pieza.PiezaZ;

import java.util.ArrayList;
import java.util.Random;

public class GestorPiezas {
    private final Partida partida;

    private Pieza piezas[]; //Array de Tipo Pieza con las piezas
    public static ArrayList<Integer> listaPiezasSiguientes; //Cola de Piezas para el uso en partida
    private Pieza piezaActual;
    private Random rand;

    /**
     * Constructor de las clase {@link GestorPiezas}
     * Inicializa un {@link ArrayList} de piezas posicionY lo rellena.
     * @param partida partida que se esta ejecutando en curso.
     */

    /**
     * Rellena hasta un maximo de 4 piezas
     */
    private void rellenarListaPiezasSiguientes() {
        while (listaPiezasSiguientes.size() < 4) {
            listaPiezasSiguientes.add(dameTipoPiezaAleatorio());
        }
    }

    /**
     * Retorna un numero alateatorio que sera el numero correspondiente al tipo de pieza de la lista.
     * Genera un random posicionY recoge el resultado.
     * @return Integer, el numero random que representa el tipo de pieza
     */
    private int dameTipoPiezaAleatorio() {
        this.rand = new Random();
        return rand.nextInt(7) + 1;
    }
    /**
     * Para acortar la pantalla, es mas facil si editamos la fila de la pieza que vaya a salir.
     */
    public Pieza[] getPiezas() {
        return piezas;
    }

    /**
     * Inicializa posicionY rellena el array de piezas.
     * Inicializacion de 7 piezas con la fila 0 posicionY la columna 5.
     */

    private void addPiezas() {
        this.piezas = new Pieza[8];
        //Las piezas estan numeradas, cada una se coloca en su posicion dentro del array
        piezas[0]=null;
        piezas[Pieza.T] = new PiezaT(0, 5);
        piezas[Pieza.S] = new PiezaS(0, 5);
        piezas[Pieza.Z] = new PiezaZ(0, 5);
        piezas[Pieza.I] = new PiezaI(0, 5);
        piezas[Pieza.O] = new PiezaO(0, 5);
        piezas[Pieza.L] = new PiezaL(0, 5);
        piezas[Pieza.J] = new PiezaJ(0, 5);
        if(PantallaAjustes.getColoresPersonalizados()){
            for(int i = 1; i<=7; i++){
                piezas[i].setTextura(PantallaAjustes.texturaPiezas.get(i));

            }
        }
    }

    public GestorPiezas(Partida partida) {
        listaPiezasSiguientes = new ArrayList<>(); //Lista con las siguientes Piezas a entrar al tablero
        this.partida = partida;

        this.addPiezas(); //Rellenamos el ArrayList con los tipos de pieza
        rellenarListaPiezasSiguientes();
    }

    /**
     * Obtiene la pieza actual.
     * Si no encontramos el objeto pieza, obtenemos una {@link Pieza} nueva posicionY rellenamos la cola.
     * @return retornamos una {@link Pieza}
     */

    public Pieza getPiezaActual() {
        if (piezaActual == null) { //Si no tenemos pieza actual
            piezaActual = piezas[listaPiezasSiguientes.remove(0)]; //Cogemos la primera de la lista
            rellenarListaPiezasSiguientes(); //Y rellenamos la lista de nuevo
        }
        return piezaActual;
    }

    /**
     * Metodo que pide a la pieza que se bloquee posicionY coloca la piezaActual a null, debido a que al
     * estar bloqueada ya no puede seguir interactuando.
     */

    public void bloquearPieza() {
        piezaActual.bloquear(); //Bloqueamos pieza
        piezaActual = null; //No tenemos pieza actual
    }

    /**
     *
     * @return
     */
    public Texture getImagenPiezaSiguiente() {
        if (piezas[listaPiezasSiguientes.get(0)] == null) { //Si no hubiese pieza en la lista la rellenamos
            rellenarListaPiezasSiguientes();
        }
        return piezas[listaPiezasSiguientes.get(0)].getImagen();
    }

    /**
     * Obtiene la textura de la {@link Pieza} que se pasa por parametro
     * @param tipo {@link Pieza} que se quiere obtener la textura
     * @return la {@link Texture} que corresponde a la {@link Pieza}
     */
    public Texture getTexturaBloque(int tipo) {
        return piezas[tipo].getTextura();
    }
}
