package es.uc3m.baldo.opinais.core.detectors;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.types.Type;

/**
 * DetectorFactory.
 * <p>Makes a new random detector object based on two probabilistic
 * parameters.</p>
 * <p>The first value is known as <em>generality bias</em> and represents
 * the probability that the next Bit is a wildcard.</p>
 * <p>The second value is known as <em>type bias</em> and represents
 * the probability that the detector is a Self detector.</p>
 * 
 * @author Alejandro Baldominos
 *
 */
public class DetectorFactory {

	/**
	 * <p>Makes a new random detector object.</p>
	 * @param length the length of the detector schema.
	 * @param typeBias the probability that the detector is a Self detector.
	 * @param generalityBias the probability that the next Bit is a wildcard.
	 * @return a new randomly generated detector.
	 */
	public static Detector makeDetector (int length, double typeBias, double generalityBias) {
		// Extracts the detector type.
		int random = (int) (Math.random() * Type.values().length);
		Type type = Type.values()[random];
		
		// Generates the threshold, represented
		// as an 8-bit string in Gray encoding.
		Bit[] threshold = new Bit[8];
		for (int i = 0; i < threshold.length; i++) {
			threshold[i] = Math.random() < 0.5? Bit.ZERO : Bit.ONE;
		}
		
		// Generates the schema bit-by-bit, represented
		// as a combination of a pattern and a mask.
		Bit[] pattern = new Bit[length];
		Bit[] mask = new Bit[length];
		for (int i = 0; i < length; i++) {
			// Extracts the next bit in the schema.
			pattern[i] = Math.random() < 0.5? Bit.ZERO : Bit.ONE;
			mask[i] =  (Math.random() < generalityBias)? Bit.ONE : Bit.ZERO;
		}
		
		return new Detector(type, threshold, pattern, mask);
	}
	
}
