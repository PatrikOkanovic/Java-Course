package hr.fer.zemris.java.hw07.observer2;
/**
 *  Writes a square of the integer stored in the {@link IntegerStorage} to the standard output.
 *  
 * @author Patrik Okanovic
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange change) {
		
		System.out.println("Provided new value: " + change.getNewValue() +
							", square is " + change.getNewValue() * change.getNewValue());

	}

}
