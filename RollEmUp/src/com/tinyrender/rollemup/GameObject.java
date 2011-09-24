package com.tinyrender.rollemup;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameObject {
	public Body body;
	public Vector2 vel;
	public Vector2 pos;
	public boolean isContacting;
	
	public PhysicsWorld world;
	
	public GameObject(PhysicsWorld world) {
		this.world = world;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}
	
	public abstract void enterContact();
	public abstract void leaveContact();
}
