package com.deepsea;

import java.awt.Font;
import java.io.IOException;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.TrueTypeFont;

public class Game {

	//windows globals
	static int xwidth = 800, xheight = 600;
	static int framerate = 60;
	static boolean fullscreen = false;
	static int debug = 1;
	static float mouseSensitivity = 0.05f;
	static float xfov = 75.0f;
	
	//frame rate calculations
	double time, lastTime;
	static float dt, dT;
	
	//classes
	static Game game;
	static Camera camera;
	static Input input;
	static Scene world;
	
	//strings
	static String title = "deepsea engine";
	static String version = "0.0.4";
	
	//fonts
	TrueTypeFont font;
	
	//logging
	public static Logger LOG = Logger.getLogger("Game");
	
	//world variables
	static int random_seed;
	
	public static void main(String[] args) throws LWJGLException {
		/*
		 * Game entry point
		 */
		
		loadLogger();
		
		System.out.println("Generating random seed");
		Random ran = new Random();
		random_seed = ran.nextInt(65535) + 1;
		LOG.log(Level.INFO, "generating random seed: " + random_seed);
		
		System.out.println("Starting game");
		LOG.log(Level.INFO, "starting game");
		
		game = new Game();
		game.initOpenGL();
		game.initDisplay();
		game.loadFonts();
		
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			/*
			 * update loop
			 */
			game.render();
			game.update();
			Display.update();
			Display.sync(framerate);
		}
		/*
		 * Game exit point
		 */
		world.destroy();
		Display.destroy();
		System.exit(0);
	}
	
	public Game() throws LWJGLException {
		/*
		 * Set up basic game system
		 */
		world = new Scene(random_seed);
		camera = new Camera(0, 0, 0);
		input = new Input();
	}
	
	private static void loadLogger() {
		FileHandler fh;
		try {
			fh = new FileHandler("./log.txt");
			LOG.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			LOG.info("entry point");
		} catch (SecurityException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void loadFonts() {
		/*
		 * This method loads the fonts to be used for writing text on screen
		 */
		Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		font = new TrueTypeFont(awtFont, false);
		LOG.log(Level.INFO, "loaded font: " + awtFont.getFontName());
	}

	private void initDisplay() {
		/*
		 * initialize the display details for a 3D game
		 */
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		//this takes care of draw distance I think
		GLU.gluPerspective(xfov, xwidth / xheight, 0.001f, 10000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		
		//these below allow alpha blending
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		
		LOG.log(Level.INFO, "display initialized");
	}

	private void initOpenGL() throws LWJGLException {
		/*
		 * initialize OpenGL and windowing details
		 */
		if(fullscreen) {
			Display.setDisplayMode(new DisplayMode(Display.getDesktopDisplayMode().getWidth(),Display.getDesktopDisplayMode().getHeight()));
		} else {
			Display.setDisplayMode(new DisplayMode(xwidth, xheight));
		}
		Display.setTitle(title + ":" + version);
		Display.setVSyncEnabled(false);
		Display.setFullscreen(fullscreen);
		Display.create();
		
		LOG.log(Level.INFO, "Display: " + title + " " + version);
	}
	
	public void clearScreen() {
		/*
		 * Clear screen
		 */
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
	}
	
	public float getTime() {
		/*
		 * This method does a time calculation that basically counts the time since the 
		 * last loop, divides by 1000 and the reciprocal is the FPS
		 */
		time = Sys.getTime();
		dt = (float)(time - lastTime)/1000.0f;
		lastTime = time;
		Display.setTitle(title + " fps: "+ Math.round(1/dt));
		return dt;
	}

	private void update() {

		dT = getTime();
		input.mapKeys();
		input.mapMouse();
		//world.update();
		camera.update(dT);
	}

	private void render() {

		this.clearScreen();
		
		camera.translatePosition();
	}
	
}
