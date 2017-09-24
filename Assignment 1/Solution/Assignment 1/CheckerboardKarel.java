import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	
	public void run(){
		if (frontIsBlocked()){
		oneColumnWorld();		//Case when world has only one vertical
		}
		
		else {
			putBeeper();		//Avoid OBOB for first point 
			
			while(leftIsClear()){	
				fillRow();		
				checkLastPoint();		//Check last point to see if there is beeper or not
				fillRow();
				moveUp();				//If possible move to up Row
			}
			
			lastRow(); //Fill last Row when world has odd Rows
		}
	}
	
	//Fill column with Beepers (Like a checker style)
	private void fillColumn(){	
			turnLeft();
			putBeeper();	//Avoid OBOB. 
			fillRow();
	}
		
	//Fill row with Beepers (Like a checker style) 
	private void fillRow(){			
		while(frontIsClear()){
				move();
			if(frontIsClear()){
				move();
				putBeeper();
			}
		}
	}
	
	/*	Precondition-Karel stands at last column on odd row facing East	
	 *	Postcondition-Karel stands "lastbefore" column on even row facing west.
	 *	In this method Karel moves on up row if possible and puts beeper at the "lastbefore" point of even row.
	 */
	private void caseWhenBeepersPresent(){			
		if(frontIsBlocked() && leftIsClear() && beepersPresent()){
				for(int i=0;i<2;i++){
					turnLeft();
					move();
				}
				putBeeper();
		}	
	}
	
	/*	Precondition-Karel stands at last column on odd row facing East	
	 *	Postcondition-Karel stands at last column on even row facing west.
	 *  In this method Karel moves on up row if possible and puts beeper at the last point of even row.
	 */
	private void caseWhenNoBeepersPresent(){			
		if(frontIsBlocked() && leftIsClear() && noBeepersPresent()) {
			turnLeft();
			move();
			turnLeft();
			putBeeper();
		}	
	}
	
	/*	Precondition-Karel stand at the last point of odd row facing east.
	 * 	Postcondition-It depends on methods mentioned below.
	 * 	This method checks row's last point condition. (Is there a beeper on the last point of row or not.)
	 */
	private void checkLastPoint(){    
		caseWhenBeepersPresent(); 
		caseWhenNoBeepersPresent();
	}
	
	/*	Precondition-Karel stand at first column on any row (On odd row or on even row)
	 *	According to case (He stands on odd row or on even row) Karel acts accordingly (This is mentioned below)
	 * 	This Method moves Karel to up Row if possible
	 */
	private void moveUp(){				  
		/*	If Karel stands at first Column (on any odd row) facing west and there is beeper, then
		 * move to up row and put beeper on this row's second point 
		 */
		if (frontIsBlocked() && rightIsClear() && beepersPresent()){ 	
			for(int i=0;i<2;i++){		
				turnRight();
				move();
			}
			putBeeper();
		}
		
		/*	If Karel stands at first Column (on any even row) facing west and there is no beeper, then
		 * 	move to up row and put beeper  
		 */
		if (frontIsBlocked() && rightIsClear() && noBeepersPresent()){	
				turnRight();		
				move();
				turnRight();
				putBeeper();
		}
		
		/*	If there is no more up row end program by manual. 
		 * 	In this case do "turnAround()";
		 */
		if(rightIsBlocked()){		
			turnAround();
		}
	}
	
	/*	Precondition-Karel stands at the start of last row which is odd. Facing east. Row is empty. 
	 * 	Postcondition-Karel stands at the end of last row which is odd. Facing east. Row is filled with Beepers (Like a checker style)
	 *  This method fills last Row when world has odd Rows.
	 */
	private void lastRow(){				
		if(leftIsBlocked() && beepersPresent()){
			fillRow();
		}
	}
	
	/*	Precondition-Karel stands at starting point facing east and front is blocked (There is only one column in world) 
	 *	PostCondition-Karel stand at the top of the column facing north
	 *	This Method works for world which has only one column.
	 */
	private void oneColumnWorld(){			
		fillColumn();				
	}
	
}