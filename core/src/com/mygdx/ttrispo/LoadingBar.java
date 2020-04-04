package com.mygdx.ttrispo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LoadingBar extends Actor {

    Animation<TextureRegion> animation;
    TextureRegion reg;
    float stateTime;
    TextureRegionDrawable logo;

    public LoadingBar(Animation<TextureRegion> animation) {
        this.animation = animation;
        reg = animation.getKeyFrame(0);
        logo = new TextureRegionDrawable(new Texture("logo.png"));
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
        reg = animation.getKeyFrame(stateTime);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(reg, getX(), getY(), logo.getMinWidth() + 30, logo.getMinHeight() + 30);
    }

}