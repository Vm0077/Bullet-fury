package com.prototype.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.prototype.game.GameScreen;

public class Bullet extends Enitity{


        private String tag;
        private float knockbackvar;
        private  float damage;
        private float bspeed;

        Texture texture;
        final Vector2 targetPos;
        public Rectangle box;

    public void setKnockbackvar(float knockbackvar) {
        this.knockbackvar = knockbackvar;
    }

    public void setBspeed(float bspeed) {
        this.bspeed = bspeed;
    }

    public float getKnockbackvar() {
        return knockbackvar;
    }

    public Bullet(float x, float y, Vector2 v){
            box = new Rectangle() ;
            box.setPosition(x,y);
            bspeed = 8;

            texture = new Texture(Gdx.files.internal("bu.png"));

            targetPos = v;
        }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void setTexture(String ftext) {
        this.texture = new Texture(Gdx.files.internal(ftext));
    }

    public Texture getTexture() {
        return texture;
    }

    public float getX() {
        return box.getX();
    }

    public float getY() {
        return box.getY();
    }

    @Override
    public void update(float delta) {

            targetPos.nor();
            box.x += targetPos.x * bspeed;
            box.y += targetPos.y * bspeed;



    }

    @Override
    public void render(SpriteBatch batch,float delta) {

            batch.draw(texture,GameScreen.centreScreen.x + box.x, GameScreen.centreScreen.y + box.y,15,15);
    }

    @Override
    public void dispose() {
            texture.dispose();

    }
}
