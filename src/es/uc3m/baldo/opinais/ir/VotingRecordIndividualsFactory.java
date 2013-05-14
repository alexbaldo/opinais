package es.uc3m.baldo.opinais.ir;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.core.types.Type;
import es.uc3m.baldo.opinais.ir.items.Item;
import es.uc3m.baldo.opinais.ir.items.VotingRecord;
import es.uc3m.baldo.opinais.ir.preprocessors.Preprocessor;
import es.uc3m.baldo.opinais.ir.readers.Reader;
import es.uc3m.baldo.opinais.ir.vectorizers.VotingRecordVectorizer;

/**
 * VotingRecordsInvidualsFactory.
 * <p>Generates a set of individuals from a source file
 * containing voting records.</p>
 *  <p>The file format corresponds to the Congressional Voting Records Dataset
 * from the UCI Machine Learning Repository
 * (http://archive.ics.uci.edu/ml/datasets/Congressional+Voting+Records).</p>
 * 
 * @author Alejandro Baldominos
 */
public class VotingRecordIndividualsFactory implements IndividualsFactory<VotingRecord> {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Individual> makeIndividuals (File inputFile, Reader<VotingRecord> reader, 
											List<Preprocessor<String>> preprocessors, int nFeatures, int nIndividuals, boolean isBalanced) {
		
		System.out.println("Reading the input file...");
		// Reads the input file and retrieves a set of items.
		Set<VotingRecord> items = reader.read(inputFile);
				
		// Selects a subset of random individuals
		// (only if the property is specified).
		if (nIndividuals > 0 && nIndividuals < items.size()) {			
			System.out.println("Selecting random items...");
			
			// Represents the set of items as a list.
			List<VotingRecord> itemsList = new LinkedList<>(items);
			
			// Randomizes the list of items.
			Collections.shuffle(itemsList);
			
			// Stores the set of items.
			items = new HashSet<>();
			
			// If individuals need not to be balanced, the
			// selects n random items.
			if (!isBalanced) {
				for (int i = 0; i < nIndividuals; i++) {
					items.add(itemsList.get(i));
				}
				
			// If individuals need to be balanced, then it
			// stores the number of individuals for each
			// class.
			}  else {
				int remaining = nIndividuals > 0 ? Math.min(nIndividuals, itemsList.size()) : itemsList.size();
				Map<Type, Integer> remainingType = new HashMap<>();
				for (Type type : Type.values()) {
					remainingType.put(type, remaining / Type.values().length);
				}
				
				for (VotingRecord item : itemsList) {
					Type type = item.getType();
					
					// This item is only stored is there is room for more items of this type.
					if (remainingType.get(type) > 0) {
						items.add(item);
						remainingType.put(type, remainingType.get(type) - 1);
						remaining--;
					}
					
					// When all individuals are added, it stops.
					// Avoids stupid calculations.
					if (remaining == 0) {
						break;
					}
				}
			}
		}
		
		// Prints the distribution of individuals for each type.
		for (Type type : Type.values()) {
			int typeOccurrences = 0;
			for (Item item : items) {
				if (item.getType() == type) {
					typeOccurrences++;
				}
			}
			System.out.println("\t\t" + type + ": " + typeOccurrences + " (" + 100 * (double) typeOccurrences / items.size() + "%)");
		}
		
		// Vectorizes each item into an individual and adds it to the set.
		System.out.println("Vectorizing the individuals...");
		Set<Individual> individuals = new HashSet<Individual>();
		VotingRecordVectorizer vectorizer = new VotingRecordVectorizer();
		for (VotingRecord item : items) {
			Bit[] bits = vectorizer.vectorize(item);
			individuals.add(new Individual(item.getType(), bits));
		}
		
		return individuals;
	}
}
