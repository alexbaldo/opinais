package es.uc3m.baldo.opinais.ir.readers.factories;

import java.util.regex.Pattern;

import es.uc3m.baldo.opinais.core.types.Type;
import es.uc3m.baldo.opinais.ir.items.Tweet;

/**
 * TweetFactory.
 * <p>Generates tweets from a line in an input file.</p>
 * 
 * @author Alejandro Baldominos
 */
public class TweetFactory implements TextItemFactory<Tweet, String> {

	/*
	 * The separator in this case is composed of three consecutive
	 * semicolons. This separator is chosen because it is not expected
	 * to be part of an actual tweet, while only one semicolon could.
	 */
	private static final Pattern SEPARATOR = Pattern.compile(";;;");
	
	/**
	 * <p>Generates a new tweet from an input line.</p>
	 * @param line the input line, which must comply with the next format:
	 * <i>type</i>;;;<i>id</i>;;;<i>text
	 * Each of these fields has an specific meaning:
	 * <ul>
	 * <li><strong>type</strong>: the class for the tweet, which may either be "+"
	 * (positive) or "-" (negative).
	 * <li><strong>id</strong>: a unique identifier for the tweet.
	 * <li><strong>text</strong>: the tweet's text.
	 * </ul>
	 * @return the generated tweeet.
	 */
	@Override
	public Tweet make (String line) {
		// Splits the input line with the specified separator.
		String[] tokens = SEPARATOR.split(line);
		
		// Extracts the tweet type.
		Type type = Type.valueOf(tokens[0]);
		
		// Extracts the tweet id.
		int id = Integer.parseInt(tokens[1]);
		
		// Extracts the tweet text.
		String text = tokens[2];
		
		// Generates and returns the new tweet.
		return new Tweet(id, text, type);
	}
}
