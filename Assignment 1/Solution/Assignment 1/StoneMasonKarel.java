/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass does next thing:
 * Karel repairs columns which has lack of bricks (In Karel's world bricks are presented as Beepers)
 * Space between each column is 3 point.
 * If there is already a beeper somewhere in column Karel doesn't puts there any more. 
 */


import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	
	public void run(){
		while(frontIsClear()){	
			fillColumn();
			moveToNextColumn();
		}
		fillColumn();			//Avoid OBOB. Fill last Column
	}
	
	/*	Precondition-Karel stands at first Row at the starting point of column which requires repair
	 * 	Postcondition-Karel stands at first Row at the starting point of column which is already repaired
	 * 	This Method fills Column with Beepers
	 */
	private void fillColumn(){				
		turnLeft();
		if(noBeepersPresent()){					//Avoid OBOB. If neccessary put Beeper on first point of Column 
			putBeeper();
		}
		while(frontIsClear()){
			move();
			if(noBeepersPresent()){
				putBeeper();
			}
		}
		getBack();
	}
	
	/*	Precondition-Karel stands at the top of the Column Facing North
	 * 	Postcondition-Karel stands at the start of Column facing East
	 * 	This Method gets Karels back from column's top at the starting point of column
	 */
	private void getBack(){						
		turnAround();
		while(frontIsClear()){
			move();
		}
		turnLeft();				
	}
	
	/*	Precondition-Karel stands at any starting point of column
	 * 	Postcondition-Karel stands at starting point of next column
	 * 	This Method moves Karel to the next column
	 */
	private void moveToNextColumn(){				
		for(int i=0; i<4; i++){
			move();
		}
	}
}
