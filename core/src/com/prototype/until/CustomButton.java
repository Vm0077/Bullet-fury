package com.prototype.until;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CustomButton {
    public static ImageButton genButton(String name, float x, float y, float scaleW, float scaleH){

        Texture myTexture = new Texture(Gdx.files.internal(name));
        TextureRegion myTextureRegion = new TextureRegion(myTexture);
        TextureRegionDrawable myTexRegionDrawable = new TextureRegionDrawable(myTextureRegion);
        ImageButton button = new ImageButton(myTexRegionDrawable);
        button.getImage().setFillParent(true);
        button.getImage().setScale(scaleW,scaleW);


        button.setPosition(x,y);//Set the button up



        return button;
    }
}
