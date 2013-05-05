package es.uc3m.baldo.opinais.ir.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import es.uc3m.baldo.opinais.ir.items.Tweet;
import es.uc3m.baldo.opinais.ir.readers.factories.TweetFactory;

public class TweetReader implements TextItemReader<Tweet> {

	public Set<Tweet> read (File file) {
		Set<Tweet> tweets = new HashSet<Tweet>();
		TweetFactory factory = new TweetFactory();
		Scanner in = null;
		try {
			in = new Scanner(file);
			while (in.hasNextLine()) {
				tweets.add(factory.make(in.nextLine()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		
		return tweets;
	}
}
