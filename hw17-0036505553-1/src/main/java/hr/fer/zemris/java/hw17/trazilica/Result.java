package hr.fer.zemris.java.hw17.trazilica;


import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
/**
 * Class used to store results after the first query has been called in {@link Konzola}
 * 
 * @author Patrik Okanovic
 *
 */
public class Result {

	/**
	 * Used to store results
	 */
	public Map<Document, Double> results = new HashMap<>();
	
	/**
	 * Gets the top ten values sorted by its similarity with the query and the document.
	 * Sort is in the descending order.
	 * 
	 * @return
	 */
	public Map<Document, Double> getTopTenSimilar() {
		Map<Document, Double> topTen =
			    results.entrySet().stream()
			       .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
			       .limit(10)
			       .collect(Collectors.toMap(
			          Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return topTen;
	}

	/**
	 * Used to empty the results before a new query is called.
	 */
	public void reset() {
		results.clear();
	}

	/**
	 * Writes top ten most similar documents to the last query in an descending order.
	 * 
	 */
	public void writeTopTen() {
		Map<Document, Double> topTen = getTopTenSimilar();
		int br = 0;
		for(Entry<Document, Double> entry : topTen.entrySet()) {
			if(br >= 10 || Math.abs(0.0 - entry.getValue())< 10e-8) {
				break;
			}
			
			System.out.printf("[%d] (%.4f) %s\n",br,entry.getValue(),entry.getKey().getPath().toString());
			br++;
		}
	}

	/**
	 * Returns a document at the given index num from the sorted
	 * topTen results based on the last query.
	 * 
	 * @param num index
	 * @return {@link Document}, if num is not [0,9] returns null
	 */
	public Document getDocumentAtIndex(int num) {
		Map<Document, Double> topTen = getTopTenSimilar();
		int br = 0;
		for(Document doc : topTen.keySet()) {
			if(br == num) {
				if(Math.abs(results.get(doc) - 0.0) < 10e-8) {
					return null;
				}
				return doc;
			}
			br++;
		}
		
		return null;
	}
}
