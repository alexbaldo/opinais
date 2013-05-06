package es.uc3m.baldo.opinais.core.detectors;

import java.util.Arrays;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.Type;

/**
 * Detector
 * <p>Represents a detector in the Artificial Immune
 * System. The detector is specialized in detecting
 * either self or nonself individuals, and is
 * represented by an schema.</p>
 * 
 * @author Alejandro Baldominos
 */
public class Detector implements Comparable<Detector> {

	/*
	 *  Detector type.
	 */
	public Type type;
	
	/*
	 *  Threshold, represented in Gray code.
	 */
	public Bit[] threshold;
	public double decodedThreshold;
	
	/*
	 *  Detector schema, represented as pattern and mask.
	 */
	public Bit[] pattern;
	public Bit[] mask;
	
	/*
	 *  Fitness
	 */
	private double fitness;

	/**
	 * <p>Builds a new Detector instance.</p>
	 * @param type the detector type (either SELF or NON_SELF).
	 * @param threshold a number in Gray encoding, so that 
	 * <em>threshold</em> / 255 represents the minimum percentage 
	 * of bit matches between the schema and the individual
	 * to consider a match.
	 * @param pattern an array of bits representing the pattern.
	 * @param mask an array of bits, containing a 1 in those positions
	 * 	which are considered as wildcards.
	 */
	public Detector (Type type, Bit[] threshold, Bit[] pattern, Bit[] mask) {
		this.type = type;
		this.threshold = new Bit[threshold.length];
		System.arraycopy(threshold, 0, this.threshold, 0, threshold.length);
		this.decodedThreshold = decodeThreshold();
		this.pattern = new Bit[pattern.length];
		System.arraycopy(pattern, 0, this.pattern, 0, pattern.length);
		this.mask = new Bit[mask.length];
		System.arraycopy(mask, 0, this.mask, 0, mask.length);
	}
		
	/**
	 * <p>Checks whether the detector actually detects the individual.</p>
	 * <p>This will happen when the percentage of bit matches (ignoring
	 * wildcards) exceeds the threshold.</p>
	 * @param individual the individual which is tried to be matched.
	 * @return true if the detector matches the individual,
	 * false otherwise.
	 */
	public double match (Individual individual) {
		// Stores the number of bit matches.
		int matches = 0;
		
		// Stores the total number of comparisons.
		int comparisons = 0;
		
		// Checks the schema and individual bit-by-bit.
		for (int i = 0; i < pattern.length; i++) {
			if (mask[i] == Bit.ZERO) {
				if (pattern[i] == individual.bits[i]) {
					matches++;
				}
				comparisons++;			
			}
		}
				
		double matchPct = (double) matches/comparisons;
		return matchPct;
	}

	private double decodeThreshold () {
		Bit[] binary = new Bit[threshold.length];
		binary[0] = threshold[0];
		for (int i = 1; i < binary.length; i++) {
			binary[i] = threshold[i].xor(binary[i-1]);
		}

		double number = 0;
		for (int i = threshold.length-1; i >= 0; i--) {
			number += binary[i] == Bit.ONE? Math.pow(2, threshold.length - i - 1) : 0;
		}		

		return number / 255;
	}
	
	public void setFitness (double fitness) {
		this.fitness = fitness;
	}
	
	public double getFitness () {
		return fitness;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo (Detector o) {
		return Double.compare(o.fitness, this.fitness);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Detector other = (Detector) obj;
		if (Double.doubleToLongBits(decodedThreshold) != Double
				.doubleToLongBits(other.decodedThreshold))
			return false;
		if (Double.doubleToLongBits(fitness) != Double
				.doubleToLongBits(other.fitness))
			return false;
		if (!Arrays.equals(mask, other.mask))
			return false;
		if (!Arrays.equals(pattern, other.pattern))
			return false;
		if (!Arrays.equals(threshold, other.threshold))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(decodedThreshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fitness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Arrays.hashCode(mask);
		result = prime * result + Arrays.hashCode(pattern);
		result = prime * result + Arrays.hashCode(threshold);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		char[] schema = new char[pattern.length];
		for (int i = 0; i < schema.length; i++) {
			schema[i] = mask[i] == Bit.ONE? '#' : pattern[i].toString().charAt(0);
		}
		return "Detector [type=" + type + ", threshold="
				+ decodedThreshold + ", schema=" + Arrays.toString(schema)
				+ ", fitness=" + fitness
				+ "]";
	}
}
