package com.mygdx.ttrispo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.ttrispo.Pantalla.Partida;

public class Tablero extends Actor {
    public static int tablero[][];
    private Partida partida;
    private Texture imagenPiezaSiguiente;
    public  int tamanyoPiezaX = (int) (50 * MyGdxGame.ratioPixelesWidth); //Tamaño Pieza
    public  int tamanyoPiezaY = (int) (51 * MyGdxGame.ratioPixelesHeight);
    //son cuadradas, pero no va si no lo hacemos de este modo
    public  int tableroX = (int) (124 * MyGdxGame.ratioPixelesWidth);
    public  int tableroY = (int) (250 * MyGdxGame.ratioPixelesHeight);
    private Image vPieza;

    private float posicionXcaja = 567*MyGdxGame.ratioPixelesWidth;
    private float posicionYcaja = 1404*MyGdxGame.ratioPixelesHeight;
    private float dimensionXcaja = 108*MyGdxGame.ratioPixelesWidth;
    private float dimensionYcaja = 108*MyGdxGame.ratioPixelesHeight;

    private Table caja;
    private Sound sonidoFila;
    private Texture bgTab;

    private boolean pintarAvance;
    private float cuantasFilas;

    public Tablero(Partida partida) {
        this.partida = partida;
        this.tablero = new int[10][20];
        this.caja = new Table();
        this.caja.setPosition(posicionXcaja, posicionYcaja);
        this.caja.setSize(dimensionXcaja, dimensionYcaja);
        this.partida.getEscenario().addActor(caja);
        this.vPieza = null;
        this.sonidoFila = Gdx.audio.newSound(Gdx.files.internal("Music/lightsaber_04.wav"));
        this.bgTab = new Texture(Gdx.files.internal("bg_tablero.png"));
        pintarAvance = false;
        cuantasFilas = 0f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int posicionX,posicionY,tipo;
        Texture imagenBloque;

        if(pintarAvance){
            ponerBackgroundFilas(batch);
        }

        for (int i = 0; i < this.tablero.length; i++){
            for (int j = 0; j < this.tablero[i].length; j++){

                posicionX = (tamanyoPiezaX * i);
                posicionY = Gdx.graphics.getHeight() - (tamanyoPiezaY * j);

                tipo = this.tablero[i][j];
                if(tipo >= 1) {
                    imagenBloque = partida.getTexturaPieza(tipo);
                    batch.draw(imagenBloque, posicionX + tableroX, posicionY - tableroY, 0, 0, (int)(tamanyoPiezaX-0.5f), (int)(tamanyoPiezaY-0.5f));
                }
            }
        }
        if (imagenPiezaSiguiente != null) {
            if(caja.getChildren().size>0){
                caja.reset();
            }
            vPieza = new Image(imagenPiezaSiguiente);
            if(imagenPiezaSiguiente.getWidth()>=219){ //Dimensiones pieza T, S, Z, J y L
                caja.add(vPieza).size(dimensionXcaja, dimensionYcaja/2);
            }else if(imagenPiezaSiguiente.getWidth()==74){ //Dimensiones pieza I
                caja.add(vPieza).size(dimensionXcaja/3, dimensionYcaja);
            }else if(imagenPiezaSiguiente.getWidth()==147){ //Dimensiones pieza O
                caja.add(vPieza).size(dimensionXcaja/2, dimensionYcaja/2);
            }
        }
    }

    public void ponerBackgroundFilas(Batch batch) {
        int cF = (int) getCuantasFilas();
        int posicionX,posicionY;
        for(int i=0; i < this.tablero.length; i++){ //columnas
            for(int j=0; j < cF; j++){              //filas
                tablero[i][j] = 0;
                posicionX = (tamanyoPiezaX * i);
                posicionY = Gdx.graphics.getHeight() - (tamanyoPiezaY * j);
                batch.draw(bgTab, posicionX + tableroX, posicionY - tableroY, 0, 0, (int)(tamanyoPiezaX-0.5f), (int)(tamanyoPiezaY-0.5f));
            }
        }
    }

