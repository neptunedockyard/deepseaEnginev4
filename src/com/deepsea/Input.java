package com.deepsea;

import java.util.logging.Level;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {

	//input globals
	public static boolean[] keys = new boolean[256];
	public static MouseState mS;
	
	//mouse state class that contains state of input
	public static class MouseState
	{
		public int x;
		public int y;
		public int dx;
		public int dy;
		public boolean left;
		public boolean middle;
		public boolean right;
	};

	public Input() {
		mS = new MouseState();
		Game.LOG.log(Level.INFO, "input initialized");
	}
	
	public void mapKeys(){
		//Update keys
		for(int i=0;i<keys.length;i++){
			keys[i] = Keyboard.isKeyDown(i);
		}
		this.writeKeys();
	}
	
	public void mapMouse() {
		//update mouse
		mS.x = Mouse.getX();
		mS.y = Mouse.getY();
		mS.dx = Mouse.getDX();
		mS.dy = Mouse.getDY();
		mS.left = Mouse.isButtonDown(0);
		mS.middle = Mouse.isButtonDown(2);
		mS.right = Mouse.isButtonDown(1);

		if(Game.debug == 1) {
			if(mS.left) {
				System.out.println("Left");
			}
			if(mS.middle) {
				System.out.println("Middle");
			}
			if(mS.right) {
				System.out.println("Right");
			}
		}
	}
	
	public void writeKeys() {
		if(Game.debug == 2) {
			System.out.println("a" + toChar(keys[Keyboard.KEY_A]) +
				"b" + toChar(keys[Keyboard.KEY_B]) +
				"c" + toChar(keys[Keyboard.KEY_C]) +
				"d" + toChar(keys[Keyboard.KEY_D]) +
				"e" + toChar(keys[Keyboard.KEY_E]) +
				"f" + toChar(keys[Keyboard.KEY_F]) +
				"g" + toChar(keys[Keyboard.KEY_G]) +
				"h" + toChar(keys[Keyboard.KEY_H]) +
				"i" + toChar(keys[Keyboard.KEY_I]) +
				"j" + toChar(keys[Keyboard.KEY_J]) +
				"k" + toChar(keys[Keyboard.KEY_K]) +
				"l" + toChar(keys[Keyboard.KEY_L]) +
				"m" + toChar(keys[Keyboard.KEY_M]) +
				"n" + toChar(keys[Keyboard.KEY_N]) +
				"o" + toChar(keys[Keyboard.KEY_O]) +
				"p" + toChar(keys[Keyboard.KEY_P]) +
				"q" + toChar(keys[Keyboard.KEY_Q]) +
				"r" + toChar(keys[Keyboard.KEY_R]) +
				"s" + toChar(keys[Keyboard.KEY_S]) +
				"t" + toChar(keys[Keyboard.KEY_T]));
				
			System.out.println("u" + toChar(keys[Keyboard.KEY_U]) +
				"v" + toChar(keys[Keyboard.KEY_V]) +
				"w" + toChar(keys[Keyboard.KEY_W]) +
				"x" + toChar(keys[Keyboard.KEY_X]) +
				"y" + toChar(keys[Keyboard.KEY_Y]) +
				"z" + toChar(keys[Keyboard.KEY_Z]) +
				"0" + toChar(keys[Keyboard.KEY_0]) +
				"1" + toChar(keys[Keyboard.KEY_1]) +
				"2" + toChar(keys[Keyboard.KEY_2]) +
				"3" + toChar(keys[Keyboard.KEY_3]) +
				"4" + toChar(keys[Keyboard.KEY_4]) +
				"5" + toChar(keys[Keyboard.KEY_5]) +
				"6" + toChar(keys[Keyboard.KEY_6]) +
				"7" + toChar(keys[Keyboard.KEY_7]) +
				"8" + toChar(keys[Keyboard.KEY_8]) +
				"9" + toChar(keys[Keyboard.KEY_9]) +
				"L" + toChar(keys[Keyboard.KEY_LEFT]) +
				"R" + toChar(keys[Keyboard.KEY_RIGHT]) +
				"U" + toChar(keys[Keyboard.KEY_UP]) +
				"D" + toChar(keys[Keyboard.KEY_DOWN]));
		}
	}
	
	public static char toChar(final Boolean b) {
		return b == null ? '?' : b ? '1' : '0';
	}
}
