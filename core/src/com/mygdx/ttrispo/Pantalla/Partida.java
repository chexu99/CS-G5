package com.mygdx.ttrispo.Pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.ttrispo.Botones.BotonBase;
import com.mygdx.ttrispo.Gestores.GestorEstado;
import com.mygdx.ttrispo.Gestores.GestorPiezas;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.MyGdxGame;
import com.mygdx.ttrispo.Pieza.Pieza;
import com.mygdx.ttrispo.Tablero;

import java.util.ArrayList;
import java.util.Random;

public class Partida extends PantallaBase {
    private Texture fondoPartida;
    private Tablero tablero;
    private ProgresoPartida progresoPartida;
    private GestorEstado gestorEstado;
    private GestorPiezas gestorPiezas;
    private static GestorEstado gestorEstado2ndPieza;
    private GestorPiezas gestorPiezas2ndPieza;
    public static float posicionX, posicionY;
    private static long puntuacion;
    public static Partida partidaAux;
    private int longitudPuntos;
    public static int filaGenera;
    private boolean segundaPieza, cambiarFila;;

    private ArrayList<Music> listaCanciones; //lista de canciones de los 80s
    private Music cancion80sActual, cancion80sAnterior;
    private Random NumAleatorio;
    private float Seconds20 = 20f;
    private float Thirty30 = 30f;
    private float Fifty50 = 50f;
    private float timeSeconds, fiftySeconds, thirySeconds = 0f;
    private int numCanciones = 9;

    private BotonBase bb;

    public Partida(MyGdxGame game) {
        super(game);
        gestorEstado = new GestorEstado(this);
        gestorPiezas = new GestorPiezas(this);
        filaGenera = 0;
        cambiarFila = false;

        //SEGUNDA PIEZA
        segundaPieza = false;
        gestorEstado2ndPieza = new GestorEstado(this);
        gestorPiezas2ndPieza = new GestorPiezas(this);

        bb = new BotonBase(stage, gestorEstado, gestorEstado2ndPieza);
        fondoPartida = GestorRecursos.get("background.jpeg");

        tablero = new Tablero(this);
        tablero.setPosition(posicionX, posicionY);
        progresoPartida = new ProgresoPartida(this);

        stage.addActor(tablero);
        stage.addActor(progresoPartida);
        this.longitudPuntos = 0;
        this.puntuacion = 0;
        stage.addActor(bb);
    }

    private void cargarArray(ArrayList<Music> listaCanciones) {
        listaCanciones.add(Gdx.audio.newMusic(Gdx.files.internal("Music/Original Tetris Soundtrack.mp3")));
        listaCanciones.add(Gdx.audio.newMusic(Gdx.files.internal("Music/Tetris 99 - Main Theme.mp3")));
        listaCanciones.add(Gdx.audio.newMusic(Gdx.files.internal("Music/Africa.mp3")));
        listaCanciones.add(Gdx.audio.newMusic(Gdx.files.internal("Music/Stayin Alive.mp3")));
        listaCanciones.add(Gdx.audio.newMusic(Gdx.files.internal("Music/Take On Me.mp3")));
        listaCanciones.add(Gdx.audio.newMusic(Gdx.files.internal("Music/Youre The One That I Want.mp3")));
        listaCanciones.add(Gdx.audio.newMusic(Gdx.files.internal("Music/Last Christmas.mp3")));
        listaCanciones.add(Gdx.audio.newMusic(Gdx.files.internal("Music/Blame It On the Boogie.mp3")));
        listaCanciones.add(Gdx.audio.newMusic(Gdx.files.internal("Music/Girls just wanna have fun.mp3")));
        listaCanciones.add(Gdx.audio.newMusic(Gdx.files.internal("Music/Mustafar.mp3")));
    }

    private void veinteSegundos(float delta) {
        timeSeconds += Gdx.graphics.getDeltaTime();
        if(timeSeconds >= Seconds20){
            nextCancion();
            System.out.println("tiempo" + timeSeconds);
            timeSeconds -= Seconds20;
        }
    }
    private void treintaSegundos(float delta) {
        thirySeconds += Gdx.graphics.getDeltaTime();
        if(thirySeconds >= Thirty30){
            thirySeconds -= Thirty30;
            segundaPieza = true;
        }
    }
    private void cincuentaSegundos(float delta) {

        fiftySeconds += Gdx.graphics.getDeltaTime();
        if(fiftySeconds >= Fifty50){
            fiftySeconds -= Fifty50;
            filaGenera+=2;
            cambiarFila = true;
            tablero.setCuantasFilas(filaGenera);
        }
    }

