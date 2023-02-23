package com.prototype.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.prototype.game.GameScreen;
import org.w3c.dom.css.Rect;

import java.awt.event.ActionEvent;
import java.lang.annotation.Target;
import java.util.Random;

public class Player extends Enitity {
    boolean go;
    Animation<TextureRegion> walkAnimationRight,walkAnimationLeft; // Must declare frame type (TextureRegion)
    Texture walkSheet;
    TextureRegion currentFrame;
    private static final int FRAME_COLS = 9, FRAME_ROWS = 2;
    boolean isDash;
    Array<Bullet> bullets;
    float lastDropTime;

    float stateTime;
    public Player(Texture texture, float x, float y, float width, float height){
        this.hp = 100;
        walkSheet = new Texture(Gdx.files.internal("pSheet.png"));
        this.box = new Rectangle(x,y,width,height);
        sprite = new Sprite(texture);
        sprite.setPosition(box.x,box.y);
        sprite.setSize(width,height);
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFramesRight = new TextureRegion[FRAME_COLS] ;
        TextureRegion[] walkFrameLeft = new TextureRegion[FRAME_COLS];
        int index = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
                walkFrameLeft[i] = tmp[1][i];
                walkFramesRight[i] = tmp[0][i];
        }
        walkAnimationRight = new Animation<TextureRegion>(0.08f, walkFramesRight);
        walkAnimationLeft  = new Animation<TextureRegion>(0.08f, walkFrameLeft);
        stateTime = 0f;

    }
    @Override
    public void update(float delta) {

        shoot(bullets);
        dash();
        int i = keyMove();
        if(i==-1){
            currentFrame = walkAnimationRight.getKeyFrame(1,false);
        }
        if(i == 1){
            currentFrame = walkAnimationRight.getKeyFrame(stateTime, true);
            stateTime += Gdx.graphics.getDeltaTime();
        }
        if(i == 0){
            stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = walkAnimationLeft.getKeyFrame(stateTime, true);
        }


    }

    public void setBullets(Array<Bullet> bullets) {
        this.bullets = bullets;
    }

    @Override
    public void render(SpriteBatch batch, float delta) {

         // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime

        batch.draw(currentFrame,GameScreen.centreScreen.x + box.getX(), GameScreen.centreScreen.y + box.getY(),40,50);

    }

    public void setTouch(Vector2 v){
        touchPos = v;
        pos = v;
    }
    public int keyMove(){
        int num1 = -1;
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            box.y +=Velocity;
            num1 = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            box.y -=Velocity;
            num1 = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            box.x+=Velocity;
            num1 = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            box.x-=Velocity;
            num1 = 0;
        }


        return num1;

    }
    public void dash(){

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){

            ///System.out.println(set.x + " "  + set.y);
            touchPos.nor();
            box.x += touchPos.x * 5 *Velocity;
            box.y += touchPos.y * 5 *Velocity;

        }
    }

    public void shoot(Array<Bullet> bullets){


        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) /*&& (TimeUtils.nanoTime() - lastDropTime > 1000000000)*/){

            bullets.add(new Bullet(this.box.x,this.box.y,touchPos));
            lastDropTime = TimeUtils.nanoTime();

        }





    }




    @Override
    public void dispose() {
        //texture.dispose();
        walkSheet.dispose();


    }
}

