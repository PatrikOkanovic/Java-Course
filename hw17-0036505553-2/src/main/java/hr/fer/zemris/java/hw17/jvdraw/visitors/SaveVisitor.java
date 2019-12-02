package hr.fer.zemris.java.hw17.jvdraw.visitors;

import java.io.IOException;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
/**
 * Implementation of the Visitor pattern used to save the current drawing
 * into a .jvd file, each visit writes the {@link GeometricalObject} in a string format
 * understandable for later reading.
 * 
 * @author Patrik Okanovic
 *
 */
public class SaveVisitor {

	/**
	 * Visit the {@link Line}
	 * Format : LINE x0 y0 x1 y1 r g b
	 * 
	 * @param line
	 * @param path
	 * @throws IOException
	 */
	public void visit(Line line, Path path) throws IOException {
		OutputStream os = Files.newOutputStream(path, StandardOpenOption.APPEND);
		StringBuilder sb = new StringBuilder();
		sb.append("LINE ");
		sb.append(line.getStartPoint().x + " " + line.getStartPoint().y + " ");
		sb.append(line.getEndPoint().x + " " + line.getEndPoint().y + " ");
		sb.append(line.getColor().getRed() + " " + line.getColor().getGreen() + " " + line.getColor().getBlue());
		sb.append("\n");
		os.write(sb.toString().getBytes());
		os.close();
		

	}

	/**
	 * Visit the {@link Circle}
	 * Format: CIRCLE cx cy radius r g b
	 * 
	 * @param circle
	 * @param path
	 * @throws IOException
	 */
	public void visit(Circle circle, Path path) throws IOException {
		OutputStream os = Files.newOutputStream(path, StandardOpenOption.APPEND);
		StringBuilder sb = new StringBuilder();
		sb.append("CIRCLE ");
		sb.append(circle.getCenter().x + " " + circle.getCenter().y + " " + circle.getRadius() + " ");
		sb.append(circle.getColor().getRed() + " " + circle.getColor().getGreen() + " " + circle.getColor().getBlue());
		sb.append("\n");
		os.write(sb.toString().getBytes());
		os.close();

	}

	/**
	 * Visit the {@link FilledCircle}
	 * Format: FCIRCLE cx cy radius r1 g1 b1 r2 g2 b2
	 * First rgb is from the foreground, line color,
	 * second  rgb is from the background color
	 * 
	 * @param filledCircle
	 * @param path
	 * @throws IOException
	 */
	public void visit(FilledCircle filledCircle, Path path) throws IOException {
		OutputStream os = Files.newOutputStream(path, StandardOpenOption.APPEND);
		StringBuilder sb = new StringBuilder();
		sb.append("FCIRCLE ");
		sb.append(filledCircle.getCenter().x + " " + filledCircle.getCenter().y + " " + filledCircle.getRadius() + " ");
		sb.append(filledCircle.getFgColor().getRed() + " " + filledCircle.getFgColor().getGreen() + " " + filledCircle.getFgColor().getBlue() + " ");
		sb.append(filledCircle.getBgColor().getRed() + " " + filledCircle.getBgColor().getGreen() + " " + filledCircle.getBgColor().getBlue());
		sb.append("\n");
		os.write(sb.toString().getBytes());
		os.close();
	}
	
	

}
