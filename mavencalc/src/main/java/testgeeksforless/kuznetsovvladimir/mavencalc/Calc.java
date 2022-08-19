package testgeeksforless.kuznetsovvladimir.mavencalc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;

public class Calc {

	private String expression;
	private double result;
	private boolean errorFlag = false;
	private String errorsMessage = "";
	private int countOfNumbers = 0;

	
	public Calc(String newExp) {
		this.expression = newExp;
	}

	
	public void calculate() {

		if (fullCheck()) {
			 result = calculateExpression();
		}
		countNumbers();
	}


	private boolean fullCheck() {

		boolean checkResult = checkBrackets() && checkSymbols() && checkSigns();
		
		errorFlag = errorFlag || !checkResult;

		return checkResult;
	}

	
	private boolean checkBrackets() {

		boolean checkResult = true;
		
		char symbols[] = expression.toCharArray();
		int countOpenBrackets = 0;
		
		for (int i = 0; i < expression.length(); i++) {
			if (symbols[i] == '(') {
				countOpenBrackets++;
			} else {
				if (symbols[i] == ')') {
					countOpenBrackets--;
				}
			}
			if (countOpenBrackets < 0) {
				errorsMessage = errorsMessage + "There are closing bracket before opening bracket! ";				
				checkResult = false;
				break;
			}
		}

		if (countOpenBrackets != 0) {
			errorsMessage = errorsMessage + "The number of open brackets does not match the number of closed ones! ";
			checkResult = false;
		}
		return checkResult;
	}

	
	private boolean checkSymbols() {

		boolean checkResult = true;
		
		String validSymbols = "0123456789-+*/(). ";
		String incorrectSymbols = "";
		
		for (int i = 0; i < expression.length(); i++) {
			String currSymbol = String.valueOf(expression.charAt(i));
			if (validSymbols.contains(currSymbol) == false) {
				incorrectSymbols = incorrectSymbols + currSymbol;
				checkResult = false;
			}
		}
		if (checkResult == false) {
			errorsMessage = errorsMessage + "The expression contains invalid characters! (" + incorrectSymbols + ")";
		}
		return checkResult;
	}


	private boolean checkSigns() {

		boolean checkResult = true;
		
		String strFirstSymbols = "(/*+-";
		String strNextSymbols = ")/*+";
		String incorrectSymbols = "";
		
		for (int i = 0; i < expression.length() - 1; i++) {
			
			String firstSymbol = String.valueOf(expression.charAt(i));
			String nextSymbol = String.valueOf(expression.charAt(i + 1));
			
			if (strFirstSymbols.contains(firstSymbol) && strNextSymbols.contains(nextSymbol)) {
				incorrectSymbols = incorrectSymbols + "{" + firstSymbol + "," + nextSymbol + "}";
				checkResult = false;
			}
		}
		if (checkResult == false) {
			errorsMessage = errorsMessage + "Incorrect symbols order! (" +incorrectSymbols + ")";
		}
		return checkResult;
	}

	
	private double calculateExpression() {

		Expression calc = new ExpressionBuilder(expression).build();
		double res = calc.evaluate();
		//System.out.println(res);

		return res;
	}

	
	private void countNumbers() {

		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");

		Matcher matcher = pat.matcher(expression);

		int count = 0;
		while (matcher.find()) {
			count++;
		}
		countOfNumbers = count;
	}

	
	public double getResult() {
		return result;
	}
	
	
	@Override
	public String toString() {
		return "Calc [result=" + result + ", countOfNumbers=" + countOfNumbers + ", expression=" + expression
				+ ", errorFlag=" + errorFlag + ", errorsMessage=" + errorsMessage + "]";
	}
}
