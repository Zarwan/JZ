package com.jzprojectz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

import static com.jzprojectz.game.JZ.UNIT;

public class Bullet {
    private final float RADIUS = 30/UNIT;
    private final String IMAGE = "black_circle.png";
    private Circle bounds;
    private Texture texture = new Texture(Gdx.files.internal(IMAGE));
    private float x;
    private float y;

    public Bullet(float x, float y) {
        this.x = x;
        this.y = y;
        bounds = new Circle(x, y, RADIUS);
    }

    public boolean collision(float x, float y) {
        return bounds.contains(x, y);
    }

    public float getRadius() {
        return RADIUS;
    }

    public Texture getTexture() {
        return texture;
    }

}
