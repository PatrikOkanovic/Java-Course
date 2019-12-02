package hr.fer.zemris.java.hw02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {


	static ComplexNumber c1 = new ComplexNumber(1, 1); // 1+i
	static ComplexNumber c2 = new ComplexNumber(-2.1, 1); // -2.1 + i
	static ComplexNumber c3 = new ComplexNumber(-3, -2.21); // -3-2.21i
	static ComplexNumber c4 = new ComplexNumber(1, -2); // 1 - 2i
	static final double DIFFERENCE = 10E-7;


	@Test
	void testComplexNumber() {
		ComplexNumber z = new ComplexNumber(5, 10);
		assertEquals(5, z.getReal());
		assertEquals(10, z.getImaginary());
	}

	@Test
	void testFromReal() {
		ComplexNumber c = ComplexNumber.fromReal(3);
		assertEquals(new ComplexNumber(3, 0), c);
	}

	@Test
	void testFromImaginary() {
		ComplexNumber c = ComplexNumber.fromImaginary(-3);
		assertEquals(new ComplexNumber(0, -3), c);
	}

	@Test
	void testFromMagnitudeAndAngle() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI/4);
		assertEquals(c1, c);
	}
	
	@Test
	void testFromMagnitudeAndAngleNegativeMagnitude() {
		try {
			ComplexNumber.fromMagnitudeAndAngle(-2, Math.PI/4);
			fail("Must fail");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}

	@Test
	void testParse1() {
		assertEquals(new ComplexNumber(1, 1), ComplexNumber.parse("1+i"));
	}
	
	@Test
	void testParse2() {
		assertEquals(new ComplexNumber(-3, -2.21), ComplexNumber.parse("-3 - 2.21i"));
	}
	
	@Test
	void testParse3() {
		assertEquals(new ComplexNumber(0, -1), ComplexNumber.parse("-i"));
	}
	
	@Test
	void testParse4() {
		assertEquals(new ComplexNumber(3.23, 0), ComplexNumber.parse("+3.23"));
	}
	
	@Test
	void testParse5() {
		assertEquals(new ComplexNumber(3.23, 0), ComplexNumber.parse("+3.23 + 0i"));
	}
	
	@Test
	void testParseNull() {
		try {
			ComplexNumber.parse(null);
			fail("Must fail");
		} catch(NullPointerException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testParseFail1() {
		try {
			ComplexNumber.parse("-2+-3i");
			fail("Must fail");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testParseFail2() {
		try {
			ComplexNumber.parse("++2-3i");
			fail("Must fail");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}
	@Test
	void testParseFail3() {
		try {
			ComplexNumber.parse("-2-+3i");
			fail("Must fail");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testParseFail4() {
		try {
			ComplexNumber.parse("-2--3i");
			fail("Must fail");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testParseFail5() {
		try {
			ComplexNumber.parse("-2-i3");
			fail("Must fail");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testParseFailWithoutI() {
		try {
			ComplexNumber.parse("-2-3");
			fail("Must fail");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testParseFailUnsupported() {
		try {
			ComplexNumber.parse("-2-f3fs");
			fail("Must fail");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}	
		
	@Test
	void testGetReal() {
		assertEquals(-2.1, c2.getReal());
	}

	@Test
	void testGetImaginary() {
		assertEquals(1, c1.getImaginary());
	}

	@Test
	void testGetMagnitude() {
		assertEquals(Math.sqrt(2), c1.getMagnitude());
	}

	@Test
	void testGetAngleFirstQuandrant() {
		assertEquals(Math.PI/4, c1.getAngle());
	}
	
	@Test
	void testGetAngleSecondQuandrant() {
		assertTrue((2.697173444 - c2.getAngle()) < DIFFERENCE);
	}
	@Test
	void testGetAngleThirdQuandrant() {
		assertTrue((3.776505676 - c3.getAngle()) < DIFFERENCE);
	}
	@Test
	void testGetAngleFourthQuandrant() {
		assertTrue((5.176036589 - c4.getAngle()) < DIFFERENCE);
	}


	@Test
	void testAdd() {
		assertEquals(new ComplexNumber(-1.1, 2), c1.add(c2));
	}
	
	@Test
	void testAddNull() {
		try {
			c1.add(null);
			fail("Must fail");
		} catch(NullPointerException exc) {
			assertTrue(true);
		}
	}

	@Test
	void testSub() {
		assertEquals(new ComplexNumber(0.9, 3.21), c2.sub(c3));
	}
	
	@Test
	void testSubNull() {
		try {
			c1.sub(null);
			fail("Must fail");
		} catch(NullPointerException exc) {
			assertTrue(true);
		}
	}

	@Test
	void testMul() {
		assertEquals(new ComplexNumber(3, -1), c1.mul(c4));
	}
	
	@Test
	void testAddMul() {
		try {
			c1.mul(null);
			fail("Must fail");
		} catch(NullPointerException exc) {
			assertTrue(true);
		}
	}

	@Test
	void testDiv() {
		assertEquals(new ComplexNumber(-0.2, 0.6), c1.div(c4));
	}
	
	@Test
	void testDivWithZero() {
		ComplexNumber c = new ComplexNumber(0, 0);
		ComplexNumber z = c1.div(c);
		assertTrue(Double.isNaN(z.getReal()));
		assertTrue(Double.isNaN(z.getImaginary()));
	}
	
	@Test
	void testAddDiv() {
		try {
			c1.div(null);
			fail("Must fail");
		} catch(NullPointerException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testPower() {
		assertEquals(new ComplexNumber(-11, 2), c4.power(3));
	}
	
	@Test
	void testPowerWithZero() {
		assertEquals(new ComplexNumber(1, 0), c4.power(0));
	}
	
	@Test
	void testPowerLowerBound() {
		try {
			c1.power(-1);
			fail("Must fail");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}

	@Test
	void testRoot() {
		ComplexNumber root1 = new ComplexNumber(1.0986841, 0.4550899);
		ComplexNumber root2 = new ComplexNumber(-1.0986841, -0.4550899);
		assertEquals(root1, c1.root(2)[0]);
		assertEquals(root2, c1.root(2)[1]);
	}
	
	@Test
	void testRootLowerBound() {
		try {
			c1.root(0);
			fail("Must fail");
		} catch(IllegalArgumentException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testEquals() {
		ComplexNumber z = new ComplexNumber(1.000000001, -2.000000001);
		ComplexNumber w = new ComplexNumber(1.000000002, -2.000000002);
		assertEquals(z, w);
	}

}
