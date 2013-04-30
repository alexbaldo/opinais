package es.uc3m.baldo.opinais.core.operators;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.Type;
import es.uc3m.baldo.opinais.core.detectors.Detector;

/**
 * Crossover
 * Perform uniform crossover of two detectors
 * 	to obtain a their child.
 * 
 * @author Alejandro Baldominos
 *
 */
public class Crossover {
	
	/**
	 * Returns a child of the two parents.
	 * @param detector1 the first parent.
	 * @param detector2 the second parent.
	 * @return the child detector.
	 */
	public Detector crossover (Detector detector1, Detector detector2) {
		
		Type type = detector1.type;
		Bit[] threshold = new Bit[detector1.threshold.length];
		Bit[] pattern = new Bit[detector1.pattern.length];
		Bit[] mask = new Bit[detector1.pattern.length];
		
		// Crosses threshold.
		for (int i = 0; i < threshold.length; i++) {
			Detector parent = Math.random() < 0.5? detector1 : detector2;
			threshold[i] = parent.threshold[i];
		}
		
		// Crosses pattern.
		for (int i = 0; i < pattern.length; i++) {
			Detector parent = Math.random() < 0.5? detector1 : detector2;
			pattern[i] = parent.pattern[i];
		}
		
		// Crosses mask.
		for (int i = 0; i < mask.length; i++) {
			Detector parent = Math.random() < 0.5? detector1 : detector2;
			mask[i] = parent.mask[i];
		}
		
		return new Detector(type, threshold, pattern, mask);
	}
}
