package es.uc3m.opinais;
/**
 * Bit
 * Represents a bit in a feature vector, which may take
 * 	the usual 0 and 1 values, as well as a wild card value.
 * Wild cards can only be used by detectors, meaning that
 * 	bits in the corresponding positions of the individual'
 * 	feature vector are ignored.
 * 
 * @author Alejandro Baldominos
 *
 */
public enum Bit {
	ZERO		('0'),
	ONE			('1');
	//WILDCARD	('#');
	
	// Text representation for the value.
	private char value;
	
	/**
	 * Builds a new Bit instance.
	 * @param value text representation for the bit value.
	 */
	private Bit (char value) {
		this.value = value;
	}
	
	@Override
	public String toString () {
		return String.valueOf(value);
	}
}
