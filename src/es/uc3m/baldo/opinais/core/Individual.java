package es.uc3m.baldo.opinais.core;

import java.util.Arrays;

/**
 * Individual.
 * <p>Represents an individual in the Artificial Immune
 * System.</p>
 * <p>This individual is represented by a feature
 * vector, which is indeed a bit array.</p>
 * 
 * @author Alejandro Baldominos
 */
public class Individual {

	/*
	 *  Individual type.
	 */
	public Type type;
	
	/*
	 *  Features vector.
	 */
	public Bit[] bits;
	
	/**
	 * <p>Builds a new Individual instance.</p>
	 * @param type the individual type.
	 * @param bits an array of bits representing the individual.
	 */
	public Individual (Type type, Bit ... bits) {
		this.type = type;
		this.bits = bits;
	}
	
	/**
	 * <p>Builds a new Individual instance.</p>
	 * <p>This method exists for commodity purposes,
	 * as it enables an easy way of creating an individual
	 * directly writing 0s and 1s.</p>
	 * @param type the individual type.
	 * @param bits an array of bits representing the individual.
	 */
	public Individual (Type type, int ... bits) {
		this.type = type;
		this.bits = new Bit[bits.length];
		for (int i = 0; i < bits.length; i++) {
			// TODO Values different from 0 are treated as 1s.
			this.bits[i] = bits[i] == 0? Bit.ZERO : Bit.ONE;
		}
	}

	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(bits);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "Individual [type=" + type + ", bits=" + Arrays.toString(bits)
				+ "]";
	}
}
