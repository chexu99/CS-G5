package com.mygdx.ttrispo.pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProgresoPartida extends Actor {
    private BitmapFont font, font2;
    private Partida partida;
    private GlyphLayout glyphLayout, glyphLayout2;

    public ProgresoPartida(Partida partida){
        this.partida = partida;
        font = new BitmapFont();
        font2= new BitmapFont();
        glyphLayout = new GlyphLayout();
        glyphLayout2 = new GlyphLayout();
    }
    //@Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.getData().setScale(8/(0.3f*partida.getLongitudPuntos() + 1));

        //imprimo puntuacion
        glyphLayout.setText(font, String.valueOf(partida.getPuntuacion()));
        font.draw(batch, glyphLayout,(Gdx.graphics.getWidth()-glyphLayout.width)/7, 0.92f*Gdx.graphics.getHeight());

        //imprimo tiempo
        glyphLayout.setText(font, String.valueOf(30-partida.getTiempo()));
        font.draw(batch, glyphLayout,(Gdx.graphics.getWidth()-glyphLayout.width)/7, 0.50f*Gdx.graphics.getHeight());



    }



}
