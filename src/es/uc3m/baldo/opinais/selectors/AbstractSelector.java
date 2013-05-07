package es.uc3m.baldo.opinais.selectors;

import java.util.Collection;

import es.uc3m.baldo.opinais.core.detectors.Detector;

/**
 * AbstractSelector.
 * <p>Provides an abstract implementation of a selector.</p>
 * <p>While this class does not actually implement the
 * <em>select</em> functionality, it exists for commodity
 * purposes, as most selectors will make their selection from
 * a collection of detectors. This class receives this collection
 * in the constructor.</p>
 * @author Alejandro Baldominos
 */
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
	 * <p>This method must be overriden and implemented by a 
	 * child class.</p>
	 * @return the selected detector.
	 */
	@Override
	public abstract Detector selectDetector ();
}
