package com.scrappile.sushiloco.object;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.scrappile.sushiloco.GameObject;
import com.scrappile.sushiloco.Level;
import com.scrappile.sushiloco.box2d.BodyFactory;

public class GameCircleObject extends GameObject {

	public GameCircleObject(TextureRegion texture, float screenPosX, float screenPosY, float angle, float density, int level, int points, boolean isSensor, boolean doUpdate, Type type) {
		screenPosX /= Level.PTM_RATIO;
		screenPosY /= Level.PTM_RATIO;
		this.points = points;
		this.level = level;
		this.type = type;

		objRep.setTexture(texture);

		float radius = objRep.getWidth() / 2.0f / Level.PTM_RATIO;

		body = BodyFactory.createCircle(screenPosX, screenPosY, radius, density, angle, 1.0f, isSensor, BodyType.DynamicBody, this);
	}
}
