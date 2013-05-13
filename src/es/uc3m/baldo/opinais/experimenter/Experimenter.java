package es.uc3m.baldo.opinais.experimenter;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.core.types.Type;

/**
 * Experimenter.
 * <p>Provides useful methods for carrying out experiments over
 * a set of individuals. In particular, this class has the
 * responsibility of generating a training and test set from the
 * input data, as well as calculating the hit rate of the detectors
 * over a set of individuals.</p>
 *
 * @author Alejandro Baldominos
 */
public class Experimenter {

	/*
	 * Training set, used for the learning phase of
	 * the Artificial Immune System.
	 */
	private Set<Individual> trainingSet;
	
	/*
	 * Test set, used for checking the generalization
	 * power of the evolved detectors.
	 */
	private Set<Individual> testSet;
	
	/**
	 * <p>Constructs a new experimenter.</p>
	 * @param individuals the complete set of individuals.
	 * @param pctTest the percentage (expressed as a fraction)
	 * of individuals which must be placed in the test set. The
	 * remaining individuals <em>(1 - pctTest)</em> will be
	 * placed in the training test.
	 */
	public Experimenter (Set<Individual> individuals, double pctTest) {
		trainingSet = new HashSet<Individual>();
		testSet = new HashSet<Individual>();
		
		// Calculates the actual number of individuals to be placed
		// in the test set.
		int testIndividuals = (int) (pctTest * individuals.size());
		
		// Randomizes the position of each individual. To ease this task,
		// the set is turned into a list.
		List<Individual> allIndividuals = new LinkedList<Individual>(individuals);
		Collections.shuffle(allIndividuals);
		
		// The test individuals are chosen.
		for (int i = 0; i < testIndividuals; i++) {
			testSet.add(allIndividuals.get(i));
		}

		// The training individuals are chosen.
		for (int i = testIndividuals; i < allIndividuals.size(); i++) {
			trainingSet.add(allIndividuals.get(i));
		}
	}
	
	/**
	 * <p>Returns the training set.</p>
	 * @return the training set.
	 */
	public Set<Individual> getTrainingSet () {
		return this.trainingSet;
	}
	
	/**
	 * <p>Returns the test set.</p>
	 * @return the test set.
	 */
	public Set<Individual> getTestSet () {
		return this.testSet;
	}
	
	/**
	 * <p>Retrieves the confusion matrix for the inference provided by the best
	 * detectors over each type of individual.</p>
	 * @param bestDetectors the best detector for each possible type.
	 * @param individuals the set of individuals.
	 * @return the hit ratio.
	 */
	public Map<Type,Map<Type, Integer>> getConfusionMatrix (Map<Type, Detector> bestDetectors, Set<Individual> individuals) {
		// Initializes the confusion matrix.
		Map<Type,Map<Type, Integer>> matrix = new HashMap<Type, Map<Type, Integer>>();
		for (Type realType : Type.values()) {
			matrix.put(realType, new HashMap<Type, Integer>());
			// Stores the unclassified instances.
			matrix.get(realType).put(null, 0);
			for (Type inferredType : Type.values()) {
				matrix.get(realType).put(inferredType, 0);
			}
		}

		// Iterates through every individual.
		for (Individual individual : individuals) {
			// Infers the type.
			Type inferredType = inferType(bestDetectors, individual);

			// Increases the counter for the Real-Inferred matrix.
			Map<Type, Integer> row = matrix.get(individual.type);
			row.put(inferredType, row.get(inferredType) + 1);
		}
		
		return matrix;
	}
	
	/**
	 * <p>Prints the confusion manner in a readable way.</p>
	 * @param confusionMatrix the confusion matrix to be printed.
	 */
	public void printConfusionMatrix (Map<Type,Map<Type, Integer>> confusionMatrix) {
		// Retrieves the maximum type name size.
		int maxSize = 0;
		for (Type type : Type.values()) {
			maxSize = Math.max(maxSize, type.name().length());
		}
		
		// Prints the header.
		System.out.print("\t" + StringUtils.repeat(' ', maxSize) + "\t");
		for (Type type : Type.values()) {
			System.out.print(type + "\t");
		}
		System.out.println("Unclassified" + " | <- classified as");
		System.out.print("\t" + StringUtils.repeat(' ', maxSize) + "\t");
		for (Type type : Type.values()) {
			System.out.print(StringUtils.repeat('-', type.name().length()) + "\t");
		}
		System.out.println(StringUtils.repeat('-', "Unclassified".length()));
		
		// Prints the matrix.
		for (Type realType : Type.values()) {
			System.out.print("\t" + realType.name() + StringUtils.repeat(' ', maxSize - realType.name().length()) + "\t");
			for (Type inferredType : Type.values()) {
				String value = String.valueOf(confusionMatrix.get(realType).get(inferredType));
				System.out.print(value + StringUtils.repeat(' ', inferredType.name().length() - value.length()) + "\t");
			}
			String value = String.valueOf(confusionMatrix.get(realType).get(null));
			System.out.println(value);			
		}
	}
	
	/**
	 * <p>Calculates the hit rate of the best detectors for each type for a set
	 * of individuals.</p>
	 * @param confusionMatrix the confusion matrix.
	 * @return the hit ratio.
	 */
	public double calculateHitRate (Map<Type,Map<Type, Integer>> confusionMatrix) {
		// Stores the number of hits.
		int hits = 0;
		
		// Stores the total number of individuals.
		int individuals = 0;
		
		// Iterates through the confusion matrix.
		for (Type realType : Type.values()) {
			hits += confusionMatrix.get(realType).get(realType);
			for (Type inferredType : confusionMatrix.get(realType).keySet()) {
					individuals += confusionMatrix.get(realType).get(inferredType);
			}
		}
		
		// The hit rate is calculated as the ratio between the total number
		// of hits and the number of individuals.
		return (double) hits / individuals;
	}
	
	/**
	 * <p>Infers the type of an individual given the best
	 * detector for each type.</p>
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
	 * @param bestDetectors the best detector for each possible type.
	 * @param individual the individual whose type is to be inferred.
	 * @return the inferred type of the individual.
	 */
	public Type inferType (Map<Type, Detector> bestDetectors, Individual individual) {
		// Stores the inferred type.
		Type inferredType = null;
		
		// Stores the maximum valid matching ratio.
		double highestMatch = 0.0;
		
		// Iterates through all possible types.
		for (Type type : bestDetectors.keySet()) {
			// Calculates the matching ratio with the best detector.			
			Detector detector = bestDetectors.get(type);
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
