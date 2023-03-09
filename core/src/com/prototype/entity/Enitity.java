package com.prototype.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

abstract public class Enitity {


    public Sprite sprite;
    public Vector2 Vec;
    public Texture texture;
    public float Width, Height;
    public Vector2 pos,touchPos;
    public Vector2 interD;
    public double hp, maxHp;

    public Rectangle box,intersect;

    public double getHp() {
        return hp;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public void setVx(float vx) {
        Vec.x = vx;
    }

    public void setVy(float vy) {
        Vec.y = vy;
    }
    public void setVec(float vx,float vy)
    {
        Vec.x = vx;
        Vec.y = vy;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    abstract public void update(float delta);

    abstract  public void render(SpriteBatch batch,float delta);

    abstract public void dispose();

}