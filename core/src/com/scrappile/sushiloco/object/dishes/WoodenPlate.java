package com.scrappile.sushiloco.object.dishes;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.scrappile.sushiloco.Assets;
import com.scrappile.sushiloco.GameObject;
import com.scrappile.sushiloco.Level;
import com.scrappile.sushiloco.box2d.BodyFactory;
import com.scrappile.sushiloco.box2d.PhysicsObject.Type;
import com.scrappile.sushiloco.object.ObjectFactory;

public class WoodenPlate implements ObjectFactory {

	private Array<Vector2[]> verts = new Array<Vector2[]>(3);
	private TextureRegion texture = Assets.atlas.findRegion("plate1");
	private Vector2 body[] = {
			new Vector2(-190.4f / Level.PTM_RATIO, 31.3f / Level.PTM_RATIO),
			new Vector2(-190.4f / Level.PTM_RATIO, -3.7f / Level.PTM_RATIO),
			new Vector2(190.4f / Level.PTM_RATIO, -3.7f / Level.PTM_RATIO),
			new Vector2(190.4f / Level.PTM_RATIO, 31.3f / Level.PTM_RATIO)
		};
	
	private Vector2 leg_l[] = {
			new Vector2(-144.4f / Level.PTM_RATIO, -4.4f / Level.PTM_RATIO),
			new Vector2(-143.7f / Level.PTM_RATIO, -30.6f / Level.PTM_RATIO),
			new Vector2(-108.4f / Level.PTM_RATIO, -30.2f / Level.PTM_RATIO),
			new Vector2(-102.7f / Level.PTM_RATIO, -16.4f / Level.PTM_RATIO),
			new Vector2(-92.1f / Level.PTM_RATIO, -10.8f / Level.PTM_RATIO),
			new Vector2(-93.2f / Level.PTM_RATIO, -5.5f / Level.PTM_RATIO)
		};
	
	private Vector2 leg_r[] = {
			new Vector2(90.3f / Level.PTM_RATIO, -5.1f / Level.PTM_RATIO),
			new Vector2(99.9f / Level.PTM_RATIO, -15.4f / Level.PTM_RATIO),
			new Vector2(108.0f / Level.PTM_RATIO, -30.2f / Level.PTM_RATIO),
			new Vector2(143.7f / Level.PTM_RATIO, -30.2f / Level.PTM_RATIO),
			new Vector2(143.0f / Level.PTM_RATIO, -5.1f / Level.PTM_RATIO),
		};

	WoodenPlate() {
		verts.add(body);
		verts.add(leg_l);
		verts.add(leg_r);
	}
	
	@Override
	public GameObject build(float screenPosX, float screenPosY) {
		screenPosX /= Level.PTM_RATIO;
		screenPosY /= Level.PTM_RATIO;
		GameObject woodenPlate = new GameObject();
		
		woodenPlate.level = 3;
		woodenPlate.points = 8;
		woodenPlate.type = Type.ROLLABLE;

		woodenPlate.objRep.setTexture(texture);
		
		screenPosY += woodenPlate.objRep.getHalfHeight() / Level.PTM_RATIO;
				
		woodenPlate.body = BodyFactory.createPoly(verts, screenPosX, screenPosY, 1.1f, 1.2f, BodyType.DynamicBody, woodenPlate);
		
		return woodenPlate;
	}
}
