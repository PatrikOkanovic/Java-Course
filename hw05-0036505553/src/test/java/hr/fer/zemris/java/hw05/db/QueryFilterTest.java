package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueryFilterTest {

	private static QueryFilter filter;
	
	@BeforeEach
	void createData() {
		ConditionalExpression expr1 = new ConditionalExpression(FieldValueGetters.JMBAG
										, "0000000003", ComparisonOperators.EQUALS);
		
		ConditionalExpression expr2 = new ConditionalExpression(FieldValueGetters.LAST_NAME,
												"L*", ComparisonOperators.LIKE);
		
		List<ConditionalExpression> list = new ArrayList<>();
		list.add(expr1);
		list.add(expr2);
		filter = new QueryFilter(list);
	}
	
	@Test
	void testAccepts() {
		assertTrue(filter.accepts(new StudentRecord("Mirko", "Lazic", "5", "0000000003")));
		assertTrue(filter.accepts(new StudentRecord("Darko", "Lesar", "5", "0000000003")));
		
	}
	
	@Test
	void doesNotAccept() {
		assertFalse(filter.accepts(new StudentRecord("Mirko", "Lazic", "5", "0055500003")));
		assertFalse(filter.accepts(new StudentRecord("Mirko", "Filipovic", "5", "0000000003")));
	}

}
