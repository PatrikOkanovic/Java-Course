package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Contains implementations of algorithms of searching the area of solutions.
 * Implements bfs and bfsv, a faster implementation ignoring previously seen states.
 * 
 * @author Patrik Okanovic
 *
 */
public class SearchUtil {
	

	/**
	 * The bfs algorithm, Breadth-first search
	 * 
	 * @param <S>
	 * @param s0
	 * @param succ
	 * @param goal
	 * @return
	 */
	public static <S> Node<S> bfs(
			 Supplier<S> s0,
			 Function<S, List<Transition<S>>> succ,
			 Predicate<S> goal) {
		
		LinkedList<Node<S>> toExplore = new LinkedList<>();
		toExplore.add(new Node<S>(null, s0.get(), 0));
		
		while(! toExplore.isEmpty()) {
			Node<S> ni = toExplore.get(0);
			toExplore.remove(0);
			
			if(goal.test(ni.getState())) {
				return ni;
			}
			List<Transition<S>> list = succ.apply(ni.getState());
			for(Transition<S> part : list) {
				toExplore.addLast(new Node<S>(ni, part.getState(), ni.getCost()+part.getCost()));
			}
		}
		
		return null;
	}
	
	/**
	 * Improved bfs, does not add previously added states.
	 * 
	 * @param <S>
	 * @param s0
	 * @param succ
	 * @param goal
	 * @return
	 */
	public static <S> Node<S> bfsv(
			 Supplier<S> s0,
			 Function<S, List<Transition<S>>> succ,
			 Predicate<S> goal) {
		
		LinkedList<Node<S>> toExplore = new LinkedList<>();
		toExplore.add(new Node<S>(null, s0.get(), 0));
		
		HashSet<S> visited = new HashSet<>();
		visited.add(s0.get());
		
		
		while(! toExplore.isEmpty()) {
			Node<S> ni = toExplore.get(0);
			toExplore.remove(0);
			
			if(goal.test(ni.getState())) {
				return ni;
			}
			List<Transition<S>> list = succ.apply(ni.getState());
			for(Transition<S> part : list) {
				if(! visited.contains(part.getState())) {
					toExplore.addLast(new Node<S>(ni, part.getState(), ni.getCost()+part.getCost()));
					visited.add(part.getState());
				}
			}
		}
		
		return null;
	}

}
