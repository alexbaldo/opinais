package es.uc3m.baldo.opinais.ir.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import es.uc3m.baldo.opinais.ir.items.Tweet;
import es.uc3m.baldo.opinais.ir.readers.factories.TweetFactory;

/**
 * TweetReader.
 * <p>Reads a set of tweets from an input file.</p>
 * 
 * @author Alejandro Baldominos
 */
public class TweetReader implements Reader<Tweet> {

	/**
	 * <p>Reads a set of tweets which are stored in a file.</p>
	 * @param file the input file.
	 * @return a set of tweets extracted from the input file.
	 */
	@Override
	public Set<Tweet> read (File file) {
		// Stores the tweets read from the file.
		Set<Tweet> tweets = new HashSet<Tweet>();
		
		// Instantiates a new factory of tweets. This
		// factory will convert each line from the source
		// file to a tweet object.
		TweetFactory factory = new TweetFactory();
		
		BufferedReader in = null;
		try {
			// Creates a new input reader to read from the file.
			in = new BufferedReader(new FileReader(file));
			
			// Iterates through the file line by line and a tweet
			// object is created and stored for each line.
			String line;
			while ((line = in.readLine()) != null) {
				Tweet tweet = factory.make(line);
				if (tweet != null) {
					tweets.add(tweet);
				}
			}
		} catch (Exception e) {
			/* TODO Manage errors. */
			e.printStackTrace();
		} finally {
			try {
				// Closes the input reader.
				in.close();
			} catch (Exception e) {}
		}
		
		return tweets;
	}
}
