/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does next thing:
 * Karel walks to the door of its house, picks up the
 * newspaper (represented by a beeper, of course), and then returns
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
	
	public void run(){			
		moveToNews();	//Move to Newspaper
		takeNews();		//Pick Newspaper
		getBack();		//Get Back at starting point
	}

	
	/*	Precondition- Karel stands at starting point
	 *	Postcondition- Karel stands at beeper(Newspaper)
	 *	This Method Move To Newspaper
	 */
	private void moveToNews(){		
		turnRight();
		move();
		turnLeft();
		while(noBeepersPresent()){
			move();
		}
		
	}
	
	//This Method Picks Newspaper
	private void takeNews(){		
		pickBeeper();
	}
	
	/*	Postcondition- Karel stands in front of the door with Beeper in bag
	 *	Postcondition-Karel stands at starting point with Beeper in bag
	 *	This Method Gets Karel Back At Starting Point
	*/
	private void getBack(){		
		turnAround();
		while(frontIsClear()){
			move();
		}
		turnRight();
		move();
		turnRight();
		
	}
}
