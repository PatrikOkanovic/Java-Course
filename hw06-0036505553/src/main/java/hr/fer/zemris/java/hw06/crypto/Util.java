package hr.fer.zemris.java.hw06.crypto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
/**
 * Used to transfer byte to hex-encoded String and reverse 
 * @author Patrik Okanovic
 *
 */
public class Util {

	/**
	 * Transfers hex-encoded String to byte[]. Accepts small and big letteres.For zero-length
	 * string,  returns zero-length byte array. Method supports both uppercase letters
	 * and lowercase letters.
	 * @param keyText
	 * @return array of bytes
	 * @throws IllegalArgumentException for odd sized strings and unsupported characters
	 */
	public static byte[] hextobyte(String keyText) {
		Objects.requireNonNull(keyText);
		if(keyText.length() % 2 == 1) {
			throw new IllegalArgumentException("Odd sized string.");
		}
		
		byte[] returnField = new byte[keyText.length() / 2];
		
		Character[] field = new Character[] {'a','b','c','d','e','f'};
		List<Character> validCharacters = Arrays.asList(field);
		for(int i = 0 ; i < keyText.length(); i += 2) {
			if((Character.isDigit(keyText.charAt(i)) || validCharacters.contains(Character.toLowerCase(keyText.charAt(i)))
				&& (Character.isDigit(keyText.charAt(i+1)) || validCharacters.contains(Character.toLowerCase(keyText.charAt(i+1)))))) {
				returnField[i/2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4) + Character.digit(keyText.charAt(i+1), 16));
			} else {
				throw new IllegalArgumentException("Invalid characters");
			}
		}
		
		return returnField;
	}
	

	/**
	 *  Takes a byte array and creates its hex-encoding: for each byte of given
	 *	array, two characters are returned in string, in big-endian notation. For zero-length array an empty string
	 *	is returned. Method uses lowercase letters for creating encoding.
	 * @param bytearray
	 * @return
	 */
	public static String bytetohex(byte[] bytearray) {
		char[] hexCharacters = new char[bytearray.length * 2];
		char[] hexArray = "0123456789abcdef".toCharArray();
		for(int i = 0; i < bytearray.length; i++) {
			int x = bytearray[i] & 0xff;
			hexCharacters[i*2] = hexArray[x>>4];
			hexCharacters[i*2+1] = hexArray[x & 0x0f];
		}
		return new String(hexCharacters);
	}
	
}
