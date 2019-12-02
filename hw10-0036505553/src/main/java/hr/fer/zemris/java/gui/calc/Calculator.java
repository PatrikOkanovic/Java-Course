package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Implements a simple calculator from homework, uses the {@link CalcModelImpl}.
 * Creates the GUI using {@link CalcLayout}.
 * 
 * @author Patrik Okanovic
 *
 */
public class Calculator extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private boolean enteredOperator = false;
	
	/**
	 * Model of the calculator
	 */
	private CalcModelImpl model = new CalcModelImpl();
	
	/**
	 * The display to show values
	 */
	private JLabel display;
	
	/**
	 * Stores numbers for push and pop buttons
	 */
	private Stack<Double> stack = new Stack<>();
	
	/**
	 * {@link BinaryOpButton} list
	 */
	private List<BinaryOpButton> binaryOp = new ArrayList<>();
	
	/**
	 * {@link UnaryOpButton} list
	 */
	private List<UnaryOpButton> unaryOp = new ArrayList<>();
	
	/**
	 * {@link PowButton}
	 */
	private PowButton powButton;
	
	/**
	 * Constructor, sets the frame.
	 */
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}

	/**
	 * Initializes the GUI
	 */
	private void initGUI() {
		getContentPane().setLayout(new CalcLayout(5));
		
		initializeDisplay();
		
		initializeDigits();
		
		//initialize +/-
		JButton plusMinus = new JButton("+/-");
		add(plusMinus,new RCPosition(5, 4));
		plusMinus.addActionListener(b -> model.swapSign());
		
		//initialize dot
		JButton dot = new JButton(".");
		add(dot,new RCPosition(5, 5));
		dot.addActionListener(b -> model.insertDecimalPoint());
		
		//initializeClr
		JButton clr = new JButton("clr");
		add(clr,new RCPosition(1, 7));
		clr.addActionListener(b -> model.clear());
		
		//initialize res
		JButton res = new JButton("reset");
		add(res,new RCPosition(2, 7));
		res.addActionListener(b ->  {
			model.clearAll();
			enteredOperator = false;
		});
		
		//initialize push
		JButton push = new JButton("push");
		add(push,new RCPosition(3, 7));
		push.addActionListener(b -> stack.push(model.getValue()));
		
		//initialize pop
		JButton pop = new JButton("pop");
		add(pop,new RCPosition(4, 7));
		pop.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				if(stack.isEmpty()) {
					JOptionPane.showMessageDialog(Calculator.this, "Stack is empty, unable to pop");
				} else {
					model.setValue(stack.pop());
				}
				
			}
		});
		
		//initialize equals
		JButton equals = new JButton("=");
		add(equals,new RCPosition(1, 6));
		equals.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.isActiveOperandSet() && model.getPendingBinaryOperation() != null) {
					double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), 
							model.getValue());
					model.setValue(result);
					model.setPendingBinaryOperation(null);
					enteredOperator = false;
				}
			};
		});
		
		initializePowerButton();
		
		initializeUnaryOperations();
		
		initializeBinaryOperations();
		
		
		
		//initialize checkbox
		JCheckBox checkBox = new JCheckBox("Inv");
		add(checkBox, new RCPosition(5, 7));
		checkBox.addActionListener(l -> {
			if(checkBox.isSelected()) {
				unaryOp.forEach(btn -> btn.reverse());
				powButton.reverse();
				
			} else {
				unaryOp.forEach(btn -> btn.reverse());
				powButton.reverse();
			}
		});
		
		
	}
	
	/**
	 * Creates the {@link PowButton}
	 */
	private void initializePowerButton() {
		
		powButton = new PowButton("x^n", "x^(1/n)", (first,second)->Math.pow(first, second),(first,second)->Math.pow(first, 1.0/second));
		binaryOp.add(powButton);
		add(powButton,new RCPosition(5, 1));
	}

	/**
	 * Creates the {@link BinaryOpButton}
	 */
	private void initializeBinaryOperations() {
		BinaryOpButton sum = new BinaryOpButton("+", Double::sum);
		add(sum, new RCPosition(5, 6));
		binaryOp.add(sum);
		
		BinaryOpButton sub = new BinaryOpButton("-", (first,second)-> first - second);
		add(sub, new RCPosition(4, 6));
		binaryOp.add(sub);
		
		BinaryOpButton mul = new BinaryOpButton("*", (first,second)-> first * second);
		add(mul, new RCPosition(3, 6));
		binaryOp.add(mul);
		
		BinaryOpButton div = new BinaryOpButton("/", (first,second)-> first / second);
		add(div, new RCPosition(2, 6));
		binaryOp.add(div);
		
		binaryOp.forEach(button -> button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	
				if(model.getPendingBinaryOperation() == null) {
					model.setActiveOperand(model.getValue());
					model.setPendingBinaryOperation(button.getOp());
//					model.clear();
					enteredOperator = true;
				} else {
					double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
					model.setValue(result);
					model.setActiveOperand(model.getValue());
					model.setPendingBinaryOperation(button.getOp());
				}
				
			}
		}));
		
		
	}


	/**
	 * Creates the {@link UnaryOpButton}
	 */
	private void initializeUnaryOperations() {
		UnaryOpButton sin = new UnaryOpButton("sin","arcsin", x -> Math.sin(x), z->Math.asin(z));
		add(sin,new RCPosition(2, 2));
		unaryOp.add(sin);
		
		UnaryOpButton cos = new UnaryOpButton("cos","arccos", x -> Math.cos(x), y->Math.acos(y));
		add(cos,new RCPosition(3, 2));
		unaryOp.add(cos);
		
		UnaryOpButton tan = new UnaryOpButton("tan","arctan", x -> Math.tan(x), y->Math.atan(y));
		add(tan,new RCPosition(4, 2));
		unaryOp.add(tan);
		
		UnaryOpButton ctg = new UnaryOpButton("ctg","arcctg", x -> 1.0/Math.tan(x), y->1.0/Math.atan(y));
		add(ctg,new RCPosition(5, 2));
		unaryOp.add(ctg);
		
		UnaryOpButton log = new UnaryOpButton("log","10^x", x -> Math.log10(x),y->Math.pow(10, y));
		add(log,new RCPosition(3,1));
		unaryOp.add(log);
		
		UnaryOpButton ln = new UnaryOpButton("ln","e^x", x -> Math.log(x),y->Math.pow(Math.E, y));
		add(ln,new RCPosition(4, 1));
		unaryOp.add(ln);
		
		UnaryOpButton invert = new UnaryOpButton("1/x","1/x", x -> Math.pow(x,-1),y -> Math.pow(y,-1));
		add(invert,new RCPosition(2, 1));
		unaryOp.add(invert);
		
		unaryOp.forEach(button -> button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				double result = button.getOp().applyAsDouble(model.getValue());
				model.setValue(result);
			}
		}));
		
		
	}

	/**
	 * Creates the {@link DigitButton}
	 */
	private void initializeDigits() {
		for(int i = 0; i <= 9; i++) {
			DigitButton btn = new DigitButton(i+"");
			add(btn, determineRCPositionForDigit(i));
			btn.setFont(btn.getFont().deriveFont(30f));
			int br = i;
			btn.addActionListener(b -> {
				if (model.isEditable()) {
					if(enteredOperator) {
						enteredOperator = false;
						model.clear();
						model.insertDigit(br);
					} else {
						model.insertDigit(br);
					}
				} else {
					model.clear();
					model.insertDigit(br);
				}
			});
		}	
	}

	/**
	 * Used to determine where will the digit i be located in the layout.
	 * 
	 * @param i digit number
	 * @return {@link RCPosition}
	 */
	private RCPosition determineRCPositionForDigit(int i) {
		switch (i) {
			case 0:
				return new RCPosition(5, 3);
			case 1:
				return new RCPosition(4, 3);
			case 2:
				return new RCPosition(4, 4);
			case 3:
				return new RCPosition(4, 5);
			case 4:
				return new RCPosition(3, 3);
			case 5:
				return new RCPosition(3, 4);
			case 6:
				return new RCPosition(3, 5);
			case 7:
				return new RCPosition(2, 3);
			case 8:
				return new RCPosition(2, 4);
			default:
				return new RCPosition(2, 5);
		}
				
	}

	/**
	 * Initializes the display
	 */
	private void initializeDisplay() {
		display = new JLabel();
		display.setOpaque(true);
		display.setBackground(Color.yellow);
		display.setBorder(BorderFactory.createLineBorder(Color.black));
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setFont(display.getFont().deriveFont(30f));
		add(display, new RCPosition(1, 1));
		model.addCalcValueListener(m -> display.setText((m.toString())));
	}
	
	/**
	 * Main method of the class.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Calculator prozor = new Calculator();
				prozor.setVisible(true);
			}
		});
	}

}
