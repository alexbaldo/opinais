package es.uc3m.baldo.opinais.ir.preprocessors;

/**
 * SymbolRemover.
 * <p>Removes all non-alphanumeric characters from a text string.</p>
 * <p>This processor is useful when analyzing texts, as
 * non-alphanumerical characters are usually meaningless.</p>
 * 
 * @author Alejandro Baldominos
 */
public class SymbolRemover implements Preprocessor<String> {

	/**
	 * <p>Removes all non-alphanumeric characters from a text string.</p>
	 * <p>Moreover, the blankspace symbol is also left, to prevent from 
	 * joining words.</p>
	 * @param item the input string.
	 * @return the string after non-alphanumeric symbols are removed.
	 */
	@Override
	public String process (String item) {
		return item.replaceAll("[^A-Za-z0-9 ]", " ");
	}
	
}
