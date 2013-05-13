package es.uc3m.baldo.opinais.ir.readers.factories;

import java.util.regex.Pattern;

import es.uc3m.baldo.opinais.core.types.Type;
import es.uc3m.baldo.opinais.ir.items.VotingRecord;

/**
 * VotingRecordFactory.
 * <p>Generates voting records from a line in an input file.</p>
 * <p>The file format corresponds to the Congressional Voting Records Dataset
 * from the UCI Machine Learning Repository
 * (http://archive.ics.uci.edu/ml/datasets/Congressional+Voting+Records).</p>
 * 
 * @author Alejandro Baldominos
 */
public class VotingRecordFactory implements Factory<VotingRecord, String> {

	/*
	 * Values are comma-separated.
	 */
	private static final Pattern SEPARATOR = Pattern.compile(",");
	
	/**
	 * <p>Generates a new voting record from an input line.</p>
	 * @param line the input line, which must comply with the next format:
	 * <i>type</i>,<i>vote1</i>,<i>vote2</i>,...,<i>voteN</i>
	 * Each of these fields has an specific meaning:
	 * <ul>
	 * <li><strong>type</strong>: the political affiliation of the voter, which 
	 * be either republican or democrat.
	 * <li><strong>voteI</strong>: the particular vote emitted by this congressman
	 * to a particular voting, which may either be 'y' (yea), 'n' (nay) or '?'
	 * (unknown).
	 * </ul>
	 * @return the generated voting record.
	 */
	@Override
	public VotingRecord make (String line) {
		// Splits the input line with the specified separator.
		String[] tokens = SEPARATOR.split(line);
		
		// Extracts the voting record type.
		Type type = null;
		try {
			type = Type.valueOf(tokens[0]);
		} catch (IllegalArgumentException e) {
			return null;
		}
		
		// Extracts the votes.
		char[] votes = new char[tokens.length - 1];
		for (int i = 0; i < votes.length; i++) {
			votes[i] = tokens[i+1].charAt(0);
		}

		// Generates and returns the new voting record.
		return new VotingRecord(votes, type);
	}
}
