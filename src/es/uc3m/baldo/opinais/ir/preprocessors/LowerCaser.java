package es.uc3m.baldo.opinais.ir.preprocessors;

/**
 * LowerCaser.
 * <p>Converts all characters from a text string to lower case.</p>
 * <p>This processor is useful when analyzing texts, as
 * words usually keep their semantics regarding how they are cased.</p>
 * 
 * @author Alejandro Baldominos
 */
public class LowerCaser implements PreProcessor<String> {

	/**
	 * <p>Converts all characters from a text string to lower case.</p>
	 * @param item the input string.
	 * @return the string with all characters lower-cased.
	 */
	@Override
	public String process (String text) {
		return text.toLowerCase();
	}
	
}
