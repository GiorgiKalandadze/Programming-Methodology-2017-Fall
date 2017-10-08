/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 * This program creates Target which consists three different oval;
 */
import java.awt.*;
import acm.graphics.*;
import acm.program.*;

public class Target extends GraphicsProgram {
	
	 double r1 = 72;					//Radius of biggest GOval;
	 double r2 = converter(1.65);		//Radius of middle GOval;
	 double r3 = converter(0.76);		//Radius of smallest GOval;
	
	public void run(){
		drawOvals();
	}	
	
	
	/*	This method converts centimetre to pixel
	 * 	According to the facts mentioned in problem, that 2.54 centimetre equals = 72 pixel,	 	
	 */
	private double converter(double cm){
		double result = 72*cm/2.54;
		return result;
	}
	
	/*	This method gets two values for coordinates of GOval 
	 * 	two values for width and height of GOval 
	 * 	one value for Color of GOval;
	 * 	and returns according GOval;
	 * 
	 */
	private GOval oval(double x, double y, double width, double height, Color color){
		GOval o = new GOval(x, y, width, height);
		o.setFilled(true);
		o.setColor(color);
		return o;
	}
	
	/*	This method adds three GOvals.
	 * 	According (x,y) coordinates, width, height and color of GOvals are mentioned below.
	 * 
	 */
	private void drawOvals(){
		add(oval(getWidth()/2-r1, getHeight()/2-r1, 2*r1, 2*r1, Color.red));		//Biggest GOval
		add(oval(getWidth()/2-r2, getHeight()/2-r2, 2*r2, 2*r2, Color.white));		//Middle GOval
		add(oval(getWidth()/2-r3, getHeight()/2-r3, 2*r3, 2*r3, Color.red));		//Smallest GOval
	}

}