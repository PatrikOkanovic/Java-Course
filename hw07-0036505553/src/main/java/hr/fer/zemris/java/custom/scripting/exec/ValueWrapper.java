package hr.fer.zemris.java.custom.scripting.exec;
/**
 * Used to store values in the {@link ObjectMultistack}
 * 
 * @author Patrik Okanovic
 *
 */
public class ValueWrapper {

	/**
	 * The value
	 */
	private Object value;

	/**
	 * Constructor of {@link ValueWrapper}
	 * 
	 * @param value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Gets the value
	 * 
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value
	 * 
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	/**
	 * Adds the incValue to the current value of the instance
	 * 
	 * @param incValue
	 */
	public void add(Object incValue) {
		Object firsOperand = convert(value);
		Object secondOperand = convert(incValue);
		if(firsOperand instanceof Double || secondOperand instanceof Double) {
			this.value = (Double) ((Number)firsOperand).doubleValue() + ((Number)secondOperand).doubleValue();
		} else {
			this.value = (Integer) ((Integer)firsOperand).intValue() + ((Integer)secondOperand).intValue();
		}
	}
	
	/**
	 * Subtracts the decValue from the current value
	 * 
	 * @param decValue
	 */
	public void subtract(Object decValue) {
		Object firsOperand = convert(value);
		Object secondOperand = convert(decValue);
		if(firsOperand instanceof Double || secondOperand instanceof Double) {
			this.value = (Double) ((Number)firsOperand).doubleValue() - ((Number)secondOperand).doubleValue();
		} else {
			this.value = (Integer) ((Integer)firsOperand).intValue() - ((Integer)secondOperand).intValue();
		}
	}
	/**
	 * Multiplies the mulValue from the current value
	 * 
	 * @param mulValue
	 */
	public void multiply(Object mulValue) {
		Object firsOperand = convert(value);
		Object secondOperand = convert(mulValue);
		if(firsOperand instanceof Double || secondOperand instanceof Double) {
			this.value = (Double) ((Number)firsOperand).doubleValue() * ((Number)secondOperand).doubleValue();
		} else {
			this.value = (Integer) ((Integer)firsOperand).intValue() * ((Integer)secondOperand).intValue();
		}
	}
	/**
	 * Divides the current value with divValue
	 * 
	 * @param divValue
	 */
	public void divide(Object divValue) {
		Object firsOperand = convert(value);
		Object secondOperand = convert(divValue);
		if(((Number)secondOperand).doubleValue() == 0) {
			throw new IllegalArgumentException("Dividing with zero!");
		}
		if(firsOperand instanceof Double || secondOperand instanceof Double) {
			this.value = (Double) ((Number)firsOperand).doubleValue() / ((Number)secondOperand).doubleValue();
		} else {
			this.value = (Integer) ((Integer)firsOperand).intValue() / ((Integer)secondOperand).intValue();
		}
	}
	/**
	 * Compares two objects and returns the int value of the comparison.
	 * 
	 * @param withValue
	 * @return integer
	 */
	public int numCompare(Object withValue) {
		Object firstOperand = convert(value);
		Object secondOperand = convert(withValue);
		return Double.valueOf(((Number) firstOperand).doubleValue())
				.compareTo(Double.valueOf(((Number) secondOperand).doubleValue()));
	}
	/**
	 * If the given value is not integer, double or string the result is false.
	 * Null is also accepted.
	 * 
	 * @param value
	 * @return boolean
	 */
	private boolean checkIsAllowedOperate(Object value) {
		if(value != null && !(value instanceof String)
				&& !(value instanceof Double)
			&& !(value instanceof Integer)) {
				return false;
			}
		return true;
	}
	/**
	 * Converts the given object to the integer, double, or parses the string to integer or double.
	 * Null is converted to integer 0
	 * 
	 * @param obj
	 * @return converted object
	 * @throws RuntimeException if the obj is not integer, double, null or parseable string
	 */
	private Object convert(Object obj) {
		if(!checkIsAllowedOperate(obj)) {
			throw new RuntimeException("Arithmetic operations cannot be made with the operand.");
		}
		
		if(obj == null) {
			return Integer.valueOf(0);
		} else if(obj instanceof Double || obj instanceof Integer) {
			return obj;
		} else {
			String operand = (String) obj;
			if(operand.contains(".") || operand.toUpperCase().contains("E")) {
				try {
					Double value = Double.parseDouble(operand);
					return value;
				} catch(NumberFormatException exc) {
					throw new RuntimeException("Arithmetic operations cannot be made with the operand.");
				}
			} else {
				try {
					Integer value = Integer.parseInt(operand);
					return value;
				} catch(NumberFormatException exc) {
					throw new RuntimeException("Arithmetic operations cannot be made with the operand.");
				}
			}
		}
	}
	
}
