/*
 * 
 */
package de.raistlin77.gl.cube;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.math.geometry.Rotation;
import org.apache.commons.math.geometry.Vector3D;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import de.intex.tools.lib.S;

// TODO: Auto-generated Javadoc
/**
 * Basic game.
 *
 * @author Name <email>
 * @version 1.0
 */
public class Game {
	
	// XXX display count
	
	/**
	 * The Class rotate.
	 */
	private static class rotate {
		/** < Turning direction. */
		public static int turn;
		/** < Step to leave. */
		public static int num;
		/** <Field with numbers of tiles. */
		public static int[] tile = new int[9];
		/** < Number of tiles. */
		public static int anz;
		
		/** < turn left/right front/rear?. */
		public static int pos;
		
		/** The speed. */
		public static int speed;
	}

	/** Game title. */
	public static final String GAME_TITLE = "Rubik's Cube";

	/** Desired frame time. */
	private static final int FRAMERATE = 60;

	/** Exit the game. */
	private static boolean finished;

	/** The Ang. */
	private static float[] Ang = new float[] { 0, 0, 0, 4.5f };
	
	/** The KGLCUBE. */
	private static Vector3D[] KGLCUBE = new Vector3D[26];
	
	/** The KY cube. */
	private static Vector3D[] KYCube = new Vector3D[26];
	
	/** The KZ cube. */
	private static Vector3D[] KZCube = new Vector3D[26];
	/** < Angles for the mainturn. zero AFTER every turn. */
	private static ANGLE[][] ANGLES = new ANGLE[26][3];
	/** < Angles for the correcting Turn. */
	private static ANGLE[][] ANGLES2 = new ANGLE[26][3];

	/** The TILES. */
	private static short[][] TILES = new short[][] { 
		{ -1, +1, +1 }, {  0, +1, +1 }, { +1, +1, +1 },
		{ -1,  0, +1 }, {  0,  0, +1 }, { +1,  0, +1 },
		{ -1, -1, +1 }, {  0, -1, +1 }, { +1, -1, +1 },
		{ -1, +1,  0 }, {  0, +1,  0 }, { +1, +1,  0 },
		{ -1,  0,  0 }, /*************/ { +1,  0,  0 },
		{ -1, -1,  0 }, {  0, -1,  0 }, { +1, -1,  0 },
		{ -1, +1, -1 }, {  0, +1, -1 }, { +1, +1, -1 },
		{ -1,  0, -1 }, {  0,  0, -1 }, { +1,  0, -1 },
		{ -1, -1, -1 }, {  0, -1, -1 }, { +1, -1, -1 }
	};
	
	/** The MANGLES. */
	private static short[][] MANGLES = new short[][] {
		{   0,   0,   0 }, //oben ///////////////////////////////////////////////////
		{   0, -90,   0 }, {   0, 180,   0 }, {   0,  90,   0 },
		{   0,   0, 180 }, //unten ///////////////////////////////////////////////////
		{ 180, -90,   0 }, { 180,   0,   0 }, { 180,  90,   0 },
		{  90,   0,   0 }, //vorne ///////////////////////////////////////////////////
		{  90, -90,   0 }, {  90, 180,   0 }, {  90,  90,   0 },
		{ -90,   0,   0 }, //unten ///////////////////////////////////////////////////
		{ -90, -90,   0 }, { -90, 180,   0 }, { -90,  90,   0 },
		{   0,   0,  90 }, //links ///////////////////////////////////////////////////
		{  90,   0,  90 }, { 180,   0,  90 }, { -90,   0,  90 },
		{   0,   0, -90 }, //rechts ///////////////////////////////////////////////////
		{  90,   0, -90 }, { 180,   0, -90 }, { -90,   0, -90 }
	};

	/** The t. */
	private static Timer t = new Timer(); 

	/** The speed. */
	private static int speed = 10;
	
	/** The SOLVESTOP. */
	private static boolean SOLVESTOP;
	
	/** The now turn. */
	private static boolean nowTurn = false;
	
	/** The count. */
	private static int count = 0;
	
	/** The c. */
	private static Cube c = new Cube();

	/** The diceing. */
	private static boolean diceing = false;

	/** The solve list. */
	private static List<Byte> solveList;
	
	/** The solveing. */
	private static boolean solveing;
	
	/** The solve tainted. */
	private static boolean solveTainted = false;

	/** The title addon. */
	private static String titleAddon = null;
	
	/** The title addon ary. */
	private static String[] titleAddonAry = {
		"Flächen erste Ebene",  // 20
		"Kanten erste Ebene",   // 21
		"Ecken erste Ebene",    // 22
		"Flächen zweite Ebene", // 23
		"Kanten zweite Ebene",  // 24
		"Umdrehen",             // 25 
		"todo sortiere Kanten dritte Ebene",  // 26 
		"todo drehenKanten dritte Ebene",  // 27 
		"todo Ecken dritte Ebene",   // 28
		"todo ",
		"fertig"
	};
	
	/**
	 * < Main vectors.
	 *
	 * @param args the arguments
	 */

	/**
	 * Application init
	 * 
	 * @param args
	 *            Commandline args
	 */
	public static void main(String[] args) {
		boolean fullscreen = (args.length == 1 && args[0].equals( "-fullscreen" ));

		try {
			init( fullscreen );
			run();
		} catch ( Exception e ) {
			e.printStackTrace( System.err );
			Sys.alert( GAME_TITLE, "An error occured and the game will exit." );
		} finally {
			cleanup();
		}
		System.exit( 0 );
	}

