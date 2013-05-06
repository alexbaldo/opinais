package es.uc3m.baldo.opinais;

import java.io.File;
import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.EvolutionaryAlgorithm;
import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.OpinaisProperties;
import es.uc3m.baldo.opinais.core.Type;
import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.experimenter.Experimenter;
import es.uc3m.baldo.opinais.ir.TextIndividualsFactory;
import es.uc3m.baldo.opinais.ir.items.Tweet;
import es.uc3m.baldo.opinais.ir.readers.TweetReader;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Loading configuration...");
		OpinaisProperties props = OpinaisProperties.readProperties("opinais.properties");

		TextIndividualsFactory<Tweet> factory = new TextIndividualsFactory<>();
		Set<Individual> individuals = factory.makeIndividuals(new File(props.inputFile), new TweetReader(), 
															  props.preprocessors, props.featuresLength, props.individualsSize);
		
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
