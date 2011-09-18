package com.tinyrender.androidgame.rollemup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL11;
import com.tinyrender.androidgame.rollemup.Assets;
import com.tinyrender.androidgame.rollemup.RollEmUp;

public class SplashScreen implements Screen {
	public static final float NEXT_SCREEN = 5.0f;
	public float totalScreenTime = 0;
	public float splashAlpha = 1.0f;
	public boolean diagnosed = false;
	
	RollEmUp game;
	
	public SplashScreen(RollEmUp g) {
		game = g;
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		Assets.batch.begin();
			if ((totalScreenTime >= 3.5f) && (splashAlpha >= 0)) {
				splashAlpha -= deltaTime;
				if(splashAlpha <= 0)
					splashAlpha = 0;
			}
			
			Assets.splashScreen.draw(Assets.batch, splashAlpha);
		Assets.batch.end();
		
		if(!(totalScreenTime <= NEXT_SCREEN))
			game.setScreen(new MainMenuScreen(game));
		
		totalScreenTime += deltaTime;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
	}
}
