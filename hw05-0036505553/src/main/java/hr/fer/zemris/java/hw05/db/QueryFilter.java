package hr.fer.zemris.java.hw05.db;

import java.util.List;
/**
 * Implementation of {@link IFilter}, which filters the given {@link StudentRecord}
 * based on all the filters in the object.
 * @author Patrik Okanovic
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * Storage of all the {@link ConditionalExpression} that must be satisfied
	 */
	private List<ConditionalExpression> list;
	
	/**
	 * Constructor in which a list of {@link ConditionalExpression} is given.
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}
	@Override
	public boolean accepts(StudentRecord record) {
		
		for(ConditionalExpression expr : list) {
			
			if(expr.getFieldGetter() == FieldValueGetters.LAST_NAME) {
				if(! expr.getComparisonOperator().satisfied(record.getSurname(), expr.getStringLiteral())) {
					return false;
				}
			} else if(expr.getFieldGetter() == FieldValueGetters.FIRST_NAME) {
				if(! expr.getComparisonOperator().satisfied(record.getName(), expr.getStringLiteral())) {
					return false;
				}
			} else {
				if(! expr.getComparisonOperator().satisfied(record.getJmbag(), expr.getStringLiteral())) {
					return false;
				}
			}
		}
		
		return true;
	}

}
