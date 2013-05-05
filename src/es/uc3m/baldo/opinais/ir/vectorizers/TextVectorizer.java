package es.uc3m.baldo.opinais.ir.vectorizers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.ir.items.TextItem;

/**
 * TextVectorizer.
 * Vectorizes a text item. To do so, the vectorizer requires a sorted list of words
 * and returns an array of bits specifying whether the item contains each of these words.
 * 
 * @author Alejandro Baldominos
 */
public class TextVectorizer implements Vectorizer<TextItem> {

	/* 
	 * Text separator, usually one or more empty spaces.
	 * It is compiled for efficiency purposes.
	 */
	public static final Pattern SEPARATOR = Pattern.compile(" +");
	
	/*
	 * The sorted list of words is represented as a map, where
	 * the key is the word and the value is the position in
	 * the array. This turns out to be a convenient representation, 
	 * as searching whether a word is contained in a hashed map is 
	 * far more efficient than doing so in a list.
	 */
	public Map<String, Integer> features;
	
	/**
	 * Builds a new text vectorized, which receives as input
	 * an array of features (words), which is converted to
	 * the map representation.
	 * @param features the array of words which represent
	 * the individual features.
	 */
	public TextVectorizer (String[] features) {
		this.features = new HashMap<String, Integer>();
		for (int i = 0; i < features.length; i++) {
			this.features.put(features[i], i);
		}
	}
	
	/**
	 * Vectorizes the text item.
	 * To do so, an array of bits is built, where a 1 in the
	 * position <i>i</i> means that the word at position <i>i</i>
	 * in the original feature array is present in the item, and a
	 * 0 represent the opposite case.
	 * @param item the text item to be vectorized.
	 * @return an array of bits representing the text item.
	 */
	@Override
	public Bit[] vectorize (TextItem item) {
		// Initializes the array to a 0-bit array.
		Bit[] bits = new Bit[features.size()];
		for (int i = 0; i < bits.length; i++) {
			bits[i] = Bit.ZERO;
		}
		
		// Splits the text into words.
		String[] words = SEPARATOR.split(item.getText());
		
		// Checks whether each word is contained in the text.
		// Actually, for efficiency purposes, the process is inverted:
		// for each word in the text, it is checked whether it is a
		// feature, and in such a case, a 1 is written in the right
		// position in the bit array.
		for (String word : words) {
			if (features.containsKey(word)) {
				bits[features.get(word)] = Bit.ONE;
			}
		}
		
		return bits;
	}
	
}
