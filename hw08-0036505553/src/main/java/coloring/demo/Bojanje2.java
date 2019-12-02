package coloring.demo;

import java.util.Arrays;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;
/**
 * Demo program from homework
 * 
 * @author Patrik Okanovic
 *
 */
public class Bojanje2 {

	/**
	 * Main method of the class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FillApp.run(FillApp.OWL, Arrays.asList(bfs,dfs,bfsv));
	}
	
	/**
	 * Static method creating FillingAlgorithm for colouring based on bfs
	 */
	private static final FillAlgorithm bfs = new FillAlgorithm() {
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfs!";
		}

		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfs(col, col, col, col);
		}
	};
	
	/**
	 * Static method creating FillingAlgorithm for colouring based on dfs
	 */
	private static final FillAlgorithm dfs = new FillAlgorithm() {
		@Override
		public String getAlgorithmTitle() {
			return "Moj dfs!";
		}

		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.dfs(col, col, col, col);
		}
	};
	/**
	 * Static method creating FillingAlgorithm for colouring based on bfsv
	 */
	private static final FillAlgorithm bfsv = new FillAlgorithm() {
		@Override
		public String getAlgorithmTitle() {
			return "Moj bfsv!";
		}

		@Override
		public void fill(int x, int y, int color, Picture picture) {
			Coloring col = new Coloring(new Pixel(x, y), picture, color);
			SubspaceExploreUtil.bfsv(col, col, col, col);
		}
	};
	


}
