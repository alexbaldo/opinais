package es.uc3m.baldo.opinais.ir.vectorizers;

import es.uc3m.baldo.opinais.core.Bit;

/**
 * Vectorizer.
 * All vectorizers must implement this interface.
 * 
 * @author Alejandro Baldominos
 */
public interface Vectorizer<T> {

	/**
	 * Vectorizes an individual, i.e., represents the individual
	 * as an array of bits.
	 * @param item the individual to be vectorized.
	 * @return a representation of the input individual as an array of bits.
	 */
	public Bit[] vectorize (T item);
}
