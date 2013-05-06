package es.uc3m.baldo.opinais.core;
/**
 * Bit.
 * <p>Represents a bit in a feature vector, which may take
 * the usual 0 and 1 values.</p>
 * 
 * @author Alejandro Baldominos
 */
public enum Bit {
	/**
	 * 0
	 */
	ZERO	(false),
	
	/**
	 * 1
	 */
	ONE		(true);
	
	/*
	 *  Representation for the value.
	 */
	private boolean value;
	
	/**
	 * <p>Builds a new Bit instance.</p>
	 * @param value text representation for the bit value.
	 */
	private Bit (boolean value) {
		this.value = value;
	}
	
	/**
	 * <p>Perform the XOR operation over two bits.</p>
	 * @param other the secong bit.
	 * @return the result of the XOR operation over the two bits.
	 */
	public Bit xor (Bit other) {
		return value == other.value? Bit.ZERO : Bit.ONE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return value? "1" : "0";
	}
}
