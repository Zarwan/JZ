package com.jzprojectz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Player {
    private float x;
    private float y;
    private Texture texture;
    private float unit;

    private static final String PLAYER_IMAGE = "pikachu.png";

    public Player(float unit) {
        this.unit = unit;
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

    public void moveRight() {
        x += unit;
    }

    public void moveLeft() {
        x -= unit;
    }

    public void moveUp() {
        y += unit;
    }

    public void moveDown() {
        y -= unit;
    }
}
