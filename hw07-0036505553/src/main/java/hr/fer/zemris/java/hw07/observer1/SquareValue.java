package hr.fer.zemris.java.hw07.observer1;
/**
 *  Writes a square of the integer stored in the {@link IntegerStorage} to the standard output.
 *  
 * @author Patrik Okanovic
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		
		System.out.println("Provided new value: " + istorage.getValue() +
							", square is " + istorage.getValue() * istorage.getValue());

	}

}
