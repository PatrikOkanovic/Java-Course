package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;

class StudentDatabaseTest {

	@Test
	void testFilter() {
		IFilter trueFilter = record -> true;
		IFilter falseFilter = record -> false;
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
					 Paths.get("./database.txt"),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			System.out.println("Could not read");
		}
		
		StudentDatabase db = new StudentDatabase(lines);
		assertEquals(0, db.filter(falseFilter).size());
		assertEquals(lines.size(), db.filter(trueFilter).size());
	}
	

}
