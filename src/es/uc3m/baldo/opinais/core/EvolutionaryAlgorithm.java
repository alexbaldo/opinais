package es.uc3m.baldo.opinais.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.core.detectors.DetectorFactory;

public class EvolutionaryAlgorithm {
	
	// Set of individuals.
	private Set<Individual> individuals;
	
	// List of detectors.
	private List<Detector> detectors;
	
	// Properties.
	private int featuresLength;
	private int genomeLength;
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
	public EvolutionaryAlgorithm (String propertiesFile) {
		readProperties(propertiesFile);
		this.detectors = new ArrayList<Detector>(speciesSize);
	}
	
	public void setIndividuals (Set<Individual> individuals) {
		this.individuals = individuals;
	}
	
	public void run () {
		
		// Creates the initial population.
		initializePopulation();
		
		// Calculates simple fitness for each detector.
		// Simple fitness is defined as fitness by individual matching,
		//	before any cooperation is taking place.
		calculateFitness(detectors);
		Collections.sort(detectors);

		// Splits the initial population in two lists.
		List<Detector> selfDetectors = filterDetectors(detectors, Type.SELF);
		List<Detector> nonSelfDetectors = filterDetectors(detectors, Type.NON_SELF);

		int generation = 0;
		//while (!stop(generation)) {
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
			
			// Increases the generations counter.
			generation++;
		//}
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
		if (generation > 0 && generation > this.maxGenerations) {
			return true;
		}
		return false;
	}
	
	private void initializePopulation () {
		for (int i = 0; i < this.speciesSize; i++) {
			this.detectors.add(DetectorFactory.makeDetector(this.featuresLength, this.typeBias, this.generalityBias));
		}
	}
	
	/**
	 * Reads the properties from a file and stores them.
	 * @param propertiesFile the path for the properties file.
	 */
	private void readProperties (String propertiesFile) {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(propertiesFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.featuresLength = Integer.parseInt(properties.getProperty("featuresLength"));
		this.genomeLength = 8 + 2 * featuresLength;
		this.speciesSize = Integer.parseInt(properties.getProperty("speciesSize"));
		this.typeBias = Double.parseDouble(properties.getProperty("typeBias"));
		this.generalityBias = Double.parseDouble(properties.getProperty("generalityBias"));
		this.crossoverRate = Double.parseDouble(properties.getProperty("crossoverRate"));
		this.mutationRate = Double.parseDouble(properties.getProperty("mutationRate")) / genomeLength; 
		this.maxGenerations = Double.parseDouble(properties.getProperty("maxGenerations"));
	}
}
