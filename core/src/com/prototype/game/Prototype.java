package com.prototype.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Prototype extends Game implements InputProcessor {
	static float SCREEN_HEIGHT = 800;
	static float SCREEN_WIDTH  = 1600;
	SpriteBatch batch;
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		this.setScreen(new MenuScreen(this));

	}

	@Override
	public void render()
	{
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
