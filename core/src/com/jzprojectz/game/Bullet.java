package com.jzprojectz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import static com.jzprojectz.game.JZ.UNIT;
import static com.jzprojectz.game.JZ.LEFT;
import static com.jzprojectz.game.JZ.RIGHT;
import static com.jzprojectz.game.JZ.UP;
import static com.jzprojectz.game.JZ.DOWN;

public class Bullet {
    private final float RADIUS = 16/UNIT;
    private Circle bounds;
    //private Rectangle bounds;
    private float x;
    private float y;
    private float distance = 20;
    private int direction;
    private float speed = 20;

    public Bullet(float x, float y, int direction) {
        this.x = x + RADIUS;
        this.y = y + RADIUS;
        this.direction = direction;
        bounds = new Circle(x + RADIUS, y + RADIUS, RADIUS);
        //bounds = new Rectangle(x, y, RADIUS, RADIUS);
    }

    public boolean collision(float x, float y, float width, float height) {
        float circleDistanceX = Math.abs(this.x - (x + width/2));
        float circleDistanceY = Math.abs(this.y - (y + height/2));

        if (circleDistanceX > (width / 2 + RADIUS)) {
            return false;
        }
        if (circleDistanceY > (height / 2 + RADIUS)) {
            return false;
        }

        if (circleDistanceX <= (width / 2)) {
            return true;
        }
        if (circleDistanceY <= (height / 2)) {
            return true;
        }

        double cornerDistanceSq = Math.pow((circleDistanceX - width / 2), 2) +
                Math.pow((circleDistanceY - height / 2), 2);

        return (cornerDistanceSq <= Math.pow(RADIUS, 2));
    }
/*
    public boolean collision(float x, float y, float width, float height) {
        Rectangle enemyRectangle = new Rectangle(x, y, width, height);
        return bounds.overlaps(enemyRectangle);
    }
   */
    public void moveBullet() {
        switch(direction) {
            case LEFT:
                x -= getDistance();
                break;

            case RIGHT:
                x += getDistance();
                break;

            case UP:
                y += getDistance();
                break;

            case DOWN:
                y -= getDistance();
                break;
        }
        //bounds = new Rectangle(x, y, RADIUS, RADIUS);
        bounds = new Circle(x, y, RADIUS);
        distance -= getDistance();
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

    public boolean isDead() {
        return distance <= 0;
    }

    private float getDistance() {
        return speed * Gdx.graphics.getDeltaTime();
    }
}
