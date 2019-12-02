package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;
/**
 * Representation of SmartScriptEngine which has a reference to {@link DocumentNode}
 * and a reference to {@link RequestContext} and implements a {@link INodeVisitor} so that the 
 * work could be done from the tree generated in documentNode using requestedContext and visitor 
 * pattern.
 * 
 * @author Patrik Okanovic
 *
 */
public class SmartScriptEngine {

	/**
	 * The {@link DocumentNode}
	 */
	private DocumentNode documentNode;
	/**
	 * The {@link RequestContext}
	 */
	private RequestContext requestContext;
	/**
	 * THe {@link ObjectMultistack}
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	

	/**
	 * Implementation of the visitor pattern for implementing scripts that are read.
	 * Implements {@link INodeVisitor}
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper initialValue = new ValueWrapper(node.getStartExpression().asText());
			Object step = node.getStepExpression().asText();
			Object end = node.getEndExpression().asText();
			String variableName = node.getVariable().asText();
			
			multistack.push(variableName, initialValue);
			
			while(initialValue.numCompare(end) <= 0) {
				for(int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
				
				multistack.peek(variableName).add(step);
			}
			multistack.pop(variableName);
			
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();
			
			for(Element element : node.getElements()) {
				if(element instanceof ElementConstantDouble) {
					stack.push(((ElementConstantDouble)element).getValue());
				} else if(element instanceof ElementConstantInteger) {
					stack.push(((ElementConstantInteger)element).getValue());
				} else if(element instanceof ElementString) {
					stack.push(element.toString());
				} else if(element instanceof ElementVariable) {
					String variable = ((ElementVariable)element).asText();
					stack.push(multistack.peek(variable).getValue());
				} else if(element instanceof ElementFunction) {
					doTheFunction(stack,(ElementFunction)element);
				} else if(element instanceof ElementOperator) {
					doTheOperation(stack,(ElementOperator)element);
				}
				
			}
			
			Stack<Object> reversedStack = new Stack<>();
			
			while(!stack.isEmpty()) {
				reversedStack.push(stack.pop());
			}
			
			while(! reversedStack.isEmpty()) {
				try {
					requestContext.write(reversedStack.pop().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}

		private void doTheOperation(Stack<Object> stack, ElementOperator element) {
			ValueWrapper firstOperand = new ValueWrapper(stack.pop());
			Object secondOperand = stack.pop();
			
			if(element.asText().equals("+")) {
				firstOperand.add(secondOperand);
			} else if(element.asText().equals("-")) {
				firstOperand.subtract(secondOperand);
			} else if(element.asText().equals("*")) {
				firstOperand.multiply(secondOperand);
			} else if(element.asText().equals("/")) {
				firstOperand.divide(secondOperand);
			}
			
			stack.push(firstOperand.getValue());
			
		}

		private void doTheFunction(Stack<Object> stack, ElementFunction element) {
			
			
			if(element.asText().equals("@sin")) {//needs to be in deg?
				Object value = stack.pop();
				Double x = Double.parseDouble(value.toString());
				stack.push(Math.sin(x * Math.PI/180));
				
			} else if(element.asText().equals("@decfmt")) {
				Object f = stack.pop();
				Object x = stack.pop();
				DecimalFormat df = new DecimalFormat(f.toString());
				Object r = df.format(x);
				stack.push(r); //not sure about this
				
			} else if(element.asText().equals("@dup")) {
				Object x = stack.pop();
				stack.push(x);
				stack.push(x);
				
			} else if(element.asText().equals("@swap")) {
				Object a = stack.pop();
				Object b = stack.pop();
				stack.push(a);
				stack.push(b);
				
			} else if(element.asText().equals("@setMimeType")) {
				String x = stack.pop().toString();
				requestContext.setMimeType(x);
				
			} else if(element.asText().equals("@paramGet")) {
				String defValue = stack.pop().toString();
				String name = stack.pop().toString();
				String value = requestContext.getParameter(name);
				stack.push(value==null ? defValue : value);
				
			} else if(element.asText().equals("@pparamGet")) {
				String defValue = stack.pop().toString();
				String name = stack.pop().toString();
				String value = requestContext.getPersistentParameter(name);
				stack.push(value==null ? defValue : value);
				
			} else if(element.asText().equals("@pparamSet")) {
				String name = stack.pop().toString();
				String value = stack.pop().toString();
				requestContext.setPersistentParameter(name, value);
				
			} else if(element.asText().equals("@pparamDel")) {
				String name = stack.pop().toString();
				requestContext.removePersistentParameter(name);
				
			} else if(element.asText().equals("@tparamGet")) {
				String defValue = stack.pop().toString();
				String name = stack.pop().toString();
				String value = requestContext.getTemporaryParameter(name);
				stack.push(value==null ? defValue : value);
				
			} else if(element.asText().equals("@tparamSet")) {
				String name = stack.pop().toString();
				String value = stack.pop().toString();
				requestContext.setTemporaryParameter(name, value);
			} else if(element.asText().equals("@tparamDel")) {
				String name = stack.pop().toString();
				requestContext.removeTemporaryParameter(name);
			}
			
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i = 0, len = node.numberOfChildren(); i < len; i++) {
				node.getChild(i).accept(this);
			}
		}
		
	};

	/**
	 * Constructor of the class.
	 * 
	 * @param documentNode {@link DocumentNode}
	 * @param requestContext {@link RequestContext}
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Calls the accept method on the documentNode with our implementation of the {@link INodeVisitor}
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
