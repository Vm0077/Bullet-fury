package com.prototype.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.prototype.game.GameScreen;

public class Enemy extends Enitity{
    public float knockBackVar = 0;
    public boolean isKnock;

    public Enemy(Texture texture, float x ,float y, float width, float height){
        this.texture = texture;
        box = new Rectangle(x,y,width,height);
        touchPos = new Vector2();
        this.hp = 100;



    }
    public void getTarget(float x, float y){

        this.touchPos.set(x - box.x,y - box.y);
    }



    @Override
    public void update(float delta) {

        touchPos.nor();
        box.x += touchPos.x * Velocity;
        box.y += touchPos.y * Velocity;
        KnockbackState(isKnock);


    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        batch.draw(this.texture,GameScreen.centreScreen.x + box.x,GameScreen.centreScreen.y + box.y,30,30);

    }
    public void renderHpbar(ShapeRenderer renderer){


    }
    public void KnockbackState(Boolean isKnock){
        if((knockBackVar == 0)) {
            isKnock = false;
        }
        else{
            knockBackVar -= .5f;
        }
        if(isKnock){
            box.x -= touchPos.x *Math.sqrt(Velocity)* knockBackVar;
            box.y -= touchPos.y *Math.sqrt(Velocity)* knockBackVar;
        }


    }
    @Override
    public void dispose() {
        this.texture.dispose();
    }
}
