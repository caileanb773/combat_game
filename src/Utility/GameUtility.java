package Utility;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameUtility {
	
	public static final Scanner in = new Scanner(System.in);
	
	public static String getUserInputStr(Scanner in) {
		String userIn = null;
		
		while (true) {
			userIn = in.nextLine();
			if (userIn.trim().isEmpty()) {
				System.out.println("Blank input detected.");
				continue;
			}
			break;
		}
		return userIn;
	}
	
	public static int getUserInputInt(Scanner in) {
		int userIn = 0;
		
		while (true) {
			try {
				userIn = in.nextInt();
				in.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Invalid input: [" + e + "]");
				in.nextLine();
				continue;
			}
			break;
		}
		return userIn;
	}

}
