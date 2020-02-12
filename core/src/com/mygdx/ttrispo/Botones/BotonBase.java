package com.mygdx.ttrispo.Botones;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.ttrispo.Gestores.GestorEstado;
import com.mygdx.ttrispo.Gestores.GestorPiezas;
import com.mygdx.ttrispo.Gestores.GestorRecursos;

public class BotonBase extends Actor {

    private GestorEstado gE2;

    private ImageButton derecha, izquierza, giro, abajo;
    public BotonBase(Stage stage, GestorEstado gestorEstado, GestorEstado gestorEstado2){
        final GestorEstado gE = gestorEstado;
        gE2 = gestorEstado2; //este se actualiza en el ciclo de vida de la otra pieza, el gE de arriba no
        //Botones
        Skin skin = new Skin(Gdx.files.internal("skins/default/skin/uiskin.json"));

        //Boton derecha
        derecha = new ImageButton(skin, "derecha");
        derecha.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-dere.jpg")));
        derecha.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("BP-dere.jpeg")));
        derecha.setSize(derecha.getStyle().imageUp.getMinWidth()-30, derecha.getStyle().imageUp.getMinHeight()-30);
        derecha.setPosition((Gdx.graphics.getWidth() - derecha.getStyle().imageUp.getMinWidth()) / 1.2f, 0.09f*Gdx.graphics.getHeight());
        stage.addActor(derecha);

        derecha.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gE.setEstado(GestorEstado.DERECHA);
                gE2.setEstado2(GestorEstado.DERECHA2);
            }
        });

        //Boton abajo
        abajo = new ImageButton(skin, "abajo");
        abajo.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-abajo.jpg")));
        abajo.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("BP-abajo.jpeg")));
        abajo.setSize(derecha.getStyle().imageUp.getMinWidth()-40, derecha.getStyle().imageUp.getMinHeight()-40);
        abajo.setPosition((Gdx.graphics.getWidth() - derecha.getStyle().imageUp.getMinWidth()) / 2.0f, 0.01f*Gdx.graphics.getHeight());
        stage.addActor(abajo);

        abajo.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gE.setEstado(GestorEstado.CAER);
                gE2.setEstado2(GestorEstado.CAER2);
            }
        });

        //Boton izquierza
        izquierza = new ImageButton(skin, "izquierda");
        izquierza.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-iz.jpg")));
        izquierza.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("BP-iz.jpeg")));
        izquierza.setSize(derecha.getStyle().imageUp.getMinWidth()-30, derecha.getStyle().imageUp.getMinHeight()-30);
        izquierza.setPosition((Gdx.graphics.getWidth() - derecha.getStyle().imageUp.getMinWidth()) / 6.0f, 0.09f*Gdx.graphics.getHeight());
        stage.addActor(izquierza);

        izquierza.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gE.setEstado(GestorEstado.IZQUIERDA);
                gE2.setEstado2(GestorEstado.IZQUIERDA2);
            }
        });

        //Boton giro
        giro = new ImageButton(skin, "rotar");
        giro.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("B-Rot.jpg")));
        giro.getStyle().imageDown = new TextureRegionDrawable(new TextureRegion(GestorRecursos.get("BP-Rot.jpeg")));
        giro.setSize(derecha.getStyle().imageUp.getMinWidth()-40, derecha.getStyle().imageUp.getMinHeight()-40);
        giro.setPosition((Gdx.graphics.getWidth() - derecha.getStyle().imageUp.getMinWidth()) / 2.0f, 0.13f*Gdx.graphics.getHeight());
        stage.addActor(giro);

        giro.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gE.setEstado(GestorEstado.GIRO);
                gE2.setEstado2(GestorEstado.GIRO2);
            }
        });
    }

    public void setNewGestorPara2(GestorEstado gestorEstado2) {
        this.gE2 = gestorEstado2;
    }
}
