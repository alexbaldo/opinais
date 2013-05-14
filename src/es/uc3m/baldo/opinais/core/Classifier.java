package es.uc3m.baldo.opinais.core;

import java.util.Map;

import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.core.types.Type;

/**
 * Classifier.
 * <p>Represents a classifier, which consists on the set of best
 * detectors for each type found by a classification algorithm.</p>
 * <p>This class has the ability of classifying a individual 
 * (i.e., inferring its type).</p>
 * 
 * @author Alejandro Baldominos
 */
public class Classifier {

	/*
	 * A classifier is defined by a set of detectors, one for each
	 * possible type.
	 */
	private Map<Type, Detector> detectors;
	
	/**
	 * <p>Builds a new classifier.</p>
	 * @param detectors the best detectors for each type, found by
	 * a classification algorith.
	 */
	public Classifier (Map<Type, Detector> detectors) {
		this.detectors = detectors;
	}
	
	/**
	 * <p>Infers the type of an individual given a classifier composed of
	 * the best detector for each type.</p>
	 * <p>In order to infer the type, each detector is tried to
	 * match the individual. The inferred type is the type of that
	 * detector whose matching ratio is valid and exceeds the 
	 * valid matching ratios of the remaining detectors.</p>
	 * <p>For a matching ratio to be considered valid, it must
	 * exceed the detector threshold. Non-valid matching ratios
	 * are ignored.</p>
	 * <p>Notice that this inference method may leave unclassified
	 * individuals, as long as any detector produces a valid matching
	 * ratio.</p>
	 * 
	 * @param individual the individual whose type is to be inferred.
	 * @return the inferred type of the individual.
	 */
	public Type classify (Individual individual) {
		// Stores the inferred type.
		Type inferredType = null;
		
		// Stores the maximum valid matching ratio.
		double highestMatch = 0.0;
		
		// Iterates through all possible types.
		for (Type type : detectors.keySet()) {
			// Calculates the matching ratio with the best detector.			
			Detector detector = detectors.get(type);
			double match = detector.match(individual);
			
			// This type would be the best type so far if its matching ratio
			// is valid and exceeds the previous best valid matching ratio.
			if (match > highestMatch && match > detector.decodedThreshold) {
				highestMatch = match;
				inferredType = type;
			}
		}
		
		return inferredType;
	}
}
