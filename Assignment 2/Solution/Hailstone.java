/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 * This program reads in a number from the user and then displays the
 * Hailstone sequence for that number.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	int count = 0;	//Create count variable to find how much steps it takes to reach 1. At first count equals 0  
	int value;
	
	public void run() {
		value = readInt("Enter a number: ");
		while(value!=1){
			 becomeOne();
		}
		println("The process took " + count + " to reach 1");
	}
	
	/*	This process makes entered number to become one step by step according to two rules:
	 * 	1. If number is even divide it by 2
	 * 	2. If number is odd, multiply it by 3 and add 1 
	 * 	Also in each step increase count by 1 (count++) to find how much steps it takes to reach 1.
	 */
	private void becomeOne(){
		if(value%2==0){
			println(value + "is even so I take half: " + value/2);
			value /=2;
			count ++;
		} 	
		else {
			println(value + "is odd, so I make 3n+1: " + (3*value+1));
			value =3*value+1;
			count ++;
		}
	}
}

