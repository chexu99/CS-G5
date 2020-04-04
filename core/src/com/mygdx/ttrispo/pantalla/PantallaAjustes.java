package com.mygdx.ttrispo.Pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.ttrispo.Gestores.GestorRecursos;
import com.mygdx.ttrispo.MyGdxGame;

import java.util.ArrayList;

public class PantallaAjustes extends PantallaBase {
    private TextButton BPiezaI, BPiezaJ, BPiezaL, BPiezaO, BPiezaS, BPiezaT, BPiezaZ;
    private ImageButton Home, Play;
    private Skin skin;
    private Color[] colores = {null, Color.YELLOW, Color.VIOLET, Color.ORANGE , Color.CYAN, Color.RED, Color.GREEN, Color.BLUE };
    private Texture fondoAjustes;
    private TextureRegion fraseAjustes;
    public int i, j, l, o, s, t, z;
    private static boolean coloresPersonalizados;
    public static ArrayList<Texture> texturaPiezas;
    private Sprite paraGirar1, paraGirar2;
    private final long switchfps = 10;
    private boolean cambio;
    private long tiempoInicial;
    private Sound sonidoJugar, sonidoHome;

    //supongamos que se guardan con el siguiente orden, para seguir el patron que tiene GestorPieza en su array de Color[]:
    //texturaPiezas = [T, S, Z, I, O, L, J], solo guarda la textura.