	/**
	 * Initialise the game.
	 *
	 * @param fullscreen the fullscreen
	 * @throws Exception if init fails
	 */
	private static void init(boolean fullscreen) throws Exception {
		// Create a fullscreen window with 1:1 orthographic 2D projection
		// (default)

		// Enable vsync if we can (due to how OpenGL works, it cannot be
		// guarenteed to always work)
		Display.setVSyncEnabled( true );

		if( fullscreen )
			Display.setFullscreen( fullscreen );
		else 
			Display.setDisplayMode( new DisplayMode( 640, 640 ) );
			
		Display.setTitle( GAME_TITLE );
		Display.create();

		// Put the window into orthographic projection mode with 1:1 pixel
		// ratio.
		// We haven't used GLU here to do this to avoid an unnecessary
		// dependency.
		GL11.glEnable( GL11.GL_DEPTH_TEST );
		GL11.glClearColor( 1f, 1f, .8f, 1f );

		for ( int i = 0; i < 26; i++ ) {
			KGLCUBE[i] = new Vector3D( TILES[i][0], TILES[i][1], TILES[i][2] );
			KYCube[i] = new Vector3D( 0, 1, 0 );
			KZCube[i] = new Vector3D( 0, 0, 1 );
			for ( int j = 0; j < 3; j++ ) {
				ANGLES[i][j] = new ANGLE( 0 );
				ANGLES2[i][j] = new ANGLE( 0 );
			}
		}

	}

	/**
	 * Runs the game (the "main loop").
	 */
	private static void run() {

		c.setShow( false );
		
		while ( !finished ) {
			// Always call Window.update(), all the time - it does some behind
			// the
			// scenes work, and also displays the rendered output
			Display.update();

			// Check for close requests
			if ( Display.isCloseRequested() ) {
				finished = true;
			}

			// The window is in the foreground, so we should play the game
			else if ( Display.isActive() ) {
				logic();
				render();
				Display.sync( FRAMERATE );
			}

			// The window is not in the foreground, so we can allow other stuff
			// to run and
			// infrequently update
			else {
				try {
					Thread.sleep( 100 );
				} catch ( InterruptedException e ) {
				}
				logic();

				// Only bother rendering if the window is visible or dirty
				if ( Display.isVisible() || Display.isDirty() ) {
					render();
				}
			}
		}
	}

	/**
	 * Do any game-specific cleanup.
	 */
	private static void cleanup() {
		// Close the window
		Display.destroy();
	}

	/**
	 * Do all calculations, handle input, etc.
	 */
	private static void logic() {
		// Example input handler: we'll check for the ESC key and finish the
		// game instantly when it's pressed
		if ( Keyboard.isKeyDown( Keyboard.KEY_ESCAPE ) ) {
			finished = true;
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_LEFT ) ){
			Ang[1] -= 1.0;
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_RIGHT ) ){
			Ang[1] += 1.0;
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && Keyboard.isKeyDown( Keyboard.KEY_UP ) ){
			Ang[0] -= 1.0;
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && Keyboard.isKeyDown( Keyboard.KEY_DOWN ) ){
			Ang[0] += 1.0;
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_UP ) ){
			Ang[2] += 1.0;
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_DOWN ) ){
			Ang[2] -= 1.0;
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_ADD ) ){
			Ang[3] += 0.1;
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_MULTIPLY ) ){
			Ang = new float[] { 0, 0, 0, 4.5f };
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_MINUS ) || Keyboard.isKeyDown( Keyboard.KEY_DIVIDE ) ){
			Ang[3] -= 0.1;
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_Q ) ){
			setSpeed( false ); // TODO better switching 
		} else if ( Keyboard.isKeyDown( Keyboard.KEY_W ) ){
			setSpeed( true ); // XXX Timer ???
		} else if ( !solveing && Keyboard.isKeyDown( Keyboard.KEY_S ) ){
			SOLVESTOP = false;
			solve(); // if solveing -> stop solving -> if no other turn take old list
		} else if ( solveing && Keyboard.isKeyDown( Keyboard.KEY_X ) ){
			solveTainted = false;
			SOLVESTOP = true;
		} else if ( !solveing && Keyboard.isKeyDown( Keyboard.KEY_D ) ){
			dice();
		} else if ( !solveing && Keyboard.isKeyDown( Keyboard.KEY_C ) ){
			count  = 0;
		} else if ( !solveing && !nowTurn && Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD1 )||Keyboard.isKeyDown( Keyboard.KEY_1 )) ){
			turn( (byte)4 );
		} else if ( !solveing && !nowTurn && Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD2 )||Keyboard.isKeyDown( Keyboard.KEY_2 )) ){
			turn( (byte)5 );
		} else if ( !solveing && !nowTurn && Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD3 )||Keyboard.isKeyDown( Keyboard.KEY_3 )) ){
			turn( (byte)6 );
		} else if ( !solveing && !nowTurn && Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD4 )||Keyboard.isKeyDown( Keyboard.KEY_4 )) ){
			turn( (byte)10 );
		} else if ( !solveing && !nowTurn && Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD5 )||Keyboard.isKeyDown( Keyboard.KEY_5 )) ){
			turn( (byte)11 );
		} else if ( !solveing && !nowTurn && Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD6 )||Keyboard.isKeyDown( Keyboard.KEY_6 )) ){
			turn( (byte)12 );
		} else if ( !solveing && !nowTurn && Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD7 )||Keyboard.isKeyDown( Keyboard.KEY_7 )) ){
			turn( (byte)16 );
		} else if ( !solveing && !nowTurn && Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD8 )||Keyboard.isKeyDown( Keyboard.KEY_8 )) ){
			turn( (byte)17 );
		} else if ( !solveing && !nowTurn && Keyboard.isKeyDown( Keyboard.KEY_LCONTROL ) && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD9 )||Keyboard.isKeyDown( Keyboard.KEY_9 )) ){
			turn( (byte)18 );
		} else if ( !solveing && !nowTurn && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD1 )||Keyboard.isKeyDown( Keyboard.KEY_1 )) ){
			turn( (byte)1 );
		} else if ( !solveing && !nowTurn && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD2 )||Keyboard.isKeyDown( Keyboard.KEY_2 )) ){
			turn( (byte)2 );
		} else if ( !solveing && !nowTurn && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD3 )||Keyboard.isKeyDown( Keyboard.KEY_3 )) ){
			turn( (byte)3 );
		} else if ( !solveing && !nowTurn && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD4 )||Keyboard.isKeyDown( Keyboard.KEY_4 )) ){
			turn( (byte)7 );
		} else if ( !solveing && !nowTurn && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD5 )||Keyboard.isKeyDown( Keyboard.KEY_5 )) ){
			turn( (byte)8 );
		} else if ( !solveing && !nowTurn && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD6 )||Keyboard.isKeyDown( Keyboard.KEY_6 )) ){
			turn( (byte)9 );
		} else if ( !solveing && !nowTurn && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD7 )||Keyboard.isKeyDown( Keyboard.KEY_7 )) ){
			turn( (byte)13 );
		} else if ( !solveing && !nowTurn && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD8 )||Keyboard.isKeyDown( Keyboard.KEY_8 )) ){
			turn( (byte)14 );
		} else if ( !solveing && !nowTurn && (Keyboard.isKeyDown( Keyboard.KEY_NUMPAD9 )||Keyboard.isKeyDown( Keyboard.KEY_9 )) ){
			turn( (byte)15 );
		}

	}
		
	/**
	 * Render the current frame.
	 */
	private static void render() {
		Display.setTitle( GAME_TITLE  + ( S.isNotNull( titleAddon ) ? " - " + titleAddon : "" ) + " - " + count);

		GL11.glLoadIdentity(); // ?!?
		GL11.glOrtho( -20, 20, -20, 20, -20, 20 ); // setzte die koordinaten
		GL11.glEnable( GL11.GL_BLEND );// ?!?
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );// ?!?
		GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
		GLU.gluLookAt( 1.5f,1.5f,1.5f, 0f,0f,0f ,0f,1f,0f );
		GL11.glPushMatrix(); // lege die jetztige matrix auf der stapel
		GL11.glTranslatef( 0, 0, 0 ); // setze den mittel punkt der
										// transformationen fest
		GL11.glScaled( Ang[3], Ang[3], Ang[3] ); // zoom für x, y, z
		GL11.glRotatef( Ang[0], 1, 0, 0 ); // rotationen
		GL11.glRotatef( Ang[1], 0, 1, 0 );
		GL11.glRotatef( Ang[2], 0, 0, 1 );
		GL11.glBegin( GL11.GL_LINES );
		GL11.glColor4f( .5f, .5f, -5f, 1f );
		GL11.glVertex3f( -5, 0, 0 );
		GL11.glVertex3f( +5, 0, 0 );
		GL11.glColor4f( .5f, .5f, .5f, .1f );
		GL11.glVertex3f( 0, -5, 0 );
		GL11.glVertex3f( 0, +5, 0 );
		GL11.glVertex3f( 0, 0, -5 );
		GL11.glVertex3f( 0, 0, +5 );
		GL11.glEnd();
	
		GL11.glEnable(GL11.GL_LIGHTING | GL11.GL_NORMALIZE);
		drawCube();
