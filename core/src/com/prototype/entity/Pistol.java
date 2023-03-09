package com.prototype.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.prototype.game.GameScreen;

import java.awt.*;

public class Pistol extends Gun {


    Pistol(){
        this.gun_sound = Gdx.audio.newSound(Gdx.files.internal("sound/lasersound.mp3"));
        knockBack = 7;
        bSpeed = 10f;
        damage = 30f;
    }



    @Override
    public void shoot(Array<Bullet> bullets, Rectangle box, Vector2 touchPos) {
        try{
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            Bullet b = new Bullet(box.x,box.y,touchPos);
            b.setBspeed(this.bSpeed);
            b.setTexture("bu yellow.png");
            b.setDamage(damage);
            b.setTag("p");
            b.setDamage(damage);
            b.setKnockbackvar(this.knockBack);
            bullets.add(b);
            gun_sound.play();



        }}catch (NullPointerException ignored)
        {

        }
    }

    @Override
    public void addSpeed(float bSpeed) {
       this.bSpeed += bSpeed;
    }

    @Override
    public void addDamage(float damage) {
        this.damage +=damage;
    }







}
