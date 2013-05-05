package es.uc3m.baldo.opinais.core;

import java.io.File;
import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.experimenter.Experimenter;
import es.uc3m.baldo.opinais.ir.TextIndividualFactory;
import es.uc3m.baldo.opinais.ir.readers.TweetReader;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Loading configuration...");
		OpinaisProperties props = OpinaisProperties.readProperties("opinais.properties");

		Set<Individual> individuals = TextIndividualFactory.makeIndividuals(new File(props.inputFile), new TweetReader(), 
																			props.preprocessors, props.featuresLength);
		
		System.out.println("Generating the training and test sets...");
		Experimenter experimenter = new Experimenter(individuals, props.testPct);
		Set<Individual> trainingSet = experimenter.getTrainingSet();
		Set<Individual> testSet = experimenter.getTestSet();
		
		for (Individual ind : individuals) {
			props.featuresLength = Math.min(props.featuresLength, ind.bits.length);
			break;
		}
		EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm(props.featuresLength, props.speciesSize, props.typeBias, props.generalityBias, 
															 props.crossoverRate, props.mutationRate, props.maxGenerations, trainingSet);
		
		System.out.println("Running the evolutionary algorithm...");
		Map<Type,Detector> bestDetectors = ea.run();
		
		System.out.println("Hit Rate over Training Set: "+ experimenter.calculateHitRate(trainingSet, bestDetectors));
		System.out.println("Hit Rate over Test Set: "+ experimenter.calculateHitRate(testSet, bestDetectors));
		
		System.out.println("Done.");
	}

}