    public float getCuantasFilas(){
        return this.cuantasFilas;
    }
    public void setCuantasFilas(float cuantasFilas){
        this.pintarAvance = true;
        this.cuantasFilas = cuantasFilas;
    }

    public void insertarBloquesDePieza(int bloques[][], int tipo) {
        int columnas, filas;
        for (int i = 0; i < bloques.length; i++) {
            columnas = bloques[i][1];
            filas = bloques[i][0];

            // Comprobar si se sale de la pantalla
            if(columnas < 10 && columnas >= 0 && filas >= 0 && filas < 20){
                tablero[bloques[i][1]][bloques[i][0]] = tipo;
            }
        }
    }

    public boolean seProduceColision(int bloques[][]){
        int columnas, filas;
        try {
            for (int i = 0; i < bloques.length; i++) {
                columnas = bloques[i][1];
                filas = bloques[i][0];
                // Comprobar si se sale de la pantalla
                if (columnas >= 10 || filas >= 20) {
                    return true;
                }
                // Colisiona con otro bloque
                if (tablero[bloques[i][1]][bloques[i][0]] != 0) {
                    return true;
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){ //Lanzo Excepcion
            return true;
        }
        return false;
    }
    /*
    A REFACTORIZAR.
    El numero de filas posicionY columnas. No consigo obtener correctamente el el numero
     */
    public boolean comprobarLineaCompleta() {
        int numeroColumnas = 10;
        int filas = 20;
        int valorFila = 0;
        int lineas=0;

        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 10; j++){
                if(tablero[j][i]!= 0){
                    valorFila++;
                }
            }

            if(valorFila == numeroColumnas){
                partida.setPuntuacion(30);
                eliminarfila(i);
                lineas++;
                bajarFilaAnterior(i);
            }
            valorFila = 0;
        }
        if (lineas == 1)
        {
            setMismoColorPiezas((int) Math.round(Math.random()*6+1));
        }
        else if(lineas > 1)
        {
            setRandomColorPiezas();
        }
        return false;
    }

    private void eliminarfila(int fila) {
        for(int j = 0; j < 10; j++){
            tablero[j][fila] = 0;
            long id = sonidoFila.play(0.3f);
            sonidoFila.setPitch(id, 4);
        }
    }

    public boolean comprobarGameOver(int bloques[][]){
        for (int i = 0; i < bloques.length; i++) {
            if (bloques[i][0] == 0 || bloques[i][0] == Partida.filaGenera){
                return true;
            }
        }
        return false;
    }

    /**
     Se recoge la fila eliminada posicionY desde ahi hasta arriba se copia la fila anterior en la actual.
     Habria que darle una vuelta en el siguiente sprint porque es un poco chapuza.

     Posible refatorizacion: Si son todpo 0 pues que no baje mas, pero eso ya mas adelante
     */
    private void bajarFilaAnterior(int fila) {
        for(;fila > 0; fila--){
            for(int c = 0; c < 10; c++){
                tablero[c][fila] = tablero[c][fila-1];
            }
        }
        for (int i = 0; i < 10; i++){ //Limpiamos la primera fila
            tablero[i][0] = 0;
        }
    }

    public void setImagenPiezaSiguiente(Texture imagenPiezaSiguiente) {
        this.imagenPiezaSiguiente = imagenPiezaSiguiente;
    }

    // Cambia el tipo (color) de todos los bloques que hay en el tablero por la del tipo(color) de la pieza en uso.
    public void setMismoColorPiezas(int tipo)
    {
        for (int i = 0; i < this.tablero.length; i++){
            for (int j = 0; j < this.tablero[i].length; j++){
                if (tablero[i][j] > 0)
                {
                    tablero[i][j]= tipo;
                }
            }
        }
    }

    // Cambia el tipo (color) de todos los bloques que hay en el tablero de forma aleatoria
    public void setRandomColorPiezas()
    {
        for (int i = 0; i < this.tablero.length; i++){
            for (int j = 0; j < this.tablero[i].length; j++){
                if (tablero[i][j] > 0)
                {
                    tablero[i][j]= (int) Math.round(Math.random()*6+1);
                }
            }
        }
    }
}
