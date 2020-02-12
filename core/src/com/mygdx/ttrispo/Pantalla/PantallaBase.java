package com.mygdx.ttrispo.Pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.ttrispo.MyGdxGame;

public class PantallaBase implements Screen {

    protected Stage stage;
    protected Skin skin;
    private ShapeRenderer shapeRenderer;
    protected SpriteBatch batch;
    protected MyGdxGame game;

    public PantallaBase(MyGdxGame game) {
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        batch = new SpriteBatch();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); //procesa todos los eventos de los actores: el botón AKA: sale rojo cuando pulsas!!
    }

    @Override
    public void hide() { // se haría everytime un show, si abandonamos la pantalla = DISPOSE
        Gdx.input.setInputProcessor(null); //para dejar de usar este stage cuando cambiemos de pantalla
        stage.clear();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0.0f, 0.0f,0.0f, 1f);
        stage.act();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

