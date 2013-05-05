package es.uc3m.baldo.opinais.ir.extractors;

import java.util.Set;

public interface FeatureExtractor<T, E> {
	
	public T[] extractFeatures (Set<? extends E> items);

}
