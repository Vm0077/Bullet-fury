package com.prototype.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.prototype.game.GameScreen;

public class Heath extends Bonus {
    public Heath(float x, float y){
        hp = MathUtils.random(20,100);
        texture = new Texture(Gdx.files.internal("hpUp.png"));
        this.box =  new Rectangle(x,y,30,30);
    }
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        batch.draw(texture, GameScreen.centreScreen.x + box.x,GameScreen.centreScreen.y + box.y,box.width,box.height);
    }
    @Override
    public boolean checkTime(float dt){
        return false;
    }


    @Override
    public void effectPlayer(Player p) {
        p.upHp((float)hp);
    }
}
