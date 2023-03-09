package com.prototype.entity;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.awt.*;

public abstract class Gun implements GunAction{
    float damage;

    float bSpeed;
    float knockBack;
    Sound gun_sound;


    public float getKnockBack() {
        return knockBack;
    }

    @Override
    public void shoot(Array<Bullet> bullets, Rectangle box, Vector2 touchPos) {
        return;
    }

     public void addSpeed(float bSpeed){
        this.bSpeed += bSpeed;
    }
     public void addDamage(float damage){
        this.damage += damage;
    }

    public void setbSpeed(float bSpeed) {
        this.bSpeed = bSpeed;
    }

    public float getDamage() {
        return damage;
    }

    public float getbSpeed() {
        return bSpeed;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
