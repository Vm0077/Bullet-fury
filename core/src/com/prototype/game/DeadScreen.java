package com.prototype.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.prototype.until.GifDecoder;
import com.prototype.until.Score;

public class DeadScreen extends Game implements Screen {

    final Prototype game;

    OrthographicCamera camera;
    BitmapFont text;
    Score score;
    Viewport viewport;
    Music gameoverMusic;
    Sound clickSound;
    Texture title;
    private int  indexH;
    ImageButton button,exitButton;
    Stage stage;
    float elapsed;
    Animation<TextureRegion> animationBackground;




    public DeadScreen(final Prototype game, Score score){
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(game.SCREEN_WIDTH,game.SCREEN_HEIGHT,camera);
        text = new BitmapFont();
        text.setColor(Color.RED);

        this.score = score;
        gameoverMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/gameover.wav"));
        animationBackground = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP,Gdx.files.internal("background.gif").read());
        game.batch.flush();
        clickSound = Gdx.audio.newSound(Gdx.files.internal("sound/bonus.wav"));
        title = new Texture(Gdx.files.internal("gameover.png"));
        indexH = 0 ;
        create();
    }


    public ImageButton genButton(String name, float x, float y, float scaleW, float scaleH){

        Texture myTexture = new Texture(Gdx.files.internal(name));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton button = new ImageButton(myTexRegionDrawable);
        button.getImage().setFillParent(true);
        button.getImage().setScale(scaleW,scaleW);


        button.setPosition(x,y);//Set the button up



        return button;
    }


    @Override
    public void show() {
     gameoverMusic.play();


        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                clickSound.play();
                game.setScreen(new MenuScreen(game));
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
        text.getData().setScale(3,3);
        text.draw(stage.getBatch(),"Score: "+score.score+"\nPlayer Level: "+score.level+"\nTime: " + score.gameTimer.getString(),400,400);
        stage.getBatch().end();




        stage.act(Gdx.graphics.getDeltaTime()); //Perform ui logic
        stage.draw(); //Draw the ui
    }



    @Override
    public void create() {


        exitButton = genButton("button/exitButton.png",675,100,1.5f,1.5f);


        stage = new Stage(viewport); //Set up a stage for the ui

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
        gameoverMusic.dispose();
        clickSound.dispose();


    }
}
