package com.jzprojectz.game;

/**
 * Created by zarwanhashem on 2016-04-23.
 */
public class Squidward extends Enemy {
    private final double DAMAGE = 0.01;

    public Squidward(JZ jz) {
        super(jz);
    }

    @Override
    protected double getDamage() {
        return DAMAGE;
    }
}
