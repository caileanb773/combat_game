package Main;

import Actor.*;
import Utility.GameUtility;

public class Driver {
	
	public static void main(String[] args) {
		
		Actor myChar = new Player("Bob", "The man from another place", 1, 5, 5, 25, 5, 10, 3);
		Enemy skelly = new Skeleton(1, 4, 4, 4, 50, 2, 2, 250); // this enemy currently gives 250xp
		
		// TODO fix player being able to attack a dead enemy
		
		myChar.combat(myChar, skelly);
		
	}

}
