package es.uc3m.baldo.opinais.selectors;

import es.uc3m.baldo.opinais.core.detectors.Detector;

/**
 * Selector.
 * <p>All selectors must implement this interface.</p>
 * 
 * @author Alejandro Baldominos
 */
public interface Selector {
	
	/**
	 * <p>Selects a detector from the population.</p>
	 * @return the selected detector.
	 */
	public Detector selectDetector ();
}
