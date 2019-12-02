package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class QueryParserTest {

	@Test
	void testEmpty() {
		assertThrows(QueryParserException.class, () -> new QueryParser(""));
	}
	
	@Test
	void testIsDirectQuery() {
		QueryParser parser = new QueryParser("jmbag = \"00005\"");
		assertTrue(parser.isDirectQuery());
	}

	@Test
	void testGetQueriedJMBAG() {
		QueryParser parser = new QueryParser("jmbag = \"00005\"");
		assertEquals("00005", parser.getQueriedJMBAG());
	}

	@Test
	void testGetQuery() {
		QueryParser parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\" AND firstName!=\"a\" and jmbag LIKE \"b\"");
		List<ConditionalExpression> list = parser.getQuery();
		assertEquals(4, list.size());
		
		assertEquals(ComparisonOperators.EQUALS, list.get(0).getComparisonOperator());
		
		assertEquals(ComparisonOperators.GREATER, list.get(1).getComparisonOperator());
		
		assertEquals(ComparisonOperators.NOT_EQUALS, list.get(2).getComparisonOperator());
		
		assertEquals(ComparisonOperators.LIKE, list.get(3).getComparisonOperator());
	}
	
	@Test
	void testFromHW1() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue(qp1.isDirectQuery());
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size()); 
	}
	
	@Test
	void testFromHW2() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(qp2.isDirectQuery()); 
		assertThrows(IllegalStateException.class, () -> qp2.getQueriedJMBAG());
		assertEquals(2, qp2.getQuery().size()); 

	}

}
