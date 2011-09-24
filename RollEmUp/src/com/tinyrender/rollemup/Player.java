package com.tinyrender.rollemup;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Player extends GameObject {
	final static float MAX_VELOCITY = 10.0f;

	public Fixture bodyFixture;
	public Body groundSensor;
	public Fixture groundSensorFixture;
	
	public boolean isGrounded;
	public boolean isJumping;
	
	ArrayList<GameObject> rolledObjects = new ArrayList<GameObject>();
	
	public Player(PhysicsWorld world) {
		super(world);
		body = createPlayer(BodyType.DynamicBody, 0, 5.0f, 2.5f, 1.0f);
		body.setUserData("player");
	}
	
	public void update() {
		vel = body.getLinearVelocity();
		pos = body.getPosition();
		isGrounded = isPlayerGrounded();

		// terminal velocity on x	
		if (Math.abs(vel.x) > MAX_VELOCITY) {			
			vel.x = Math.signum(vel.x) * MAX_VELOCITY;
			body.setLinearVelocity(vel.x, vel.y);
		}
 
		// apply a force when the phone is tilted enough,
		// otherwise dampen down the acceleration to a stop
		if ((Gdx.input.getAccelerometerY() <= -0.2f && vel.x > -MAX_VELOCITY) ||
				Gdx.input.getAccelerometerY() >= 0.2f && vel.x < MAX_VELOCITY) {
			body.applyForceToCenter(Gdx.input.getAccelerometerY() * 12.0f, 0);
		} else {
			body.setLinearVelocity(vel.x * 0.9f, vel.y);
		}

		// regain rolling momentum with a small impulse
		if (vel.x < MAX_VELOCITY/3 || vel.x > -MAX_VELOCITY/3)
			body.applyLinearImpulse(Gdx.input.getAccelerometerY() * 2.0f, 0, pos.x, pos.y);
		
		// jump if grounded
		if(isJumping) {
			isJumping = false;
			if(isGrounded)
				body.applyLinearImpulse(0, 300.0f, pos.x, pos.y);
		}
	}
	
	private Body createPlayer(BodyType bodyType, float x, float y, float radius, float density) {
		BodyDef bd = new BodyDef();
		bd.type = bodyType;
		bd.position.set(x, y);
		Body body = world.b2world.createBody(bd);
 
		CircleShape shape = new CircleShape();		
		shape.setRadius(radius);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = shape;
		fd.density = density;
		fd.friction = 1.0f;
		bodyFixture = body.createFixture(fd);
		shape.dispose();
		
		groundSensor = createPlayerSensor(x, y-radius+.5f, radius/2);
		
		Vector2 anchorA = new Vector2(body.getPosition().x, body.getPosition().y);
		RevoluteJointDef djd = new RevoluteJointDef();
		
		djd.initialize(body, groundSensor, anchorA);
		world.b2world.createJoint(djd);
		
		return body;
	}
	
	private Body createPlayerSensor(float x, float y, float radius) {
		BodyDef bd = new BodyDef();
		bd.position.set(x, y);
		bd.type = BodyType.DynamicBody;
		Body body = world.b2world.createBody(bd);
 
		CircleShape shape = new CircleShape();		
		shape.setRadius(radius);
		
		FixtureDef fd = new FixtureDef();
		fd.isSensor = true;
		fd.shape = shape;
		groundSensorFixture = body.createFixture(fd);
		shape.dispose();
		
		return body;
	}

	private boolean isPlayerGrounded() {
		List<Contact> contactList = world.b2world.getContactList();
		for(int i = 0; i < contactList.size(); i++) {
			Contact contact = contactList.get(i);
			if(contact.isTouching() && (contact.getFixtureA() == groundSensorFixture ||
			   contact.getFixtureB() == groundSensorFixture)) {
				return true;
			}
		}
		return false;
	}
	
	public void enterContact() {
		
	}
	
	public void leaveContact() {
		
	}
}
