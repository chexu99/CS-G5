package com.mygdx.ttrispo.Gestores;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import com.mygdx.ttrispo.MyGdxGame;
import com.mygdx.ttrispo.Pantalla.PantallaGameOver;
import com.mygdx.ttrispo.com.mygdx.ttrispo.camara.InterfazCamara;

import static java.lang.Thread.sleep;

public  class GestorRecursos {
    private static AssetManager manager = new AssetManager();
    private static int contador = 0;

    public static void cargarImagenes() {
        manager.load("fondoInicio.jpg", Texture.class);
        manager.load("tetris.png", Texture.class);
        manager.load("B-start.png", Texture.class);
        manager.load("B-ajustes.png", Texture.class);
        manager.load("colorPiezas.png", Texture.class);
        manager.load("B-atras.png", Texture.class);
        manager.load("B-retry.png", Texture.class);
        manager.load("B-home.png", Texture.class);
        manager.load("colorPiezas.png", Texture.class);

        manager.load("B-dere.jpg", Texture.class);
        manager.load("BP-dere.jpeg", Texture.class);
        manager.load("B-iz.jpg", Texture.class);
        manager.load("BP-iz.jpeg", Texture.class);
        manager.load("B-Rot.jpg", Texture.class);
        manager.load("BP-Rot.jpeg", Texture.class);
        manager.load("B-abajo.jpg", Texture.class);
        manager.load("BP-abajo.jpeg", Texture.class);

        manager.load("T.jpg", Texture.class);
        manager.load("S.jpg", Texture.class);
        manager.load("Z.jpg", Texture.class);
        manager.load("J.jpg", Texture.class);
        manager.load("L.jpg", Texture.class);
        manager.load("I.jpg", Texture.class);
        manager.load("O.jpg", Texture.class);
        manager.load("TCompleta.png", Texture.class);
        manager.load("SCompleta.png", Texture.class);
        manager.load("ZCompleta.png", Texture.class);
        manager.load("JCompleta.png", Texture.class);
        manager.load("LCompleta.png", Texture.class);
        manager.load("ICompleta.png", Texture.class);
        manager.load("OCompleta.png", Texture.class);
        manager.load("background.jpeg", Texture.class);
        manager.load("GameOver.jpeg", Texture.class);

        //musica
        manager.load("Music/The Force Theme.mp3", Music.class); //inicio
        manager.load("Music/Imperial March.mp3", Music.class); //gameover
        manager.load("Music/lightsaber_04.wav", Sound.class);  //partida
        manager.load("Music/The Force Suite.mp3", Music.class); // inicio sustituti

        //aleatorias
        manager.load("Music/Africa.mp3", Music.class);
        manager.load("Music/Blame It On the Boogie.mp3", Music.class);
        manager.load("Music/Girls just wanna have fun.mp3",Music.class);
        manager.load("Music/Last Christmas.mp3", Music.class);
        manager.load("Music/Mustafar.mp3", Music.class);
        manager.load("Music/Never Gonna Give You Up.mp3", Music.class);
        manager.load("Music/Original Tetris Soundtrack.mp3", Music.class);
        manager.load("Music/Stayin Alive.mp3", Music.class);
        manager.load("Music/Take On Me.mp3", Music.class);
        manager.load("Music/Tetris 99 - Main Theme.mp3", Music.class);
        manager.load("Music/Youre The One That I Want.mp3", Music.class);


        manager.load("profile.png", Texture.class);
        while (!manager.update()) {
            //System.out.println("Cargando...");
            contador++;
        }
    }

    public static void cargarPrevia(final InterfazCamara interfazCamara){
        for(int i = 1; i<=10; i++){
            interfazCamara.getImagenConPosicion(i);
            while(interfazCamara.getTamanioDescargadoImagen() != interfazCamara.getTamanioTotalImagen());
            if(interfazCamara.getTamanioDescargadoImagen() == interfazCamara.getTamanioTotalImagen()){
                System.out.println("Descarga completada imagen " + i);
                MyGdxGame.VARIABLE_GLOBAL_PROGRESO+=0.05f;
                interfazCamara.setTamanioDescargadoImagen(0);
                interfazCamara.setTamanioTotalImagen(1);
            }
        }
    }

    public void conversor(final PantallaGameOver pantallaGameOver){
        for(int i = 1; i<=10; i++){
            pantallaGameOver.pasameImagenAbytes(i);
        }
    }

    public static AssetManager dameManager(){
        return manager;
    }

    public static void limpiarAssets(){
        manager.clear();
    }

    public static Texture get(String s) {
        return manager.get(s);
    }

}
