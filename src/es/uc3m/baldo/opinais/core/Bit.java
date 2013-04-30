package es.uc3m.baldo.opinais.core;
/**
 * Bit
 * Represents a bit in a feature vector, which may take
 * 	the usual 0 and 1 values.
 * 
 * @author Alejandro Baldominos
 *
 */
public enum Bit {
	ZERO		(false),
	ONE			(true);
	
	// Representation for the value.
	private boolean value;
	
	/**
	 * Builds a new Bit instance.
	 * @param value text representation for the bit value.
	 */
	private Bit (boolean value) {
		this.value = value;
	}
	
	public Bit xor (Bit other) {
		return value == other.value? Bit.ZERO : Bit.ONE;
	}
	
	@Override
	public String toString () {
		return value? "1" : "0";
	}
}
