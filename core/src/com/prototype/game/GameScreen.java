package com.prototype.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.prototype.entity.*;
import com.prototype.until.CustomButton;
import com.prototype.until.GameTimer;
import com.prototype.until.Score;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class GameScreen extends Game implements Screen {
    float s = 0,damage,Vecl,HP,x_,y_;

   static int score,enemyTime = 1;
   Score data;
    final Prototype game;
    float SCREEN_HEIGHT;
    float SCREEN_WIDTH;
    TiledMap tiledMap;

    Vector2 touchPos;
    ImageButton damageButton,hpUP,BulletSpeed;

    Stage stage;
    OrthogonalTiledMapRenderer tMR;
    OrthographicCamera camera = new OrthographicCamera();
    FitViewport viewport;
    private float lastspawn;

    public static Vector2 centreScreen;
    MapObjects objects;

    Array<Bullet> bullets;
    private Array<Enemy> enemies;
    Array<Bonus> bonusesArrays;
    BitmapFont textfont;
    Texture tOver,LUp;

    static public State state = State.RUN;

    float x = 10, y = 10, x1 = 10, y1 = 20;

    int mapPixelWidth;
    int mapPixelHeight;
    Pixmap pm;

    Music music;
    Sound bonusSound,hitSound;



    Player pl1;
    ShapeRenderer shapeRenderer;

    Timer timer;
    GameTimer gameTimer;


    public GameScreen(final Prototype game) {

        this.game = game;
        SCREEN_WIDTH = Prototype.SCREEN_WIDTH;
        SCREEN_HEIGHT = Prototype.SCREEN_HEIGHT;

        // renderer and camera;
        tiledMap = new TmxMapLoader().load("bigmap.tmx");
        HP = 100;

        // renderer
        MapLayer mapBoundlayer = tiledMap.getLayers().get(2);
        objects = mapBoundlayer.getObjects();
        tMR = new OrthogonalTiledMapRenderer(tiledMap);
        shapeRenderer = new ShapeRenderer();
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
        String name = mapBoundlayer.getName();
        System.out.println(name);
        score = 0;


// acquire tilemap information for rending purpose;
        MapProperties prop = tiledMap.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;
        System.out.println(mapPixelWidth + " " + mapPixelHeight);
        x = mapPixelWidth / 2;
        y = mapPixelHeight / 2;
        centreScreen = new Vector2(x, y);


// create mouse icon  with asset image;
        pm = new Pixmap(Gdx.files.internal("crosshair.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 8, 8));
        tOver = new Texture(Gdx.files.internal("pause.png"));
        LUp = new Texture(Gdx.files.internal("leveup.png"));


        /// sound and music;
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/Music.mp3"));
        bonusSound = Gdx.audio.newSound(Gdx.files.internal("sound/bonus.wav"));
        hitSound =  Gdx.audio.newSound(Gdx.files.internal("sound/hit.wav"));
        music.setLooping(true);



// create every entity
        pl1 = new Player(new Texture(Gdx.files.internal("guy.png")), 0, 0, 40, 50);
        bullets = new Array<>();
        enemies = new Array<>();
        bonusesArrays = new Array<>();
        pl1.setBullets(bullets);
        textfont = new BitmapFont();
        pl1.setVec(2.5f, 2.5f);

        damage = 0.5f;
        Vecl = 0.7f;
        //create Stage
        stage = new Stage();

        // create timer
        timer = new Timer();
        gameTimer = new GameTimer(0,0);
        create();

    }

    public enum State {
        PAUSE,
        RUN,
        RESUME,
        STOPPED
    }

    private void spawnEnemy() {
        Enemy enemy = new Enemy(0, 0, 40, 50);
        Vector2 pos = new Vector2();
        pos.set(MathUtils.random(centreScreen.x-100, centreScreen.x), MathUtils.random(MathUtils.random(centreScreen.y- 100, centreScreen.y))).rotateRad(MathUtils.random(0, MathUtils.PI2));
        enemy.setVx(Vecl);
        enemy.setVy(Vecl);
        enemy.setDamage(damage);
        enemy.setHp(HP);
        enemy.box.setPosition(pos);
        enemy.setEnemies(enemies);
        enemies.add(enemy);

    }

    private void enemyLevelup(){

       if(enemyTime == gameTimer.getMn()) {
           Vector2 v = new Vector2();
           this.Vecl += 0.25f;
           v.set(MathUtils.random(0 , centreScreen.x), MathUtils.random(MathUtils.random(0, centreScreen.y))).rotateRad(MathUtils.random(0, MathUtils.PI2));
           Heath h = new Heath(v.x,v.y);
           bonusesArrays.add(h);
           System.out.println("Heath Spawn, Enemy Level Up");
           HP += 30;
           enemyTime = gameTimer.getMn()+1;
       }
    }
    private void  drawLevelBar() {
        //draw border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(300,SCREEN_HEIGHT - 50,1005,20);
        shapeRenderer.end();
        // draw fill rectangle
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GOLD);
        shapeRenderer.rect(302,SCREEN_HEIGHT - 47,(pl1.getXp() / pl1.getMaxXp())*1000f,15);

        shapeRenderer.end();
    }


    private void drawHealthBar(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(100, 100, 200, 20);
        shapeRenderer.setColor(Color.GREEN);

        shapeRenderer.rect(100, 100, (float) (pl1.getHp() / pl1.getMaxHp())*200f, 20);
        shapeRenderer.end();

    }

    // put this in batch.begin and batch.end for processing
    private void drawTextHeatlh(){

        textfont.setColor(Color.RED);
        textfont.getData().setScale(1.2f,1.2f);

        textfont.draw(game.batch,pl1.getHp()+" / "+pl1.getMaxHp(),(int)(camera.position.x - 700),(int)(camera.position.y -250));
        textfont.draw(game.batch,"score: "+score,(int)(camera.position.x - 400),(int)(camera.position.y -250));
    }
    private  void drawTextXp(){
        textfont.setColor(Color.GOLD);
        textfont.getData().setScale(1.5f,1.5f);



        textfont.draw(game.batch,"level "+pl1.getLevel(),(int)(camera.position.x - 580),(int)(camera.position.y + 368));

        textfont.draw(game.batch,"level "+(pl1.getLevel()+1),(int)(camera.position.x + 550),(int)(camera.position.y +368));
        //draw the timer of the level;
        //without using redundant code
        String time = String.format("%02d:%02d",gameTimer.getMn(),gameTimer.getS());
        textfont.getData().setScale(2f,2f);
        textfont.draw(game.batch,time,camera.position.x-10,camera.position.y+ 200);

    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.update();
        tMR.setView((OrthographicCamera) viewport.getCamera());
        tMR.render();
        float x_ = Gdx.input.getX();
        float y_ = Gdx.input.getY();
        touchPos = new Vector2(x_ - SCREEN_WIDTH / 2, -y_ + SCREEN_HEIGHT / 2);

        boolean go = false;

        switch (state) {


            case RUN:
                pl1.sprite.setPosition(centreScreen.x + pl1.box.x, centreScreen.y + pl1.box.y);
                pl1.update(0f);


                Array.ArrayIterator<Bullet> iter = bullets.iterator();


                while (iter.hasNext()) {
                    Bullet b = iter.next();
                    b.update(0f);
                    if (b.getX() < -centreScreen.x || b.getX() > centreScreen.x || b.getY() > centreScreen.y || b.getY() < -centreScreen.y) {
                        iter.remove();


                    }

                }
                Array.ArrayIterator<Bonus> iter1 = bonusesArrays.iterator();
                while (iter1.hasNext()) {
                    Bonus c = iter1.next();
                    if(c.checkTime(Gdx.graphics.getDeltaTime())){
                        iter1.remove();
                    }
                    if (pl1.box.overlaps(c.box)) {
                        c.effectPlayer(pl1);
                        iter1.remove();
                        bonusSound.play();
                    }


                }
                for (Bullet b : bullets) {
                    for (Enemy e : enemies) {
                        if (Intersector.overlaps(b.box, e.box)) {

                            e.isKnock = true;
                            e.knockBackVar = b.getKnockbackvar();
                            switch (b.getTag()) {
                                case "sh":

                                    break;
                                default:

                                    break;
                            }
                            bullets.removeValue(b, true);
                            hitSound.play();
                            e.hit();

                            e.hp -= b.getDamage();


                        }

                        if (e.hp < 0) {
                            bonusesArrays.add(new Crystal(e.box.x, e.box.y));
                            enemies.removeValue(e, true);
                            score += 3;
                            e.dispose();

                        }

                    }
                }
                Rectangle InterR = new Rectangle();
                for (int i = 0; i < enemies.size; i++) {

                    enemies.get(i).getTarget(pl1.box.x, pl1.box.y);


                    for (int j = 0; j < enemies.size; j++) {
                        if (i == j) continue;
                        if (Intersector.intersectRectangles(enemies.get(i).box, enemies.get(j).box, InterR)) {
                            Rectangle r = new Rectangle(), r1 = new Rectangle();
                            r = enemies.get(i).box;
                            r1 = enemies.get(j).box;
                            Vector2 v = new Vector2((r.x - r1.x), (r.y - r1.y));
                            v.nor();


                            enemies.get(i).block(enemies.get(j), InterR);


                        }

                    }
                    enemies.get(i).update(1f);
                }



                game.batch.begin();


                for (Bullet b : bullets
                ) {
                    b.render(game.batch, 0f);

                }
                for (Enemy e : enemies) {

                    e.render(game.batch, 0f);
                }
                pl1.render(game.batch, 0f);

                for (Bonus c : bonusesArrays) {
                    c.render(game.batch, 0f);
                }

                drawTextHeatlh();
                drawTextXp();
               // textfont.draw(game.batch, "camera position x :" + camera.position.x + " y: " + camera.position.y, camera.position.x, camera.position.y);
                game.batch.end();



                for (int I = 0; I < enemies.size; I++) {

                // when player sprite overlay enemy and getting damage;
                if (Intersector.overlaps(enemies.get(I).box, pl1.box)) pl1.hp -= enemies.get(I).damage;

                 }
                for (RectangleMapObject rectangleMapObject : objects.getByType(RectangleMapObject.class)) {
                    Rectangle rectangle = rectangleMapObject.getRectangle();
                    Rectangle rectangle1 = new Rectangle();
                    rectangle1.set(pl1.box.x + centreScreen.x, pl1.box.y + centreScreen.y, pl1.box.width, pl1.box.height);
                    Rectangle intersect = new Rectangle();

                    if (Intersector.intersectRectangles(rectangle, rectangle1, intersect)) {
                        Vector2 v = new Vector2((rectangle1.width / 2 + rectangle1.getX()) - (rectangle.width / 2 + rectangle.getX()), (rectangle1.height / 2 + rectangle1.getY()) - (rectangle.height / 2 + rectangle.getY()));
                        v.nor();
                        pl1.block(intersect, v);


                    }



                }
                s += Gdx.graphics.getDeltaTime();
                if(s>=1)
                {
                    gameTimer.update();
                    s=0;
                }
                lastspawn += Gdx.graphics.getDeltaTime();


                if(lastspawn >= 2)
                {
                    spawnEnemy();
                    lastspawn = 0;
                }

                // draw graphic bar
                drawHealthBar();
                drawLevelBar();

                // every minute passed enemy level up
                enemyLevelup();

                break;
            case PAUSE:

                game.batch.begin();
                game.batch.draw(tOver, camera.position.x - tOver.getWidth() / 2, camera.position.y - tOver.getHeight() / 2);
                game.batch.end();


                break;

            case RESUME:
                break;
            case STOPPED:
                buffDialog();
                break;
        }





// camera movement attach to the player;

        camera.position.x = centreScreen.x + pl1.box.x;
        camera.position.y = centreScreen.y + pl1.box.y;
        pl1.setTouch(touchPos);


/// render health bar


        /// set batch to draw camera
        game.batch.setProjectionMatrix(viewport.getCamera().combined);

        /// enemies spawn interval and conditions
        if (pl1.hp <= 0 || Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            data = new Score();
            data.level = pl1.getLevel();
            data.score = score;
            data.gameTimer =gameTimer;
            dispose();
           game.setScreen(new DeadScreen(game,data));

        }
        if (pl1.box.getX() < -centreScreen.x -30 || pl1.box.getX() > centreScreen.x + 30|| pl1.box.getY() > centreScreen.y + 30 || pl1.box.getY() < -centreScreen.y - 30) {

            data = new Score();
            data.level = pl1.getLevel();
            data.score = score;
            data.gameTimer =gameTimer;
            game.setScreen(new DeadScreen(game,data));
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
           pl1.leveUp();
        }



        /// DEAD
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            dispose();
//            music.stop();
            //game.setScreen(new DeadScreen(game));
            this.pause();


        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//            dispose();
//            music.stop();
            //game.setScreen(new DeadScreen(game));
            this.state = State.STOPPED;


        }


        /// draw every object with batch


    }

    @Override
    public void dispose() {

        pl1.dispose();
        tMR.dispose();
        tiledMap.dispose();
        pm.dispose();

        pl1.dispose();
        tOver.dispose();


        for (Bullet b : bullets
        ) {
            b.dispose();
        }
        for (Enemy b : enemies) {
            b.dispose();
        }

        for (Bonus c : bonusesArrays) {
            c.dispose();
        }
        music.dispose();
        stage.dispose();
        hitSound.dispose();





    }

    @Override
    public void show() {
        music.play();final Gun[] guns = pl1.getGuns();
        damageButton.addListener(new ClickListener(){

            @Override
            public void clicked (InputEvent event, float x, float y) {
                guns[0].setDamage(guns[0].getDamage()*(1.5f));
                guns[1].setDamage(guns[1].getDamage()*(1.5f));
                state = State.RUN;
            }

        });
        hpUP.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                pl1.upHp((float)MathUtils.random(10,100));
                state = State.RUN;

            }
        });

        BulletSpeed.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                guns[0].setbSpeed((float) (guns[0].getbSpeed() * (1.3)));
                guns[1].setbSpeed((float) (guns[1].getbSpeed() * (1.3)));
                state = State.RUN;



            }
        });


    }


    @Override
    public void create() {
        damageButton = CustomButton.genButton("button/damage@4x.png",pl1.box.x + centreScreen.x + 200,pl1.box.y + centreScreen.y,2,2);
        hpUP = CustomButton.genButton("button/hp@4x.png",pl1.box.x + centreScreen.x + 200,pl1.box.y + centreScreen.y - 100,2,2);
        BulletSpeed = CustomButton.genButton("button/bspeed@4x.png",pl1.box.x + centreScreen.x + 200,pl1.box.y + centreScreen.y -200,2,2);
        stage = new Stage(viewport);
        stage.addActor(damageButton);
        stage.addActor(hpUP);
        stage.addActor(BulletSpeed);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        if (this.state != State.PAUSE) {
            this.state = State.PAUSE;
            music.pause();
        } else {
            this.resume();
            show();
        }


    }
    public void buffDialog(){
        damageButton.setPosition(pl1.box.x + centreScreen.x - 120,pl1.box.y + centreScreen.y);
        hpUP.setPosition(pl1.box.x + centreScreen.x - 120,pl1.box.y + centreScreen.y - 100);
        BulletSpeed.setPosition(pl1.box.x + centreScreen.x - 120,pl1.box.y + centreScreen.y - 200);
        stage.getBatch().begin();
        stage.getBatch().draw(LUp,pl1.box.x + centreScreen.x - LUp.getWidth()/2,pl1.box.y + centreScreen.y + 100);
        stage.getBatch().end();
        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
    }

    @Override
    public void resume() {
        this.state = State.RUN;

    }

    @Override
    public void hide() {

    }

}
