package com.scrappile.sushiloco.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.scrappile.sushiloco.box2d.PhysicsObject.Type;

public abstract class PhysicsWorld implements ContactListener {
    public final static int PTM_RATIO = 64;
    final static float UPDATE_INTERVAL = 1.0f / 60.0f;
    final static float MINIMUM_TIMESTEP = UPDATE_INTERVAL / 2.0f;
    final static int MAX_CYCLES_PER_FRAME = 25;
    public static World b2world;
    private static Vector2 gravity = new Vector2(0, -12.0f);
    // TODO: Make private and create listeners
    public FrustrumCulling frustrumCulling = new FrustrumCulling();
    private Array<Body> bodies = new Array<Body>();

    public PhysicsWorld() {
        b2world = new World(gravity, true);
        b2world.setContactListener(this);
    }

    protected void physicsStep(float deltaTime) {
        float frameTime = deltaTime;
        int stepsPerformed = 0;

        while (frameTime > 0.0f && stepsPerformed < MAX_CYCLES_PER_FRAME) {
            float dt = Math.min(frameTime, UPDATE_INTERVAL);
            frameTime -= dt;

            if (frameTime < MINIMUM_TIMESTEP) {
                dt += frameTime;
                frameTime = 0.0f;
            }

            b2world.step(dt, 5, 3);
            stepsPerformed++;
        }

        b2world.clearForces();
    }

    protected void updateBodies() {
        PhysicsObject physicsObject;

        getBodies();

        for (Body b : bodies) {
            physicsObject = (PhysicsObject) b.getUserData();

            if (physicsObject.isDead) {
                b2world.destroyBody(physicsObject.body);
            } else {
                // TODO: Move to FrustrumCulling
                if (physicsObject.type == Type.ROLLABLE) {
                    if (frustrumCulling.contains(b.getPosition())) {
                        if (!b.isActive())
                            b.setActive(true);
                    } else {
                        b.setActive(false);
                    }
                }

                if (physicsObject.body.isActive())
                    physicsObject.update();
            }
        }
    }

    public FrustrumCulling getFrustrumCulling() {
        return frustrumCulling;
    }

    public World getWorld() {
        return b2world;
    }

    public void resumeWorld() {
        if (null == b2world)
            b2world = new World(gravity, true);
    }

    public Array<Body> getBodies() {
        b2world.getBodies(bodies);
        return bodies;
    }

    public void disposeWorld() {
        b2world.dispose();
        b2world = null;
    }

    @Override
    public void beginContact(Contact contact) {
        PhysicsObject objectA = (PhysicsObject) contact.getFixtureA().getBody().getUserData();
        PhysicsObject objectB = (PhysicsObject) contact.getFixtureB().getBody().getUserData();

        objectA.contactResolver.enterContact(objectB);
        objectB.contactResolver.enterContact(objectA);
    }

    @Override
    public void endContact(Contact contact) {
        PhysicsObject objectA = (PhysicsObject) contact.getFixtureA().getBody().getUserData();
        PhysicsObject objectB = (PhysicsObject) contact.getFixtureB().getBody().getUserData();

        objectA.contactResolver.leaveContact(objectB);
        objectB.contactResolver.leaveContact(objectA);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }
}
