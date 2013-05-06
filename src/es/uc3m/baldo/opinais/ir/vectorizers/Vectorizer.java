package es.uc3m.baldo.opinais.ir.vectorizers;

import es.uc3m.baldo.opinais.core.Bit;

/**
 * Vectorizer.
 * <p>All vectorizers must implement this interface.</p>
 * 
 * @param T type of item to be vectorized to a genome.
 * 
 * @author Alejandro Baldominos
 */
public interface Vectorizer<T> {

	/**
	 * <p>Vectorizes an individual, i.e., represents the individual
	 * as a genome (i.e., an array of bits.)</p>
	 * @param item the individual to be vectorized.
	 * @return a representation of the input individual as an array of bits.
	 */
	public Bit[] vectorize (T item);
}
