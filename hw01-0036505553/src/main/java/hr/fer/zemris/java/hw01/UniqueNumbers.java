package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program creates a simple binary tree and implements basic tree operations.
 * @author Patrik Okanovic
 * @version 1.0
 *
 */
public class UniqueNumbers {
	
	/**
	 * A helping structure for implementing a binary tree.
	 *
	 */
	static class TreeNode {
		TreeNode left, right;
		int value;
	}

	/**
	 * Method is called when starting the program.
	 * @param args program gets no arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TreeNode head = null;
		while(true) {
			System.out.format("Unesite broj > ");
			if(sc.hasNext()) {
				String input = sc.next();
				try {
					int number = Integer.parseInt(input);
					if(!containsValue(head, number)) {
						head = addNode(head, number);
						System.out.println("Dodano.");
					} else {
						System.out.println("Broj već postoji. Preskačem.");
					}
					
				} catch(NumberFormatException exc) {
					if(input.equals("kraj")) {
						System.out.format("Ispis od najmanjeg: ");
						printAscending(head);
						System.out.println();
						System.out.format("Ispis od najvećeg: ");
						printDescending(head);
						System.out.println();
						break;
					} else {
						System.out.println("\'" + input + "\' nije cijeli broj.");
					}
				}
			}
		}
		sc.close();

	}
	
	/**
	 * Adds number to the left if it is smaller than head's value or right if it is bigger.
	 * Doesn't add if it already contains that number.
	 * @param node, number head of the tree and which number to add
	 * @return head of the new tree with the added number
	 */
	public static TreeNode addNode(TreeNode node, int number) {
		if(node == null) {
			node = new TreeNode();
			node.value = number;
		} else if(number < node.value) {
			node.left = addNode(node.left, number);
		} else if(number > node.value) {
			node.right = addNode(node.right, number);
		}
		return node;
	}
	
	/**
	 * Calculates number of elements in the tree.
	 * @param node represents the root of the tree
	 * @return size number of elements in the tree
	 */
	public static int treeSize(TreeNode node) {
		if(node != null ) {
			return 1 + treeSize(node.left) + treeSize(node.right);
		}
		return 0;
		
	}
	
	/**
	 * Checks if the number is present in the tree.
	 * @param node represents the root of the tree
	 * @param number which we check if it is present in the tree
	 * @return true or false
	 */
	public static boolean containsValue(TreeNode node, int number) {
		if(node == null) {
			return false;
		}
		if(node.value == number) {
			return true;
		} else if(number < node.value) {
			return containsValue(node.left, number);
		} else {
			return containsValue(node.right, number);
		}
		
	}
	
	/**
	 * Prints elements of the tree in an ascending order.
	 * @param node represents the root of the tree
	 */
	public static void printAscending(TreeNode node) {
		if(node != null) {
			printAscending(node.left);
			System.out.format("%d ",node.value);
			printAscending(node.right);
		}
	}
	
	/**
	 * Prints elements of the tree in an descending order.
	 * @param node represents the root of the tree
	 */
	public static void printDescending(TreeNode node) {
		if(node != null) {
			printDescending(node.right);
			System.out.format("%d ",node.value);
			printDescending(node.left);
		}
	}

}
