package es.uc3m.baldo.opinais.core.detectors;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.Type;

/**
 * DetectorFactory
 * Makes a new detector object based on two probabilistic
 * 	parameters.
 * The first value is known as "generality bias" and represents
 * 	the probability that the next Bit is a wildcard.
 * The second value is known as "type bias" and represents
 *	the probability that the detector is a Self detector.
 * 
 * @author Alejandro Baldominos
 *
 */
public class DetectorFactory {

	public static Detector makeDetector (int length, double typeBias, double generalityBias) {
		// Extracts the detector type.
		Type type = Math.random() < typeBias? Type.SELF : Type.NON_SELF;
		
		// Generates the threshold, represented
		//	as an 8-bit string in Gray encoding.
		Bit[] threshold = new Bit[8];
		for (int i = 0; i < threshold.length; i++) {
			threshold[i] = Math.random() < 0.5? Bit.ZERO : Bit.ONE;
		}
		
		// Generates the schema bit-by-bit, represented
		//	as a combination of a pattern and a mask.
		Bit[] pattern = new Bit[length];
		Bit[] mask = new Bit[length];
		for (int i = 0; i < length; i++) {
			// Extracts the next pattern bit.
			pattern[i] = Math.random() < 0.5? Bit.ZERO : Bit.ONE;
			mask[i] =  (Math.random() < generalityBias)? Bit.ONE : Bit.ZERO;
		}
		
		return new Detector(type, threshold, pattern, mask);
	}
	
}
