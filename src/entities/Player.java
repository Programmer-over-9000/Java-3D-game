package entities;

import models.TexturedModel;

import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

	private static final float RUN_SPEED = 40;
	private static final float TURN_SPEED = 35;
	public static final float GRAVITY = -50;
	private static final float JUMP_POWER = 18;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;

	private boolean isInAir = true;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move(Terrain terrain) {
		checkInputs();
		calculateAngleAroundPlayer();
		// super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);


		float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);
		// System.out.println("super"+super.getPosition().y);
		// System.out.println("terrainHeight"+terrainHeight);
		if (super.getPosition().y < -150) {
			super.getPosition().y = terrainHeight+150;
		}
		System.out.println(super.getPosition());
		System.out.println(400*Math.sin(Math.toRadians(super.getRotY())));
		System.out.println(400*Math.cos(Math.toRadians(super.getRotY())));

		if (super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}

		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dy = (float) (upwardsSpeed * DisplayManager.getFrameTimeSeconds());
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, dy, dz);	

	}

	private void jump() {
		if (!isInAir) {
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}

	private void checkInputs() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		} else {
			this.currentSpeed = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		} else {
			this.currentTurnSpeed = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}

	private void calculateAngleAroundPlayer(){
		setRotY( this.getRotY() - Mouse.getDX() * 0.3f);
	}

}
