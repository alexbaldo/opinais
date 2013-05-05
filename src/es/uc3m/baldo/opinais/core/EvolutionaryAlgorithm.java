package es.uc3m.baldo.opinais.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.core.detectors.DetectorFactory;
import es.uc3m.baldo.opinais.core.operators.CrossoverOperator;
import es.uc3m.baldo.opinais.core.operators.MutationOperator;
import es.uc3m.baldo.opinais.selectors.RouletteSelector;

public class EvolutionaryAlgorithm {
	
	// Set of individuals.
	private Set<Individual> individuals;
	
	// List of detectors.
	private List<Detector> detectors;
	
	// Properties.
	private int featuresLength;
	private int speciesSize;
	private double typeBias;
	private double generalityBias;
	private double crossoverRate;
	private double mutationRate;
	private double maxGenerations;
	
	/**
	 * Builds a new evolutionary algorithm.
	 * @param speciesSize the size of the population of detectors.
	 */
	public EvolutionaryAlgorithm (int featuresLength, int speciesSize, double typeBias, double generalityBias, 
								  double crossoverRate, double mutationRate, double maxGenerations, 
								  Set<Individual> individuals) {
		this.featuresLength = featuresLength;
		this.speciesSize = speciesSize;
		this.typeBias = typeBias;
		this.generalityBias = generalityBias;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.maxGenerations = maxGenerations;
		this.detectors = new ArrayList<Detector>(speciesSize);
		this.individuals = individuals;
	}
	
	public Map<Type, Detector> run () {
		
		// Creates the initial population.
		initializePopulation();
		
		// Splits the initial population in two lists.
		List<Detector> selfDetectors = filterDetectors(detectors, Type.SELF);
		List<Detector> nonSelfDetectors = filterDetectors(detectors, Type.NON_SELF);

		// Initializes the evolutionary operators.
		CrossoverOperator crossover = new CrossoverOperator(crossoverRate);
		MutationOperator mutation = new MutationOperator(mutationRate);
		
		int generation = 0;
		while (!stop(generation)) {
			// Calculates simple fitness for each detector.
			// Simple fitness is defined as fitness by individual matching,
			//	before any cooperation is taking place.
			calculateFitness(selfDetectors);
			calculateFitness(nonSelfDetectors);
			
			// Evolves the self detectors, cooperating with the best non-self detector.
			for (Detector selfDetector : selfDetectors) {
				calculateFitness(selfDetector, nonSelfDetectors.get(0));
			}
			Collections.sort(selfDetectors);

			// Evolves the non-self detectors, cooperating with the best self detector.
			for (Detector nonSelfDetector : nonSelfDetectors) {
				calculateFitness(nonSelfDetector, selfDetectors.get(0));
			}
			Collections.sort(nonSelfDetectors);
			
			// Generates the new populations.
			RouletteSelector roulette;
			List<Detector> newSelfDetectors = new LinkedList<Detector>();
			roulette = new RouletteSelector(selfDetectors);
			while (newSelfDetectors.size() < selfDetectors.size()) {
				Detector parent1 = roulette.selectDetector();
				Detector parent2 = roulette.selectDetector();
				Detector child = crossover.crossover(parent1, parent2);
				child = mutation.mutate(child);
				newSelfDetectors.add(child);
			}
			
			List<Detector> newNonSelfDetectors = new LinkedList<Detector>();
			roulette = new RouletteSelector(nonSelfDetectors);
			while (newNonSelfDetectors.size() < nonSelfDetectors.size()) {
				Detector parent1 = roulette.selectDetector();
				Detector parent2 = roulette.selectDetector();
				Detector child = crossover.crossover(parent1, parent2);
				child = mutation.mutate(child);
				newNonSelfDetectors.add(child);
			}
			
			// Increases the generations counter.
			generation++;
		}
		
		// Prints the ultimate detectors.
		calculateFitness(selfDetectors);
		calculateFitness(nonSelfDetectors);
		System.out.println("\t" + selfDetectors.get(0));
		System.out.println("\t" + nonSelfDetectors.get(0));
		
		Map<Type, Detector> bestDetectors = new HashMap<Type, Detector>();
		bestDetectors.put(Type.SELF, selfDetectors.get(0));
		bestDetectors.put(Type.NON_SELF, nonSelfDetectors.get(0));
		
		return bestDetectors;
	}
	
	private void calculateFitness (List<Detector> detectors) {
		for (Detector detector : detectors) {
			// Stores the true matches.
			int matches = 0;
			
			// Stores the false positives.
			int mistakes = 0;
			
			// Checks the matching for each individual.
			for (Individual individual : individuals) {
				double matchingPct = detector.match(individual);
				if (matchingPct > detector.decodedThreshold) {
					if (detector.type == individual.type) {
						matches++;
					} else {
						mistakes++;
					}
				}
			}
			
			// Stores the fitness.
			detector.setFitness(matches - mistakes);
		}
		
		Collections.sort(detectors);
	}
	
	private void calculateFitness (Detector detector, Detector foreignDetector) {
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
		
		// Stores the fitness.
		detector.setFitness(matches - mistakes);
	}
	
	private List<Detector> filterDetectors (List<Detector> detectors, Type type) {
		List<Detector> filtered = new LinkedList<Detector>();
		for (Detector detector : detectors) {
			if (detector.type == type) {
				filtered.add(detector);
			}
		}
		
		return filtered;
	}
	
	private boolean stop (int generation) {
		if (this.maxGenerations > 0 && generation > this.maxGenerations) {
			return true;
		}
		return false;
	}
	
	private void initializePopulation () {
		for (int i = 0; i < this.speciesSize; i++) {
			this.detectors.add(DetectorFactory.makeDetector(this.featuresLength, this.typeBias, this.generalityBias));
		}
	}
}
