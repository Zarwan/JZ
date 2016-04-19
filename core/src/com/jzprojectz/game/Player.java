package com.jzprojectz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import static com.jzprojectz.game.JZ.LEFT;
import static com.jzprojectz.game.JZ.RIGHT;
import static com.jzprojectz.game.JZ.UP;
import static com.jzprojectz.game.JZ.DOWN;

public class Player {
    private JZ jz;
    private float x;
    private float y;
    private Texture texture;

    private static final String PLAYER_IMAGE = "player.png";

    public Player(JZ jz) {
        this.jz = jz;
        texture = new Texture(Gdx.files.internal(PLAYER_IMAGE));
        x = 6;
        y = 4;
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

    public void moveLeft() {
        if (!onEdge(LEFT)) {
            return;
        }
        x--;
    }

    public void moveRight() {
        if (!onEdge(RIGHT)) {
            return;
        }
        x++;
    }

    public void moveUp() {
        if (!onEdge(UP)) {
            return;
        }
        y++;
    }

    public void moveDown() {
        if (!onEdge(DOWN)) {
            return;
        }
        y--;
    }

    private boolean onEdge(int direction) {
        switch (direction) {
            case LEFT:
                return !jz.sideOfMapVisible(LEFT) || x > 0;
            case RIGHT:
                return !jz.sideOfMapVisible(RIGHT) || x < jz.getMapWidth() - 2;
            case UP:
                return !jz.sideOfMapVisible(UP) || y < jz.getMapHeight() - 2;
            case DOWN:
                return !jz.sideOfMapVisible(DOWN) || y > 0;
        }
        return true;
    }
}
