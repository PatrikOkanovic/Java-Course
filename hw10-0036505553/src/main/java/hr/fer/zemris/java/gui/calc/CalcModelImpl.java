package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
/**
 * An implementation of {@link CalcModel}.
 * Used as a model for implementing {@link Calculator}.
 * 
 * @author Patrik Okanovic
 *
 */
public class CalcModelImpl implements CalcModel{
	
	/**
	 * True if calc is editable
	 */
	private boolean isEditable = true;
	
	/**
	 * True if number is negative
	 */
	private boolean isNegative;
	
	/**
	 * Current input
	 */
	private String input = "";
	/**
	 * Numerical value of current input
	 */
	private double numericalInput;
	/**
	 * The active operand
	 */
	private Double activeOperand;
	/**
	 * The pending operation
	 */
	private DoubleBinaryOperator pendingOperation;
	/**
	 * List of {@link CalcValueListener}
	 */
	private List<CalcValueListener> listeners = new ArrayList<>(); // try copyonwritearraylist

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
		
	}

	@Override
	public double getValue() {
		return numericalInput;
	}

	@Override
	public void setValue(double value) {
		isNegative = value < 0 ? true : false;
		numericalInput = value;
		input = Double.toString(Math.abs(value));
		
		isEditable = false;
		
		notifyListeners();
		
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		this.isEditable = true;
		
		input = "";
		numericalInput = 0;
		isNegative = false;
		
		notifyListeners();
		
	}

	@Override
	public void clearAll() {
		clear();
		this.pendingOperation = null;
		this.activeOperand = null;
		
		notifyListeners();
		
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException("Cannot swap sign because calc is not editable");
		}
		
		isNegative = isNegative ^ true;
		numericalInput = isNegative ? -1*Math.abs(numericalInput) : Math.abs(numericalInput);
		
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(!isEditable) {
			throw new CalculatorInputException("Cannot input digit because calc is not editable");
		}
		
		if(input.contains(".")) {
			throw new CalculatorInputException("Already containing decimal point, cannot add another");
		}
		
		if(input.isEmpty()) {
			throw new CalculatorInputException("Cannot add decimal point to empty value");
		}
		
		StringBuilder sb = new StringBuilder(input);
		sb.append(".");
		input = sb.toString();
		
		notifyListeners();
		
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(!isEditable) {
			throw new CalculatorInputException("Cannot input digit because calc is not editable");
		}
		
		if(digit == 0 && input.equals("0")) {
			return;	
		}
		
		StringBuilder sb = new StringBuilder(input);
		sb.append(digit);
		
		try {
			Double.parseDouble(sb.toString());
			if(input.equals("0")) {
				input = sb.toString().substring(1); // remove starting zero
			} else {
				input = sb.toString();
			}
			numericalInput = Double.parseDouble(input);
			if(checkIsInfOrNaN()) {
				throw new CalculatorInputException("Number became infinity or Nan");
			}
			numericalInput = isNegative ? numericalInput * -1 : numericalInput;
		} catch(NumberFormatException exc) {
			throw new IllegalArgumentException("Number is no longer parsable to double");
			//isEditable = false;
		}
		
		notifyListeners();
	}

	/**
	 * Checks if numericalInput is inf or NaN
	 * 
	 * @return true if numericalInput is inf or NaN
	 */
	private boolean checkIsInfOrNaN() {
		 return Double.compare(Math.abs(numericalInput), Double.POSITIVE_INFINITY) == 0 
				 || Double.compare(Math.abs(numericalInput), Double.NaN)==0;
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(activeOperand == null) {
			throw new IllegalStateException("There is no active operand");
		}
		
		return activeOperand.doubleValue();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		
		notifyListeners();
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = null;
		
		notifyListeners();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		
		notifyListeners();
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(isNegative) {
			sb.append("-");
		}
		if(input.isEmpty()) {
			sb.append("0");
		} else {
			sb.append(input);
		}
		return sb.toString();
		
	}
	/**
	 * Used to notify each of the listeners to the {@link CalcModelImpl}.
	 */
	private  void notifyListeners() {
		listeners.forEach(l -> l.valueChanged(this));
	}

}