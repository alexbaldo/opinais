package es.uc3m.baldo.opinais.experimenter;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.Type;
import es.uc3m.baldo.opinais.core.detectors.Detector;

public class Experimenter {

	private Set<Individual> trainingSet;
	private Set<Individual> testSet;
	
	public Experimenter (Set<Individual> individuals, double pctTest) {
		trainingSet = new HashSet<Individual>();
		testSet = new HashSet<Individual>();
		
		int testIndividuals = (int) (pctTest * individuals.size());
		List<Individual> allIndividuals = new LinkedList<Individual>(individuals);
		Collections.shuffle(allIndividuals);
		
		for (int i = 0; i < testIndividuals; i++) {
			testSet.add(allIndividuals.get(i));
		}
		for (int i = testIndividuals; i < allIndividuals.size(); i++) {
			trainingSet.add(allIndividuals.get(i));
		}		
	}
	
	public Set<Individual> getTrainingSet () {
		return this.trainingSet;
	}
	
	public Set<Individual> getTestSet () {
		return this.testSet;
	}
	
	public double calculateHitRate (Set<Individual> individuals, Map<Type, Detector> bestDetectors) {
		double hits = 0;
				
		for (Individual individual : individuals) {
			Type inferredType = null;
			double highestMatch = 0.0;
			for (Type type : bestDetectors.keySet()) {
				Detector detector = bestDetectors.get(type);
				double match = detector.match(individual);
				if (match > highestMatch && match > detector.decodedThreshold) {
					highestMatch = match;
					inferredType = type;
				}
			}
			
			if (inferredType == individual.type) {
				hits++;
			}
		}
		
		return hits / individuals.size();
	}
}
