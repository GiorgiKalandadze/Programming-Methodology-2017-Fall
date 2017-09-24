/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {
	public void run(){
		if(frontIsBlocked()){
		oneColumnWorld();
		}
		
		else {
			putBeeper();	//Avoid OBOB. put beeper at start point
			fillRow();			//Fill Row with Beepers
			pickLastTwoBeepers();	//Pick last Beepers from Left and Right of row
			pickOneBeepersEachTime();	//Pick one beeper from left and right of row each time(while beepers present on row)
		}
	}
	
	/*	Precondition-Karel stands at start point. There are no beepers in row except first point(1;1)
	 * 	Postcondition-Karel stands at the end of row. Row is full with beepers
	 *	In this method Karel fills row with beepers
	 */
	private void fillRow(){					
		while(frontIsClear()){
			move();
			putBeeper();
		}
	} 
	
	/*	Precondition- Karel stands at the end of row. Row is full with beepers
	 *	Postcondition-Karel stands and second point of first row (At second Vertical/Column) (1;2). No beepers at start and last points.
	 *	In this method Karel picks first and last beepers in row.
	 */
	private void pickLastTwoBeepers(){   
		pickBeeper();
		turnAround();
		while(frontIsClear()){
			move();
		}
		pickBeeper();
		turnAround();
		move();
	}
	
	/*	Precondition-Karel stands at first row on a second column(1;2)
	 * 	Postcondition-Karel stands at mid point of row. There are no beepers in row except mid point. 
	 *	In this method Karel picks one beeper from left and right of row each time(while beepers present on row) 
	 *	and finds mind point.
	 */
	private void pickOneBeepersEachTime(){		
		while(beepersPresent()){
			move();	
			if(noBeepersPresent()){						
				moveOnBackPoint();
				pickBeeper();
				move();
			}
		}
		//Get back at mid point and put Beeper
		turnAround();			
		move();					
		putBeeper();			
	}
	
	//In This Method Karel moves at back point
	private void moveOnBackPoint(){		
		turnAround();
		move();
	}
	
	/* Precondition-Karel stands at first point. 
	 * Postcondition-Karel stand at first point with beeper on it.
	 * Case when world has only one Column
	 */
	private void  oneColumnWorld(){
		putBeeper();
	}
}
