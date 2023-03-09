package com.prototype.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.prototype.game.GameScreen;

public class Player extends Enitity {
    boolean go;
    float xp,maxXp;
    private int level;
    Animation<TextureRegion> walkAnimationRight,walkAnimationLeft; // Must declare frame type (TextureRegion)
    Texture walkSheet;
    TextureRegion currentFrame;
    Gun[] guns = new Gun[2];
    private static final int FRAME_COLS = 9, FRAME_ROWS = 2;
    boolean isDash,isBlock;
    Array<Bullet> bullets;

    public int getLevel() {
        return level;
    }


    public float getMaxXp() {
        return maxXp;
    }

    public float getXp() {
        return xp;
    }
    // intersect direction of two rectangle



    public void  upXp(int xp){
        this.xp += xp;
    }
    public  void upHp(float percent){

        hp += (int) (maxHp * (percent/100));
        if(hp > maxHp) hp = maxHp;
    }
    float stateTime;
    public Player(Texture texture, float x, float y, float width, float height){
        this.hp = 100;
        this.maxHp = 100;
        xp = 0;
        maxXp = 70;
        this.level = 1;
        Vec = new Vector2();
        walkSheet = new Texture(Gdx.files.internal("pSheet.png"));

        sprite = new Sprite(texture);
        this.box =  new Rectangle(x,y,width,height);
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
        walkAnimationRight = new Animation<TextureRegion>(0.1f, walkFramesRight);
        walkAnimationLeft  = new Animation<TextureRegion>(0.1f, walkFrameLeft);
        stateTime = 0f;
        guns[0] = new Pistol();
        guns[1] = new Shoutgun();

    }
    @Override
    public void update(float delta) {

        action(bullets);

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
        if(xp >= maxXp) leveUp();


    }
    public void block(Rectangle rectangle,Vector2 v)
    {
      isBlock = true;
      interD = new Vector2(v);
      intersect = rectangle;
    }

    public void leveUp()
    {
        this.level ++;
        float per = (float) (hp/maxHp);
        maxHp += 100;
        maxXp += 100;
        hp = (int) maxHp * per;
        xp = 0;
        System.out.println("level : " + this.level);
        GameScreen.state = GameScreen.State.STOPPED;

    }

    public Gun[] getGuns() {
        return guns;
    }

    public void setBullets(Array<Bullet> bullets) {
        this.bullets = bullets;
    }

    @Override
    public void render(SpriteBatch batch, float delta) {

         // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime

        batch.draw(currentFrame,GameScreen.centreScreen.x + box.getX(), GameScreen.centreScreen.y + box.getY(),box.width,box.height);

    }

    public void setTouch(Vector2 v){
        touchPos = v;
        pos = v;
    }
    public int keyMove(){

        int num1 = -1;
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            box.y +=Vec.y;
            num1 = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            box.y -=Vec.y;
            num1 = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            box.x+=Vec.x;
            num1 = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            box.x-=Vec.x;
            num1 = 0;
        }

        if(isBlock){
          if(intersect.getWidth()<=intersect.getHeight()){
              box.x += Math.round(interD.x) * intersect.getWidth();
              isBlock = false;
          }
          else{
             box.y += Math.round(interD.y) * intersect.getHeight();
             isBlock = false;
          }
        }


        return num1;

    }


    public void action(Array<Bullet> bullets){
        guns[0].shoot(bullets,this.box,this.touchPos);
        guns[1].shoot(bullets,this.box,this.touchPos);

    }




    @Override
    public void dispose() {
        walkSheet.dispose();
        guns[0].gun_sound.dispose();
        guns[1].gun_sound.dispose();
    }
}

