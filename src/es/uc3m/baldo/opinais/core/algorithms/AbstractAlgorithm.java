package es.uc3m.baldo.opinais.core.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.core.detectors.DetectorFactory;
import es.uc3m.baldo.opinais.core.types.Type;

/**
 * <p>Provides an abstract implementation of an algorithm.</p>
 * <p>While this class does not actually implement the
 * <em>run</em> functionality, it exists for commodity purposes,
 * as most algorithms will share some common functionality, such
 * as the initialization of random detectors, the stop condition;
 * as well as common parameters.</p>
 * @author Alejandro Baldominos
 */
public abstract class AbstractAlgorithm implements Algorithm {
	
	/**
	 *  <p>Set containing the individuals.</p>
	 */
	protected Set<Individual> individuals;
	
	/**
	 *  <p>List of detectors.</p>
	 *  <p>A list is chosen rather than a set because
	 *  most algorithms may prefer a sorted ADT to sort
	 *  detectors based on some fitness measure.</p>
	 */
	protected List<Detector> detectors;
	
	/**
	 * <p>The number of features of each individual.</p>
	 */
	protected int featuresLength;
	
	/**
	 * <p>The size of the detectors population.</p>
	 */
	protected int speciesSize;
	
	/**
	 * <p>The probability that a randomly created detector
	 * detects self individuals.</p>
	 */
	protected double typeBias;
	
	/**
	 * <p>The probability that a bit in a randomly created
	 * detector is a wildcard.</p>
	 */
	protected double generalityBias;
	
	/**
	 * <p>The maximum number of generations of the algorithm.</p>
	 */
	protected int maxGenerations;
	
	/**
	 * <p>Initializes some fields common to most algorithms.</p>
	 * @param featuresLength the number of features of each individual.
	 * @param speciesSize the size of the detectors population.
	 * @param typeBias the probability that a randomly created detector
	 * detects self individuals.
	 * @param generalityBias the probability that a bit in a randomly
	 * created detector is a wildcard.
	 * @param maxGenerations the maximum number of generations of the 
	 * algorithm.
	 * @param individuals set containing the individuals.
	 */
	public AbstractAlgorithm (int featuresLength, int speciesSize, double typeBias, double generalityBias, 
						 	  int maxGenerations, Set<Individual> individuals) {
		this.featuresLength = featuresLength;
		this.speciesSize = speciesSize;
		this.typeBias = typeBias;
		this.generalityBias = generalityBias;
		this.maxGenerations = maxGenerations;
		this.detectors = new ArrayList<Detector>(speciesSize);
		this.individuals = individuals;
	}
	
	/**
	 * <p>Runs the AIS-based algorithm to retrieve
	 * a detector for each possible type.</p>
	 * <p>This method must be overriden and implemented
	 * by a child class.</p>
	 * @return a map which maps a type to the best
	 * detector found by the algorithm for that type.
	 */
	@Override
	public abstract Map<Type, Detector> run ();
	
	/**
	 * <p>Decides whether the algorithm must stop.</p>
	 * <p>By default, the stop condition for an algorithm
	 * is that the maximum number of generations is achieved.</p>
	 * <p>Child classes may re-implement the stop condition
	 * or add new ones.</p>
	 * @param generation the current generation.
	 * @return true if the algorithm must stop because the 
	 * maximum number of generations has been already achieved,
	 * false otherwise.
	 */
	protected boolean stop (int generation) {
		if (this.maxGenerations > 0 && generation > this.maxGenerations) {
			return true;
		}
		return false;
	}
	
	/**
	 * <p>Initializes a random population of detectors.</p>
	 */
	protected void initializePopulation () {
		for (int i = 0; i < this.speciesSize; i++) {
			this.detectors.add(DetectorFactory.makeDetector(this.featuresLength, this.typeBias, this.generalityBias));
		}
	}
}