//		GL11.glDisable(GL11.GL_LIGHTING);
		
//		java.awt.Font awtFont = new Font("Times New Roman", 1, 16);
//        org.newdawn.slick.Font font = new TrueTypeFont( awtFont , false);
//        font.drawString(150F, 300F, "HELLO LWJGL WORLD", org.newdawn.slick.Color.yellow);
		
		
		GL11.glPopMatrix(); // lege die alte Matix auf
		
//		
//		
//		FloatBuffer lightAmbient  = BufferUtils.createFloatBuffer(4);
//		FloatBuffer lightDiffuse  = BufferUtils.createFloatBuffer(4);
//		FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
//		
//		lightAmbient.put( 0, 0.2f );
//		lightAmbient.put( 1, 0.2f );
//		lightAmbient.put( 2, 0.2f );
//		lightAmbient.put( 3, 1.0f );
//
//		lightDiffuse.put( 0, 1.0f );
//		lightDiffuse.put( 1, 1.0f );
//		lightDiffuse.put( 2, 1.0f );
//		lightDiffuse.put( 3, 1.0f );
//		
//		lightPosition.put( 0, 0.0f );
//		lightPosition.put( 1, 0.0f );
//		lightPosition.put( 2, 0.0f );
//		lightPosition.put( 3, 0.0f );			
//		
//		GL11.glMaterial( GL11.GL_FRONT, GL11.GL_SPECULAR, lightDiffuse );
//		GL11.glMaterialf( GL11.GL_FRONT, GL11.GL_SHININESS, 50.0f );
//		
//		GL11.glLight( GL11.GL_LIGHT0, GL11.GL_AMBIENT, lightAmbient );
//		GL11.glLight( GL11.GL_LIGHT0, GL11.GL_DIFFUSE, lightDiffuse );
//		GL11.glLight( GL11.GL_LIGHT0, GL11.GL_SPECULAR, lightDiffuse );
//		GL11.glLight( GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition );
//		GL11.glEnable( GL11.GL_LIGHT0 );

	}

	/**
	 * Draw cube.
	 */
	private static void drawCube() {
		GL11.glPushMatrix();
		GL11.glShadeModel( GL11.GL_FLAT ); // ?!?
		for ( int i = 0; i < 26; i++ ) {
			GL11.glPushMatrix();
			GL11.glRotatef( ANGLES[i][0].toFloat(), 1, 0, 0 ); // rotationen
			GL11.glRotatef( ANGLES[i][1].toFloat(), 0, 1, 0 );
			GL11.glRotatef( ANGLES[i][2].toFloat(), 0, 0, 1 );
			GL11.glTranslated( KGLCUBE[i].getX(), KGLCUBE[i].getY(), KGLCUBE[i].getZ() );
			GL11.glRotatef( ANGLES2[i][0].toFloat(), 1, 0, 0 ); // rotationen
			GL11.glRotatef( ANGLES2[i][1].toFloat(), 0, 1, 0 );
			GL11.glRotatef( ANGLES2[i][2].toFloat(), 0, 0, 1 );
			GL11.glColor4f( 1, 1, 1, .95f );
			drawCube( i + 1 );
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}

	/**
	 * Draw cube.
	 *
	 * @param i the i
	 */
	private static void drawCube(int i) {
		float col;
		float alpha = 1.0f;
		
		float gray = 0.05f;
		float black = 0.15f;
		
		// vorne
		col = (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9) ? black : gray;
		GL11.glColor4f( col, col, col, alpha );
		GL11.glBegin( GL11.GL_POLYGON );
		GL11.glVertex3f( +0.4f, -0.49f, +0.49f );
		GL11.glVertex3f( +0.49f, -0.4f, +0.49f );
		GL11.glVertex3f( +0.49f, +0.4f, +0.49f );
		GL11.glVertex3f( +0.4f, +0.49f, +0.49f );
		GL11.glVertex3f( -0.4f, +0.49f, +0.49f );
		GL11.glVertex3f( -0.49f, +0.4f, +0.49f );
		GL11.glVertex3f( -0.49f, -0.4f, +0.49f );
		GL11.glVertex3f( -0.4f, -0.49f, +0.49f );
		GL11.glEnd();
		// hinten
		col = (i == 18 || i == 19 || i == 20 || i == 21 || i == 22 || i == 23 || i == 24 || i == 25 || i == 26) ? black : gray;
		GL11.glColor4f( col, col, col, alpha );
		GL11.glBegin( GL11.GL_POLYGON );
		GL11.glVertex3f( +0.4f, -0.49f, -0.49f );
		GL11.glVertex3f( +0.49f, -0.4f, -0.49f );
		GL11.glVertex3f( +0.49f, +0.4f, -0.49f );
		GL11.glVertex3f( +0.4f, +0.49f, -0.49f );
		GL11.glVertex3f( -0.4f, +0.49f, -0.49f );
		GL11.glVertex3f( -0.49f, +0.4f, -0.49f );
		GL11.glVertex3f( -0.49f, -0.4f, -0.49f );
		GL11.glVertex3f( -0.4f, -0.49f, -0.49f );
		GL11.glEnd();
		// rechts
		col = (i == 1 || i == 2 || i == 3 || i == 10 || i == 11 || i == 12 || i == 18 || i == 19 || i == 20) ? black : gray;
		GL11.glColor4f( col, col, col, alpha );
		GL11.glBegin( GL11.GL_POLYGON );
		GL11.glVertex3f( +0.4f, +0.49f, -0.49f );
		GL11.glVertex3f( +0.49f, +0.49f, -0.4f );
		GL11.glVertex3f( +0.49f, +0.49f, +0.4f );
		GL11.glVertex3f( +0.4f, +0.49f, +0.49f );
		GL11.glVertex3f( -0.4f, +0.49f, +0.49f );
		GL11.glVertex3f( -0.49f, +0.49f, +0.4f );
		GL11.glVertex3f( -0.49f, +0.49f, -0.4f );
		GL11.glVertex3f( -0.4f, +0.49f, -0.49f );
		GL11.glEnd();
		// links
		col = (i == 7 || i == 8 || i == 9 || i == 15 || i == 16 || i == 17 || i == 24 || i == 25 || i == 26) ? black : gray;
		GL11.glColor4f( col, col, col, alpha );
		GL11.glBegin( GL11.GL_POLYGON );
		GL11.glVertex3f( +0.4f, -0.49f, -0.49f );
		GL11.glVertex3f( +0.49f, -0.49f, -0.4f );
		GL11.glVertex3f( +0.49f, -0.49f, +0.4f );
		GL11.glVertex3f( +0.4f, -0.49f, +0.49f );
		GL11.glVertex3f( -0.4f, -0.49f, +0.49f );
		GL11.glVertex3f( -0.49f, -0.49f, +0.4f );
		GL11.glVertex3f( -0.49f, -0.49f, -0.4f );
		GL11.glVertex3f( -0.4f, -0.49f, -0.49f );
		GL11.glEnd();
		// oben
		col = (i == 3 || i == 6 || i == 9 || i == 12 || i == 14 || i == 17 || i == 20 || i == 23 || i == 26) ? black : gray;
		GL11.glColor4f( col, col, col, alpha );
		GL11.glBegin( GL11.GL_POLYGON );
		GL11.glVertex3f( +0.49f, +0.4f, -0.49f );
		GL11.glVertex3f( +0.49f, +0.49f, -0.4f );
		GL11.glVertex3f( +0.49f, +0.49f, +0.4f );
		GL11.glVertex3f( +0.49f, +0.4f, +0.49f );
		GL11.glVertex3f( +0.49f, -0.4f, +0.49f );
		GL11.glVertex3f( +0.49f, -0.49f, +0.4f );
		GL11.glVertex3f( +0.49f, -0.49f, -0.4f );
		GL11.glVertex3f( +0.49f, -0.4f, -0.49f );
		GL11.glEnd();
		// unten
		col = (i == 1 || i == 4 || i == 7 || i == 10 || i == 13 || i == 15 || i == 18 || i == 21 || i == 24) ? black : gray;
		GL11.glColor4f( col, col, col, alpha );
		GL11.glBegin( GL11.GL_POLYGON );
		GL11.glVertex3f( -0.49f, +0.4f, -0.49f );
		GL11.glVertex3f( -0.49f, +0.49f, -0.4f );
		GL11.glVertex3f( -0.49f, +0.49f, +0.4f );
		GL11.glVertex3f( -0.49f, +0.4f, +0.49f );
		GL11.glVertex3f( -0.49f, -0.4f, +0.49f );
		GL11.glVertex3f( -0.49f, -0.49f, +0.4f );
		GL11.glVertex3f( -0.49f, -0.49f, -0.4f );
		GL11.glVertex3f( -0.49f, -0.4f, -0.49f );
		GL11.glEnd();

		// eck'chen
		col = black;
		GL11.glColor4f( col, col, col, alpha );
		GL11.glBegin( GL11.GL_TRIANGLES );
		GL11.glVertex3f( +.49f, +.4f, +.49f );
		GL11.glVertex3f( +.4f, +.49f, +.49f );
		GL11.glVertex3f( +.49f, +.49f, +.4f );
		GL11.glVertex3f( -.49f, +.4f, +.49f );
		GL11.glVertex3f( -.4f, +.49f, +.49f );
		GL11.glVertex3f( -.49f, +.49f, +.4f );
		GL11.glVertex3f( +.49f, -.4f, +.49f );
		GL11.glVertex3f( +.4f, -.49f, +.49f );
		GL11.glVertex3f( +.49f, -.49f, +.4f );
		GL11.glVertex3f( -.49f, -.4f, +.49f );
		GL11.glVertex3f( -.4f, -.49f, +.49f );
		GL11.glVertex3f( -.49f, -.49f, +.4f );
		GL11.glVertex3f( +.49f, +.4f, -.49f );
		GL11.glVertex3f( +.4f, +.49f, -.49f );
		GL11.glVertex3f( +.49f, +.49f, -.4f );
		GL11.glVertex3f( -.49f, +.4f, -.49f );
		GL11.glVertex3f( -.4f, +.49f, -.49f );
		GL11.glVertex3f( -.49f, +.49f, -.4f );
		GL11.glVertex3f( +.49f, -.4f, -.49f );
		GL11.glVertex3f( +.4f, -.49f, -.49f );
		GL11.glVertex3f( +.49f, -.49f, -.4f );
		GL11.glVertex3f( -.49f, -.4f, -.49f );
		GL11.glVertex3f( -.4f, -.49f, -.49f );
		GL11.glVertex3f( -.49f, -.49f, -.4f );
		GL11.glEnd();
		// vorne
		if ( i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 ) {
			GL11.glColor4f( 1.0f, 0.0f, 0.0f, .8f );
			GL11.glBegin( GL11.GL_POLYGON );
			GL11.glVertex3f( +0.3f, +0.4f, +0.51f );
			GL11.glVertex3f( +0.4f, +0.3f, +0.51f );
			GL11.glVertex3f( +0.4f, -0.3f, +0.51f );
			GL11.glVertex3f( +0.3f, -0.4f, +0.51f );
			GL11.glVertex3f( -0.3f, -0.4f, +0.51f );
			GL11.glVertex3f( -0.4f, -0.3f, +0.51f );
			GL11.glVertex3f( -0.4f, +0.3f, +0.51f );
			GL11.glVertex3f( -0.3f, +0.4f, +0.51f );
			GL11.glEnd();
		}
		// hinten
		if ( i == 18 || i == 19 || i == 20 || i == 21 || i == 22 || i == 23 || i == 24 || i == 25 || i == 26 ) {
			GL11.glColor4f( 1.0f, 1.0f, 0.0f, .8f );
			GL11.glBegin( GL11.GL_POLYGON );
			GL11.glVertex3f( +0.3f, +0.4f, -0.51f );
			GL11.glVertex3f( +0.4f, +0.3f, -0.51f );
			GL11.glVertex3f( +0.4f, -0.3f, -0.51f );
			GL11.glVertex3f( +0.3f, -0.4f, -0.51f );
			GL11.glVertex3f( -0.3f, -0.4f, -0.51f );
			GL11.glVertex3f( -0.4f, -0.3f, -0.51f );
			GL11.glVertex3f( -0.4f, +0.3f, -0.51f );
			GL11.glVertex3f( -0.3f, +0.4f, -0.51f );
			GL11.glEnd();
		}
		// rechts
		if ( i == 1 || i == 2 || i == 3 || i == 10 || i == 11 || i == 12 || i == 18 || i == 19 || i == 20 ) {
			GL11.glColor4f( 0.0f, 0.0f, 1.0f, .8f );
			GL11.glBegin( GL11.GL_POLYGON );
			GL11.glVertex3f( +0.3f, +0.51f, +0.4f );
			GL11.glVertex3f( +0.4f, +0.51f, +0.3f );
			GL11.glVertex3f( +0.4f, +0.51f, -0.3f );
			GL11.glVertex3f( +0.3f, +0.51f, -0.4f );
			GL11.glVertex3f( -0.3f, +0.51f, -0.4f );
			GL11.glVertex3f( -0.4f, +0.51f, -0.3f );
			GL11.glVertex3f( -0.4f, +0.51f, +0.3f );
			GL11.glVertex3f( -0.3f, +0.51f, +0.4f );
			GL11.glEnd();
		}
		// links
		if ( i == 7 || i == 8 || i == 9 || i == 15 || i == 16 || i == 17 || i == 24 || i == 25 || i == 26 ) {
			GL11.glColor4f( 0.0f, 1.0f, 1.0f, .8f );
			GL11.glBegin( GL11.GL_POLYGON );
			GL11.glVertex3f( +0.3f, -0.51f, +0.4f );
			GL11.glVertex3f( +0.4f, -0.51f, +0.3f );
			GL11.glVertex3f( +0.4f, -0.51f, -0.3f );
			GL11.glVertex3f( +0.3f, -0.51f, -0.4f );
			GL11.glVertex3f( -0.3f, -0.51f, -0.4f );
			GL11.glVertex3f( -0.4f, -0.51f, -0.3f );
			GL11.glVertex3f( -0.4f, -0.51f, +0.3f );
			GL11.glVertex3f( -0.3f, -0.51f, +0.4f );
			GL11.glEnd();
		}
		// oben
		if ( i == 3 || i == 6 || i == 9 || i == 12 || i == 14 || i == 17 || i == 20 || i == 23 || i == 26 ) {
			GL11.glColor4f( 0.0f, 1.0f, 0.0f, .8f );
			GL11.glBegin( GL11.GL_POLYGON );
			GL11.glVertex3f( +0.51f, +0.3f, +0.4f );
			GL11.glVertex3f( +0.51f, +0.4f, +0.3f );
			GL11.glVertex3f( +0.51f, +0.4f, -0.3f );
			GL11.glVertex3f( +0.51f, +0.3f, -0.4f );
			GL11.glVertex3f( +0.51f, -0.3f, -0.4f );
			GL11.glVertex3f( +0.51f, -0.4f, -0.3f );
			GL11.glVertex3f( +0.51f, -0.4f, +0.3f );
			GL11.glVertex3f( +0.51f, -0.3f, +0.4f );
			GL11.glEnd();
		}
		// unten
		if ( i == 1 || i == 4 || i == 7 || i == 10 || i == 13 || i == 15 || i == 18 || i == 21 || i == 24 ) {
			GL11.glColor4f( 1.0f, 0.0f, 1.0f, .8f );
			GL11.glBegin( GL11.GL_POLYGON );
			GL11.glVertex3f( -0.51f, +0.3f, +0.4f );
			GL11.glVertex3f( -0.51f, +0.4f, +0.3f );
			GL11.glVertex3f( -0.51f, +0.4f, -0.3f );
			GL11.glVertex3f( -0.51f, +0.3f, -0.4f );
			GL11.glVertex3f( -0.51f, -0.3f, -0.4f );
			GL11.glVertex3f( -0.51f, -0.4f, -0.3f );
			GL11.glVertex3f( -0.51f, -0.4f, +0.3f );
			GL11.glVertex3f( -0.51f, -0.3f, +0.4f );
			GL11.glEnd();
		}
	}
	
	/**
	 * Sets the speed.
	 *
	 * @param a the new speed
	 */
	private static void setSpeed( boolean a ) {
		if ( a ) speed=10;
		else     speed=01;
	}
	
	/**
	 * Turn.
	 *
	 * @param r the r
	 * @param l the l
	 * @param xyz the xyz
	 */
	private static void turn(int r, int l, int xyz) {
		// emit turned( false );
		solveTainted = true;
		rotate.speed = speed;
		if( diceing  )
			rotate.num = 0;
		else 
			rotate.num = (short) (90 / rotate.speed);
		rotate.pos = l;
		for ( int i = 0, j = 0; i < 26; i++ ) {
			if ( xyz == 0 && switchCoord( r, KGLCUBE[i].getX() ) ) {
				rotate.tile[j++] = i;
			} else if ( xyz == 1 && switchCoord( r, KGLCUBE[i].getY() ) ) {
				rotate.tile[j++] = i;
			} else if ( xyz == 2 && switchCoord( r, KGLCUBE[i].getZ() ) ) {
				rotate.tile[j++] = i;
			}
		}
		turning();
	}

	/**
	 * Switch coord.
	 *
	 * @param a the a
	 * @param val the val
	 * @return true, if successful
	 */
	private static boolean switchCoord(int a, double val) {
		if ( (a == +1 && val > 0.5) || (a == 0 && val < 0.5 && val > -0.5) || (a == -1 && val < -0.5) ) {
			return true;
		}
		return false;
	}
	
	/**
	 * Glturn2cubeturn.
	 *
	 * @param turn the turn
	 * @return the int
	 */
	private static int glturn2cubeturn(int turn) {
		switch ( turn ) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			return turn;
		case 7:
		case 8:
		case 9:
		case 13:
		case 14:
		case 15:
			return turn + 3;
		case 10:
		case 11:
		case 12:
		case 16:
		case 17:
		case 18:
			return turn - 3;
		}
		return turn;
	}

	/**
	 * Turn.
	 *
	 * @param move the move
	 */
	private static void turn(byte move){
		nowTurn = true;
//		if(!diceing) System.out.println( String.format( "turn(%s) ", move) );
		switch(move){
		case 1:
			turn_01();
			break;
		case 2:
			turn_02();
			break;
		case 3:
			turn_03();
			break;
		case 4:
			turn_04();
			break;
		case 5:
			turn_05();
			break;
		case 6:
			turn_06();
			break;
		case 7:
			turn_07();
			break;
		case 8:
			turn_08();
			break;
		case 9:
			turn_09();
			break;
		case 10:
			turn_10();
			break;
		case 11:
			turn_11();
			break;
		case 12:
			turn_12();
			break;
		case 13:
			turn_13();
			break;
		case 14:
			turn_14();
			break;
		case 15:
			turn_15();
			break;
		case 16:
			turn_16();
			break;
		case 17:
			turn_17();
			break;
		case 18:
			turn_18();
			break;
		default:
			titleAddon = titleAddonAry[ move-20 ];
			System.out.println( String.format("title: %s  Rest: %d now:%d", titleAddon, solveList.size(), move ) );
			if(solveList.size()>0) turn( (byte)glturn2cubeturn( solveList.remove( 0 ) ) );
			else if(solveing && solveList.size()==0) solveing = nowTurn = false;
			break;
		}
	}
	
	/**
	 * Dice.
	 */
	private static void dice(){
		Random r = new Random();
		diceing = true;
//		c.setShow( false );
		for( int i=0; i<2000; i++ ){
			if( i%200 == 0 )
				Display.update();
			int rand = 1+r.nextInt( 18 );
			turn( (byte)rand );
		}
//		c.setShow( true );
		System.out.println( c );
		diceing = false;

	}

	/**
	 * Solve.
	 */
	private static void solve(){
		solveing = true;
		System.out.println( String.format(" %s %s", S.isNotNull( solveList ), solveTainted) );
		if( S.isNotNull( solveList ) && solveList.size()>0 && !solveTainted ){
			turn( (byte)glturn2cubeturn( solveList.remove( 0 ) ) );
		} else {
			count  = 0;
			c.saveState();
//			c.setShow( false );
			c.solve();
//			c.setShow( true );
			c.restoreState();
			solveList = c.getSolveList();
			turn( (byte)glturn2cubeturn( solveList.remove( 0 ) ) );
		}
	}
		
	/**
	 * Turn_01.
	 */
	private static void turn_01() {
		rotate.turn = 1;
		rotate.anz = 9;
		turn( -1, +1, 0 );
	}

	/**
	 * Turn_02.
	 */
	private static void turn_02() {
		rotate.turn = 2;
		rotate.anz = 8;
		turn( 0, +1, 0 );
	}

	/**
	 * Turn_03.
	 */
	private static void turn_03() {
		rotate.turn = 3;
		rotate.anz = 9;
		turn( +1, +1, 0 );
	}

	/**
	 * Turn_04.
	 */
	private static void turn_04() {
		rotate.turn = 4;
		rotate.anz = 9;
		turn( -1, -1, 0 );
	}

	/**
	 * Turn_05.
	 */
	private static void turn_05() {
		rotate.turn = 5;
		rotate.anz = 8;
		turn( 0, -1, 0 );
	}

	/**
	 * Turn_06.
	 */
	private static void turn_06() {
		rotate.turn = 6;
		rotate.anz = 9;
		turn( +1, -1, 0 );
	}

	/**
	 * Turn_07.
	 */
	private static void turn_07() {
		rotate.turn = 7;
		rotate.anz = 9;
		turn( +1, +1, 1 );
	}

	/**
	 * Turn_08.
	 */
	private static void turn_08() {
		rotate.turn = 8;
		rotate.anz = 8;
		turn( 0, +1, 1 );
	}

	/**
	 * Turn_09.
	 */
	private static void turn_09() {
		rotate.turn = 9;
		rotate.anz = 9;
		turn( -1, +1, 1 );
	}

	/**
	 * Turn_10.
	 */
	private static void turn_10() {
		rotate.turn = 10;
		rotate.anz = 9;
		turn( +1, -1, 1 );
	}

	/**
	 * Turn_11.
	 */
	private static void turn_11() {
		rotate.turn = 11;
		rotate.anz = 8;
		turn( 0, -1, 1 );
	}

	/**
	 * Turn_12.
	 */
	private static void turn_12() {
		rotate.turn = 12;
		rotate.anz = 9;
		turn( -1, -1, 1 );
	}

	/**
	 * Turn_13.
	 */
	private static void turn_13() {
		rotate.turn = 13;
		rotate.anz = 9;
		turn( +1, +1, 2 );
	}

	/**
	 * Turn_14.
	 */
	private static void turn_14() {
		rotate.turn = 14;
		rotate.anz = 8;
		turn( 0, +1, 2 );
	}

	/**
	 * Turn_15.
	 */
	private static void turn_15() {
		rotate.turn = 15;
		rotate.anz = 9;
		turn( -1, +1, 2 );
	}

	/**
	 * Turn_16.
	 */
	private static void turn_16() {
		rotate.turn = 16;
		rotate.anz = 9;
		turn( +1, -1, 2 );
	}

	/**
	 * Turn_17.
	 */
	private static void turn_17() {
		rotate.turn = 17;
		rotate.anz = 8;
		turn( 0, -1, 2 );
	}

	/**
	 * Turn_18.
	 */
	private static void turn_18() {
		rotate.turn = 18;
		rotate.anz = 9;
		turn( -1, -1, 2 );
	}

	/**
	 * Turning.
	 */
	private static void turning() {
		Rotation r = null;
		int sw = rotate.turn;
		if ( rotate.num == 0 ) {
			sw = 0;
		}
//		if ( sw == 0 && SOLVECUBE == true ) {
//			sw = 255;
//		}
		switch ( sw ) {
		case 0:
			count++;
			nowTurn = false;
		case 255:
			c.move( (byte)glturn2cubeturn( rotate.turn ) );
			for ( int i = 0; i < rotate.anz; i++ ) {
				if ( rotate.turn <= 6 ) {
					r = new Rotation( new Vector3D( 1f, 0f, 0f ), (-Math.PI / 2) * rotate.pos );
				} else if ( rotate.turn > 6 && rotate.turn < 13 ) {
					r = new Rotation( new Vector3D( 0f, 1f, 0f ), (-Math.PI / 2) * rotate.pos );
				} else if ( rotate.turn >= 13 ) {
					r = new Rotation( new Vector3D( 0f, 0f, 1f ), (-Math.PI / 2) * rotate.pos );
				}
				KGLCUBE[rotate.tile[i]] = r.applyTo( KGLCUBE[rotate.tile[i]] );
				KYCube[rotate.tile[i]] = r.applyTo( KYCube[rotate.tile[i]] );
				KZCube[rotate.tile[i]] = r.applyTo( KZCube[rotate.tile[i]] );
				setAngles( rotate.tile[i] );
			}
			if( SOLVESTOP ) solveing=false;
			
			if(solveing && solveList.size()>0)       turn( (byte)glturn2cubeturn( solveList.remove( 0 ) ) );
			else if(solveing && solveList.size()==0) solveing = false;
			
			return;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			for ( int i = 0; i < rotate.anz; i++ ) {
				ANGLES[rotate.tile[i]][0].add( rotate.turn < 4 ? -rotate.speed : rotate.speed );
			}
			break;
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
			for ( int i = 0; i < rotate.anz; i++ ) {
				ANGLES[rotate.tile[i]][1].add( rotate.turn < 10 ? -rotate.speed : rotate.speed );
			}
			break;
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
			for ( int i = 0; i < rotate.anz; i++ ) {
				ANGLES[rotate.tile[i]][2].add( rotate.turn < 16 ? -rotate.speed : rotate.speed );
			}
			break;
		}
		rotate.num--;

		// updateGL();
		t.schedule( new TimerTask() { @Override public void run() { turning(); } }, 25 );

	}
	
	/**
	 * Switch vector.
	 *
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @param v the v
	 * @return true, if successful
	 */
	private static boolean switchVector(int x, int y, int z, Vector3D v) {
		return (switchCoord( x, v.getX() ) && switchCoord( y, v.getY() ) && switchCoord( z, v.getZ() ));

	}
	
	/**
	 * Sets the angles.
	 *
	 * @param tile the new angles
	 */
	private static void setAngles( int tile ) {
		  int NUM=-1;
		  if ( switchVector( 0,1,0, KYCube[tile] ) ) {// gerade nach oben ////////////////////////////////////
		    if ( switchVector( 0,0,1, KZCube[tile] ) ) {
		      NUM=0;
		    } else if ( switchVector( -1,0,0, KZCube[tile] ) ) {
		      NUM=1;
		    } else if ( switchVector( 0,0,-1, KZCube[tile] ) ) {
		      NUM=2;
		    } else if ( switchVector( 1,0,0, KZCube[tile] ) ) {
		      NUM=3;
		    }
		  } else if ( switchVector( 0,-1,0, KYCube[tile] ) ) {// gerade nach unten //////////////////////////////
		    if ( switchVector( 0,0,1, KZCube[tile] ) ) {
		      NUM=4;
		    } else if ( switchVector( -1,0,0, KZCube[tile] ) ) {
		      NUM=5;
		    } else if ( switchVector( 0,0,-1, KZCube[tile] ) ) {
		      NUM=6;
		    } else if ( switchVector( 1,0,0, KZCube[tile] ) ) {
		      NUM=7;
		    }
		  }///////////////////////////////////////////////////////////////////////////////////////////////////
		  else if ( switchVector( 0,0,1, KYCube[tile] ) ) {// gerade nach vorne ///////////////////////////////
		    if ( switchVector( 0,-1,0, KZCube[tile] ) ) {
		      NUM=8;
		    } else if ( switchVector( -1,0,0, KZCube[tile] ) ) {
		      NUM=9;
		    } else if ( switchVector( 0,1,0, KZCube[tile] ) ) {
		      NUM=10;
		    } else if ( switchVector( 1,0,0, KZCube[tile] ) ) {
		      NUM=11;
		    }
		  } else if ( switchVector( 0,0,-1, KYCube[tile] ) ) {// gerade nach hinten //////////////////////////////
		    if ( switchVector( 0,1,0, KZCube[tile] ) ) {
		      NUM=12;
		    } else if ( switchVector( -1,0,0, KZCube[tile] ) ) {
		      NUM=13;
		    } else if ( switchVector( 0,-1,0, KZCube[tile] ) ) {
		      NUM=14;
		    } else if ( switchVector( 1,0,0, KZCube[tile] ) ) {
		      NUM=15;
		    }
		  }///////////////////////////////////////////////////////////////////////////////////////////////////
		  else if ( switchVector( -1,0,0, KYCube[tile] ) ) {// gerade nach links ///////////////////////////////
		    if ( switchVector( 0,0,1, KZCube[tile] ) ) {
		      NUM=16;
		    } else if ( switchVector( 0,-1,0, KZCube[tile] ) ) {
		      NUM=17;
		    } else if ( switchVector( 0,0,-1, KZCube[tile] ) ) {
		      NUM=18;
		    } else if ( switchVector( 0,1,0, KZCube[tile] ) ) {
		      NUM=19;
		    }
		  } else if ( switchVector( 1,0,0, KYCube[tile] ) ) {// gerade nach rechts //////////////////////////////
		    if ( switchVector( 0,0,1, KZCube[tile] ) ) {
		      NUM=20;
		    } else if ( switchVector( 0,-1,0, KZCube[tile] ) ) {
		      NUM=21;
		    } else if ( switchVector( 0,0,-1, KZCube[tile] ) ) {
		      NUM=22;
		    } else if ( switchVector( 0,1,0, KZCube[tile] ) ) {
		      NUM=23;
		    }
		  }
		  ANGLES2[tile][0].set( MANGLES[NUM][0] );
		  ANGLES2[tile][1].set( MANGLES[NUM][1] );
		  ANGLES2[tile][2].set( MANGLES[NUM][2] );
		  ANGLES[tile][0].set( 0 );
		  ANGLES[tile][1].set( 0 );
		  ANGLES[tile][2].set( 0 );
		}

}