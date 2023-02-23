package com.prototype.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.prototype.game.GameScreen;

public class Bullet extends Enitity{



        Texture texture;
        final Vector2 targetPos;
        public Rectangle box;

        public Bullet(float x, float y,Vector2 v){
            box = new Rectangle() ;
            box.setPosition(x,y);
            texture = new Texture(Gdx.files.internal("bu.png"));
            targetPos = v;
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
        box.x += targetPos.x *10f;
        box.y += targetPos.y *10f;


    }

    @Override
    public void render(SpriteBatch batch,float delta) {

            batch.draw(texture,GameScreen.centreScreen.x + box.x, GameScreen.centreScreen.y + box.y);
    }

    @Override
    public void dispose() {

            texture.dispose();

    }
}
