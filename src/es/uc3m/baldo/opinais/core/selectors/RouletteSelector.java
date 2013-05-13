package es.uc3m.baldo.opinais.core.selectors;

import java.util.List;

import es.uc3m.baldo.opinais.core.detectors.Detector;

/**
 * RouletteSelector.
 * <p>Selects one individual from the population randomly, but 
 * in a fitness-proportional way.</p>
 * <p>For the roulette selector to work, all fitness must 
 * be positive.</p>
 * 
 * @author Alejandro Baldominos
 */
public class RouletteSelector extends AbstractSelector {
	
	/*
	 * The sum of fitness for all the detectors.
	 */
	private double totalCumulativeFitness;
	
	/**
	 * <p>Builds a new roulette selector.</p>
	 * @param population the list of detectors, which must be sorted by descending fitness.
	 */
	public RouletteSelector (List<Detector> population) {
		super(population);
		this.totalCumulativeFitness = calculateTotalCumulativeFitness(population);
	}
	
	/**
	 * <p>Selects a detector from the population. This selection is performed
	 * randomly, but preferring those detectors which have a higher fitness
	 * value.</p>
	 * @return the selected detector.
	 */
	@Override
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
		
		// This case is actually impossible.
		return null;		
	}
	
	/**
	 * <p>Calculates the total sum of fitness of all detectors.</p>
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
