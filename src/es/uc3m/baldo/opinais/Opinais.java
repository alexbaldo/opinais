package es.uc3m.baldo.opinais;

import java.io.File;
import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.OpinaisProperties;
import es.uc3m.baldo.opinais.core.detectors.Detector;
import es.uc3m.baldo.opinais.core.types.Type;
import es.uc3m.baldo.opinais.experimenter.Experimenter;

/**
 * Opinais.
 * <p>Entry point to the the Opinais framework.</p>
 * <p>This class must not be modified to run the application,
 * rather, all properties must be set in the properties file.</p>
 * 
 * @author Alejandro Baldominos
 */
public class Opinais {

	/**
	 * <p>Runs the application.</p>
	 * @param args the location of the properties file,
	 * containing all the information required for the 
	 * application to start.
	 */
	public static void main (String[] args) {
		// Reads the input arguments.
		String propertiesFile = null;
		if (args.length < 1) {
			System.err.println("Usage: Opinais <PropertiesFile>");
			System.exit(-1);
		} else {
			propertiesFile = args[0];
		}
		
		// Loads the configuration from the properties file.
		System.out.println("Loading configuration...");
		OpinaisProperties props = OpinaisProperties.readProperties(propertiesFile);

		// Generates the individuals from the input source.
		Set<Individual> individuals = props.factory.makeIndividuals(new File(props.inputFile), props.reader, 
									  								 props.preprocessors, props.featuresLength, props.individualsSize, props.isBalanced);
		
		// Runs the experimenter and generates the training and test sets.
		System.out.println("Generating the training and test sets...");
		Experimenter experimenter = new Experimenter(individuals, props.testPct);
		Set<Individual> trainingSet = experimenter.getTrainingSet();
		Set<Individual> testSet = experimenter.getTestSet();
		
		// Runs the algorithm.
		System.out.println("Running the evolutionary algorithm...");
		props.algorithm.setIndividuals(trainingSet);
		Map<Type,Detector> bestDetectors = props.algorithm.run();
		
		// Gets the results.
		Map<Type, Map<Type, Integer>> confusionMatrixTrain = experimenter.getConfusionMatrix(bestDetectors, trainingSet);
		System.out.println("Confussion Matrix over Training Set:");
		experimenter.printConfusionMatrix(confusionMatrixTrain);
		System.out.println("Hit Rate over Training Set: " + experimenter.calculateHitRate(confusionMatrixTrain));
		
		Map<Type, Map<Type, Integer>> confusionMatrixTest = experimenter.getConfusionMatrix(bestDetectors, testSet);
		System.out.println("Confussion Matrix over Test Set:");
		experimenter.printConfusionMatrix(confusionMatrixTest);
		System.out.println("Hit Rate over Test Set: " + experimenter.calculateHitRate(confusionMatrixTest));
		
		// Done :)
		System.out.println("Done.");
	}

}
