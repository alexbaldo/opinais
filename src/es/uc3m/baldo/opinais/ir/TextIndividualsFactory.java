package es.uc3m.baldo.opinais.ir;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.ir.extractors.TextFeaturesExtractor;
import es.uc3m.baldo.opinais.ir.items.TextItem;
import es.uc3m.baldo.opinais.ir.preprocessors.PreProcessor;
import es.uc3m.baldo.opinais.ir.readers.Reader;
import es.uc3m.baldo.opinais.ir.vectorizers.TextVectorizer;

/**
 * TextInvidualsFactory.
 * <p>Generates a set of text individuals from a source file.</p>
 * 
 * @param <T> the type of the items to be converted to individuals.
 * 
 * @author Alejandro Baldominos
 */
public class TextIndividualsFactory<T extends TextItem> {
	
	/**
	 * <p>Generates a set of text individuals from a source file.</p>
	 * @param inputFile the source file containing the items.
	 * @param reader a reader object which provides the logic to
	 * retrieve items from the input file.
	 * @param preprocessors a list of pre-processors to be executed
	 * over the items.
	 * @param nFeatures the number of features to be extracted.
	 * @param nIndividuals the number of individuals to be generated,
	 * where 0 means that all individuals are actually chosen.
	 * @return the set of generated individuals.
	 */
	public Set<Individual> makeIndividuals (File inputFile, Reader<T> reader, 
											List<PreProcessor<String>> preprocessors, int nFeatures, int nIndividuals) {
		
		System.out.println("Reading the input file...");
		// Reads the input file and retrieves a set of items.
		Set<T> items = reader.read(inputFile);
				
		// Selects a subset of random individuals
		// (only if the property is specified).
		if (nIndividuals > 0 && nIndividuals < items.size()) {
			System.out.println("Selecting random items...");
			
			// Represents the set of items as a list.
			List<T> itemsList = new LinkedList<>(items);
			
			// Randomizes the list of items.
			Collections.shuffle(itemsList);
			
			// Selects n random items.
			items = new HashSet<>();
			for (int i = 0; i < nIndividuals; i++) {
				items.add(itemsList.get(i));
			}
		}
		
		// Executes the specified preprocessors over the items.
		System.out.println("Executing the preprocessors...");
		for (PreProcessor<String> preprocessor : preprocessors) {
			for (TextItem item : items) {
				item.setText(preprocessor.process(item.getText()));
			}
		}
		
		// Retrieves the list of features.
		System.out.println("Retrieving the list of features...");
		TextFeaturesExtractor extractor = new TextFeaturesExtractor(nFeatures);
		String[] features = extractor.extractFeatures(items);
		
		
		// Vectorizes each item into an individual and adds it to the set.
		System.out.println("Vectorizing the individuals...");
		Set<Individual> individuals = new HashSet<Individual>();
		TextVectorizer vectorizer = new TextVectorizer(features);
		for (TextItem item : items) {
			Bit[] bits = vectorizer.vectorize(item);
			individuals.add(new Individual(item.getType(), bits));
		}
		
		return individuals;
	}

}
