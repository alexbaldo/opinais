package es.uc3m.baldo.opinais.core;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import es.uc3m.baldo.opinais.ir.preprocessors.PreProcessor;

/**
 * OpinaisProperties.
 * <p>Stores the properties read from an external file,
 * which are required by the AIS.</p>
 * 
 * @author Alejandro Baldominos
 */
public class OpinaisProperties {
	
	/**
	 * <p>The number of features of each individual.</p>
	 */
	public int featuresLength;
	
	/**
	 * <p>The size of the detectors population.</p>
	 */
	public int speciesSize;
	
	/**
	 * <p>The maximum number of individuals.</p>
	 * <p>The individuals are selected randomly from
	 * a set containing all the input individuals.</p>
	 * <p>0 means that all the individuals are considered.</p>
	 */
	public int individualsSize;
	
	/**
	 * <p>The probability that a randomly created detector
	 * detects self individuals.</p>
	 */
	public double typeBias;
	
	/**
	 * <p>The probability that a bit in a randomly created
	 * detector is a wildcard.</p>
	 */
	public double generalityBias;
	
	/**
	 * <p>The probability that crossover is performed over
	 * two parent detectors to obtain a child.</p>
	 */
	public double crossoverRate;
	
	/**
	 * <p>The probability that mutation is performed in a bit
	 * in the detector schema.</p>
	 */
	public double mutationRate;
	
	/**
	 * <p>The maximum number of generations of the evolutionary
	 * algorithm.</p>
	 */
	public int maxGenerations;
	
	/**
	 * <p>Required umber of consecutive generations where the fitness 
	 * is not increased in order to stop.</p>
	 * TODO This parameter is ignored so far.
	 */
	public int stagnationGenerations;
	
	/**
	 * <p>The input file containing all the individuals.</p>
	 */
	public String inputFile;
	
	/**
	 * <p>The percentage of individuals to be stored in the test set.</p>
	 * <p>This value must be expressed as a fraction.</p>
	 */
	public double testPct;
	
	/**
	 * <p>Sorted list of pre-processors to be executed over the
	 * individuals.</p>
	 */
	public List<PreProcessor<String>> preprocessors;
	
	public OpinaisProperties () {
		this.preprocessors = new LinkedList<PreProcessor<String>>();
	}
	
	/**
	 * <p>Reads the properties from a file and stores them.</p>
	 * @param propertiesFile the path for the properties file.
	 */
	@SuppressWarnings("unchecked")
	public static OpinaisProperties readProperties (String propertiesFile) {
		OpinaisProperties opinaisProps = new OpinaisProperties();
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(propertiesFile));
			opinaisProps.featuresLength = Integer.parseInt(properties.getProperty("featuresLength"));			
			opinaisProps.speciesSize = Integer.parseInt(properties.getProperty("speciesSize"));	
			opinaisProps.individualsSize = Integer.parseInt(properties.getProperty("individualsSize"));	
			opinaisProps.typeBias = Double.parseDouble(properties.getProperty("typeBias"));	
			opinaisProps.generalityBias = Double.parseDouble(properties.getProperty("generalityBias"));	
			opinaisProps.crossoverRate = Double.parseDouble(properties.getProperty("crossoverRate"));	
			opinaisProps.mutationRate = Double.parseDouble(properties.getProperty("mutationRate"));	
			opinaisProps.maxGenerations = Integer.parseInt(properties.getProperty("maxGenerations"));
			opinaisProps.stagnationGenerations = Integer.parseInt(properties.getProperty("stagnationGenerations"));
			opinaisProps.testPct = Double.parseDouble(properties.getProperty("testPct"));	
			opinaisProps.inputFile = properties.getProperty("inputFile");
			if (properties.containsKey("preprocessors")) {
				String[] preprocessors = properties.getProperty("preprocessors").split(",");
				for (String preprocessor : preprocessors) {
					opinaisProps.preprocessors.add((PreProcessor<String>)Class.forName("es.uc3m.baldo.opinais.ir.preprocessors." + preprocessor).newInstance());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return opinaisProps;
	}

}
