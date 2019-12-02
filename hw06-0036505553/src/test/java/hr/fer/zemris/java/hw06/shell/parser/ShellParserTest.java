package hr.fer.zemris.java.hw06.shell.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class ShellParserTest {

	@Test
	void test() {
		ShellParser parser = new ShellParser("\"C:\\data\\moj fajl\"  D:\\data\\prob1.txt");
		List<String> list = parser.getPaths();
		assertEquals("C:\\data\\moj fajl",list.get(0));
		assertEquals("D:\\data\\prob1.txt", list.get(1));
	}
	
	@Test
	void testWithEscaping() {
		ShellParser parser = new ShellParser("\"C\\\\:/data/moj/\\\"debeli\\\"\"  D:/data/prob1.txt");
		List<String> list = parser.getPaths();
		assertEquals("C\\:/data/moj/\"debeli\"",list.get(0));
		assertEquals("D:/data/prob1.txt", list.get(1));
	}
	
	

}
