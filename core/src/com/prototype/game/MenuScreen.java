package com.prototype.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.swing.text.View;

public class MenuScreen implements Screen {

    final Prototype game;
    float SCREEN_WIDTH;
    float SCREEN_HEIGHT;


    OrthographicCamera camera;
    Viewport viewport;


   public MenuScreen(final Prototype game)
   {
       this.game = game;
       SCREEN_WIDTH = Prototype.SCREEN_WIDTH;
       SCREEN_HEIGHT = Prototype.SCREEN_HEIGHT;
       camera = new OrthographicCamera(SCREEN_WIDTH,SCREEN_HEIGHT);
       viewport = new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT);
       viewport.setCamera(camera);

   }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
