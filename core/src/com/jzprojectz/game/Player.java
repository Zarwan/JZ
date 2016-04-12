package com.jzprojectz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    private float x;
    private float y;
    private Texture texture;

    private static final String PLAYER_IMAGE = "pikachu.png";

    public Player() {
        texture = new Texture(Gdx.files.internal(PLAYER_IMAGE));
        x = 0;
        y = 0;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return texture.getWidth()/JZ.UNIT;
    }

    public float getHeight() {
        return texture.getHeight()/JZ.UNIT;
    }

    public void moveRight() {
        x += 1;
    }

    public void moveLeft() {
        x -= 1;
    }

    public void moveUp() {
        y += 1;
    }

    public void moveDown() {
        y -= 1;
    }
}
