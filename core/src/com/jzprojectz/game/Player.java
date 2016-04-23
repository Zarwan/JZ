package com.jzprojectz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import static com.jzprojectz.game.JZ.LEFT;
import static com.jzprojectz.game.JZ.RIGHT;
import static com.jzprojectz.game.JZ.UP;
import static com.jzprojectz.game.JZ.DOWN;

public class Player {
    private final float WIDTH = 2;
    private final float HEIGHT = 2;
    private JZ jz;
    private float x;
    private float y;
    private Texture texture;
    private float speed = 12;
    private int health = 1000;

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
        return WIDTH;
    }

    public float getHeight() {
        return HEIGHT;
    }

    public void attacked(double damage) {
        health -= damage;
    }

    public void moveLeft() {
        if (!onEdge(LEFT)) {
            return;
        }
        x -= getDistance();
    }

    public void moveRight() {
        if (!onEdge(RIGHT)) {
            return;
        }
        x += getDistance();
    }

    public void moveUp() {
        if (!onEdge(UP)) {
            return;
        }
        y += getDistance();
    }

    public void moveDown() {
        if (!onEdge(DOWN)) {
            return;
        }
        y -= getDistance();
    }

    public boolean isDead() {
        return health <= 0;
    }

    private boolean onEdge(int direction) {
        switch (direction) {
            case LEFT:
                return !jz.sideOfMapVisible(LEFT) || x - getDistance() > 0;
            case RIGHT:
                return !jz.sideOfMapVisible(RIGHT) || x + getDistance() < jz.getMapWidth() - WIDTH;
            case UP:
                return !jz.sideOfMapVisible(UP) || y + getDistance() < jz.getMapHeight() - HEIGHT;
            case DOWN:
                return !jz.sideOfMapVisible(DOWN) || y - getDistance() > 0;
        }
        return true;
    }

    public float getDistance() {
        return speed * Gdx.graphics.getDeltaTime();
    }
}
