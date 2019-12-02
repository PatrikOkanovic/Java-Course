package hr.fer.zemris.java.hw17.trazilica;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
/**
 * Represents an interactive environment where user can give commands and get results
 * from searching the text database whose location is given as an argument to the program.
 * Search is based on the tfidf vector representing documents as a bag of words.
 * 
 * Valid commands are:
 * 
 * 		query args - get top ten similar documents, or less if similarity coefficient is 0.0
 * 		type num - writes the given document at the index from the last query
 * 		results - rewrites the last results from the last query
 * 
 * @author Patrik Okanovic
 *
 */
public class Konzola {

	/**
	 * Location of the text database
	 */
	private static String DIR;

	/**
	 * Location of the stop words
	 */
	private static final String STOP_WORDS = "hrvatski_stoprijeci.txt";
	
	/**
	 * Collection of words gotten from the text files
	 */
	private static List<String> vocabulary = new ArrayList<>();
	
	/**
	 * Loaded stop words
	 */
	private static List<String> stopWords = new ArrayList<>();
	
	/**
	 * Collection of {@link Document} from the given path
	 */
	private static List<Document> documents = new ArrayList<>();
	
	/**
	 * Number of files in the given location.
	 */
	private static int numberOfFiles = 0;
	
	/**
	 * Represents idf vector
	 */
	private static Map<String, Double> idf = new TreeMap<>();
	
	/**
	 * {@link Result} of the last query
	 */
	private static Result result = new Result();

