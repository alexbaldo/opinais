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
import es.uc3m.baldo.opinais.selectors.Selector;

/**
 * EvolutionaryAlgorithm.
 * <p>Implements an evolutionary algorithm.</p>
 * <p>The evolutionary algorithm evolves a population of detectors, based
 * on their fitness, which is calculated as the number of recognized individuals
 * minus the number of false positives.</p>
 * <p>A roulette selector method is used to select individuals. Uniform
 * crossover and bit mutation is implemented to reproduce and mutate
 * detectors.</p>
 * TODO The algorithm only works for two types (self and non-self).
 * 
 * @author Alejandro Baldominos
 */
public class EvolutionaryAlgorithm extends AbstractAlgorithm {
	
	/**
	 * <p>The probability that crossover is performed over
	 * two parent detectors to obtain a child.</p>
	 */
	protected double crossoverRate;
	
	/**
	 * <p>The probability that mutation is performed in a bit
	 * in the detector schema.</p>
	 */
	protected double mutationRate;
	
	/**
	 * <p>Builds a new evolutionary algorithm.</p>
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
	public EvolutionaryAlgorithm (int featuresLength, int speciesSize, double typeBias, double generalityBias, 
								  double crossoverRate, double mutationRate, int maxGenerations, 
								  Set<Individual> individuals) {
		super(featuresLength, speciesSize, typeBias, generalityBias, maxGenerations, individuals);
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
	}
	
	/**
	 * <p>Runs the evolutionary algorithm.</p>
	 * <p>The evolutionary algorithm evolves a population of detectors, based
	 * on their fitness, which is calculated as the number of recognized individuals
	 * minus the number of false positives.</p>
	 * <p>A roulette selector method is used to select individuals. Uniform
	 * crossover and bit mutation is implemented to reproduce and mutate
	 * detectors.</p>
	 * @return a map which maps a type to the best detector found by 
	 * the algorithm for that type.
	 */
	@Override
	public Map<Type, Detector> run () {
		// Creates the initial population.
		initializePopulation();
		
		// Splits the initial population in two lists.
		List<Detector> selfDetectors = filterDetectors(detectors, Type.SELF);
		List<Detector> nonSelfDetectors = filterDetectors(detectors, Type.NON_SELF);

		// Initializes the evolutionary operators.
		RouletteSelector roulette = null;
		CrossoverOperator crossover = new CrossoverOperator(crossoverRate);
		MutationOperator mutation = new MutationOperator(mutationRate);
		
		int generation = 0;
		// The algorithm runs while the stop condition is not met.
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
		
		// Prints the ultimate detectors.
		System.out.println("\t" + selfDetectors.get(0));
		System.out.println("\t" + nonSelfDetectors.get(0));
		
		// Creates the map with the best detectors.
		Map<Type, Detector> bestDetectors = new HashMap<Type, Detector>();
		bestDetectors.put(Type.SELF, selfDetectors.get(0));
		bestDetectors.put(Type.NON_SELF, nonSelfDetectors.get(0));
		
		return bestDetectors;
	}
	
	/**
	 * <p>Generates a new child detector.</p>
	 * @param selector a selector which will provide two parent detectors.
	 * @param crossover a crossover operator to cross the two parents into
	 * a child detector.
	 * @param mutator a mutation operator to mutate the child detector.
	 * @return a child detector after crossover and mutation operators are applied.
	 */
	protected Detector generateChildDetector (Selector selector, CrossoverOperator crossover, 
											  MutationOperator mutator) {
		// Selects two parent detectors.
		Detector parent1 = selector.selectDetector();
		Detector parent2 = selector.selectDetector();
		
		// Generates a new child detector using crossover.
		Detector child = crossover.crossover(parent1, parent2);
		
		// Performs mutation over the child detector.
		child = mutator.mutate(child);

		return child;
	}
	
	/**
	 * <p>Calculates the normalized fitness for a detector.</p>
	 * <p>The fitness is calculated as the number of individuals
	 * correctly classified minus the number of false positives.
	 * This way, the fitness function penalizes the cases when the
	 * detector detects the wrong type of individuals.</p>
	 * <p>The fitness is normalized in the range [0,1] to prevent
	 * negative filters, which may cause malfunctions in some selectors,
	 * such as the roulette.</p>
	 * @param detector the detector whose fitness is to be calculated.
	 * @return the detector fitness in the range [0,1].
	 */
	protected double fitness (Detector detector) {
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
		
		// Fitness is calculated and normalized to obtain a number in the range [0,1].
		double fitness = matches - mistakes;
		fitness = (fitness + individuals.size()) / (2 * individuals.size());
		
		return fitness;
	}
				
	/**
	 * <p>Filters a list of detectors, i.e., returns only
	 * those detectors in the list which have the specified
	 * type.</p>
	 * @param detectors a list with all the detectors.
	 * @param type the type of the detectors to be filtered in.
	 * @return a sublist of the original list containing only 
	 * the detectors of the specified type.
	 */
	protected List<Detector> filterDetectors (List<Detector> detectors, Type type) {
		List<Detector> filtered = new LinkedList<Detector>();
		
		// Iterates through all the detectors checking whether they
		// have the specified type.
		for (Detector detector : detectors) {
			if (detector.type == type) {
				filtered.add(detector);
			}
		}
		
		return filtered;
	}
}
