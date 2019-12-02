package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class Vector2DTest {

	static Vector2D v1;
	
	@BeforeEach
	void resetData() {
		v1 = new Vector2D(1, 1);
	}
	
	@Test
	void testVector2D() {
		assertEquals(1, v1.getX());
		assertEquals(1, v1.getY());
	}

	@Test
	void testGetX() {
		assertEquals(1, v1.getX());
	}

	@Test
	void testGetY() {
		assertEquals(1, v1.getY());
	}

	@Test
	void testTranslate() {
		v1.translate(new Vector2D(1, 0));
		assertEquals(2, v1.getX());
		assertEquals(1, v1.getY());
	}

	@Test
	void testTranslated() {
		v1.translate(new Vector2D(1, 0));
		assertEquals(new Vector2D(2,1), v1);
	}

	@Test
	void testRotate() {
		Vector2D v2 = new Vector2D(-1,1);
		Vector2D v3 = new Vector2D(-1,-1);
		Vector2D v4 = new Vector2D(1,-1);
		
		v1.rotate(Math.PI/2);
		v2.rotate(Math.PI/2);
		v3.rotate(Math.PI/2);
		v4.rotate(Math.PI/2);
		
		assertEquals(new Vector2D(-1, 1), v1);
		assertEquals(new Vector2D(-1, -1), v2);
		assertEquals(new Vector2D(1, -1), v3);
		assertEquals(new Vector2D(1, 1), v4);
	}

	@Test
	void testRotated() {
		assertEquals(new Vector2D(-1, 1), v1.rotated(Math.PI/2));
	}

	@Test
	void testScale() {
		v1.scale(2);
		assertEquals(2, v1.getX());
		assertEquals(2, v1.getY());
	}

	@Test
	void testScaled() {
		assertEquals(new Vector2D(2, 2), v1.scaled(2));
	}

	@Test
	void testCopy() {
		assertEquals(new Vector2D(1, 1), v1.copy());
	}

}
