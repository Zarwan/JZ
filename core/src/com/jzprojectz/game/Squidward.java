package com.jzprojectz.game;

/**
 * Created by zarwanhashem on 2016-04-23.
 */
public class Squidward extends Enemy {
    private final int WIDTH = 2;
    private final int HEIGHT = 2;
    private final int BOUNTY = 1;
    private final double DAMAGE = 0.01;
    private double speed = 3;
    private int health = 3;
    private static final String IMAGE = "handsome.png";

    public Squidward(JZ jz) {
        super(jz);
    }

    @Override
    protected double getDamage() {
        return DAMAGE;
    }

    @Override
    protected String getImage() {
        return IMAGE;
    }

    @Override
    protected int getHealth() {
        return health;
    }

    @Override
    protected void shot() {
        health--;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public int getBounty() {
        return BOUNTY;
    }

    @Override
    protected double getSpeed() {
        return speed;
    }
}
