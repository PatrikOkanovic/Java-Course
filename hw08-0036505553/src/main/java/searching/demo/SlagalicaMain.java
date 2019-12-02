package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;
/**
 * Demo program from homework of puzzle with visualization.
 * 
 * @author Patrik Okanovic
 *
 */
public class SlagalicaMain {
	public static void main(String[] args) {
		if(! checkIsValidArgument(args[0])) {
			System.out.println("Invalid input of arguments");
			return;
		}
		
		int[] konfiguracija = getConfiguration(args[0]);
		
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(konfiguracija));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			
			SlagalicaViewer.display(rješenje);
	
		}
	}

	/**
	 * Creates a field of integer from the given argument.
	 * 
	 * @param arg
	 * @return
	 */
	private static int[] getConfiguration(String arg) {
		int[] field = new int[9];
		
		for(int i = 0; i < arg.length(); i++) {
			field[i] = Integer.parseInt(Character.toString(arg.charAt(i)));
		}
		
		return field;
	}

	/**
	 * Checks if argument has 012345678
	 * 
	 * @param arg
	 * @return
	 */
	private static boolean checkIsValidArgument(String arg) {
		if(arg.length() != 9) {
			return false;
		}
		
		String s = "012345678";
		
		for(Character c : s.toCharArray()) {
			if(! arg.contains(Character.toString(c))) {
				return false;
			}
		}
		
		return true;
	}
}
