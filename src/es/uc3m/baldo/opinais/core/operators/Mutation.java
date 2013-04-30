package es.uc3m.baldo.opinais.core.operators;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.Type;
import es.uc3m.baldo.opinais.core.detectors.Detector;

/**
 * Mutation
 * Perform mutation by bit-flipping over
 * 	a detector.
 * 
 * @author Alejandro Baldominos
 *
 */
public class Mutation {

	// Mutation rate.
	private double mutationRate;

	/**
	 * Builds a new Mutation instance.
	 * @param mutationRate the mutation rate.
	 */
	public Mutation (double mutationRate) {
		this.mutationRate = mutationRate;
	}
	
	/**
	 * Returns a mutated version of the detector, based on bit-flipping.
	 * @param detector the detector to be mutated.
	 * @return the mutated detector.
	 */
	public Detector mutate (Detector detector) {
		
		Type type = detector.type;
		Bit[] threshold = new Bit[detector.threshold.length];
		Bit[] pattern = new Bit[detector.pattern.length];
		Bit[] mask = new Bit[detector.pattern.length];
		
		// Mutates threshold.
		for (int i = 0; i < threshold.length; i++) {
			if (Math.random() < mutationRate) {
				threshold[i] = detector.threshold[i] == Bit.ZERO? Bit.ONE : Bit.ZERO;
			} else {
				threshold[i] = detector.threshold[i];
			}
		}
		
		// Mutates pattern.
		for (int i = 0; i < pattern.length; i++) {
			if (Math.random() < mutationRate) {
				pattern[i] = detector.pattern[i] == Bit.ZERO? Bit.ONE : Bit.ZERO;
			} else {
				pattern[i] = detector.pattern[i];
			}
		}
		
		// Mutates mask.
		for (int i = 0; i < threshold.length; i++) {
			if (Math.random() < mutationRate) {
				pattern[i] = detector.pattern[i] == Bit.ZERO? Bit.ONE : Bit.ZERO;
			} else {
				pattern[i] = detector.pattern[i];
			}
		}
		
		return new Detector(type, threshold, pattern, mask);
	}
}
