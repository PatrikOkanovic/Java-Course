package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
/**
 * Demo program from the homework
 * @author Patrik Okanovic
 *
 */
public class Glavni3 {

	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);

	}
}
