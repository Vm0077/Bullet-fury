package com.prototype.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class DeadScreen implements Screen {

    Prototype game;
    OrthographicCamera camera;
    BitmapFont text;
    Viewport viewport;



    public DeadScreen(Prototype game){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(game.SCREEN_WIDTH,game.SCREEN_HEIGHT,camera);
        text = new BitmapFont();
        text.setColor(Color.RED);


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();
        text.draw(game.batch,"YOU DEAD \n CLICK TO CONTINUE",100,100);
        game.batch.end();


        if(Gdx.input.isTouched())
        {
            game.setScreen(new GameScreen(this.game));
            dispose();
        }
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

    @Override
    public void dispose() {

    }
}
