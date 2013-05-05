package es.uc3m.baldo.opinais.ir.readers.factories;

import java.util.regex.Pattern;

import es.uc3m.baldo.opinais.core.Type;
import es.uc3m.baldo.opinais.ir.items.Tweet;

public class TweetFactory implements TextItemFactory<Tweet> {

	public static final Pattern SEPARATOR = Pattern.compile(";;;");
	
	public Tweet make (String document) {
		String[] tokens = SEPARATOR.split(document);
		Type type = null;
		if (tokens[0].equals("+")) {
			type = Type.SELF;
		} else if (tokens[0].equals("-")) {
			type = Type.NON_SELF;
		}
		int id = Integer.parseInt(tokens[1]);
		String text = tokens[2];
		
		return new Tweet(id, text, type);
	}
}