	/**
	 * Main program.
	 * @param args path of the text database where documents are located
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Potrebno je unijeti putanju do direktorija.");
			return;
		}
		
		DIR = args[0];
		
		try {
			stopWords = Files.readAllLines(Paths.get(STOP_WORDS));
		} catch(IOException e) {
			System.out.println("Nisam uspio pročitati stopRiječi.");
			return;
		}
		
		
		try {
			loadVocabulary();
		} catch (IOException e) {
			System.out.println("Nisam uspio napraviti vokabular");
			return;
		}
		
		try {
			createTf();
		} catch (IOException e) {
			System.out.println("Nisam uspio drugi napraviti tf");
			return;
		}
		
		
		
		System.out.println("Veličina rječnika je " + vocabulary.size() + " riječi.");
		
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.printf("Enter command > ");
			if(sc.hasNext()) {
				String line = sc.nextLine();
				boolean end = parseCommand(line);
				if(end) {
					System.out.println("Doviđenja");
					break;
				}
			}
		}
		
		sc.close();

	}

	/**
	 * Creates the tf vector for each pair word document in a seconds reading of the files.
	 * 
	 * @throws IOException
	 */
	private static void createTf() throws IOException {
		
		
		Files.walkFileTree(Paths.get(DIR), new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				
				
				
				List<String> lines = Files.readAllLines(file);
				
				Map<String, Integer> wordFreq = getWordFreqMap(lines);
				
				Document doc = new Document();
				doc.setPath(file.toAbsolutePath());
				Vector tfidf = createVector(wordFreq);
				doc.setTfidf(tfidf);
				documents.add(doc);
				return FileVisitResult.CONTINUE;
			}

			

			

			private Map<String, Integer> getWordFreqMap(List<String> lines) {
				Map<String, Integer> wordFreq = new LinkedHashMap<>();
				
				StringBuilder sb = new StringBuilder();
				for(String line : lines) {
					for(char c : line.toCharArray()) {
						if(Character.isAlphabetic(c)) {
							sb.append(Character.toLowerCase(c));
						} else {
							if(sb.length() != 0) {
								if(vocabulary.contains(sb.toString())) {
									String word = sb.toString();
									if(wordFreq.containsKey(word)) {
										wordFreq.put(word, wordFreq.get(word) + 1);
									} else {
										wordFreq.put(word, 1);
									}
								}
							}
							sb.setLength(0);
						}
					}
					if(vocabulary.contains(sb.toString())) {
						String word = sb.toString();
						if(wordFreq.containsKey(word)) {
							wordFreq.put(word, wordFreq.get(word) + 1);
						} else {
							wordFreq.put(word, 1);
						}
					}
					sb.setLength(0);
				}
				return wordFreq;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;

			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;

			}
		});
		
		
		
	}
	
	/**
	 * Creates the tfidf vector based on the frequency of each word in a document
	 * It is assumed the idf vector has been created.
	 * 
	 * @param wordFreq map of (word, frequency)
	 * @return
	 */
	private static Vector createVector(Map<String, Integer> wordFreq) {
		double[] field = new double[vocabulary.size()];
		int i = 0;
		for(String word : vocabulary) {
			int tf = 0;
			if(wordFreq.containsKey(word)) {
				tf = wordFreq.get(word);
			}
			field[i++] = tf * idf.get(word);
		}
		
		Vector vec = new Vector(field);
		
		return vec;
	}
	
	/**
	 * Returns a set of words found in the given collection of lines.
	 * 
	 * @param lines collection of string sentences
	 * @return
	 */
	private static Set<String> getWords(List<String> lines) {
		Set<String> words = new TreeSet<>();
		StringBuilder sb = new StringBuilder();
		for(String line : lines) {
			for(char c : line.toCharArray()) {
				if(Character.isAlphabetic(c)) {
					sb.append(Character.toLowerCase(c));
				} else {
					if(sb.length() != 0) {
						if(! stopWords.contains(sb.toString())) {
							words.add(sb.toString());
						}
					}
					sb.setLength(0);
				}
			}
			if(sb.length() != 0) {
				if(! stopWords.contains(sb.toString())) {
					words.add(sb.toString());
				}
			}
			sb.setLength(0);
		}
		return words;
	}

	/**
	 * Used to parse the given command and determine the next action.
	 * 
	 * @param line
	 * @return
	 */
	private static boolean parseCommand(String line) {
		line = line.trim();
		String[] splitted = line.split(" ");
		if(splitted[0].equals("exit")) {
			return true;
		} else if(splitted[0].equals("query")) {
			doQuery(splitted);
		} else if(splitted[0].equals("type")) {
			try {
				doType(splitted);
			} catch (IOException e) {
				System.out.println("Pogreška prilikom ispisa.");
			}
		} else if(splitted[0].equals("results")) {
			doResults(splitted);
		} else {
			System.out.println("Nepoznata naredba.");
		}
		
		return false;
	}

	/**
	 * Shows results from the last query.
	 * 
	 * @param splitted
	 */
	private static void doResults(String[] splitted) {
		if(result.results.isEmpty()) {
			System.out.println("Potrebno je prvo obaviti query naredbu.");
			return;
		}
		
		result.writeTopTen();
	}

	/**
	 * Writes the file at the given index from the last query.
	 * Query first has to been called before calling this command.
	 * 
	 * @param splitted
	 * @throws IOException
	 */
	private static void doType(String[] splitted) throws IOException {
		if(result.results.isEmpty()) {
			System.out.println("Potrebno je prvo obaviti query naredbu.");
			return;
		}
		int num;
		try {
			num = Integer.parseInt(splitted[1]);
		} catch(NumberFormatException e) {
			System.out.println("Neispravna naredba");
			return;
		}
		if(num < 0 || num >= 10) {
			System.out.println("Dupušteni interval jet [0,9]");
			return;
		}
		
		Document document = result.getDocumentAtIndex(num);
		if(document == null) {
			System.out.println("Nema rezultata za taj indeks");
			return;
		}
		List<String> lines = Files.readAllLines(document.getPath());
		System.out.println("Dokument: " + document.getPath());
		writeHeader();
		
		for(String line : lines) {
			System.out.println(line);
		}
		
		
	}

	/**
	 * Write the header of "-----"
	 */
	private static void writeHeader() {
		for(int i = 0; i < 50; i++) {
			System.out.printf("-");
			if(i == 49) {
				System.out.println("-");
			}
		}
	}

	/**
	 * Does the query from the given input, calculates the similarity with documents and
	 * writes the top ten similar documents to the given query.
	 * Takes only words which are located in the vocabulary.
	 * 
	 * @param splitted
	 */
	private static void doQuery(String[] splitted) {
		Set<String> query = new LinkedHashSet<>(); 
		Map<String, Integer> queryWordFreq = new LinkedHashMap<>();
		splitted = toLowerCaseWord(splitted);
		if(splitted.length == 1) {
			System.out.println("Query naredba mora primit minimalno jedan argument");
			return;
		}
		for(int i = 1; i < splitted.length; i++) {
			if(vocabulary.contains(splitted[i])) {
				query.add(splitted[i]);
				if(queryWordFreq.containsKey(splitted[i])) {
					queryWordFreq.put(splitted[i], queryWordFreq.get(splitted[i])+1);  
				} else {
					queryWordFreq.put(splitted[i], 1);
				}
			}
		}
		result.reset();
		if(query.isEmpty()) {
			System.out.println("Nema takvih riječi.");
			return;
		}
		System.out.printf("Query is: ");
		System.out.println(query);
		System.out.println("Najboljih 10 rezultata:");
		
		Vector queryVector = createVector(queryWordFreq);
		
		
		
		for(Document doc : documents) {
			double similarity = queryVector.cosSimilarity(doc.getTfidf());
			result.results.put(doc, similarity);
		}
		
		result.writeTopTen();
		

	}

	/**
	 * Returns a field of string transfered to lower case
	 * 
	 * @param splitted
	 * @return
	 */
	private static String[] toLowerCaseWord(String[] splitted) {
		for(int i = 0; i < splitted.length; i++) {
			splitted[i] = splitted[i].toLowerCase();
		}
		return splitted;
	}

	/**
	 * Used to create vocabulary and create an idf vector.
	 * Words which satisfy Character.isAlphabetic in continuous order form a word. 
	 * pero27da parses to pero and da.
	 * 
	 * @throws IOException
	 */
	private static void loadVocabulary() throws IOException {
		Set<String> setVocabulary = new TreeSet<>();
		Files.walkFileTree(Paths.get(DIR), new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				numberOfFiles++;
				List<String> lines = Files.readAllLines(file);
				Set<String> words = getWords(lines);
				words.forEach(word -> setVocabulary.add(word));
				for(String word : words) {
					if(! idf.containsKey(word)) {
						idf.put(word, 1.0);
					} else {
						idf.put(word,  idf.get(word) + 1);
					}
				}
				return FileVisitResult.CONTINUE;
			}


			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				return FileVisitResult.CONTINUE;
			}
		});
//		setVocabulary.remove("ŕ");//mistake in the files, not listed in stop words
		setVocabulary.forEach(s->vocabulary.add(s));
		
		for(Entry<String, Double> entry : idf.entrySet()) {
			Double value = Math.log(numberOfFiles / entry.getValue());
			idf.put(entry.getKey(), value);
		}
		
	}

}
