package es.uc3m.baldo.opinais.core;

import java.util.Arrays;

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

	// Individual type.
	public Type type;
	
	// Feature vector.
	public Bit[] bits;

	/**
	 * Builds a new Individual instance.
	 * @param type the individual type.
	 * @param bits an array of bits representing the individual.
	 */
	public Individual (Type type, Bit ... bits) {
		this.type = type;
		this.bits = new Bit[bits.length];
		System.arraycopy(bits, 0, this.bits, 0, bits.length);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bits);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Individual other = (Individual) obj;
		if (!Arrays.equals(bits, other.bits))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Individual [type=" + type + ", bits=" + Arrays.toString(bits)
				+ "]";
	}
}
