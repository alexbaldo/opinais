package es.uc3m.baldo.opinais.core.operators;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.Type;
import es.uc3m.baldo.opinais.core.detectors.Detector;

/**
 * Mutation
 * <p>Performs mutation by bit-flipping over
 * a detector.</p>
 * 
 * @author Alejandro Baldominos
 *
 */
public class MutationOperator {

	/*
	 *  Mutation rate.
	 */
	private double mutationRate;

	/**
	 * <p>Builds a new mutation operator.</p>
	 * @param mutationRate the mutation rate.
	 */
	public MutationOperator (double mutationRate) {
		this.mutationRate = mutationRate;
	}
	
	/**
	 * <p>Returns a mutated version of the detector, based 
	 * on random bit-flipping for each bit.</p>
	 * @param detector the detector to be mutated.
	 * @return the mutated detector.
	 */
	public Detector mutate (Detector detector) {
		// Actually, both detectors must have the same type.
		Type type = detector.type;
		
		// Stores the features for the new detector.
		Bit[] threshold = new Bit[detector.threshold.length];
		Bit[] pattern = new Bit[detector.pattern.length];
		Bit[] mask = new Bit[detector.pattern.length];
		
		// Mutates the threshold.
		for (int i = 0; i < threshold.length; i++) {
			if (Math.random() < mutationRate) {
				threshold[i] = detector.threshold[i] == Bit.ZERO? Bit.ONE : Bit.ZERO;
			} else {
				threshold[i] = detector.threshold[i];
			}
		}
		
		// Mutates the pattern.
		for (int i = 0; i < pattern.length; i++) {
			if (Math.random() < mutationRate) {
				pattern[i] = detector.pattern[i] == Bit.ZERO? Bit.ONE : Bit.ZERO;
			} else {
				pattern[i] = detector.pattern[i];
			}
		}
		
		// Mutates the mask.
		for (int i = 0; i < mask.length; i++) {
			if (Math.random() < mutationRate) {
				mask[i] = detector.mask[i] == Bit.ZERO? Bit.ONE : Bit.ZERO;
			} else {
				mask[i] = detector.mask[i];
			}
		}
		
		return new Detector(type, threshold, pattern, mask);
	}
}
