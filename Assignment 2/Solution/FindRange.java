/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 * This program finds Maximum and Minimum numbers, from numbers entered by user. 
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int SENTINEl = 0;
	int number;
	
	public void run() {
		println("This program finds the largest and smallest numbers.     Program gets numbers until you enter: " + SENTINEl);
		number = readInt("? ");
		
		
		if(number==SENTINEl){								//If at first turn user enters SENTINEL than print: (Mentioned below)
			println("You haven't entered number");
			println("Please enter number different from " + SENTINEl);
			number = readInt("? ");
			find();
		}
		
		else find();
	}
	
	//This method finds Maximum and Minimum numbers
	private void find(){
		int min = number;  //At first give min value of number (which is entered by user)
		int max = 0;		//At first give max value of 0
		
		while(number!=SENTINEl){  //This loops works until user enters SENTINEL
			
			if(number<min){		//If entered number is less than value of min, than change value with entered number
				min = number;
			}
			
			if(number>max){		//If entered number is more than value of max, than change value with entered number
				max = number;
			}
			number = readInt("? ");
		}
		println("Min = " + min);		//Print minimum from entered numbers
		println("Max = " + max);		//Print maximum from entered numbers
	}
}



