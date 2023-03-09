package com.prototype.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.prototype.game.GameScreen;

public class Crystal extends Bonus{
public static int xp;
    public Crystal(float x, float y){
       xp = 5;
       texture = new Texture(Gdx.files.internal("xp.png"));
       this.box =  new Rectangle(x,y,20,20);
    }
    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch, float delta) {
        batch.draw(texture, GameScreen.centreScreen.x + box.x,GameScreen.centreScreen.y + box.y,box.width,box.height);
    }

    @Override
    public void dispose() {
        texture.dispose();

    }
    @Override
    public void effectPlayer(Player p){
        p.upXp(Crystal.xp);
    }
}
