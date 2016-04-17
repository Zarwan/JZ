package com.jzprojectz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Player {
    private JZ jz;
    private float x;
    private float y;
    private Texture texture;

    private static final String PLAYER_IMAGE = "pikachu.png";

    public Player(JZ jz) {
        this.jz = jz;
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

    public void move(int direction) {
        if (!onEdge(direction)) {
            return;
        }
        switch (direction) {
            case JZ.LEFT:
                x -= 1;
                break;
            case JZ.RIGHT:
                x += 1;
                break;
            case JZ.UP:
                y += 1;
                break;
            case JZ.DOWN:
                y -= 1;
                break;
        }
    }

    private boolean onEdge(int direction) {
        switch (direction) {
            case JZ.LEFT:
                return !jz.sideOfMapVisible(JZ.LEFT) || x > 0;
            case JZ.RIGHT:
                return !jz.sideOfMapVisible(JZ.RIGHT) || x < jz.getMapWidth() - 2;
            case JZ.UP:
                return !jz.sideOfMapVisible(JZ.UP) || y < jz.getMapHeight() - 2;
            case JZ.DOWN:
                return !jz.sideOfMapVisible(JZ.DOWN) || y > 0;
        }
        return true;
    }
}
