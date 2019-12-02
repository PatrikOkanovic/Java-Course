package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;


/**
 * Class with concrete implementations of algorithms for searching the graph, or coloring the {@link Picture}
 * and moving through the {@link Pixel}
 * 
 * @author Patrik Okanovic
 *
 */
public class SubspaceExploreUtil {

	/**
	 * Implements the BFS algorithm, Breadth-First_search
	 * 
	 * @param <S>
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void bfs(
			 Supplier<S> s0,
			 Consumer<S> process,
			 Function<S,List<S>> succ,
			 Predicate<S> acceptable
			) {
		
		LinkedList<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		
		while(! toExplore.isEmpty()) {
			S si = toExplore.getFirst();
			toExplore.remove(0);
			if(! acceptable.test(si)) {
				continue;
			}
			process.accept(si);
			List<S> list = succ.apply(si);
			for(S s : list) {
				toExplore.addLast(s);
			}
		}
		
		
		
	}
	
	/**
	 * Implements the DFS algorithm, Depth-First-Search
	 * 
	 * @param <S>
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void dfs(
			 Supplier<S> s0,
			 Consumer<S> process,
			 Function<S,List<S>> succ,
			 Predicate<S> acceptable
			) {
		LinkedList<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		
		while(! toExplore.isEmpty()) {
			S si = toExplore.getFirst();
			toExplore.remove(0);
			if(! acceptable.test(si)) {
				continue;
			}
			process.accept(si);
			toExplore.addAll(0,succ.apply(si));
			
		}
	}
	/**
	 * Improved BFS algorithm, once visited {@link Pixel} are not tested or visited again, much faster than 
	 * previous implementations.
	 * 
	 * @param <S>
	 * @param s0
	 * @param process
	 * @param succ
	 * @param acceptable
	 */
	public static <S> void bfsv(
			 Supplier<S> s0,
			 Consumer<S> process,
			 Function<S,List<S>> succ,
			 Predicate<S> acceptable
			) {
		LinkedList<S> toExplore = new LinkedList<>();
		toExplore.add(s0.get());
		HashSet<S> visited = new HashSet<>();
		visited.add(s0.get());
		
		while(! toExplore.isEmpty()) {
			S si = toExplore.getFirst();
			toExplore.remove(0);
			if(! acceptable.test(si)) {
				continue;
			}
			process.accept(si);
			
			List<S> children = succ.apply(si);
			children.removeAll(visited);
			for(S s : children) {
				toExplore.addLast(s);
				visited.add(s);
			}
			
		}
		
		
	}



}
