package es.uc3m.baldo.opinais;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.OpinaisProperties;
import es.uc3m.baldo.opinais.core.Type;
import es.uc3m.baldo.opinais.core.algorithms.CoEvolutionaryAlgorithm;
import es.uc3m.baldo.opinais.core.algorithms.EvolutionaryAlgorithm;
import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.experimenter.Experimenter;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Loading configuration...");
		OpinaisProperties props = OpinaisProperties.readProperties("opinais.properties");

		Set<Individual> individuals = new HashSet<Individual>();
		individuals.add(new Individual(Type.SELF,0,0,1,0,0,1,1,1,0,1));
		individuals.add(new Individual(Type.SELF,1,0,0,1,1,0,0,1,1,0));
		individuals.add(new Individual(Type.SELF,0,0,1,0,0,1,1,1,0,1));
		individuals.add(new Individual(Type.SELF,1,0,0,1,1,0,0,1,1,0));
		individuals.add(new Individual(Type.SELF,0,0,1,0,0,1,1,1,0,1));
		individuals.add(new Individual(Type.SELF,1,0,0,1,1,0,0,1,1,0));
		individuals.add(new Individual(Type.SELF,0,0,1,0,0,1,1,1,0,1));
		individuals.add(new Individual(Type.SELF,1,0,0,1,1,0,0,1,1,0));
		individuals.add(new Individual(Type.SELF,0,0,1,0,0,1,1,1,0,1));
		individuals.add(new Individual(Type.SELF,1,0,0,1,1,0,0,1,1,0));
		
		individuals.add(new Individual(Type.NON_SELF,1,1,1,0,0,0,1,1,1,0));
		individuals.add(new Individual(Type.NON_SELF,1,1,0,0,1,1,0,0,0,1));
		individuals.add(new Individual(Type.NON_SELF,1,1,1,0,0,0,1,0,1,0));
		individuals.add(new Individual(Type.NON_SELF,1,1,0,0,1,1,0,0,0,1));
		individuals.add(new Individual(Type.NON_SELF,1,1,1,0,0,0,1,1,1,0));
		individuals.add(new Individual(Type.NON_SELF,1,1,0,0,1,1,0,0,0,1));
		individuals.add(new Individual(Type.NON_SELF,1,1,1,0,0,0,1,1,1,0));
		individuals.add(new Individual(Type.NON_SELF,1,1,0,0,1,1,0,0,0,1));
		individuals.add(new Individual(Type.NON_SELF,1,1,1,0,0,0,1,1,1,0));
		individuals.add(new Individual(Type.NON_SELF,1,1,0,0,1,1,0,0,0,1));
		
		System.out.println("Generating the training and test sets...");
		Experimenter experimenter = new Experimenter(individuals, props.testPct);
		Set<Individual> trainingSet = experimenter.getTrainingSet();
		Set<Individual> testSet = experimenter.getTestSet();
		
		for (Individual ind : individuals) {
			props.featuresLength = Math.min(props.featuresLength, ind.bits.length);
			break;
		}
		EvolutionaryAlgorithm ea = new CoEvolutionaryAlgorithm(props.featuresLength, props.speciesSize, props.typeBias, props.generalityBias, 
															 props.crossoverRate, props.mutationRate, props.maxGenerations, trainingSet);
		
		System.out.println("Running the evolutionary algorithm...");
		Map<Type,Detector> bestDetectors = ea.run();
		
		System.out.println("Hit Rate over Training Set: " + experimenter.calculateHitRate(bestDetectors, trainingSet));
		System.out.println("Hit Rate over Test Set: " + experimenter.calculateHitRate(bestDetectors, testSet));
		
		System.out.println("Done.");
	}

}
