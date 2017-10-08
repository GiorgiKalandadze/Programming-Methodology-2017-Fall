/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 * 
 * This program draws a pyramid consisting of bricks 
 * arranged in horizontal rows, so that the number of bricks in each row decreases by one
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	public void run() {
		drawPyramid();
	}
	
	private void drawPyramid(){
		/*	//At first n = numbers of bricks in last row;
		 * 	Then it becomes numbers of bricks in each up row;
		 *  n decreases according to the loop mentioned below
		 */
		int n = BRICKS_IN_BASE;   
		double w = getWidth();
		double h = getHeight();
		
		double x = w/2-(BRICKS_IN_BASE*BRICK_WIDTH)/2;  // x = X coordinate of first brick; Which is first from south-west 
		double y = h-BRICK_HEIGHT;						// y = Y coordinate of first brick;	Which is first from south-west
		
		//This loop is for building rows
		for(int i = 0; i < BRICKS_IN_BASE; i++){
		
			/*	This loop is for building columns.  
			 *	On the other words it builds corresponding numbers of bricks in each row.
			 *	That's why after this loop n decreases by one;
			 */
			for(int j = 0; j < n; j++){
				GRect rect = new GRect(x + j*BRICK_WIDTH, y - i * BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT);
				add(rect);
			}
			
			n--;
			x = w/2-(n*BRICK_WIDTH)/2;
		}
	
	}

}