    public PantallaAjustes (final MyGdxGame game) {
        super(game);
        skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));
        fondoAjustes = GestorRecursos.get("fondoInicio.jpg");
        fraseAjustes = new TextureRegion(GestorRecursos.get("colorPiezas.png"));
        coloresPersonalizados = false;
        cambio = false;
        tiempoInicial = 0;
        paraGirar1 = new Sprite(fondoAjustes);
        paraGirar2 = new Sprite(fondoAjustes);
        texturaPiezas = new ArrayList<>();

        BPiezaT = new TextButton("T", skin);
        BPiezaT.getLabel().setFontScale(3);
        BPiezaS = new TextButton("S", skin);
        BPiezaS.getLabel().setFontScale(3);
        BPiezaZ = new TextButton("Z", skin);
        BPiezaZ.getLabel().setFontScale(3);
        BPiezaI = new TextButton("I", skin);
        BPiezaI.getLabel().setFontScale(3);
        BPiezaO = new TextButton("O", skin);
        BPiezaO.getLabel().setFontScale(3);
        BPiezaL = new TextButton("L", skin);
        BPiezaL.getLabel().setFontScale(3);
        BPiezaJ = new TextButton("J", skin);
        BPiezaJ.getLabel().setFontScale(3);

        Home = new ImageButton(skin, "atras");
        Play = new ImageButton(skin, "start");

        t=1; s=2; z=3; i=4; o=5; l=6; j=7;

        //color inicial de cada boton
        BPiezaT.setColor(colores[t]);
        BPiezaS.setColor(colores[s]);
        BPiezaZ.setColor(colores[z]);
        BPiezaI.setColor(colores[i]);
        BPiezaO.setColor(colores[o]);
        BPiezaL.setColor(colores[l]);
        BPiezaJ.setColor(colores[j]);
        super.stage.addActor(BPiezaT);
        super.stage.addActor(BPiezaS);
        super.stage.addActor(BPiezaZ);
        super.stage.addActor(BPiezaI);
        super.stage.addActor(BPiezaO);
        super.stage.addActor(BPiezaL);
        super.stage.addActor(BPiezaJ);

        //añadir los botones de cada pieza a la tabla
        Table table = new Table();
        table.padLeft(100);
        table.add(new Image(GestorRecursos.get("ICompleta.png"))).padBottom(100).left();
        table.add(BPiezaI).size(100,100).padBottom(100).center();
        table.add(new Image(GestorRecursos.get("SCompleta.png"))).padBottom(100).left();
        table.add(BPiezaS).size(100,100).padBottom(100).center();
        table.row();
        table.add(new Image(GestorRecursos.get("JCompleta.png"))).padBottom(100).left();
        table.add(BPiezaJ).size(100,100).padBottom(100).center();
        table.add(new Image(GestorRecursos.get("TCompleta.png"))).padBottom(100).left();
        table.add(BPiezaT).size(100,100).padBottom(100).center();
        table.row();
        table.add(new Image(GestorRecursos.get("LCompleta.png"))).padBottom(100).left();
        table.add(BPiezaL).size(100,100).padBottom(100).center();
        table.add(new Image(GestorRecursos.get("ZCompleta.png"))).padBottom(100).left();
        table.add(BPiezaZ).size(100,100).padBottom(100).center();
        table.row();
        table.add();
        table.add(new Image(GestorRecursos.get("OCompleta.png"))).padBottom(100).left();
        table.add(BPiezaO).size(100,100).padBottom(100).center();
        table.add();
        table.add();
        table.row();
        table.add();
        //botón para jugar
        table.add(Play).size(300,100).center();;
        Play.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-start.png")));
        Play.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-start.png")));
        //boton de ir atrás
        table.add(Home).size(100,100).center();
        Home.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-atras.png")));
        Home.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-atras.png")));
        table.add().size(300, 100);

        table.align(Align.center);
        table.setFillParent(true);
        super.stage.addActor(table);
        EventosBotones();

        sonidoJugar = Gdx.audio.newSound(Gdx.files.internal("Music/Playful R2D2.mp3"));
        sonidoHome =  Gdx.audio.newSound(Gdx.files.internal("Music/Another beep.mp3"));
    }

    public static void setColoresPersonalizados(boolean b) {
        coloresPersonalizados = b;
    }


    private void EventosBotones () {
        BPiezaI.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                i = resetContador(i);
                BPiezaI.setColor(colores[i]);
            }
        });

        BPiezaJ.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                j = resetContador(j);
                BPiezaJ.setColor(colores[j]);
            }
        });

        BPiezaL.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                l = resetContador(l);
                BPiezaL.setColor(colores[l]);
            }
        });

        BPiezaO.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                o = resetContador(o);
                BPiezaO.setColor(colores[o]);
            }
        });

        BPiezaS.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                s = resetContador(s);
                BPiezaS.setColor(colores[s]);
            }
        });

        BPiezaT.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                t = resetContador(t);
                BPiezaT.setColor(colores[t]);
            }
        });

        BPiezaZ.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                z = resetContador(z);
                BPiezaZ.setColor(colores[z]);
            }
        });

        Home.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sonidoHome.play();
                game.setScreen(game.pantallaInicio);
            }
        });

        Play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                coloresPersonalizados = true;
                texturaPiezas.add(null);//posicion 0, siempre es recomendable
                texturaPiezas.add(getColorNuevoPieza(t)); // T = 1
                System.out.println("Color pieza T: " + t);
                texturaPiezas.add(getColorNuevoPieza(s)); // S = 2
                System.out.println("Color pieza S: " + s);
                texturaPiezas.add(getColorNuevoPieza(z)); // Z = 3
                System.out.println("Color pieza Z: " + z);
                texturaPiezas.add(getColorNuevoPieza(i)); // I = 4
                System.out.println("Color pieza I: " + i);
                texturaPiezas.add(getColorNuevoPieza(o)); // O = 5
                System.out.println("Color pieza O: " + o);
                texturaPiezas.add(getColorNuevoPieza(l)); // L = 6
                System.out.println("Color pieza L: " + l);
                texturaPiezas.add(getColorNuevoPieza(j)); // J = 7
                System.out.println("Color pieza J: " + j);
                sonidoJugar.play();
                game.setScreen(new Partida(game));
            }
        });
    }


    public static boolean getColoresPersonalizados(){
        return coloresPersonalizados;
    }

    private int resetContador(int letra) {
        if(letra == 7){
            letra = 1;
        }else{
            letra+=1;
        }
        return letra;
    }

    private Texture getColorNuevoPieza(int letra) {  //en teoría esto debería cambiar la textura de la pieza I
        Texture textura;
        switch (letra) {
            case 1:
                textura = GestorRecursos.get("T.jpg");
                 //yellow
               break;
            case 2:
                textura = GestorRecursos.get("S.jpg");
                 //violet
                break;
            case 3:
                textura = GestorRecursos.get("Z.jpg");
                 //orange
                break;
            case 4:
                textura = GestorRecursos.get("I.jpg");
                 //blue
                break;
            case 5:
                textura = GestorRecursos.get("O.jpg");
                 //red
                break;
            case 6:
                textura = GestorRecursos.get("L.jpg");
                 //green
                break;
            case 7:
                textura = GestorRecursos.get("J.jpg");
                //cyan
                break;
            default:
                textura = null;
        }
        return textura;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(fondoAjustes, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paraGirar1.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paraGirar1.rotate((float) 0.1);
        paraGirar1.draw(batch, 100);
        paraGirar2.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paraGirar2.rotate((float) -0.1);
        paraGirar2.draw(batch, 100);
        batch.draw(fraseAjustes, 0.1f*Gdx.graphics.getWidth(), 0.9f*Gdx.graphics.getHeight(), 0.5f*fraseAjustes.getRegionWidth(), 0.5f*fraseAjustes.getRegionHeight());
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

}
