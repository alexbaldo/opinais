package es.uc3m.baldo.opinais.selectors;

import java.util.List;

import es.uc3m.baldo.opinais.core.detectors.Detector;

/**
 * RouletteSelector.
 * Selects one individual from the population randomly, but 
 * in a fitness-proportional way.
 * 
 * @author Alejandro Baldominos
 */
public class RouletteSelector {

	/*
	 * The list of detectors, sorted by descending fitness.
	 */
	private List<Detector> population;
	
	/*
	 * The sum of fitness for all the detectors.
	 */
	private double totalCumulativeFitness;
	
	/**
	 * Builds a new roulette selector.
	 * @param population the list of detectors, which must be sorted by descending fitness.
	 */
	public RouletteSelector (List<Detector> population) {
		this.population = population;
		this.totalCumulativeFitness = calculateTotalCumulativeFitness(population);
	}
	
	/**
	 * Selects a detector from the population. This selection is performed
	 * randomly, but preferring those detectors which have a higher fitness
	 * value.
	 * @return the selected detector.
	 */
	public Detector selectDetector () {
		// Generates a random number between zero and the total sum of fitness.
		double rand = Math.random() * totalCumulativeFitness;
		
		// Stores the cumulative fitness.
		double cumulative = 0;
		
		// Iterates over each detector.
		for (Detector detector : population) {
			cumulative += detector.getFitness();
			
			// If the cumulative fitness is already higher than
			// the random number, then this is the selected detector.
			if (rand <= cumulative) {
				return detector;
			}
		}
		
		// This case is actually impossible, but to prevent the warning,
		// in the (theoretical rather than practical) case that a detector
		// was not returned before, the last one in the list is returned.
		return population.get(population.size() - 1);		
	}
	
	/**
	 * Calculates the total sum of fitness of all detectors.
	 * @param population a list of detectors.
	 * @return the sum of all fitness for all detectors.
	 */
	private double calculateTotalCumulativeFitness (List<Detector> population) {
		// Stores the sum of fitness.
		double cumulative = 0;
		
		// Iterates over each detector.
		for (Detector detector : population) {
			cumulative += detector.getFitness();
		}
		
		return cumulative;
	}
}
