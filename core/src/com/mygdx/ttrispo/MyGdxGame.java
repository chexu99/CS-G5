package com.mygdx.ttrispo;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.ttrispo.BaseDeDatos.FirebaseHelper;
import com.badlogic.gdx.Gdx;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.Pantalla.PantallaAjustes;
import com.mygdx.ttrispo.Pantalla.PantallaGameOver;
import com.mygdx.ttrispo.Pantalla.PantallaInicio;
import com.mygdx.ttrispo.com.mygdx.ttrispo.camara.InterfazCamara;

import java.util.concurrent.CountDownLatch;

public class MyGdxGame extends Game implements ApplicationListener {
    public static float ratioPixelesHeight, ratioPixelesWidth;
    public static float VARIABLE_GLOBAL_PROGRESO = 0;

    public PantallaInicio pantallaInicio;
    public volatile PantallaGameOver pantallaGameOver;
    public PantallaAjustes pantallaAjustes;
    public static FirebaseHelper firebaseHelper;
    private InterfazCamara interfazCamara;
    private MyGdxGame myGdxGame;
    private final GestorRecursos gestorRecursos = new GestorRecursos();

    public MyGdxGame(InterfazCamara interfazCamara){
        this.interfazCamara = interfazCamara;
    }

    public static AssetManager manager = new AssetManager();

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        GestorRecursos.cargarImagenes();
        myGdxGame = this;
        ratioPixelesHeight = (float) Gdx.graphics.getHeight()/GestorRecursos.get("background.jpeg").getHeight();
        ratioPixelesWidth = (float) Gdx.graphics.getWidth()/GestorRecursos.get("background.jpeg").getWidth();    //pixeles = pantallaMovil/background
        pantallaInicio = new PantallaInicio(myGdxGame);
        pantallaAjustes = new PantallaAjustes(myGdxGame);
        firebaseHelper = new FirebaseHelper();
        pantallaGameOver = new PantallaGameOver(myGdxGame, interfazCamara);
        setScreen(new SplashScreen(this));
        //mientras esta cargando cargan las imagenes
        new Thread(new Runnable() {
            @Override
            public void run() {
                gestorRecursos.cargarPrevia(interfazCamara);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gestorRecursos.conversor(pantallaGameOver);
                    }
                });
            }
        }).start();
    }

    @Override
    public void dispose() {
        GestorRecursos.limpiarAssets();
        getScreen().dispose();
        Gdx.app.exit();
    }

    @Override
    public void render() {
        super.render();
    }
}
