/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 * 
 * This program accepts values for a and b as ints and then calculates
 * the square root from a's and b's squares sum ,c as a double.  
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("Enter values to compute Pythagorean theorem.");
		
		int a = readInt("a: ");
		int b = readInt("b: ");
		
		println("c = " + pyth(a,b));
	}
	
	//This method finds square root of two integer number
	private double pyth(int x, int y){
		double c = Math.sqrt(x*x+y*y);
		return c;
	}
	
}
