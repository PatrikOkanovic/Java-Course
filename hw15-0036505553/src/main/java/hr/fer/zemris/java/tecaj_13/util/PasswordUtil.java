package hr.fer.zemris.java.tecaj_13.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Util use for generating the password from the given string based on the SHA-1
 * 
 * @author Patrik Okanovic
 *
 */
public class PasswordUtil {
	
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

	/**
	 * Used to generate a password based on string using SHA-1 algorithm
	 * 
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String hashPassword(String password) {
		try {
			MessageDigest messageDisgest = MessageDigest.getInstance("SHA-1");
			messageDisgest.update(password.getBytes());
			byte[] byteArray = messageDisgest.digest();
			return bytetohex(byteArray);
		} catch(NoSuchAlgorithmException e) {
			//ignore
		}
		return null;
	}
}
