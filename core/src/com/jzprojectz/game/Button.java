package com.jzprojectz.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Button {
    private final int WIDTH;
    private final int HEIGHT;
    private Rectangle bounds;
    private Texture texture;

    public Button(int x, int y, int width, int height, String imagePath) {
        WIDTH = width;
        HEIGHT = height;
        bounds = new Rectangle(x, y, width, height);
        texture = new Texture(Gdx.files.internal(imagePath));
    }

    public boolean clicked(float x, float y) {
        return bounds.contains(x, y);
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public float getXBound() {
        return bounds.getX();
    }

    public float getYBound() {
        return bounds.getY();
    }

    public Texture getTexture() {
        return texture;
    }
}
