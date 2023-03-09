package com.prototype.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.prototype.game.GameScreen;

abstract public class Bonus extends Enitity {

    float despawnTime;
    float time = 10;
    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        batch.draw(texture, GameScreen.centreScreen.x + box.x,GameScreen.centreScreen.y + box.y,box.width,box.height);
    }

    public boolean checkTime(float dt)
    {
        despawnTime += dt;
        if(despawnTime >= time){
            this.dispose();
            return true;
        }
        return false;
    }

    @Override
    public void dispose() {
        this.texture.dispose();
    }
    abstract public void effectPlayer(Player p);
}
