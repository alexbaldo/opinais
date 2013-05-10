package es.uc3m.baldo.opinais.core;

import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import es.uc3m.baldo.opinais.core.algorithms.Algorithm;
import es.uc3m.baldo.opinais.core.types.TypeBuilder;
import es.uc3m.baldo.opinais.ir.IndividualsFactory;
import es.uc3m.baldo.opinais.ir.items.Item;
import es.uc3m.baldo.opinais.ir.preprocessors.PreProcessor;
import es.uc3m.baldo.opinais.ir.readers.Reader;

/**
 * OpinaisProperties.
 * <p>Stores the properties read from an external file,
 * which are required by the AIS.</p>
 * 
 * @author Alejandro Baldominos
 */
public class OpinaisProperties {
	
	/**
	 * <p>Stores the reader which is going to be used to read
	 * the input file and retrieve the items.</p>
	 */
	public Reader<Item> reader;
	
	/**
	 * <p>Stores the factory which is going to be used to convert
	 * the items into individuals.</p>
	 */
	public IndividualsFactory<Item> factory;
	
	/**
	 * <p>The input file containing all the individuals.</p>
	 */
	public String inputFile;
	
	/**
	 * <p>The maximum number of individuals.</p>
	 * <p>The individuals are selected randomly from
	 * a set containing all the input individuals.</p>
	 * <p>0 means that all the individuals are considered.</p>
	 */
	public int individualsSize;
	
	/**
	 * <p>Is the number of individuals for each type balanced?</p>
	 * <p>If this value is true, then all types will contain the
	 * same number of individuals, as long as it is possible.</p>
	 */
	public boolean isBalanced;
	
	
	/**
	 * <p>The number of features of each individual.</p>
	 */
	public int featuresLength;
	
	/**
	 * <p>Sorted list of pre-processors to be executed over the
	 * individuals.</p>
	 */
	public List<PreProcessor<String>> preprocessors;
	
	/**
	 * <p>The percentage of individuals to be stored in the test set.</p>
	 * <p>This value must be expressed as a fraction.</p>
	 */
	public double testPct;

	/**
	 * <p>The algorithm to be used.</p>
	 * <p>This algorithm is automatically built from
	 * the types and parameters specified in the 
	 * properties file.</p>
	 */
	public Algorithm algorithm;

	/**
	 * <p>Initializes the properties class.</p>
	 */
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
			
			// Dynamically adds the set of types.
			String[] types = properties.getProperty("types").split(",");
			for (String type : types) {
				TypeBuilder.addType(type);
			}
			
			// Gets the reader and the individuals factory.
			// TODO: What if the reader or factory required some parameters? So far, this
			// functionality is not covered.
			String readerName = properties.getProperty("reader");
			String factoryName = properties.getProperty("factory");
			opinaisProps.reader = (Reader<Item>) Class.forName("es.uc3m.baldo.opinais.ir.readers." + readerName).newInstance();
			opinaisProps.factory = (IndividualsFactory<Item>) Class.forName("es.uc3m.baldo.opinais.ir." + factoryName).newInstance();
			
			// Gets properties related to the individuals.
			opinaisProps.inputFile = properties.getProperty("inputFile");	
			opinaisProps.individualsSize = Integer.parseInt(properties.getProperty("individualsSize"));	
			opinaisProps.isBalanced = Boolean.parseBoolean(properties.getProperty("isBalanced"));
			
			// Gets properties related to the features extraction and information retrieval.
			opinaisProps.featuresLength = Integer.parseInt(properties.getProperty("featuresLength"));
			if (properties.containsKey("preprocessors")) {
				String[] preprocessors = properties.getProperty("preprocessors").split(",");
				for (String preprocessor : preprocessors) {
					opinaisProps.preprocessors.add((PreProcessor<String>)Class.forName("es.uc3m.baldo.opinais.ir.preprocessors." + preprocessor).newInstance());
				}
			}
			
			// Gets properties related to the experimenter.
			opinaisProps.testPct = Double.parseDouble(properties.getProperty("testPct"));
			
			// Get properties related to the AIS algorithm.	
			// Reflection is used to generate the algorithm calling the constructor with the parameters
			// specified in the properties.
			// TODO Ok, I really love reflection. Yet, more elegant alternatives (such as a map of properties
			// for the algorithm) may be used in the feature. The current code state is just the result
			// of quick refactoring.
			String[] algorithmTypesStr = properties.getProperty("algorithmTypes").split(",");		
			String[] algorithmParametersStr = properties.getProperty("algorithmParameters").split(",");
			Class<?>[] algorithmTypes = new Class<?>[algorithmTypesStr.length];
			Object[] algorithmParameters = new Object[algorithmParametersStr.length];
			for (int i = 0; i < algorithmTypes.length; i++) {
				String property = properties.getProperty(algorithmParametersStr[i]);
				switch (algorithmTypesStr[i]) {
					case "Integer":
						algorithmTypes[i] = int.class;
						algorithmParameters[i] = Integer.parseInt(property);
						break;
					case "Double":
						algorithmTypes[i] = double.class;
						algorithmParameters[i] = Double.parseDouble(property);
						break;
					case "String":
						algorithmTypes[i] = String.class;
						algorithmParameters[i] = property;
						break;
				}
			}
			Class<Algorithm> algorithmClass = (Class<Algorithm>) Class.forName("es.uc3m.baldo.opinais.core.algorithms." + properties.getProperty("algorithm"));
			Constructor<Algorithm> constructor = algorithmClass.getDeclaredConstructor(algorithmTypes);
			opinaisProps.algorithm = constructor.newInstance(algorithmParameters);
		
		// TODO Some error handling would be OK.
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return opinaisProps;
	}
}
