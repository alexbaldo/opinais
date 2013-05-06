package es.uc3m.baldo.opinais.ir.preprocessors;

import org.tartarus.snowball.ext.PorterStemmer;

/**
 * Stemmer.
 * <p>Reduces each word in an input text to its stem.</p>
 * <p>This processor is useful when analyzing texts, as
 * stemming words reduces the universe of discourse, combining
 * with words which usually have similar semantics into one.</p>
 * <p>The stemmer used in this pre-processor is the Porter Stemmer,
 * which works fine for the English language, and is described in 
 * <cite>M.F. Porter, 1980, An Algorithm for Suffix Stripping, 
 * <i>Program</i>,<strong>14</strong>(3) pp. 130-137</cite>.</p>
 * 
 * @author Alejandro Baldominos
 */
public class Stemmer implements PreProcessor<String> {

	/**
	 * <p>Reduces each word in an input text to its stem.</p>
	 * @param item the input string.
	 * @return the string after stemming is performed on each word.
	 */
	@Override
	public String process (String text) {
		// Splits the text in words.
		String newText = "";
		String[] words = SEPARATOR.split(text);
		
		// Iterates over each word in the original text.
		for (String word : words) {
			// Performs stemming over the word and appends
			// it to the string to be returned.
			PorterStemmer stemmer = new PorterStemmer();
			stemmer.setCurrent(word);
			stemmer.stem();
			newText += stemmer.getCurrent() + " ";
		}
		
		// The string is trimmed before it is returned, to prevent spaces
		// in the first or last positions.
		return newText.trim();
	}
}
