package es.uc3m.baldo.opinais.selectors;

import java.util.Collection;

import es.uc3m.baldo.opinais.core.detectors.Detector;

public abstract class AbstractSelector implements Selector {

	/**
	 * The list of detectors, sorted by descending fitness.
	 */
	protected Collection<Detector> population;
	
	/**
	 * <p>Builds a new abstract selector.</p>
	 * @param population the list of detectors.
	 */
	public AbstractSelector (Collection<Detector> population) {
		this.population = population;
	}
	
	/**
	 * <p>Selects a detector from the population.</p>
	 * <p>This method must be implemented by a child class.</p>
	 * @return the selected detector.
	 */
	public abstract Detector selectDetector ();
}
