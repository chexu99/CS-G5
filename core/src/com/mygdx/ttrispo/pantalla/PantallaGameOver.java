package com.mygdx.ttrispo.pantalla;

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
import com.mygdx.ttrispo.BaseDeDatos.FirebaseHelper;
import com.mygdx.ttrispo.BaseDeDatos.Jugador;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.MyGdxGame;
import com.mygdx.ttrispo.com.mygdx.ttrispo.camara.InterfazCamara;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mygdx.ttrispo.MyGdxGame.*;

public class PantallaGameOver extends PantallaBase {
    private static final String CLASSNAME ="PantallaGameOver";
    private Skin aspect;
    private ImageButton retry;
    private ImageButton home;
    private Texture fondoGameOver;
    private BitmapFont font;
    private boolean isRankingLoaded;
    private boolean activo;
    private boolean posNuevoJug;
    private ArrayList<Jugador> listaRanking;
    private Table table;

    private GlyphLayout glyphLayout;
    private String alias;
    private long pasado;

    private Music musicaGameOver;
    private Sound r2D2Triste;

    private  Pixmap pmap;
    private ImageButton imageButton;
    private ImageButton ibaux;
    private Texture tex;
    private final InterfazCamara iC;
    private Image vistaImagen;
    private Image imagenActual;
    final Texture profileP=GestorRecursos.get("profile.png");

    private int dimensionImagen;

    private final Logger logger=Logger.getLogger(PantallaGameOver.getName());
    final FirebaseHelper fbHelper= new FirebaseHelper();
    static float progreso= VARIABLE_GLOBAL_PROGRESO;

    public PantallaGameOver(final MyGdxGame game, final InterfazCamara interfazCamara){
        super(game);
        fondoGameOver = GestorRecursos.get("GameOver.jpeg");
        aspect = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
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
        table.setSkin(aspect);

        imagenActual = new Image(profileP);

        this.iC=interfazCamara;
        imageButton = new ImageButton(aspect, "foto");
        imageButton.getStyle().imageUp = new TextureRegionDrawable(profileP);
        imageButton.setSize(200, 200);
        imageButton.setPosition(0,0);
        imageButton.setName("imageButton");
        super.stage.addActor(imageButton);

        ibaux = new ImageButton(aspect, "foto");
        ibaux.getStyle().imageUp = new TextureRegionDrawable(profileP);
        ibaux.setSize(200, 200);
        ibaux.setPosition(0,0);

        //Boton start con imagen
        retry = new ImageButton(aspect, "reiniciar");
        retry.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-retry.png")));
        retry.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-retry.png")));
        retry.setSize(retry.getStyle().imageUp.getMinWidth(), retry.getStyle().imageUp.getMinHeight());
        float x= (float)Gdx.graphics.getWidth()/2.0f;
        float xm= retry.getStyle().imageUp.getMinWidth()/2.0f;
        float y= (float)Gdx.graphics.getHeight()/6;


        retry.setPosition((x)-(xm), y);
        super.stage.addActor(retry);

        //Boton retry con imagen
        float x3= (float) 0.1*home.getStyle().imageUp.getMinWidth();
        float y2= (float)Gdx.graphics.getHeight()/10;
        home = new ImageButton(aspect, "inicio");
        home.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-home.png")));
        home.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-home.png")));
        home.setSize(0.2f*home.getStyle().imageUp.getMinWidth(), 0.2f*home.getStyle().imageUp.getMinHeight());
        home.setPosition((x) - (x3), y2);
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
                    logger.log(Level.INFO, "Puntos superados {0}",Partida.partidaAux.getPuntuacion());
                }
                else if (Partida.partidaAux.getPuntuacion()<= 250){
                    mensajeAlerta();
                    logger.log(Level.INFO, "Puntos no superados {0}",Partida.partidaAux.getPuntuacion());
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
        r2D2Triste = Gdx.audio.newSound(Gdx.files.internal("Music/Sad R2D2.mp3"));
        musicaGameOver = Gdx.audio.newMusic(Gdx.files.internal("Music/Imperial March.mp3"));
        musicaGameOver.setLooping(true);
    }


    public static String getName(){
        return CLASSNAME;
    }

    private void mensajeAlerta() {
        r2D2Triste.play(0.4f);
        Dialog alerta = new Dialog("Error", aspect, "dialog") {
            @Override
            public void result(Object obj) {

                logger.log(Level.INFO,"result {0} ",obj);


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
            logger.log(Level.INFO, "error{0}",e);
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


             fbHelper.rellenarArrayDeRanking(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<Jugador> lista) {
                fbHelper.insertarPuntuacionEnRanking(alias, Partida.partidaAux.getPuntuacion(), iC);
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


    private  ArrayList<Image> vistaImagenes;
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
                int pos = fbHelper.determinarPosicionJugador(Partida.partidaAux.getPuntuacion());
                if (pos != 0) {
                    iC.subirImagen(pos);//segundo
                }
                iC.setResultado1(false);
            }
            if (iC.getResultado2()) {
                fbHelper.insertarPuntuacionEnRanking(alias, Partida.partidaAux.getPuntuacion(), iC);
                iC.setResultado2(false);
            }
            font.setColor(Color.YELLOW);
            glyphLayout.setText(font, "TOP 10 MEJORES PUNTUACIONES");
            font.draw(batch, glyphLayout, (Gdx.graphics.getWidth() - glyphLayout.width) / 2, 0.95f * Gdx.graphics.getHeight());
            font.setColor(Color.WHITE);
            if (isRankingLoaded) {
                boolean nuevoRank = false;
                try {
                    cargaRanking(nuevoRank);

                } catch (NullPointerException npe) {
                    logger.log(Level.INFO, "ERROR: aun no se habia cargado del todo el ranking.");


                }
                isRankingLoaded = false;
            } else if ( listaRanking == null) {
                tempo();
            }
            batch.end();
            stage.draw(); // Pintar los actores los botones por encima del background
        }
    }
    private void tempo(){
        long futuro;
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
    private void cargaRanking(boolean nuevoRank){
        Label label;
        Label labelID;
        Label labelAlias;
        for (int i = 1; i < listaRanking.size(); i++) {
            labelID = new Label(i + "ª", aspect);
            labelAlias = new Label(listaRanking.get(i).getNombre(), aspect);
            label = new Label(String.valueOf(listaRanking.get(i).getPuntuacion()), aspect);
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
    }
    public void pasameImagenAbytes(int posicion){
        File file = iC.getArrayImagenes().get(posicion);
        byte[] bites = iC.convertirFileAbyte(file);
        while (iC.getContadorBytesArchivo() != iC.getContadorBytesArray());
        if(iC.getContadorBytesArchivo() == iC.getContadorBytesArray()){
            progreso = progreso + 0.05f;
            vistaImagen = new Image(conversorBytesAImagen(bites));
            synchronized (vistaImagenes){
                vistaImagenes.add(vistaImagen);
            }
        }
    }

    private Texture nuevaTextura;


    public Texture conversorBytesAImagen(byte[] bytes) {
         Pixmap pix;
        if(bytes != null){
            pix = new Pixmap(bytes, 0, bytes.length);
            nuevaTextura = new Texture(pix);
        }else{
            logger.log(Level.INFO, "No he recibido na");
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
    //Just a comment to emulate a bugfix to show how to use gitflow properly
    @Override
    public void dispose () {
        musicaGameOver.dispose();
    }
}
