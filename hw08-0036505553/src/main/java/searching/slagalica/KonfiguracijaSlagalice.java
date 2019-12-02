package searching.slagalica;

import java.util.Arrays;
/**
 * Used to save the configuration of {@link Slagalica}, and to quickly acces where
 * the empty space is located in the field.
 * 
 * @author Patrik Okanovic
 *
 */
public class KonfiguracijaSlagalice {
	
	/**
	 * Storage of configuration for {@link Slagalica}
	 */
	private int polje[];

	/**
	 * Constructor of the class
	 * 
	 * @param polje
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		super();
		this.polje = polje;
	}

	/**
	 * @return the polje
	 */
	public int[] getPolje() {
		return Arrays.copyOf(polje, polje.length);
	}
	
	public int indexOfSpace() {
		int index = -1;
		for(int i = 0; i < polje.length; i++) {
			if(polje[i] == 0) {
				index = i;
			}
		}
		
		return index;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(polje[3*i+j] == 0) {
					sb.append("* ");
				} else {
					sb.append(polje[3*i + j]);
					sb.append(" ");
				}
				
			}
			sb.append("\n");
			
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}

}
