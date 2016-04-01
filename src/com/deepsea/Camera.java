package com.deepsea;

import java.util.logging.Level;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position, prevposition, rotation;
	public float dx, dy, dz, dt;
	private Vector3f originVec;
	
	boolean moveForward = false, moveBackward = false;
	boolean strafeLeft = false, strafeRight = false;
	boolean jump = false, flyUp = false, flyDown = false;
	boolean rollCW = false, rollCCW = false;
	boolean run = false, origin = false;
	static float speed = 0.01f;
	static final float gravity = 0.981f;

	public Camera(float x, float y, float z) {
		position = new Vector3f(x, y, z);
		prevposition = new Vector3f();
		rotation = new Vector3f();
		originVec = position;
		Mouse.setGrabbed(true);
		Game.LOG.log(Level.INFO, "player initialized: " + position);
	}
	
	public void update(float delta){
		updatePrevious();
		getinput();
		updateVector(delta);
	}
	
	public void updateVector(float delta){
		if(moveForward){
			position.x -= (float) (Math.sin(-rotation.y * Math.PI / 180) * speed);
			position.z -= (float) (Math.cos(-rotation.y * Math.PI / 180) * speed);
		}
		if(moveBackward){
			position.x += (float) (Math.sin(-rotation.y * Math.PI / 180) * speed);
			position.z += (float) (Math.cos(-rotation.y * Math.PI / 180) * speed);
		}
		if(strafeLeft){
			position.x += (float) (Math.sin((-rotation.y - 90) * Math.PI / 180) * speed);
			position.z += (float) (Math.cos((-rotation.y - 90) * Math.PI / 180) * speed);
		}
		if(strafeRight){
			position.x += (float) (Math.sin((-rotation.y + 90) * Math.PI / 180) * speed);
			position.z += (float) (Math.cos((-rotation.y + 90) * Math.PI / 180) * speed);
		}
		if(jump){
			position.y += (float) delta;
		}
		if(flyDown){
			position.y -= (float) (Math.sin((-rotation.z + 90) * Math.PI / 180) * speed);
		}
		if(flyUp){
			position.y += (float) (Math.sin((-rotation.z + 90) * Math.PI / 180) * speed);
		}
		if(rollCW){
			rotation.x += (float) (Math.sin((-rotation.z + 90) * Math.PI / 180) * speed * 10);
			rotation.y += (float) (Math.sin((-rotation.z + 90) * Math.PI / 180) * speed * 10);
			rotation.z += (float) (Math.sin((-rotation.z + 90) * Math.PI / 180) * speed * 10);
		}
		if(rollCCW){
			rotation.y -= (float) (Math.sin((-rotation.z + 90) * Math.PI / 180) * speed * 10);
		}
		if(run) {
			speed = 0.1f;
		} else {
			speed = 0.01f;
		}
		if(origin) {
			returntoZero();
		}

	}
	
	public void translatePosition() {
		//This is the code that changes 3D perspective to the camera's perspective.
		GL11.glRotatef(rotation.x, 1, 0, 0);
		GL11.glRotatef(rotation.y, 0, 1, 0);
		GL11.glRotatef(rotation.z, 0, 0, 1);
		//-vector.y-2.4f means that your y is your feet, and y+2.4 is your head.
		GL11.glTranslatef(-position.x, -position.y-2.4f, -position.z);
	}
	
	public void returntoZero() {
		// TODO: fix return home, currently does nothing
		/*
		 * Return camera to origin
		 */
		System.out.println("Returning home");
		this.position.x = originVec.x;
		this.position.y = originVec.y;
		this.position.z = originVec.z;
		translatePosition();
		updatePrevious();
	}
	
	public void updatePrevious() {
		//Update last position (for collisions (later))
		prevposition.x = position.x;
		prevposition.y = position.y;
		prevposition.z = position.z;
	}
	
	public void getinput(){
		//Keyboard Input for Movement
		moveForward = Input.keys[Keyboard.KEY_W];
		moveBackward = Input.keys[Keyboard.KEY_S];
		strafeLeft = Input.keys[Keyboard.KEY_A];
		strafeRight = Input.keys[Keyboard.KEY_D];
		jump = Input.keys[Keyboard.KEY_SPACE];
		flyUp = Input.keys[Keyboard.KEY_F];
		flyDown = Input.keys[Keyboard.KEY_C];
		rollCW = Input.keys[Keyboard.KEY_E];
		rollCCW = Input.keys[Keyboard.KEY_Q];
		run = Input.keys[Keyboard.KEY_LSHIFT];
		origin = Input.keys[Keyboard.KEY_F1];
		
		//Mouse Input for looking around...
		if(Mouse.isGrabbed()){
			float mouseDX = Input.mS.dx * 0.8f * 0.16f;
			float mouseDY = Input.mS.dy * 0.8f * 0.16f;
			
			
			if (rotation.y + mouseDX >= 360) {
				rotation.y = rotation.y + mouseDX - 360;
			} else if (rotation.y + mouseDX < 0) {
				rotation.y = 360 - rotation.y + mouseDX;
			} else {
				rotation.y += mouseDX;
			}
			if (rotation.x - mouseDY >= -89 && rotation.x - mouseDY <= 89) {
				rotation.x += -mouseDY;
			} else if (rotation.x - mouseDY < -89) {
				rotation.x = -89;
			} else if (rotation.x - mouseDY > 89) {
				rotation.x = 89;
			}
		}
	}
}
