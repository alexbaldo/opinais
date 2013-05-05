package es.uc3m.baldo.opinais.ir.preprocessors;

import org.tartarus.snowball.ext.PorterStemmer;

public class Stemmer implements PreProcessor<String> {

	public String process (String text) {
		String newText = "";
		String[] words = SEPARATOR.split(text);
		for (String word : words) {
			PorterStemmer stemmer = new PorterStemmer();
			stemmer.setCurrent(word);
			stemmer.stem();
			newText += stemmer.getCurrent() + " ";
		}
		
		return newText.trim();
	}
}
