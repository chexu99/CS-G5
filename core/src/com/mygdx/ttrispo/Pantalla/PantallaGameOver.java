package com.mygdx.ttrispo.Pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.audio.Music;

import com.mygdx.ttrispo.BaseDeDatos.FirebaseCallback;
import com.mygdx.ttrispo.BaseDeDatos.Jugador;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.MyGdxGame;
import com.mygdx.ttrispo.com.mygdx.ttrispo.camara.InterfazCamara;

import java.io.File;
import java.util.ArrayList;

public class PantallaGameOver extends PantallaBase {
    private Skin skin;
    private ImageButton retry, home;
    private Texture fondoGameOver;
    private BitmapFont font;
    private boolean isRankingLoaded, activo, posNuevoJug;
    private ArrayList<Jugador> listaRanking;
    private Table table;
    private Label label, labelID, labelAlias;
    private GlyphLayout glyphLayout;
    private String alias;
    private long pasado, futuro;
    private Music musicaGameOver;
    private Sound R2D2Triste;

    public static Pixmap pmap;
    private ImageButton imageButton, ibaux;
    private Texture tex;
    private final InterfazCamara iC;
    private Image vistaImagen, imagenActual;

    private int dimensionImagen;

    public PantallaGameOver(final MyGdxGame game, final InterfazCamara interfazCamara){
        super(game);
        fondoGameOver = GestorRecursos.get("GameOver.jpeg");
        skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
        font = new BitmapFont();
        isRankingLoaded = false;
        table = new Table();
        glyphLayout = new GlyphLayout();
        vistaImagen = null;
        vistaImagenes = new ArrayList<>();
        vistaImagenes.add(null); //posicion 0, no me interesa
        posNuevoJug = false;
        dimensionImagen = 100;

        Container<Table> tableContainer = new Container<>();
        float sw = Gdx.graphics.getWidth();
        float sh = Gdx.graphics.getHeight();
        float cw = sw * 0.2f;
        float ch = sh * 0.8f;
        tableContainer.setSize(cw, ch);
        tableContainer.setPosition((sw-cw)/2.0f, (sh-ch)/1.1f);
        tableContainer.fillX();
        table.setSkin(skin);

        imagenActual = new Image(GestorRecursos.get("profile.png"));

        this.iC=interfazCamara;
        imageButton = new ImageButton(skin, "foto");
        imageButton.getStyle().imageUp = new TextureRegionDrawable(GestorRecursos.get("profile.png"));
        imageButton.setSize(200, 200);
        imageButton.setPosition(0,0);
        imageButton.setName("imageButton");
        super.stage.addActor(imageButton);

        ibaux = new ImageButton(skin, "foto");
        ibaux.getStyle().imageUp = new TextureRegionDrawable(GestorRecursos.get("profile.png"));
        ibaux.setSize(200, 200);
        ibaux.setPosition(0,0);

        //Boton start con imagen
        retry = new ImageButton(skin, "reiniciar");
        retry.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-retry.png")));
        retry.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-retry.png")));
        retry.setSize(retry.getStyle().imageUp.getMinWidth(), retry.getStyle().imageUp.getMinHeight());
        retry.setPosition((Gdx.graphics.getWidth()/2.0f)-(retry.getStyle().imageUp.getMinWidth()/2.0f), Gdx.graphics.getHeight()/6);
        super.stage.addActor(retry);

        //Boton retry con imagen
        home = new ImageButton(skin, "inicio");
        home.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-home.png")));
        home.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-home.png")));
        home.setSize(0.2f*home.getStyle().imageUp.getMinWidth(), 0.2f*home.getStyle().imageUp.getMinHeight());
        home.setPosition((Gdx.graphics.getWidth() / 2.0f) - (0.1f*home.getStyle().imageUp.getMinWidth()), Gdx.graphics.getHeight() / 10);
        super.stage.addActor(home);

        //Contenedor de la tabla del ranking
        tableContainer.setActor(table);
        super.stage.addActor(tableContainer);

        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iC.selectImage();
            }
        });

        retry.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Partida.partidaAux.getPuntuacion()>= 250){
                    game.setScreen(new Partida(game));
                    System.out.println("Puntos superados: " + Partida.partidaAux.getPuntuacion());
                }
                else if (Partida.partidaAux.getPuntuacion()<= 250){
                    MensajeAlerta();
                    System.out.println("Puntos no superados: " + Partida.partidaAux.getPuntuacion());
                }
                table.reset();
                musicaGameOver.stop();
                game.setScreen(new Partida(game));
            }
        });
        home.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(listaRanking!=null){
                    listaRanking.clear();
                }
                table.reset();
                PantallaAjustes.texturaPiezas.clear();
                musicaGameOver.stop();
                game.setScreen(game.pantallaInicio);
            }
        });
        //MUSICA GAME OVER
        R2D2Triste = Gdx.audio.newSound(Gdx.files.internal("Music/Sad R2D2.mp3"));
        musicaGameOver = Gdx.audio.newMusic(Gdx.files.internal("Music/Imperial March.mp3"));
        musicaGameOver.setLooping(true);
    }

    private void MensajeAlerta() {
        R2D2Triste.play(0.4f);
        Dialog alerta = new Dialog("Error", skin, "dialog") {
            public void result(Object obj) {
                System.out.println("result "+obj);
            }
        };
        alerta.text("No has conseguido derrotar al lado oscuro, eres muy débil.");
        alerta.button("Ok", true);
        alerta.setSize(stage.getWidth()/2, stage.getHeight()/4);
        alerta.center();
        alerta.show(stage);
    }

    public void onByteArrayOfCroppedImageReciever(byte[] bytes) {
        try {
            if(bytes != null){
                pmap = new Pixmap(bytes, 0, bytes.length);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        tex = new Texture(pmap);
                        imageButton.setSize(300,300);
                        imageButton.getStyle().imageUp = new TextureRegionDrawable(tex);
                        imagenActual = new Image(tex);
                    }
                });
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        super.show();
        activo = false;
        if(listaRanking != null){
            listaRanking=null;
        }
        pasado = 0;
        musicaGameOver.play();
    }
    private void realShow1() {
        game.firebaseHelper.rellenarArrayDeRanking(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Jugador> lista) {
                game.firebaseHelper.insertarPuntuacionEnRanking(alias, Partida.partidaAux.getPuntuacion(), iC);
                listaRanking = lista;
                if(listaRanking!=null){
                    isRankingLoaded = true;
                }
            }
        });
    }
    private void realShow2() {
        recogerAlias(new AliasCallback() {
            @Override
            public void onCallback(String cadena) {
                alias = cadena;
                if (alias.length() > 8) {
                    alias = alias.substring(0, 8);
                    alias = alias + "...";
                }
                pasado = System.currentTimeMillis();
                realShow1();
            }
        });
    }

    @Override
    public void hide() {
        musicaGameOver.stop();
    }


    private static ArrayList<Image> vistaImagenes;
    @Override
    public void render(float delta) {
        super.render(delta);
        synchronized (vistaImagenes) {
            batch.begin();
            batch.draw(fondoGameOver, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            font.getData().setScale(3);
            if (!activo) {
                realShow2();
                activo = true;
            }
            if (iC.getResultado1()) {
                onByteArrayOfCroppedImageReciever(iC.getDatos()); //primero
                int pos = game.firebaseHelper.determinarPosicionJugador(Partida.partidaAux.getPuntuacion());
                if (pos != 0) {
                    iC.subirImagen(pos);//segundo
                }
                iC.setResultado1(false);
            }
            if (iC.getResultado2()) {
                game.firebaseHelper.insertarPuntuacionEnRanking(alias, Partida.partidaAux.getPuntuacion(), iC);
                iC.setResultado2(false);
            }
            font.setColor(Color.YELLOW);
            glyphLayout.setText(font, "TOP 10 MEJORES PUNTUACIONES");
            font.draw(batch, glyphLayout, (Gdx.graphics.getWidth() - glyphLayout.width) / 2, 0.95f * Gdx.graphics.getHeight());
            font.setColor(Color.WHITE);
            if (isRankingLoaded) {
                boolean nuevoRank = false;
                try {
                    for (int i = 1; i < listaRanking.size(); i++) {
                        labelID = new Label(i + "ª", skin);
                        labelAlias = new Label(listaRanking.get(i).getNombre(), skin);
                        label = new Label(String.valueOf(listaRanking.get(i).getPuntuacion()), skin);
                        label.setAlignment(Align.right);
                        labelAlias.setAlignment(Align.center);
                        labelID.setAlignment(Align.left);
                        if ((!nuevoRank) && (Partida.partidaAux.getPuntuacion() == listaRanking.get(i).getPuntuacion())) {
                            label.setFontScale(8);
                            dimensionImagen = 180;
                            labelID.setFontScale(9);
                            labelAlias.setFontScale(5);
                            nuevoRank = true;
                            posNuevoJug = true;
                            for (int j = vistaImagenes.size()-1; j > i; j--) { //desplazar los elementos
                                vistaImagenes.set(j, vistaImagenes.get(j - 1)); //a cada elemento se le asigna el anterior
                            }
                            vistaImagenes.set(i, imagenActual);
                        } else {
                            label.setFontScale(4);
                            dimensionImagen = 120;
                            labelID.setFontScale(5);
                            labelAlias.setFontScale(3);
                        }
                        table.row();
                        table.add(labelID).padRight(50);
                        vistaImagen = vistaImagenes.get(i);
                        if(posNuevoJug){
                            table.add(ibaux).size(dimensionImagen, dimensionImagen);
                            posNuevoJug = false;
                        }else{
                            table.add(vistaImagen).size(dimensionImagen, dimensionImagen);
                        }
                        table.add(labelAlias).padLeft(50);
                        table.add(label).padLeft(50);
                    }

                } catch (NullPointerException npe) {
                    System.out.println("ERROR: aun no se habia cargado del todo el ranking.");
                }
                isRankingLoaded = false;
            } else if (!isRankingLoaded && listaRanking == null) {
                font.getData().setScale(2.5f);
                futuro = System.currentTimeMillis();
                if (futuro >= pasado + 20000 && pasado != 0) { //20 SEGUNDOS DE ESPERA
                    glyphLayout.setText(font, "Conectate a internet para");
                    font.draw(batch, glyphLayout, (Gdx.graphics.getWidth() - glyphLayout.width) / 2, 0.75f * Gdx.graphics.getHeight());
                    glyphLayout.setText(font, "ver el ranking online");
                    font.draw(batch, glyphLayout, (Gdx.graphics.getWidth() - glyphLayout.width) / 2, 0.7f * Gdx.graphics.getHeight());
                } else {
                    glyphLayout.setText(font, "cargando ranking...");
                    font.draw(batch, glyphLayout, (Gdx.graphics.getWidth() - glyphLayout.width) / 2, 0.75f * Gdx.graphics.getHeight());
                }
            }
            batch.end();
            stage.draw(); // Pintar los actores los botones por encima del background
        }
    }
    public void pasameImagenAbytes(int posicion){
        File file = iC.getArrayImagenes().get(posicion);
        byte[] bites = iC.convertirFileAbyte(file);
        while (iC.getContadorBytesArchivo() != iC.getContadorBytesArray());
        if(iC.getContadorBytesArchivo() == iC.getContadorBytesArray()){
            game.VARIABLE_GLOBAL_PROGRESO+=0.05f;
            vistaImagen = new Image(conversorBytesAImagen(bites));
            synchronized (vistaImagenes){
                vistaImagenes.add(vistaImagen);
            }
        }
    }

    private Texture nuevaTextura;
    private Pixmap pix;

    public Texture conversorBytesAImagen(byte[] bytes) {
        if(bytes != null){
            pix = new Pixmap(bytes, 0, bytes.length);
            nuevaTextura = new Texture(pix);
        }else{
            System.out.println("No he recibido na");
        }
        return nuevaTextura;
    }

    private void recogerAlias(final AliasCallback aliasCallback){
        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input(String cadena) {
                alias = cadena;
                aliasCallback.onCallback(cadena);

            }

            @Override
            public void canceled() {
                alias = "annonymous";
                aliasCallback.onCallback(alias);
            }
        }, "Introduce tu alias", "", " _ _ _ _ _ _ _ _");
    }

    public void dispose () {
        musicaGameOver.dispose();
    }
}
