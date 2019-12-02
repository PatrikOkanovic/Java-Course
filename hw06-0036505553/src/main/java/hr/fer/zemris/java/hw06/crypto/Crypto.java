package hr.fer.zemris.java.hw06.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class allows the user to encrypt/decrypt given file using the AES cryptoalgorithm and the 128-bit 
 * encryption key or calculate and check the SHA-256 file digest.
 * 
 * @author patri
 *
 */
public class Crypto {

	/**
	 * Main method of the program, offers the user to check the sha of the given file or encrypt/decrypt a file.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length != 2 && args.length != 3) {
			System.out.println("Invalid input of arguments");
			System.exit(1);
		}
		
		if(args.length == 2) {
			if(!args[0].equals("checksha")) {
				System.out.println("Invalid command");
				System.exit(1);
			}
			
			System.out.printf("Please provide expected sha-256 digest for:%s%n> ",args[1]);
			
			try(Scanner sc = new Scanner(System.in)) {
				String expectedDigest = sc.nextLine();
				checksha(Paths.get(args[1]), expectedDigest);
			}
		} else {
			System.out.printf("Please provide password as "
					+ "hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
			try(Scanner sc = new Scanner(System.in)) {
				String password = sc.nextLine();
				System.out.printf("Please provide initialization "
						+ "vector as hex-encoded text (32 hex-digits):%n> ");
				String vector = sc.nextLine();
				crypt(args[0], Paths.get(args[1]), Paths.get(args[2]), password, vector);
			}
		}
	}
	/**
	 * Implementation of the digest calculation using SHA-256 algorithm.
	 *
	 * @param path
	 * @param excpected
	 * @return
	 */
	public static void  checksha(Path path, String expectedHexDigest) {
		try(InputStream is = Files.newInputStream(path, StandardOpenOption.READ)) {
			
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			
			byte[] buff = new byte[4096];
			
			while(true) {
				int r = is.read(buff);
				if(r < 1) {
					break;
				}
				
				sha.update(buff,0,r);
			}
			byte[] hash = sha.digest();
			
			String hexDigest = Util.bytetohex(hash);
			
			if(hexDigest.equals(expectedHexDigest)) {
				System.out.printf("Digesting completed. Digest of %s matches expected digest.%n", path.getFileName());
			} else {
				System.out.printf("Digesting completed. Digest of %s does not match the expected digest. Digest was: %s%n"
																			, path.getFileName(),hexDigest);
			}
			
			
			
		} catch (IOException | NoSuchAlgorithmException e) {
			System.out.println("Error while digesting");
			e.printStackTrace();
		}
		
		
		
	}
	/**
	 * Encrypts or decrypts the given data.
	 * Encryption the data into a form, called a ciphertext, that can not be easily understood by
	 * unauthorized people. Decrypts is the reverse process: it is a transformation of the ciphertext back into its
	 * original form. Uses symmetric crypting with the same key .
	 *
	 * @param cryptMode
	 * @param inputPath
	 * @param outputPath
	 * @param keyText
	 * @param ivText
	 */
	public static void crypt(String cryptMode, Path inputPath, Path outputPath, String keyText, String ivText) {
		
		if(!cryptMode.equals("encrypt") && !cryptMode.equals("decrypt")) {
			System.out.println("Invalid command");
			return;
		}
		
		try (InputStream is = Files.newInputStream(inputPath, StandardOpenOption.READ);
				OutputStream os = Files.newOutputStream(outputPath, StandardOpenOption.CREATE)) {

			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			boolean encrypt = cryptMode.equals("encrypt");

			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			byte[] buff = new byte[4096];
			while(true) {
				int r = is.read(buff);
				
				if(r < 1) {
					break;
				}
				
				os.write(cipher.update(buff, 0, r));
			}
			
			os.write(cipher.doFinal());
			
			if(encrypt) {
				System.out.printf("Encryption completed. Generated file %s based on file %s.%n", outputPath.getFileName(),inputPath.getFileName());
			} else {
				System.out.printf("Decryption completed. Generated file %s based on file %s.%n", outputPath.getFileName(),inputPath.getFileName());
			}

		} catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			System.out.println("Error during crypting");
			e.printStackTrace();
		}
		
	}
}
