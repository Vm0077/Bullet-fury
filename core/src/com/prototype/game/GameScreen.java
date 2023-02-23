package com.prototype.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.prototype.entity.Bullet;
import com.prototype.entity.Enemy;
import com.prototype.entity.Player;

import java.security.Key;
import java.util.Iterator;
import java.util.Vector;

public class GameScreen implements Screen {


    final Prototype game;
    float SCREEN_HEIGHT;
    float SCREEN_WIDTH;
    TiledMap tiledMap;

    Stage stage;

    OrthogonalTiledMapRenderer tMR;
    OrthographicCamera camera = new OrthographicCamera();
    FitViewport viewport;
    private long lastspawn;

    public static Vector2 centreScreen;
    Rectangle r;

    Array<Bullet> bullets;
    Array<Enemy> enemies;
    BitmapFont textfont,damage;

    float x=10, y=10,x1=10,y1=20;

    int mapPixelWidth;
    int mapPixelHeight;
    Pixmap pm ;


    Player pl1;
    ShapeRenderer shapeRenderer;
    public GameScreen(final Prototype game){

        this.game = game;
        SCREEN_WIDTH = Prototype.SCREEN_WIDTH;
        SCREEN_HEIGHT = Prototype.SCREEN_HEIGHT;

        // renderer and camera;
        tiledMap = new TmxMapLoader().load("bigmap.tmx");
        tMR = new OrthogonalTiledMapRenderer(tiledMap);
        shapeRenderer = new ShapeRenderer();
        camera.setToOrtho(false,SCREEN_WIDTH,SCREEN_HEIGHT);
        viewport = new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT,camera);





// acquire tilemap information for rending purpose;
        MapProperties prop = tiledMap.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);
        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;
        System.out.println(mapPixelWidth + " " + mapPixelHeight);
        x = mapPixelWidth/2;
        y = mapPixelHeight/2;
        centreScreen = new Vector2(x,y);


// create mouse icon  with asset image;
        pm = new Pixmap(Gdx.files.internal("crosshair.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 8, 8));


// create every entity
        pl1 = new Player(new Texture(Gdx.files.internal("guy.png")),0,0,50,100);
        bullets = new Array<>();
        enemies = new Array<>();
        pl1.setBullets(bullets);
        textfont = new BitmapFont();
        pl1.setVelocity(2.5f);

    }

    public void spawnEnemy(){
        Enemy enemy = new Enemy(new Texture(Gdx.files.internal("crosshair.png")),0,0,30,30);
        Vector2 pos = new Vector2();
        pos.set(MathUtils.random(pl1.box.x+camera.position.x/2,centreScreen.x),MathUtils.random(MathUtils.random(pl1.box.y+camera.position.y/2,centreScreen.y))).rotateRad(MathUtils.random(0,MathUtils.PI2));
        enemy.setVelocity(5f);
        enemy.box.setPosition(pos);
        enemies.add(enemy);
        lastspawn = TimeUtils.nanoTime();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        camera.update();
        tMR.setView((OrthographicCamera) viewport.getCamera());
        tMR.render();
        float x_ = Gdx.input.getX();
        float y_ = Gdx.input.getY();
        Vector2 touchPos = new Vector2(x_- SCREEN_WIDTH/2,-y_ + SCREEN_HEIGHT/2);
        boolean go = false;




        Vector3 v3 = new Vector3();
        camera.unproject(v3);










        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            game.batch.begin();
            textfont.draw(game.batch,"clicked  x :"+Gdx.input.getX()+ " y: "+Gdx.input.getY(),v3.x,v3.y);
            game.batch.end();
        }
        Array.ArrayIterator<Bullet> iter = bullets.iterator();
        Array.ArrayIterator<Enemy> iter1 = enemies.iterator();


        while (iter.hasNext()) {
            Bullet b = iter.next();
            b.update(0f);
            if (b.getX() < -centreScreen.x || b.getX() > centreScreen.x || b.getY() > centreScreen.y || b.getY() < -centreScreen.y) {
                iter.remove();


            }

        }

        while (iter1.hasNext()){
            Enemy e = iter1.next();
            e.getTarget(pl1.box.x,pl1.box.y);
            e.update(0f);


        }
       for(Bullet b: bullets) {
           for (Enemy e: enemies){
               if(Intersector.overlaps(b.box,e.box))
               {
                   e.knockBackVar = 7;
                   e.isKnock = true;
                   bullets.removeValue(b,true);
                   e.hp -= 30;



               }

               if(e.hp < 0) enemies.removeValue(e,true);
           }
       }
       for(Enemy e: enemies)
       {
           if(Intersector.overlaps(e.box,pl1.box)) pl1.hp -=0.5f;
       }


// handle touchPostion;

        camera.position.x = centreScreen.x + pl1.box.x;
        camera.position.y = centreScreen.y + pl1.box.y;
        pl1.setTouch(touchPos);




        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(100,100,200,20);

        shapeRenderer.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(100,100, (float) (pl1.hp*2f),20);
        shapeRenderer.end();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);

        if(TimeUtils.nanoTime() - lastspawn > 2000_000_000 ) spawnEnemy();
        pl1.sprite.setPosition(centreScreen.x+pl1.box.x,centreScreen.y+pl1.box.y);
        pl1.update(0f);
        game.batch.begin(); pl1.render(game.batch,0f);
        for (Bullet b:bullets
        ) {
            b.render(game.batch,0f);

        }
        for(Enemy e: enemies){
            e.render(game.batch,0f);
        }

        textfont.draw(game.batch,"camera position x :"+camera.position.x + " y: "+camera.position.y, camera.position.x, camera.position.y);
        game.batch.end();

        if(pl1.hp < 0)
        {
            game.setScreen(new DeadScreen(this.game));
        }





    }
    @Override
    public void dispose () {

        pl1.dispose();
        tMR.dispose();
        tiledMap.dispose();
        pm.dispose();
        game.batch.dispose();
        pl1.dispose();


        for (Bullet b:bullets
             ) {
            b.dispose();
        }
        for(Enemy b: enemies){
            b.dispose();
        }





    }
    @Override
    public void show() {


        
    }






    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

}
