package com.prototype.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

abstract public class Enitity {


    public Sprite sprite;
    public float Velocity;
    public Texture texture;
    public float Width, Height;
    public Vector2 pos,touchPos;
    public double hp;

    public Rectangle box;

    public void setVelocity(float velocity) {
        Velocity = velocity;
    }

    abstract public void update(float delta);

    abstract  public void render(SpriteBatch batch,float delta);

    abstract public void dispose();

}