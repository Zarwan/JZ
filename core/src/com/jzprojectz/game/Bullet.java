package com.jzprojectz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

import static com.jzprojectz.game.JZ.UNIT;
import static com.jzprojectz.game.JZ.LEFT;
import static com.jzprojectz.game.JZ.RIGHT;
import static com.jzprojectz.game.JZ.UP;
import static com.jzprojectz.game.JZ.DOWN;

public class Bullet {
    private final float RADIUS = 32/UNIT;
    private final String IMAGE = "black_circle.png";
    private Circle bounds;
    private Texture texture = new Texture(Gdx.files.internal(IMAGE));
    private float x;
    private float y;
    private int moveCountdown = 20;
    private int direction;

    public Bullet(float x, float y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        bounds = new Circle(x, y, RADIUS);
    }

    public boolean collision(float x, float y) {
        return bounds.contains(x, y);
    }

    public void moveBullet() {
        switch(direction) {
            case LEFT:
                x--;
                break;

            case RIGHT:
                x++;
                break;

            case UP:
                y++;
                break;

            case DOWN:
                y--;
                break;
        }
        bounds = new Circle(x, y, RADIUS);
        moveCountdown--;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return RADIUS;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isDead() {
        return moveCountdown <= 0;
    }
}
