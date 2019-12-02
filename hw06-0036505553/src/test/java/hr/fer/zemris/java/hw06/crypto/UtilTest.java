package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testHexToByte() {
		byte[] expected = new byte[] {1, -82, 34};
		byte[] returnField = Util.hextobyte("01aE22");
		assertEquals(expected[0], returnField[0]);
		assertEquals(expected[1], returnField[1]);
		assertEquals(expected[2], returnField[2]);
	}
	
	@Test
	void testHexToByteOdd() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("ab3"));
	}
	
	@Test
	void testHexToByteInvalidCharacters() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("pb3e"));
	}
	
	@Test
	void testByteToHex() {
		byte[] bytes = new byte[] {1, -82, 34};
		String hex = Util.bytetohex(bytes);
		assertEquals("01ae22",hex);
	}
	
	@Test
	void testEmptyByteToHex() {
		byte[] bytes = new byte[0];
		String hex = Util.bytetohex(bytes);
		assertEquals("",hex);
	}

}
