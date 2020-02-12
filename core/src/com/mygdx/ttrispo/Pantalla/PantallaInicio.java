package com.mygdx.ttrispo.Pantalla;

 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.audio.Music;
 import com.badlogic.gdx.audio.Sound;
 import com.badlogic.gdx.graphics.OrthographicCamera;
 import com.badlogic.gdx.graphics.Texture;
 import com.badlogic.gdx.graphics.g2d.Sprite;
 import com.badlogic.gdx.graphics.g2d.TextureRegion;
 import com.badlogic.gdx.scenes.scene2d.InputEvent;
 import com.badlogic.gdx.scenes.scene2d.ui.Image;
 import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
 import com.badlogic.gdx.scenes.scene2d.ui.Skin;
 import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
 import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
 import com.mygdx.ttrispo.Gestores.GestorRecursos;
 import com.mygdx.ttrispo.MyGdxGame;

public class PantallaInicio extends PantallaBase{
    private Skin skin;
    private ImageButton start, settings;
    private Texture fondoInicio;
    private Sprite paraGirar1, paraGirar2;
    private long tiempoInicial;
    private final long switchfps = 10;
    private boolean cambio;
    private Image tetris;
    private Music musicaInicio;
    private Sound sonidoOptions, sonidoJugar;

    public PantallaInicio (final MyGdxGame game) {
        super(game);
        fondoInicio = GestorRecursos.get("fondoInicio.jpg");
        skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
        paraGirar1 = new Sprite(fondoInicio);
        paraGirar2 = new Sprite(fondoInicio);
        tiempoInicial = 0;
        cambio = false;

        //Tetris imagen
        tetris = new Image(new TextureRegion(GestorRecursos.get("tetris.png")));
        tetris.setSize(0.9f*tetris.getWidth(), 0.9f*tetris.getHeight());
        tetris.setPosition((Gdx.graphics.getWidth() - tetris.getWidth()) / 2.0f, 0.6f * Gdx.graphics.getHeight());
        super.stage.addActor(tetris);

        //Boton start con imagen
        start = new ImageButton(skin, "start");
        start.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-start.png")));
        start.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-start.png")));
        start.setSize(start.getStyle().imageUp.getMinWidth(), start.getStyle().imageUp.getMinHeight());
        start.setPosition((Gdx.graphics.getWidth() - start.getStyle().imageUp.getMinWidth()) / 2.0f, 0.3f * Gdx.graphics.getHeight());
        super.stage.addActor(start);

        //Boton ajustes con imagen
        settings = new ImageButton(skin, "ajustes");
        settings.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-ajustes.png")));
        settings.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-ajustes.png")));
        settings.setSize(settings.getStyle().imageUp.getMinWidth()/1.5f, settings.getStyle().imageUp.getMinHeight()/1.5f);
        settings.setPosition((Gdx.graphics.getWidth() - settings.getStyle().imageUp.getMinWidth()/1.5f)/ 2.0f,
                0.2f * Gdx.graphics.getHeight());
        super.stage.addActor(settings);

        //Eventos de ambos
        start.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sonidoJugar.play(0.4f);
                PantallaAjustes.setColoresPersonalizados(false);
                game.setScreen(new Partida(game));
                musicaInicio.stop();
            }
        });

        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sonidoOptions.play(0.4f);

                musicaInicio.stop();
                game.setScreen(new PantallaAjustes(game));
            }
        });
        // musica de la pantalla inicio
        // no puedo usar el gestor de recursos
        // musicaInicio = (Music) GestorRecursos.get("Music/The Force Theme.mp3");
        sonidoJugar = Gdx.audio.newSound(Gdx.files.internal("Music/Playful R2D2.mp3"));
        sonidoOptions = Gdx.audio.newSound(Gdx.files.internal("Music/Another beep.mp3"));
        musicaInicio = Gdx.audio.newMusic(Gdx.files.internal("Music/The Force Theme.mp3"));
        musicaInicio.setLooping(true);
    }

    @Override
    public void show() {
        super.show();
        musicaInicio.play();
    }

    @Override
    public void hide() { //usamos dispose porque si cambiamos muchas veces de pantalla
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(fondoInicio, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paraGirar1.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paraGirar1.rotate((float) 0.1);
        paraGirar1.draw(batch, 100);
        paraGirar2.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paraGirar2.rotate((float) - 0.1);
        paraGirar2.draw(batch, 100);

        if(cambio){
            ((OrthographicCamera)stage.getCamera()).zoom-=0.001f;
            tiempoInicial++;
            if(tiempoInicial==switchfps){
                tiempoInicial=0;
                cambio = false;
            }
        }else {
            ((OrthographicCamera)stage.getCamera()).zoom+=0.001f;
            tiempoInicial++;
            if(tiempoInicial==switchfps){
                tiempoInicial=0;
                cambio = true;
            }
        }
        batch.end();
        stage.draw();
    }
    @Override
    public void dispose() {
        super.dispose();
        musicaInicio.dispose();
    }

}

