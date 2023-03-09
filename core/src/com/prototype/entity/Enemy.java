package com.prototype.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.prototype.game.GameScreen;

public class Enemy extends Enitity{
    public float knockBackVar = 0;
    public float damage;
    public boolean isKnock,isBlock,isHit;
    private static final int FRAME_COLS = 3, FRAME_ROWS = 2;
    Animation<TextureRegion> walkRight,walkLeft;
    TextureRegion currentFrame;

    Array<Enemy> enemies;
    float stateTime;
    public void setEnemies(Array<Enemy> enemies) {
        this.enemies = enemies;
    }



    public Enemy( float x ,float y, float width, float height){

        Vec = new Vector2();
        this.texture = new Texture(Gdx.files.internal("enemyZombieSheet.png"));





        touchPos = new Vector2();


        TextureRegion[][] tmp = TextureRegion.split(this.texture,
                this.texture.getWidth() / FRAME_COLS,
                this.texture.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFramesRight = new TextureRegion[FRAME_COLS] ;
        TextureRegion[] walkFrameLeft = new TextureRegion[FRAME_COLS];
        int index = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
            walkFrameLeft[i] = tmp[1][i];
            walkFramesRight[i] = tmp[0][i];
        }
        walkRight = new Animation<TextureRegion>(0.3f, walkFramesRight);
        walkLeft  = new Animation<TextureRegion>(0.3f, walkFrameLeft);
        stateTime = 0f;
        currentFrame = walkLeft.getKeyFrame(1,true);

        sprite = new Sprite(currentFrame);
        sprite.setSize(width,height);
        box = sprite.getBoundingRectangle();
        box.setPosition(x,y);
    }
    public void getTarget(float x, float y){

        this.touchPos.set(x - box.x,y - box.y);
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }


    @Override
    public void update(float delta) {


            touchPos.nor();
            box.x += touchPos.x * Vec.x *delta;
            box.y += touchPos.y * Vec.y *delta;

            if(Math.round(touchPos.x) >0){
                currentFrame = walkRight.getKeyFrame(stateTime,true);

            }
            else{
                currentFrame = walkLeft.getKeyFrame(stateTime,true);
            }


        stateTime += Gdx.graphics.getDeltaTime();
        sprite.setRegion(currentFrame);



        KnockbackState(isKnock);


    }

    @Override
    public void render(SpriteBatch batch, float delta) {



        sprite.setPosition(GameScreen.centreScreen.x + box.x,GameScreen.centreScreen.y + box.y);
        sprite.draw(batch);
        batch.setColor(Color.WHITE);

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
            box.x -= touchPos.x *Math.sqrt(Vec.x)* knockBackVar;
            box.y -= touchPos.y *Math.sqrt(Vec.y)* knockBackVar;
        }


    }

    public void hit(){
        isHit = true;





    }
    public void block(Enemy e,Rectangle inter)
    {


        if(inter.width < inter.height)
        {
            float res = (this.touchPos.y + e.touchPos.y)/2;
            if(e.box.x < this.box.x + this.box.width/2)
            {
                box.x +=  inter.width/2;
                e.box.x -= inter.width/2;
//                this.touchPos.x =res;
//                e.touchPos.x =-res;
            }
            else{
                box.x -=  inter.width/2;
                e.box.x += inter.width/2;
//                this.touchPos.x =-res;
//                e.touchPos.x =res;


            }


        }
        else{
            float res = (this.touchPos.y + e.touchPos.y)/2;
            if( e.box.y< this.box.y + (this.box.height/2))
            {
                this.box.y +=  inter.height/2;
                e.box.y -= inter.height/2;
//                this.touchPos.y =res;
//                e.touchPos.y =-res;
            }
            else{
                this.box.y  -=  inter.height/2;
                e.box.y += inter.height/2;
//                this.touchPos.y =-res;
//                e.touchPos.y =res;


            }

        }
    }
    public void touchState(Boolean isKnock){
        if((knockBackVar == 0)) {
            isKnock = false;
        }
        else{
            knockBackVar -= .5f;
        }
        if(isKnock){
            box.x -= touchPos.x *Math.sqrt(Vec.x)* knockBackVar;
            box.y -= touchPos.y *Math.sqrt(Vec.y)* knockBackVar;
        }


    }
    @Override
    public void dispose() {
        this.texture.dispose();
    }
}
