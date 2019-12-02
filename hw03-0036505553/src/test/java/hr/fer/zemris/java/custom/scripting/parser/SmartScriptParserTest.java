package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import hr.fer.zemris.java.hw03.SmartScriptTester;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

class SmartScriptParserTest {

	@Test
	void testExampleFromHomework() {
		String docBody = loader("doc1.txt");

		SmartScriptParser parser = null;

		try {
			
			parser = new SmartScriptParser(docBody);
			
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit (-1);
		} catch (Exception exc) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
	
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = SmartScriptTester.createOriginalDocumentBody(document2);

		assertEquals(originalDocumentBody, originalDocumentBody2); //the parsed text must be reparseable and if the two strings
		// of originalDocumentBodies are equal the parser is working
	}
	
	@Test
	void testExampleWithEscaping() {
		String docBody = loader("doc2.txt");

		SmartScriptParser parser = null;

		try {
			
			parser = new SmartScriptParser(docBody);
			
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit (-1);
		} catch (Exception exc) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
	
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = SmartScriptTester.createOriginalDocumentBody(document2);

		assertEquals(originalDocumentBody, originalDocumentBody2); //the parsed text must be reparseable and if the two strings
		// of originalDocumentBodies are equal the parser is working
	}

	
	@Test
	void testFORFail1() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("{$ FOR 3 1 10 1 $}");
			fail("Must fail!");
		} catch(SmartScriptParserException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testFORFail2() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("{$ FOR * \"1\" -10 \"1\" $}");
			fail("Must fail!");
		} catch(SmartScriptParserException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testFORFail3() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("{$ FOR year @sin 10 $}");
			fail("Must fail!");
		} catch(SmartScriptParserException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testFORFail4() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("{$ FOR year 1 10 \"1\" \"10\" $}");
			fail("Must fail!");
		} catch(SmartScriptParserException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testFORFail5() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("{$ FOR year $}");
			fail("Must fail!");
		} catch(SmartScriptParserException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testFORFail6() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("{$ FOR year 1 10 1 3 $}");
			fail("Must fail!");
		} catch(SmartScriptParserException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testEchoFail1() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("{$ = . year 1 10 1 3 $}");
			fail("Must fail!");
		} catch(SmartScriptParserException exc) {
			assertTrue(true);
		}
	}
	
	@Test
	void testEchoFail2() {
		try {
			@SuppressWarnings("unused")
			SmartScriptParser parser = new SmartScriptParser("{$ =  year {$ 1 10 1 3 $}");
			fail("Must fail!");
		} catch(SmartScriptParserException exc) {
			assertTrue(true);
		}
	}
	
	/**
	 * Method from the homework used as string loader from the textual document.
	 * @param filename
	 * @return
	 */
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
		

}
