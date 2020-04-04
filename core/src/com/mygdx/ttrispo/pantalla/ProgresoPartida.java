package com.mygdx.ttrispo.Pantalla;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProgresoPartida extends Actor {
    private BitmapFont font;
    private Partida partida;
    private GlyphLayout glyphLayout;

    public ProgresoPartida(Partida partida){
        this.partida = partida;
        font = new BitmapFont();
        glyphLayout = new GlyphLayout();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        font.getData().setScale(8/(0.3f*partida.getLongitudPuntos() + 1));
        glyphLayout.setText(font, String.valueOf(partida.getPuntuacion()));
        font.draw(batch, glyphLayout,(Gdx.graphics.getWidth()-glyphLayout.width)/7, 0.92f*Gdx.graphics.getHeight());
    }
}
