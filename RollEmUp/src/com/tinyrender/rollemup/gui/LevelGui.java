package com.tinyrender.rollemup.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.tinyrender.rollemup.Assets;
import com.tinyrender.rollemup.Level;
import com.tinyrender.rollemup.LevelState;
import com.tinyrender.rollemup.RollEmUp;
import com.tinyrender.rollemup.Timer;
import com.tinyrender.rollemup.screen.PlayScreen;

public class LevelGui extends Gui implements LevelState {
	BitmapFont droidSans;
	public LabelButton paused;
	public LabelButton quit;
	public GoalMeter goalMeter;
    public Timer timer;
    
    int state;

    public LevelGui(Level level) {
    	droidSans = Assets.getBitmapFont("data/droidsans.fnt", "droidsans");
    	timer = new Timer();
    	goalMeter = new GoalMeter();
    	
    	paused = new LabelButton(droidSans, "Paused");
    	paused.setPosition(RollEmUp.TARGET_HALF_WIDTH-paused.bounds.width/2.0f, RollEmUp.TARGET_HALF_HEIGHT+150.0f);
    	
    	quit = new LabelButton(droidSans, "Quit");
    	quit.setPosition(RollEmUp.TARGET_HALF_WIDTH-quit.bounds.width/2.0f, RollEmUp.TARGET_HALF_HEIGHT+80.0f);
    }
    
    public void update(int state, int totalScore) {
    	this.state = state;
    	goalMeter.scale = totalScore * 0.009f;
    }
    
    public void render(float deltaTime) {
		Assets.batch.setProjectionMatrix(cam.combined);
		Assets.batch.begin();
		
		switch (state) {
			case PlayScreen.GAME_READY:
				ready(deltaTime);
				break;
			case PlayScreen.GAME_RUNNING:
				running(deltaTime);
				break;
			case PlayScreen.GAME_PAUSED:
				paused(deltaTime);
				break;
			case PlayScreen.GAME_LEVEL_END:
				levelEnd(deltaTime);
				break;
			case PlayScreen.GAME_OVER:
				gameOver(deltaTime);
				break;
		}
		
		Assets.batch.end();
    }
    
    @Override
	public void ready(float deltaTime) {}
	
    @Override
	public void running(float deltaTime) {
		timer.update();
		
		timer.draw();
		goalMeter.draw();
	}

    @Override
	public void paused(float deltaTime) {
		paused.draw();
		quit.draw();
	}

    @Override public void levelEnd(float deltaTime) {}
    @Override public void gameOver(float deltaTime) {}
}
