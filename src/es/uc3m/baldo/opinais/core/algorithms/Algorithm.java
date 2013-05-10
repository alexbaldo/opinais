package es.uc3m.baldo.opinais.core.algorithms;

import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.core.types.Type;

/**
 * Algorithm.
 * <p>All algorithms must implement this interface.</p>
 *
 * @author Alejandro Baldominos
 */
public interface Algorithm {

	/**
	 * <p>Runs the AIS-based algorithm to retrieve
	 * a detector for each possible type.</p>
	 * @return a map which maps a type to the best
	 * detector found by the algorithm for that type.
	 */
	public Map<Type, Detector> run ();
	
	/**
	 * <p>Sets the training individuals.</p>
	 * @param individuals set containing the individuals.
	 */
	public void setIndividuals (Set<Individual> individuals);
}
