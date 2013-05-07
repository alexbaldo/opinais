package es.uc3m.baldo.opinais.core.operators;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.Type;
import es.uc3m.baldo.opinais.core.detectors.Detector;

/**
 * CrossoverOperator
 * <p>Performs uniform crossover of two detectors
 * to obtain their child.</p>
 * <p>In uniform crossover, each bit in the child
 * detector is randomly selected between the bits
 * located in the same position for their parents.</p>
 * 
 * @author Alejandro Baldominos
 *
 */
public class CrossoverOperator {
	
	/*
	 *  Crossover rate.
	 *  
	 */
	private double crossoverRate;
	
	/**
	 * <p>Builds a new crossover operator.</p>
	 * @param crossoverRate the crossover rate.
	 */
	public CrossoverOperator (double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}
	
	/**
	 * <p>Returns a child of the two parents.</p>
	 * @param detector1 the first parent.
	 * @param detector2 the second parent.
	 * @return the child detector.
	 */
	public Detector crossover (Detector detector1, Detector detector2) {
		
		// Checks whether crossover is actually performed.
		// If crossover were not performed, then one of the
		// parents were randomly returned.
		if (Math.random() < crossoverRate) {
			// Actually, both detectors must have the same type.
			Type type = detector1.type;
			
			// Stores the features for the new detector.
			Bit[] threshold = new Bit[detector1.threshold.length];
			Bit[] pattern = new Bit[detector1.pattern.length];
			Bit[] mask = new Bit[detector1.pattern.length];
			
			// Crosses the threshold.
			for (int i = 0; i < threshold.length; i++) {
				Detector parent = Math.random() < 0.5? detector1 : detector2;
				threshold[i] = parent.threshold[i];
			}
			
			// Crosses the pattern.
			for (int i = 0; i < pattern.length; i++) {
				Detector parent = Math.random() < 0.5? detector1 : detector2;
				pattern[i] = parent.pattern[i];
			}
			
			// Crosses the mask.
			for (int i = 0; i < mask.length; i++) {
				Detector parent = Math.random() < 0.5? detector1 : detector2;
				mask[i] = parent.mask[i];
			}
			
			return new Detector(type, threshold, pattern, mask);
		} else {
			// One of the two parents is returned randomly (crossover not performed).
			return Math.random() < 0.5? detector1.clone() : detector2.clone();
		}
	}
}