    private void nextCancion() { //ArrayList<Music> listaCanciones
        if (cancion80sActual == null) {
            System.out.println("array vacio");
            listaCanciones = new ArrayList<>();
            cargarArray(listaCanciones);
        }
        if (!(cancion80sAnterior == null)) {
            cancion80sAnterior.stop();
            System.out.println("cancion eliminada " + cancion80sAnterior);
        }
        cancion80sActual = listaCanciones.get(dameNumAleatorio());
        cancion80sActual.play();
        System.out.println("cancion actual " + cancion80sActual);
        cancion80sAnterior = cancion80sActual;
    }

    private int dameNumAleatorio() {
        this.NumAleatorio = new Random();
        return NumAleatorio.nextInt(numCanciones) + 1;
    }

    @Override
    public void show() {
        super.show();
        nextCancion();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(fondoPartida, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        cicloDeVida(delta); // Ciclo de vida
        stage.draw();  // Pintar los actores
    }

    private void cicloDeVida(float delta) {
        veinteSegundos(delta);
        treintaSegundos(delta);
        switch (gestorEstado.getEstado(delta)) {
            case (GestorEstado.REPOSO): //Si el Gestor esta en reposo
                if (gestorPiezas.getPiezaActual() == null) { //Y no hay pieza siguiente
                    gestorEstado.setEstado(GestorEstado.SINPIEZA); //Modo Sin Pieza
                }
                break;

            case (GestorEstado.SINPIEZA):
                estadoGestorSinPieza(gestorPiezas.getPiezaActual()); //Selecciona una nueva Pieza y vuelve al modo de Reposo
                gestorEstado.setVelocity(gestorEstado.getVelocity()-0.005f); //Velocdad
                gestorEstado.setEstado(GestorEstado.REPOSO);
                break;

            case (GestorEstado.IZQUIERDA):
                estadoGestorDesplazarIzq(gestorPiezas.getPiezaActual());
                gestorEstado.setEstado(gestorEstado.REPOSO);
                break;

            case (GestorEstado.DERECHA):
                moverDerechaState(gestorPiezas.getPiezaActual());
                gestorEstado.setEstado(gestorEstado.REPOSO);
                break;
            // La pieza intenta caer
            case (GestorEstado.CAER):
                if(caerState(gestorPiezas.getPiezaActual())){
                    gestorEstado.setEstado(gestorEstado.REPOSO);
            }else{
                    gestorEstado.setEstado(gestorEstado.BLOQUEAR);
            }
                break;
            case (GestorEstado.GIRO):
                giroState(gestorPiezas.getPiezaActual());
                gestorEstado.setEstado(gestorEstado.REPOSO);
                break;
            case (GestorEstado.BLOQUEAR):
                bloquearPieza(gestorPiezas.getPiezaActual(), gestorPiezas);
                if(cambiarFila){
                    for(int i=0; i<gestorPiezas.listaPiezasSiguientes.size(); i++){
                        gestorPiezas.getPiezas()[gestorPiezas.listaPiezasSiguientes.get(i)].setFila(filaGenera);
                    }
                    cambiarFila = false;
                }
                gestorEstado.setEstado(GestorEstado.SINPIEZA);
                break;
        }
        if(segundaPieza) {
            switch (gestorEstado2ndPieza.getEstado2(delta)) {
                case (GestorEstado.REPOSO2): //Si el Gestor esta en reposo
                    if (gestorPiezas2ndPieza.getPiezaActual() == null) { //Y no hay pieza siguiente
                        gestorEstado2ndPieza.setEstado2(GestorEstado.SINPIEZA2); //Modo Sin Pieza
                    }
                    break;

                case (GestorEstado.SINPIEZA2):
                    estadoGestorSinPieza(gestorPiezas2ndPieza.getPiezaActual()); //Selecciona una nueva Pieza y vuelve al modo de Reposo
                    gestorEstado2ndPieza.setVelocity2(gestorEstado2ndPieza.getVelocity2()-0.02f); //Velocdad
                    gestorEstado2ndPieza.setEstado2(GestorEstado.REPOSO2);
                    break;

                case (GestorEstado.IZQUIERDA2):
                    estadoGestorDesplazarIzq(gestorPiezas2ndPieza.getPiezaActual());
                    gestorEstado2ndPieza.setEstado2(GestorEstado.REPOSO2);
                    break;

                case (GestorEstado.DERECHA2):
                    moverDerechaState(gestorPiezas2ndPieza.getPiezaActual());
                    gestorEstado2ndPieza.setEstado2(GestorEstado.REPOSO2);
                    break;
                // La pieza intenta caer
                case (GestorEstado.CAER2):
                    if(caerState(gestorPiezas2ndPieza.getPiezaActual())){
                        gestorEstado2ndPieza.setEstado2(GestorEstado.REPOSO2);
                    }else{
                        gestorEstado2ndPieza.setEstado2(gestorEstado.BLOQUEAR2);
                    }
                    break;
                case (GestorEstado.GIRO2):
                    giroState(gestorPiezas2ndPieza.getPiezaActual());
                    gestorEstado2ndPieza.setEstado2(GestorEstado.REPOSO2);
                    break;
                case (GestorEstado.BLOQUEAR2):
                    bloquearPieza(gestorPiezas2ndPieza.getPiezaActual(), gestorPiezas2ndPieza);
                    gestorEstado2ndPieza = new GestorEstado(this);
                    gestorPiezas2ndPieza = new GestorPiezas(this);
                    bb.setNewGestorPara2(gestorEstado2ndPieza);
                    segundaPieza = false;
                    break;
            }
        }
        cincuentaSegundos(delta);
    }

    private void bloquearPieza(Pieza pieza, GestorPiezas gp) {
        tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), pieza.getTipo());
        partidaAux = this;
        if (tablero.comprobarGameOver(pieza.getPosicionPieza())) {
            this.dispose();
            game.setScreen(game.pantallaGameOver);
        }
        tablero.comprobarLineaCompleta();
        gp.bloquearPieza();
    }

    private void giroState(Pieza pieza) {
        tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), Pieza.VACIA);
        pieza.girarDer();
        int piezaGirada[][] = pieza.getPosicionPieza();
        if (tablero.seProduceColision(piezaGirada)) {
            // La pieza no puede girar
            pieza.girarIz();
            tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), pieza.getTipo());
        } else {
            tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), pieza.getTipo());
        }

    }

    private boolean caerState(Pieza pieza) {

        int posicionPiezaAbajo[][] = pieza.getPosicionAbajo();
        tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), Pieza.VACIA);
        if (tablero.seProduceColision(posicionPiezaAbajo)) {
            return false;
        } else {
            // La pieza puede baja
            tablero.insertarBloquesDePieza(posicionPiezaAbajo, pieza.getTipo());
            pieza.setFila(pieza.getFila() + 1);
            return true;
        }
    }

    private void moverDerechaState(Pieza pieza) {
        int posicionPiezaDerecha[][] = pieza.getPosicionDerecha();
        tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), Pieza.VACIA);
        if (tablero.seProduceColision(posicionPiezaDerecha)) {
            tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), pieza.getTipo());
            //si no puede seguir moviendo a la derecha pues ahi se queda
        } else {
            tablero.insertarBloquesDePieza(posicionPiezaDerecha, pieza.getTipo());
            pieza.setColumna(pieza.getColumna() + 1);
        }
        //Cambia a reposo. pero esto hay que refactorizarlo por el amor de dios

    }

    private void estadoGestorDesplazarIzq(Pieza pieza) {
        int posicionPiezaIzquierda[][] = pieza.getPosicionIzquierda();
        tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), Pieza.VACIA);

        if (tablero.seProduceColision(posicionPiezaIzquierda)) {
            tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), pieza.getTipo());
            //si no puede seguir moviendo a la izquierda pues ahi se queda
        } else {
            tablero.insertarBloquesDePieza(posicionPiezaIzquierda, pieza.getTipo());
            pieza.setColumna(pieza.getColumna() - 1);
        }
        //Cambia a reposo. pero esto hay que refactorizarlo por el amor de dios
}

    private void estadoGestorSinPieza(Pieza pieza) {
        tablero.insertarBloquesDePieza(pieza.getPosicionPieza(), pieza.getTipo());
        tablero.setImagenPiezaSiguiente(gestorPiezas.getImagenPiezaSiguiente());
    }

    public Texture getTexturaPieza(int tipo){
        return gestorPiezas.getTexturaBloque(tipo);
    }
    
    public long getPuntuacion(){
        return this.puntuacion;
    }
    
    public void setPuntuacion(int i) {
        long nuevaPuntuacion = puntuacion + i;
        if(String.valueOf(puntuacion).length() < (String.valueOf(nuevaPuntuacion).length())){
            System.out.println("ESTADO TRUE");
            longitudPuntos++;
        }else{
            System.out.println("ESTADO FALSE");
        }
        puntuacion = nuevaPuntuacion;
    }

    public int getLongitudPuntos(){
        return this.longitudPuntos;
    }

    public Stage getEscenario() {
        return stage;
    }

    public void hide() {
        cancion80sAnterior.stop();
        cancion80sActual.stop();
    }

    @Override
    public void dispose() {
        super.dispose();
         try {
            cancion80sActual.stop();
            cancion80sAnterior.stop();
        }catch (NullPointerException npe){
           cancion80sActual.dispose();
           cancion80sAnterior.dispose();
        }
        listaCanciones.clear();
    }
}
