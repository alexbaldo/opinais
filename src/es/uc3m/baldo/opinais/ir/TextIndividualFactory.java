package es.uc3m.baldo.opinais.ir;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.ir.extractors.TextFeatureExtractor;
import es.uc3m.baldo.opinais.ir.items.TextItem;
import es.uc3m.baldo.opinais.ir.preprocessors.PreProcessor;
import es.uc3m.baldo.opinais.ir.readers.TextItemReader;
import es.uc3m.baldo.opinais.ir.vectorizers.TextVectorizer;

public class TextIndividualFactory {
	
	public static Set<Individual> makeIndividuals (File inputFile, TextItemReader<? extends TextItem> reader, 
												   List<PreProcessor<String>> preprocessors, int nFeatures) {
		
		// Reads the input file and retrieves a set of items.
		System.out.println("Reading the input file...");
		Set<? extends TextItem> items = reader.read(inputFile);
				
		// Executes the specified preprocessors over the items.
		System.out.println("Executing the preprocessors...");
		for (PreProcessor<String> preprocessor : preprocessors) {
			for (TextItem item : items) {
				item.setText(preprocessor.process(item.getText()));
			}
		}
		
		// Retrieves the list of features.
		System.out.println("Retrieving the list of features...");
		TextFeatureExtractor extractor = new TextFeatureExtractor(nFeatures);
		String[] features = extractor.extractFeatures(items);

		// Vectorizes each item into an individual.
		System.out.println("Vectorizing the individual...");
		Set<Individual> individuals = new HashSet<Individual>();
		TextVectorizer vectorizer = new TextVectorizer(features);
		for (TextItem item : items) {
			Bit[] bits = vectorizer.vectorize(item);
			individuals.add(new Individual(item.getType(), bits));
		}
		
		return individuals;
	}

}
