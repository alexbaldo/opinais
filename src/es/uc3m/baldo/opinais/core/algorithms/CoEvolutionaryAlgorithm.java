package es.uc3m.baldo.opinais.core.algorithms;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.core.operators.CrossoverOperator;
import es.uc3m.baldo.opinais.core.operators.MutationOperator;
import es.uc3m.baldo.opinais.core.types.Type;
import es.uc3m.baldo.opinais.selectors.RouletteSelector;

/**
 * CoEvolutionaryAlgorithm.
 * <p>Implements an evolutionary algorithm.</p>
 * <p>The evolutionary algorithm evolves a population of detectors, based
 * on their fitness, which is calculated as the number of recognized individuals
 * minus the number of false positives.</p>
 * <p>A roulette selector method is used to select individuals. Uniform
 * crossover and bit mutation is implemented to reproduce and mutate
 * detectors.</p>
 * <p>The co-evolutionary algorithm includes a second phase of fitness calculation,
 * where the fitness of all the detectors for a type with the best detectors of
 * the remaining types is evaluated.</p>
 * <p>This way, the algorithm benefits from the cooperation of detectors from
 * different types.</p>
 * 
 * @author Alejandro Baldominos
 */
public class CoEvolutionaryAlgorithm extends EvolutionaryAlgorithm {
		
	/**
	 * <p>Builds a new co-evolutionary algorithm.</p>
	 * @param featuresLength the number of features of each individual.
	 * @param speciesSize the size of the detectors population.
	 * @param typeBias the probability that a randomly created detector
	 * detects self individuals.
	 * @param generalityBias the probability that a bit in a randomly
	 * created detector is a wildcard.
	 * @param crossoverRate the probability that crossover is performed 
	 * over two parent detectors to obtain a child.
	 * @param mutationRate the probability that mutation is performed 
	 * in a bit in the detector schema.
	 * @param maxGenerations the maximum number of generations of the 
	 * algorithm.
	 * @param individuals set containing the individuals.
	 */
	public CoEvolutionaryAlgorithm (int featuresLength, int speciesSize, double typeBias, double generalityBias, 
								    double crossoverRate, double mutationRate, int maxGenerations, 
								    Set<Individual> individuals) {
		super(featuresLength, speciesSize, typeBias, generalityBias, crossoverRate, mutationRate, 
			  maxGenerations, individuals);
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
	}
	
	/**
	 * {@inheritDoc}
	 * <p>The co-evolutionary algorithm includes a second phase of fitness calculation,
	 * where the fitness of all the detectors for a type with the best detectors of
	 * the remaining types is evaluated.</p>
	 * <p>This way, the algorithm benefits from the cooperation of detectors from
	 * different types.</p>
	 * @return a map which maps a type to the best detector found by 
	 * the algorithm for that type.
	 */
	@Override
	public Map<Type, Detector> run () {
		// Creates the initial population.
		initializePopulation();
		
		// Splits the initial population in lists for each type.
		Map<Type, List<Detector>> detectors = new HashMap<Type, List<Detector>>();
		for (Type type : Type.values()) {
			detectors.put(type, filterDetectors(this.detectors, type));
		}

		// Initializes the evolutionary operators.
		RouletteSelector roulette = null;
		CrossoverOperator crossover = new CrossoverOperator(crossoverRate);
		MutationOperator mutation = new MutationOperator(mutationRate);
		
		int generation = 0;
		while (!stop(generation)) {
			// Calculates the fitness for each detector and sorts the lists by 
			// descending fitness.
			for (Type type : Type.values()) {
				for (Detector detector : detectors.get(type)) {
					detector.setFitness(fitness(detector));
				}
				Collections.sort(detectors.get(type));
			}
				
			// Evolves the self detectors, cooperating with the best non-self detector.
			for (Type type : Type.values()) {
				// Initializes the map containing the best detectors for each type.
				Map<Type, Detector> bestDetectors = new HashMap<Type, Detector>();
				for (Type type2: Type.values()) {
					bestDetectors.put(type2, detectors.get(type2).get(0));
				}
				
				// Calculates the cooperative fitness for each detector of this type.
				for (Detector detector : detectors.get(type)) {
					// The detector of this type is replaced with the detector whose
					// fitness is to be evaluated.
					bestDetectors.put(type, detector);
					detector.setFitness(fitness(bestDetectors));
				}
				
				// Sorts the list of detectors by fitness
				Collections.sort(detectors.get(type));
			}
			// Generates the new populations.
			for (Type type : Type.values()) {
				List<Detector> newDetectors = new LinkedList<Detector>();
				roulette = new RouletteSelector(detectors.get(type));
				while (newDetectors.size() < detectors.get(type).size()) {
					newDetectors.add(generateChildDetector(roulette, crossover, mutation));
				}
				
				// Replaces the generations.
				detectors.put(type, newDetectors);
			}

			
			if (maxGenerations >= 100 && generation % (maxGenerations / 100) == 0) {
				System.out.println("\t" + generation / (maxGenerations / 100) + "% completed.");
			}
			
			// Increases the generations counter.
			generation++;
		}
		
		// Calculates the fitness for each detector and sorts the lists by 
		// descending fitness.
		for (Type type : Type.values()) {
			for (Detector detector : detectors.get(type)) {
				detector.setFitness(fitness(detector));
			}
			Collections.sort(detectors.get(type));
		}
		
		// Creates the map with the best detectors.
		Map<Type, Detector> bestDetectors = new HashMap<Type, Detector>();
		for (Type type : Type.values()) {
			bestDetectors.put(type, detectors.get(type).get(0));
			
			// Prints the ultimate detectors.		
			System.out.println("\t" + detectors.get(type).get(0));
		}
		
		return bestDetectors;
	}

	/**
	 * <p>Calculates the cooperative fitness.</p>
	 * <p>This fitness is calculated for a detector of a given type, when
	 * it is asked to cooperate with the best detectors of the remaining types
	 * to guess the type of an individual.</p>
	 * @param detectors the detector whose fitness is to be evaluated and the 
	 * best detectors of the remaining types.
	 * @return the value for the cooperative fitness.
	 */
	private double fitness (Map<Type, Detector> detectors) {
		// Stores the fitness.
		double fitness = 0.0;
		
		// Infers the type of the individual and checks
		// whether it is a hit or a miss.
		for (Individual individual : individuals) {
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
			
			// Checks if the inferred type is a hit or a miss.
			fitness += inferredType == individual.type? 1 : -1;
		}
		
		// Fitness is calculated and normalized to obtain a number in the range [0,1].
		fitness = (fitness + individuals.size()) / (2 * individuals.size());
				
		return fitness;
	}

}
