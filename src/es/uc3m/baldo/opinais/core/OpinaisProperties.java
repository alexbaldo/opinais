package es.uc3m.baldo.opinais.core;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import es.uc3m.baldo.opinais.ir.preprocessors.PreProcessor;

public class OpinaisProperties {
	
	public int featuresLength;
	public int speciesSize;
	public double typeBias;
	public double generalityBias;
	public double crossoverRate;
	public double mutationRate;
	public int maxGenerations;
	public int stagnationGenerations;
	public String inputFile;
	public double testPct;
	public List<PreProcessor<String>> preprocessors;
	
	public OpinaisProperties () {
		this.preprocessors = new LinkedList<PreProcessor<String>>();
	}
	
	/**
	 * Reads the properties from a file and stores them.
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
