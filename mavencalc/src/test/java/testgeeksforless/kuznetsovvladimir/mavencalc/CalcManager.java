package testgeeksforless.kuznetsovvladimir.mavencalc;

import java.util.Scanner;

public class CalcManager {

	public static void main(String[] args) {

		String newExp = ""; 
		//newExp = "(90 + 8.96*3) * 6 / ((3+2) + (8-6) *2 )";
		
		if (newExp == "") {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter your expression:");
			newExp = scanner.nextLine();
			scanner.close();
		}	
		
		Calc calc = new Calc(newExp);
		calc.calculate();
		System.out.println(calc);
		
		DBConnector connector = new DBConnector();
		connector.getData();
		connector.findResult(calc.getResult());
	}
}
