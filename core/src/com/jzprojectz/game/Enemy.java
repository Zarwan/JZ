package com.jzprojectz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

import static com.jzprojectz.game.JZ.LEFT;
import static com.jzprojectz.game.JZ.RIGHT;
import static com.jzprojectz.game.JZ.UP;
import static com.jzprojectz.game.JZ.DOWN;

public abstract class Enemy {
    private JZ jz;
    private float x;
    private float y;
    private Texture texture;

    public Enemy(JZ jz) {
        this.jz = jz;
        texture = new Texture(Gdx.files.internal(getImage()));

        Random random = new Random();

        //Initial position is on the outer edge of the map
        if (random.nextInt(2) == 0) {
            if (random.nextInt(2) == 0) {
                x = getWidth() * -1;
            } else {
                x = JZ.mapX;
            }
            y = random.nextInt(JZ.mapY + getHeight()*2 + 1) - getHeight();

        } else {
            if (random.nextInt(2) == 0) {
                y = getHealth() * -1;
            } else {
                y = JZ.mapY;
            }
            x = random.nextInt(JZ.mapX + getWidth()*2 + 1) - getWidth();
        }
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

    public void followAndAttack(Player player) {

        //Enemy is in one of the corners of the player sprite
        if (Math.abs(y - (player.getY() + player.getHeight())) <= getDistance() ||
                Math.abs(player.getY() - (y + getHeight())) <= getDistance()) {

            if (player.getX() - x >= getDistance()) {
                moveRight();
            } else if (x - player.getX() >= getDistance()) {
                moveLeft();
            } else {
                attackPlayer(player);
            }

        } else {
            boolean movedX = true;

            if (player.getX() - (x + getWidth()) >= getDistance()) {
                moveRight();
            } else if (x - (player.getX() + player.getWidth()) >= getDistance()) {
                moveLeft();
            } else {
                movedX = false;
            }

            if (player.getY() - (y + getHeight()) > getDistance()) {
                moveUp();
            } else if (y - (player.getY() + player.getHeight()) > getDistance()) {
                moveDown();
            } else {
                if (!movedX) {
                    attackPlayer(player);
                }
            }
        }
    }

    private void attackPlayer(Player player) {
        player.attacked(getDamage());
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
        return getHealth() <= 0;
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
        return (float) (getSpeed() * Gdx.graphics.getDeltaTime());
    }

    protected abstract double getSpeed();

    protected abstract double getDamage();

    protected abstract String getImage();

    protected abstract int getHealth();

    protected abstract void shot();

    public abstract int getWidth();

    public abstract int getHeight();
}
