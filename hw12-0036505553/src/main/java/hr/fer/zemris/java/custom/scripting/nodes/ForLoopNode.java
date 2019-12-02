package hr.fer.zemris.java.custom.scripting.nodes;


import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
/**
 * A node representing a single for-loop construct. It inherits from Node class
 * Used in parser to create a tree.
 * @author Patrik Okanovic
 *
 */
public class ForLoopNode extends Node {
	/**
	 * Variable of the for loop
	 */
	private ElementVariable variable;
	/**
	 * The start expression of the for loop
	 */
	private Element startExpression;
	/**
	 * The end expression for loop
	 */
	private Element endExpression;
	/**
	 * The step expression for loop. Can be null.
	 */
	private Element stepExpression;
	/**
	 * Creates an instance of the ForLoopNode
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		Objects.requireNonNull(variable);
		Objects.requireNonNull(startExpression);
		Objects.requireNonNull(endExpression);
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * @return the startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * @return the endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * @return the stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {// toString is used and not method asText() so that the escaping \\ and again parsing would work
		String s = "{$FOR ";
		s += variable.toString() + " " + startExpression.toString() 
				+ " " + endExpression.toString() + " ";
		if(stepExpression == null) {
			s += "$}";
		} else {
			s += stepExpression.toString() + "$}";
		}
		
		return s;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
