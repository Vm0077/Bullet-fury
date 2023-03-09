package com.prototype.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.awt.*;
import java.util.Vector;

interface GunAction {

    void shoot(Array<Bullet> bullets, Rectangle box, Vector2 touchPos);





}
