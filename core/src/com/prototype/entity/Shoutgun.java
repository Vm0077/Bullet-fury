package com.prototype.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Shoutgun extends Gun {

    Shoutgun() {
        this.gun_sound = Gdx.audio.newSound(Gdx.files.internal("sound/gunsound.mp3"));
        knockBack = 12;
        damage = 10f;
        bSpeed = 5f;
    }


    @Override
    public void shoot(Array<Bullet> bullets, Rectangle box, Vector2 touchPos){
        try{
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 v1 = new Vector2(), v2 = new Vector2(0,0);
            Bullet b1 = new Bullet(box.x, box.y, v1.set(touchPos).rotateDeg(-5));
            Bullet b2 = new Bullet(box.x, box.y, touchPos);
            Bullet b3 = new Bullet(box.x, box.y, v2.set(touchPos).rotateDeg(5));
            b1.setTag("sh");
            b2.setTag("sh");
            b3.setTag("sh");
            b1.setKnockbackvar(this.knockBack);
            b2.setKnockbackvar(this.knockBack);
            b3.setKnockbackvar(this.knockBack);
            b1.setDamage(damage);
            b2.setDamage(damage);
            b3.setDamage(damage);
            b1.setBspeed(bSpeed);
            b2.setBspeed(bSpeed);
            b3.setBspeed(bSpeed);

            bullets.add(b1);
            bullets.add(b2);
            bullets.add(b3);
            gun_sound.play();


        }}catch (NullPointerException e)
        {

        }

    }
    public void dispose(){
        gun_sound.dispose();
    }
}
