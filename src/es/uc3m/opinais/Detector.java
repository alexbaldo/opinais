package es.uc3m.opinais;

import es.uc3m.opinais.utils.GrayCode;

/**
 * Detector
 * Represents a detector in the Artificial Immune
 * 	System. The detector is specialized in detecting
 * 	either self or nonself individuals, and is
 * 	represented by an schema.
 * 
 * @author Alejandro Baldominos
 *
 */
public class Detector {

	// Detector type.
	private Type type;
	
	// Threshold, represented in Gray code.
	private Bit[] threshold;
	private double decodedThreshold;
	
	// Detector schema, represented as pattern and mask.
	private Bit[] pattern;
	private Bit[] mask;

	/**
	 * Builds a new Detector instance.
	 * @param type the detector type (either SELF or NON_SELF).
	 * @param threshold a number in Gray encoding, so that 
	 * 	<i>threshold</i> / 255 represents the minimum percentage 
	 * 	of bit matches between the schema and the individual
	 * 	to consider a match.
	 * @param pattern an array of bits representing the pattern.
	 * @param mask an array of bits, containing a 1 in those positions
	 * 	which are considered as wildcards.
	 */
	public Detector (Type type, Bit[] threshold, Bit[] pattern, Bit[] mask) {
		this.type = type;
		this.threshold = new Bit[threshold.length];
		System.arraycopy(threshold, 0, this.threshold, 0, threshold.length);
		this.decodedThreshold = decodeThreshold(this.threshold);
		this.pattern = new Bit[pattern.length];
		System.arraycopy(pattern, 0, this.pattern, 0, pattern.length);
		this.mask = new Bit[mask.length];
		System.arraycopy(mask, 0, this.mask, 0, mask.length);
	}
	
	/**
	 * Checks whether the detector actually detects the individual.
	 * 	This will happen when the percentage of bit matches (ignoring
	 * 	wildcards) exceeds the threshold.
	 * @param individual the individual which is tried to be matched.
	 * @return true if the detector matches the individual,
	 * 	false otherwise.
	 */
	public boolean match (Individual individual) {
		// Stores the number of bit matches.
		int matches = 0;
		
		// Stores the total number of comparisons.
		int comparisons = 0;
		
		// Checks the schema and individual bit-by-bit.
		for (int i = 0; i < pattern.length; i++) {
			if (mask[i] == Bit.ZERO) {
				if (pattern[i] == individual.bit(i)) {
					matches++;
				}
				comparisons++;			
			}
		}
				
		double matchPct = (double) matches/comparisons;
		return matchPct > decodedThreshold;
	}
	
	private double decodeThreshold (Bit[] threshold) {
		long number = 0;
		for (int i = 0; i < threshold.length; i++) {
			number += (threshold[threshold.length-i-1] == Bit.ZERO? 0 : 1) * Math.pow(10, i);
		}
		
		return (double) GrayCode.decodeGray(number) / 255;
	}
}
