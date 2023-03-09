package com.prototype.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.prototype.until.CustomButton;
import com.prototype.until.GifDecoder;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.swing.text.View;

public class MenuScreen extends Game implements Screen {

    final Prototype game;
    float SCREEN_WIDTH;
    float SCREEN_HEIGHT;
    float elapsed;
    Music music;
    Sound clickSound;

    Animation<TextureRegion> animationBackground;


    private Stage stage;
//    private Texture myTexture;
//    private TextureRegion myTextureRegion;
//    private TextureRegionDrawable myTexRegionDrawable;
    private ImageButton button,exitButton;


    OrthographicCamera camera;
    Viewport viewport;
    Texture title;


   public MenuScreen(final Prototype game)
   {
       this.game = game;
       SCREEN_WIDTH = Prototype.SCREEN_WIDTH;
       SCREEN_HEIGHT = Prototype.SCREEN_HEIGHT;
       camera = new OrthographicCamera(SCREEN_WIDTH,SCREEN_HEIGHT);
       viewport = new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT);
       viewport.setCamera(camera);
       title = new Texture(Gdx.files.internal("bullet fury.png"));
       animationBackground = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,Gdx.files.internal("background.gif").read());
       music = Gdx.audio.newMusic(Gdx.files.internal("sound/menu.mp3"));
       clickSound = Gdx.audio.newSound(Gdx.files.internal("sound/bonus.wav"));
       music.setLooping(true);
       create();

   }



    @Override
    public void show() {

        music.play();
        button.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                clickSound.play();
                game.setScreen(new GameScreen(game));
                dispose();
            }

        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {

                clickSound.play();
                Gdx.app.exit();
                dispose();
            }
        });

    }

    @Override
    public void render(float delta) {
        elapsed += Gdx.graphics.getDeltaTime();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getBatch().begin();
        stage.getBatch().draw(animationBackground.getKeyFrame(elapsed),0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().draw(title,300,400,1000,250);
        stage.getBatch().end();


        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
    }



    @Override
    public void create() {
       button = new ImageButton(null, (Drawable) null);
        button = CustomButton.genButton("button/playbutton.png", 675,200,1.5f,1.5f);
        exitButton = CustomButton.genButton("button/exitButton.png",675,100,1.5f,1.5f);


        stage = new Stage(viewport); //Set up a stage for the ui
        stage.addActor(button);
        stage.addActor(exitButton);
        //stage.addActor(exitButton);//Add the button to the stage to perform rendering and take input.
        Gdx.input.setInputProcessor(stage); //Start taking input from the ui
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
      stage.dispose();
      title.dispose();
      music.dispose();
      clickSound.dispose();


    }
}
