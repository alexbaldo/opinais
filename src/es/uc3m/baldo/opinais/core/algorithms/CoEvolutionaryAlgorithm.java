package es.uc3m.baldo.opinais.core.algorithms;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.Type;
import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.core.operators.CrossoverOperator;
import es.uc3m.baldo.opinais.core.operators.MutationOperator;
import es.uc3m.baldo.opinais.selectors.RouletteSelector;

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
	
	@Override
	public Map<Type, Detector> run () {
		// Creates the initial population.
		initializePopulation();
		
		// Splits the initial population in two lists.
		RouletteSelector roulette = null;
		List<Detector> selfDetectors = filterDetectors(detectors, Type.SELF);
		List<Detector> nonSelfDetectors = filterDetectors(detectors, Type.NON_SELF);

		// Initializes the evolutionary operators.
		CrossoverOperator crossover = new CrossoverOperator(crossoverRate);
		MutationOperator mutation = new MutationOperator(mutationRate);
		
		int generation = 0;
		while (!stop(generation)) {
			// Calculates the fitness for each detector and
			// sorts the lists by descending fitness.
			for (Detector detector : selfDetectors) {
				detector.setFitness(fitness(detector));
			}
			Collections.sort(selfDetectors);
			for (Detector detector : nonSelfDetectors) {
				detector.setFitness(fitness(detector));
			}
			Collections.sort(nonSelfDetectors);
						
			// Evolves the self detectors, cooperating with the best non-self detector.
			for (Detector selfDetector : selfDetectors) {
				selfDetector.setFitness(fitness(selfDetector, nonSelfDetectors.get(0)));
			}
			Collections.sort(selfDetectors);

			// Evolves the non-self detectors, cooperating with the best self detector.
			for (Detector nonSelfDetector : nonSelfDetectors) {
				nonSelfDetector.setFitness(fitness(nonSelfDetector, selfDetectors.get(0)));
			}
			Collections.sort(nonSelfDetectors);
			
			// Generates the new populations.
			List<Detector> newSelfDetectors = new LinkedList<Detector>();
			List<Detector> newNonSelfDetectors = new LinkedList<Detector>();
			roulette = new RouletteSelector(selfDetectors);
			while (newSelfDetectors.size() < selfDetectors.size()) {
				newSelfDetectors.add(generateChildDetector(roulette, crossover, mutation));
			}
			roulette = new RouletteSelector(nonSelfDetectors);
			while (newNonSelfDetectors.size() < nonSelfDetectors.size()) {
				newNonSelfDetectors.add(generateChildDetector(roulette, crossover, mutation));
			}
			
			if (generation % (maxGenerations / 100) == 0) {
				System.out.println("\t" + generation / (maxGenerations / 100) + "% completed.");
			}
				
			// Replaces the generations.
			selfDetectors = newSelfDetectors;
			nonSelfDetectors = newNonSelfDetectors;
			
			// Increases the generations counter.
			generation++;
		}
		
		// Prints the ultimate detectors.
		for (Detector detector : selfDetectors) {
			detector.setFitness(fitness(detector));
		}
		Collections.sort(selfDetectors);
		for (Detector detector : nonSelfDetectors) {
			detector.setFitness(fitness(detector));
		}
		Collections.sort(nonSelfDetectors);
		
		// Prints the ultimate detectors.
		System.out.println("\t" + selfDetectors.get(0));
		System.out.println("\t" + nonSelfDetectors.get(0));
		
		// Creates the map with the best detectors.
		Map<Type, Detector> bestDetectors = new HashMap<Type, Detector>();
		bestDetectors.put(Type.SELF, selfDetectors.get(0));
		bestDetectors.put(Type.NON_SELF, nonSelfDetectors.get(0));
		
		return bestDetectors;
	}

	private double fitness (Detector detector, Detector foreignDetector) {
		// Stores the true matches.
		int matches = 0;
		
		// Stores the false positives.
		int mistakes = 0;
		
		// Checks the matching for each individual.
		for (Individual individual : individuals) {
			double matchingPct = detector.match(individual);
			double matchingPctForeign = foreignDetector.match(individual);
			if (matchingPct > matchingPctForeign && matchingPct > detector.decodedThreshold) {
				if (detector.type == individual.type) {
					matches++;
				} else {
					mistakes++;
				}
			} else if (matchingPctForeign > matchingPct && matchingPctForeign > foreignDetector.decodedThreshold) {
				if (foreignDetector.type == individual.type) {
					matches++;
				} else {
					mistakes++;
				}
			}
		}
		
		// Fitness is calculated and normalized to obtain a number in the range [0,1].
		double fitness = matches - mistakes;
		fitness = (fitness + individuals.size()) / (2 * individuals.size());
				
		return fitness;
	}

}
