package com.mygdx.ttrispo;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.ttrispo.BaseDeDatos.FirebaseHelper;
import com.badlogic.gdx.Gdx;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.pantalla.PantallaAjustes;
import com.mygdx.ttrispo.pantalla.PantallaGameOver;
import com.mygdx.ttrispo.pantalla.PantallaInicio;
import com.mygdx.ttrispo.com.mygdx.ttrispo.camara.InterfazCamara;

public class MyGdxGame extends Game implements ApplicationListener {
    public static float ratioPixelesHeight, ratioPixelesWidth;
    public static float VARIABLE_GLOBAL_PROGRESO = 0;

    public PantallaInicio pantallaInicio;
    private boolean estadoIdioma = false;
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

    public MyGdxGame() {

    }

    public void setVARIABLE_GLOBAL_PROGRESO(float progeso){
        this.VARIABLE_GLOBAL_PROGRESO= progeso;
    }
    public float getVARIABLE_GLOBAL_PROGRESO(){
        return VARIABLE_GLOBAL_PROGRESO;
    }
    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        GestorRecursos.cargarImagenes();
        myGdxGame = this;
        ratioPixelesHeight = (float) Gdx.graphics.getHeight() / GestorRecursos.get("background.png").getHeight();
        ratioPixelesWidth = (float) Gdx.graphics.getWidth() / GestorRecursos.get("background.png").getWidth();    //pixeles = pantallaMovil/background
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

    public boolean isEstadoIdioma() {
        return estadoIdioma;
    }

    public void setEstadoIdioma(boolean estadoIdioma) {
        this.estadoIdioma = estadoIdioma;
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
