package es.uc3m.baldo.opinais.ir.extractors;

import java.util.Set;

/**
 * FeaturesExtractor.
 * <p>All features extractors must implement this interface.</p>
 * 
 * @param <T> the type of values stored by the feature.
 * @param <E> the type of the items whose features are to
 * be extracted.
 * 
 * @author Alejandro Baldominos
 */
public interface FeaturesExtractor<T, E> {
	
	/**
	 * <p>Extracts the features from a set of items and returns them
	 * in an array, as ordering is important for a further vectorization.</p>
	 * @param the set of items needed to extract the features.
	 * @return a sorted array with the features.
	 */
	public T[] extractFeatures (Set<? extends E> items);

}
