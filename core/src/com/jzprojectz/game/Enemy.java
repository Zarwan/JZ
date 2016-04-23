package com.jzprojectz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import static com.jzprojectz.game.JZ.LEFT;
import static com.jzprojectz.game.JZ.RIGHT;
import static com.jzprojectz.game.JZ.UP;
import static com.jzprojectz.game.JZ.DOWN;

public class Enemy {
    private final float WIDTH = 2;
    private final float HEIGHT = 2;
    private JZ jz;
    private float x;
    private float y;
    private Texture texture;
    private float speed = 3;
    private int health = 10;

    private static final String ENEMY_IMAGE = "handsome.png";

    public Enemy(JZ jz) {
        this.jz = jz;
        texture = new Texture(Gdx.files.internal(ENEMY_IMAGE));
        x = 40;
        y = 25;
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

    public void shot() {
        health--;
    }

    public void follow(Player player) {

        if (Math.abs(y - (player.getY() + player.getHeight())) <= getDistance() ||
                Math.abs(player.getY() - (y + HEIGHT)) <= getDistance()) {

            if (player.getX() - x >= getDistance()) {
                moveRight();
            } else if (x - player.getX() >= getDistance()) {
                moveLeft();
            }

        } else {
            if (player.getX() - (x + WIDTH) >= getDistance()) {
                moveRight();
            } else if (x - (player.getX() + player.getWidth()) >= getDistance()) {
                moveLeft();
            }

            if (player.getY() - (y + HEIGHT) > getDistance()) {
                moveUp();
            } else if (y - (player.getY() + player.getHeight()) > getDistance()) {
                moveDown();
            }
        }
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

    private float getDistance() {
        return speed * Gdx.graphics.getDeltaTime();
    }
}
