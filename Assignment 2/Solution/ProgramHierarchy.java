/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 * 
 * This program  draws a partial diagram of the acm.program
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

import javax.swing.text.LabelView;


public class ProgramHierarchy extends GraphicsProgram {	
	
	private static final int width = 150;
	private static final int height = 40;
	
	GLabel lab1 = new GLabel("Program");
	GLabel lab2 = new GLabel("Console");
	GLabel lab3 = new GLabel("Graphics");
	GLabel lab4 = new GLabel("Dialog");
	
	
	public void run(){
		drawLines();	
		drawBox();     
	}
	
	
	//Rects Part
	
	/*	This method gets two values for rect's coordinate
	 *  Two value for rect's width and height
	 *  one value for label.
	 *	and adds rect with label in it(in centre)
	 */
	private void rect(double x, double y, int width, int height, GLabel lab){
		GRect rec = new GRect(x, y, width, height);
		add(lab);
		lab.setLocation(x+width/2-lab.getWidth()/2,  y+height/2+lab.getAscent()/2);
		add(rec);
	}
	
	//	This method draws four rects with labels in them
	private void drawBox(){
		int x1 = getWidth()/2-width/2;			//X coordinate of rect with label "Program"
		int y1 = getHeight()/2-height-height/2; //Y coordinate of rect with label "Program" 
		int x2 = getWidth()/2-2*width;			//X coordinate of rect with label "Console"
		int y2 = getHeight()/2+height/2; 		//Y coordinate of rect with label "Console"
		int x3 = getWidth()/2-width/2;			//X coordinate of rect with label "Graphics"
		int y3 = getHeight()/2+height/2; 		//Y coordinate of rect with label "Graphics"
		int x4 = getWidth()/2+width;			//X coordinate of rect with label "Dialog"
		int y4 = getHeight()/2+height/2; 		//Y coordinate of rect with label "Dialog"
		
		rect(x1, y1, width, height,lab1);
		rect(x2, y2, width, height,lab2);
		rect(x3, y3, width, height,lab3);
		rect(x4, y4, width, height,lab4);
	}

	
	//Lines Part
	/*	This method gets four double values for GLines's coordinates
	 * 	and return GLine's type line
	 */
	private GLine gLine(double x, double y, double a, double b){
		GLine line = new GLine(x, y, a, b);
		return line;
	}
	
	/*	This method draws three lines;
	 * 	Start point coordinates	x1 and y1,  and end points coordinate y2 are same for all lines;
	 * 	Different is only x2 for all;
	 * 	Distance among lines' each x2 coordinate, is 3*width/2;
	 */
	private void drawLines(){
		double x1 = getWidth()/2;				//Start point coordinate x	
		double y1 = getHeight()/2-height/2;		//Start point coordinate y	
		double x2;								//End point coordinate x	
		double y2 = getHeight()/2+height/2;;	//End point coordinate y 	
		for(int i = 0; i < 3; i++){
			x2 = getWidth()/2 - 3*width/2 + (3*width/2)*i;
			add(gLine(x1, y1, x2, y2));
		}
	}

}