package es.uc3m.baldo.opinais.ir;

import java.io.File;
import java.util.List;
import java.util.Set;

import es.uc3m.baldo.opinais.core.Individual;
import es.uc3m.baldo.opinais.ir.preprocessors.PreProcessor;
import es.uc3m.baldo.opinais.ir.readers.Reader;

/**
 * InvidualsFactory.
 * <p>Generates a set of individuals from a source file.</p>
 * 
 * @param <T> the type of the items to be converted to individuals.
 * 
 * @author Alejandro Baldominos
 */
public interface IndividualsFactory<T> {
	
	/**
	 * <p>Generates a set of individuals from a source file.</p>
	 * @param inputFile the source file containing the items.
	 * @param reader a reader object which provides the logic to
	 * retrieve items from the input file.
	 * @param preprocessors a list of pre-processors to be executed
	 * over the items.
	 * @param nFeatures the number of features to be extracted.
	 * @param nIndividuals the number of individuals to be generated,
	 * where 0 means that all individuals are actually chosen.
	 * @param isBalanced whether the number of individuals for each type
	 * must be balanced.
	 * @return the set of generated individuals.
	 */
	public Set<Individual> makeIndividuals (File inputFile, Reader<T> reader, 
											List<PreProcessor<String>> preprocessors, int nFeatures, int nIndividuals, boolean isBalanced);

}
