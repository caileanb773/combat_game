package Main;

import java.util.Scanner;

import Actor.*;
import Utility.GameUtility;

public class Driver {
	
	public static void main(String[] args) {
		
		Actor myChar = new Player("Bob", "The man from another place", 1, 5, 5, 25, 5, 10, 3);
		Enemy skelly = new Skeleton(1, 4, 4, 4, 50, 2, 2, 50);
		Scanner in = new Scanner(System.in);
		GameUtility util = new GameUtility();
		boolean isPlayerTurn = true;
		
		// TODO fix player being able to attack a dead enemy
		
		System.out.println("You encountered a " + skelly);
		System.out.println(myChar);
		System.out.println(skelly);
		do {
		    System.out.println("Press 1 to attack.");

		    // Player's turn
		    if (isPlayerTurn) {
		        System.out.println(myChar.showActorHP());
		        System.out.println(skelly.showActorHP());

		        if (util.getUserInputInt(in) == 1) {
		            // Check if the skeleton is alive before attacking
		            if (skelly.isAlive()) {
		                myChar.attack(skelly);
		                // Check if the enemy is defeated
		                if (!skelly.isAlive()) {
		                    myChar.gainExp(skelly.getExpReward());
		                    System.out.println("You defeated Lvl. [1] Skeleton!");
		                    break;  // Exit loop if the enemy is dead
		                }
		            }
		        } else {
		            System.out.println("Chose to do nothing!");
		        }
		        isPlayerTurn = false;  // Switch to enemy's turn
		    } else {
		        // Enemy's turn
		        if (skelly.isAlive()) {
		            skelly.attack(myChar);
		            // Check if the player is defeated
		            if (!myChar.isAlive()) {
		                System.out.println("You were defeated by " + skelly + ".");
		                break;  // Exit loop if player is dead
		            }
		        }
		        isPlayerTurn = true;  // Switch to player's turn
		    }
		} while (myChar.isAlive() && skelly.isAlive());

		
		if (myChar.isAlive()) {
			System.out.println("You won.");
			System.out.println(myChar);
		} else {
			System.out.println("You lost.");
		}
		
	}

}
