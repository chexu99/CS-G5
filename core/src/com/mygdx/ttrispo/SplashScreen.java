package com.mygdx.ttrispo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.ttrispo.BaseDeDatos.FirebaseHelper;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.Pantalla.PantallaAjustes;
import com.mygdx.ttrispo.Pantalla.PantallaBase;
import com.mygdx.ttrispo.Pantalla.PantallaGameOver;
import com.mygdx.ttrispo.Pantalla.PantallaInicio;
import com.mygdx.ttrispo.com.mygdx.ttrispo.camara.InterfazCamara;

public class SplashScreen implements Screen {
    private SpriteBatch batch;
    private Texture ttrSplash;
    private final TextureRegionDrawable logo = new TextureRegionDrawable(new Texture("logo.png"));

    private Stage stage;

    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image loadingBg;

    private float startX, endX;
    private float percent;

    private long pasado, futuro;

    private Actor loadingBar;
    private MyGdxGame game;

    public SplashScreen(MyGdxGame game) {
        batch = new SpriteBatch();
        this.game = game;

        ttrSplash = new Texture("splash-bg.png");


    }

    @Override
    public void show() {
        game.manager.load("data/loading.pack", TextureAtlas.class);
        game.manager.finishLoading();
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        pasado = System.currentTimeMillis();
        // Get our textureatlas from the manager
        TextureAtlas atlas = game.manager.get("data/loading.pack", TextureAtlas.class);

        // Recogida de atlas del pack
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));

        // Barra animada de carga
        Animation<TextureRegion> anim = new Animation(0.05f, atlas.findRegions("loading-bar-anim") );
        anim.setPlayMode(Animation.PlayMode.LOOP_REVERSED);
        loadingBar = new LoadingBar(anim);
        loadingBar.setSize(logo.getMinWidth() + 30, logo.getMinHeight()+ 30);

        // Add all the actors to the stage
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);

        //estos no hacen falta
        //stage.addActor(loadingBarHidden);
        //stage.addActor(loadingFrame);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        futuro = System.currentTimeMillis();
        // Interpolar el porcentaje para hacerlo lineal
        percent = Interpolation.linear.apply(percent, game.VARIABLE_GLOBAL_PROGRESO, 0.1f);
        // Actualizar las posiciones
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX());
        loadingBg.setWidth(logo.getMinWidth() + 30 - (logo.getMinWidth()+ 30) * percent);
        loadingBg.invalidate();
        stage.draw();
        stage.act();
        batch.begin();
        //que se dibuje por delante del actor de la barra
        batch.draw(ttrSplash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        logo.draw(batch, (Gdx.graphics.getWidth()-logo.getMinWidth())/2, (Gdx.graphics.getHeight()-logo.getMinHeight())/2,
                logo.getMinWidth(), logo.getMinHeight());
        batch.end();
        if((percent >= 0.9998f) || (futuro >= pasado + 20000)){
            game.setScreen(game.pantallaInicio);
            this.dispose();
        }
    }

    @Override
    public void hide() {
        game.manager.unload("data/loading.pack");
    }

    @Override
    public void resize(int width, int height) {

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((stage.getWidth() - loadingFrame.getWidth()) / 2);
        loadingFrame.setY((stage.getHeight() - loadingFrame.getHeight()) / 2);
        loadingFrame.setSize(logo.getMinWidth() + 30, logo.getMinHeight() + 30);

        loadingBar.setX(loadingFrame.getX());
        loadingBar.setY(loadingFrame.getY());
        loadingBar.setHeight(logo.getMinHeight() + 30);

        loadingBarHidden.setX(loadingBar.getX());
        loadingBarHidden.setY(loadingBar.getY());
        loadingBarHidden.setHeight(logo.getMinHeight() + 30);
        startX = loadingBarHidden.getX();
        endX =logo.getMinWidth() + 30;
        loadingBg.setHeight(logo.getMinHeight() + 30);
        loadingBg.setX(loadingBarHidden.getX());
        loadingBg.setY(loadingBarHidden.getY());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        ttrSplash.dispose();
        batch.dispose();
    }
}