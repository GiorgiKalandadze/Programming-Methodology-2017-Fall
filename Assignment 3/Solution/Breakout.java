
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;
	
	private double vx, vy; // Velocity of ball
	private GOval ball; // Ball
	private GRect rec; // Brick
	private GRect pad; // Paddle
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private static final int DELAY = 10; // DELAY
	private int attempt = NTURNS;	//Number of attempts
	private int countBricks = NBRICKS_PER_ROW * NBRICK_ROWS;	//Number of whole bricks

	public void init() {
		addMouseListeners();
		setUp();
	}
	//Set up of the game
	private void setUp() {
		drawBricks();
		drawPaddle();
		drawBall();
	}

	// This method draws bricks
	private void drawBricks() {
		//Loop for drawing rows
		for (int i = 0; i < NBRICK_ROWS; i++) {
			
			//Loop for drawing bricks in each row
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {	
				
				//X is offset from the left of the first column. 
				double x = (getWidth() - (NBRICKS_PER_ROW * BRICK_WIDTH + (NBRICKS_PER_ROW - 1) * BRICK_SEP)) / 2;
				
				rec = new GRect(x + j * (BRICK_WIDTH + BRICK_SEP), BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP),
						BRICK_WIDTH, BRICK_HEIGHT);
				rec.setFilled(true);
				
				//Coloring Rects
				if (i % 10 < 2) {
					rec.setColor(Color.red);
					add(rec);
				} else if (i % 10 < 4) {
					rec.setColor(Color.orange);
					add(rec);
				} else if (i % 10 < 6) {
					rec.setColor(Color.yellow);
					add(rec);
				} else if (i % 10 < 8) {
					rec.setColor(Color.green);
					add(rec);
				} else if (i % 10 < 10) {
					rec.setColor(Color.cyan);
					add(rec);
				}
			}
		}
	}

	// This method draws paddle
	private void drawPaddle() {
		pad = new GRect(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT, PADDLE_WIDTH,
				PADDLE_HEIGHT);
		pad.setFilled(true);
		pad.setColor(Color.black);
		add(pad);
	}

	// This method draws ball
	private void drawBall() {
		ball = new GOval(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.black);
		add(ball);
		ball.setVisible(false);	// We want ball to be invisible until mouse is clicked at first attempt
	}

	
	//If moust moved, then change paddles location accordingly
	public void mouseMoved(MouseEvent e) { 
		//Set "frame" for paddle. Paddle must be able to move only in(beetwen) walls
		if (e.getX() >= 0 && e.getX() + PADDLE_WIDTH <= WIDTH) {
			pad.setLocation(e.getX(), HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}

	public void run() {
		game();
	}

	//Game process part
	private void game() {
		while (gameIsntOver()) { 
			GLabel lab = new GLabel("Click Mouse To Start");
			lab.setLocation(WIDTH / 2 - lab.getWidth() / 2, HEIGHT / 2 + 2 * lab.getAscent());
			lab.setColor(Color.blue);
			add(lab);
			
			waitForClick();
			remove(lab);
			ball.setVisible(true);
			
			ballMove();
		}
		checkResult(); //If while loop is finished it means that game is over and user has won or lose. 
	}
	/*	There are two reasons of ending game
	 * 	1 - There are no bricks left
	 * 	2 - There are no attempts left
	 */
	private boolean gameIsntOver(){
		if(countBricks != 0 && attempt != 0){
			return true;
		}
		else return false;
	}
	//This method checks whether user has won or lost the game
	private void checkResult() {
		//If there are no bricks left it means that user has won the game
		if (countBricks == 0) {
			removeAll();
			GLabel lab = new GLabel("You won");
			lab.setLocation(WIDTH / 2 - lab.getWidth() / 2, HEIGHT / 2 + lab.getAscent());
			lab.setColor(Color.red);
			add(lab);
		} else {
			removeAll();
			GLabel lab = new GLabel("You lose");
			lab.setLocation(WIDTH / 2 - lab.getWidth() / 2, HEIGHT / 2 + lab.getAscent() / 2);
			lab.setColor(Color.red);
			add(lab);
		}
	}

	// This method provides ball movement
	private void ballMove() {
		vy = 3.0; // Y Velocity of ball
		vx = rgen.nextDouble(1.0, 3.0); // X velocity of ball. It is chosen by random
									 
		if (rgen.nextBoolean(0.5)) { // Return true or false by chance 50% to set X's direction
			vx = -vx;
		}
		
		while (attempIsntOver()) {
			ball.move(vx, vy);
			if (ball.getX() >= getWidth() - 2 * BALL_RADIUS || ball.getX() <= 0) {
				vx = -vx;
			}
			if (ball.getY() <= 0) {
				vy = -vy;
			}
			checkHit();
			pause(DELAY);
		}
		//If ball passes low wall(floor) then decrease attempt and set ball location in centre
		if (ball.getY() >= getHeight() - 2 * BALL_RADIUS) {
			ball.setLocation(WIDTH / 2 - BALL_RADIUS, HEIGHT / 2 - BALL_RADIUS);
			pad.setLocation(WIDTH / 2 - PADDLE_WIDTH / 2, HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			attempt--;
		}
	}
	/*	There are two reasons of ending attempt
	 * 	1 - There are no bricks left
	 * 	2 - Ball has passed low wall(floor)
	 */
	private boolean attempIsntOver(){
		if(countBricks != 0 && ball.getY() <= getHeight() - 2 * BALL_RADIUS){
			return true;
		}
		else return false;
	}
	

	// This method checks whether returned object is brick or not. If it is than it removes brick.
	private void checkHit() {
		GObject o = getCollidingObject();
		if (o != null && o != pad) { //If o isn't null and isn't pad so it means it is brick
			countBricks--;
			remove(o);
		}
	}

	// This method returns null if ball hasn't hit the object. If yes then returns this object.
	private GObject getCollidingObject() {
		/*	If up point has hit object than it means that ball was going up, with vy velocity where vy was negative
		 * 	so i want that after hit ball went down, with positive vy velocity
		 */
		if (getElementAt(ball.getX() + BALL_RADIUS, ball.getY() - 1) != null) {  //Checks ball's up point
			GObject collider = getElementAt(ball.getX() + BALL_RADIUS, ball.getY() - 1);
			vy = Math.abs(vy);	
			return collider;
		}
		/*	If low point has hit object than it means that ball was going down, with vy velocity where vy was positive
		 * 	so i want that after hit ball went up, with negative vy velocity
		 */
		else if (getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS + 1) != null) {	//Check's balls low point
			GObject collider = getElementAt(ball.getX() + BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS + 1);
			vy = -Math.abs(vy);
			return collider;
		} 
		/*	If left point has hit object than it means that ball was going left, with vx velocity where vx was negative
		 * 	so i want that after hit ball went right, with positive vx velocity
		 */
		else if (getElementAt(ball.getX() - 1, ball.getY() + BALL_RADIUS) != null) {	//Check's ball's left point
			GObject collider = getElementAt(ball.getX() - 1, ball.getY() + BALL_RADIUS);
			vx = Math.abs(vx);
			return collider;
		} 
		/*	If right point has hit object than it means that ball was going right, with vx velocity where vx was posotive
		 * 	so i want that after hit ball went left, with negative vx velocity
		 */
		else if (getElementAt(ball.getX() + 2 * BALL_RADIUS + 1, ball.getY() + BALL_RADIUS) != null) {	//Check's ball's right point
			GObject collider = getElementAt(ball.getX() + 2 * BALL_RADIUS + 1, ball.getY() + BALL_RADIUS);
			vx = -Math.abs(vx);
			return collider;
		} else return null; //If ball hasn't hit object than return null
	}
}