package es.uc3m.opinais;
/**
 * Individual
 * Represents an individual in the Aritificial Immune
 * 	System. This individual is represented by a feature
 * 	vector, which is indeed a bit array.
 * 
 * @author Alejandro Baldominos
 *
 */
public class Individual {

	// Feature vector.
	private Bit[] bits;

	/**
	 * Builds a new Individual instance.
	 * @param bits an array of bits representing the individual.
	 */
	public Individual (Bit ... bits) {
		this.bits = new Bit[bits.length];
		System.arraycopy(bits, 0, this.bits, 0, bits.length);
	}
	
	/**
	 * Returns the bit at position <i>index</i>.
	 * @param index the position of the bit to be returned.
	 * @return the bit at position <i>index</i>.
	 */
	public Bit bit (int index) {
		return bits[index];
	}
}
