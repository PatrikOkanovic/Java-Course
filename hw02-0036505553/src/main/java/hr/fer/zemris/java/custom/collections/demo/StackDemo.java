package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Invalid input of arguments.");
			return;
		}
		String[] elements = args[0].trim().split(" ");
		ObjectStack stack = new ObjectStack();
		for(String element : elements) {
			try {
				int number = Integer.parseInt(element);
				stack.push(number);
			} catch(NumberFormatException exc) {
				int result = 0;
				int secondOperand,firstOperand;
				try {
					secondOperand = (Integer)stack.pop();
					firstOperand =(Integer) stack.pop();
				} catch(EmptyStackException e) {
					System.out.println("Invalid input of arguments.");
					return;
				}
				
				if(element.equals("+")) {
					result = firstOperand + secondOperand;
				} else if(element.equals("-")) {
					result = firstOperand - secondOperand;
				} else if(element.equals("/")) {
					if(secondOperand == 0) {
						System.out.println("Cannot divide with zero!");
						return;
					}
					result = firstOperand / secondOperand;
				} else if(element.equals("*")) {
					result = firstOperand * secondOperand;
				} else if(element.equals("%")) {
					result = firstOperand % secondOperand;
				} else {
					System.out.println("Invalid input of arguments.");
					return;
				}
				stack.push(result);
			}
		}
		if(stack.size() != 1) {
			System.out.println("Error!");
			return;
		} else {
			System.out.println("Expression evaluates to " + stack.pop());
		}
	}
}
