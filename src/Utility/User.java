package Utility;

import java.util.Scanner;

public class User {
	
	public String getUserIn(Scanner in) {
		String userInStr = null;
		
		while (true) {
			userInStr = in.nextLine();
			if (userInStr.trim().isEmpty()) {
				System.out.println("Blank input detected.");
				continue;
			}
			break;
		}
		return userInStr;
	}

}
