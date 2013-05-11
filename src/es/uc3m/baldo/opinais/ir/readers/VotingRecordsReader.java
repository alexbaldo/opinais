package es.uc3m.baldo.opinais.ir.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import es.uc3m.baldo.opinais.ir.items.VotingRecord;
import es.uc3m.baldo.opinais.ir.readers.factories.VotingRecordFactory;

/**
 * USVotingRecordsReader.
 * <p>Reads the file containing data from the
 * Congressional Voting Records Dataset
 * from the UCI Machine Learning Repository
 * (http://archive.ics.uci.edu/ml/datasets/Congressional+Voting+Records).</p>
 * 
 * @author Alejandro Baldominos
 */
public class VotingRecordsReader implements Reader<VotingRecord> {

	/**
	 * <p>Reads a set of voting records contained in an input file.</p>
	 * @param file the input file.
	 * @return a set of voting records extracted from the input file.
	 */
	public Set<VotingRecord> read (File file) {
		// Stores the voting records read from the file.
		Set<VotingRecord> records = new HashSet<VotingRecord>();
		
		// Instantiates a new factory of voting records. This
		// factory will convert each line from the source
		// file to a voting record object.
		VotingRecordFactory factory = new VotingRecordFactory();
		
		BufferedReader in = null;
		try {
			// Creates a new input reader to read from the file.
			in = new BufferedReader(new FileReader(file));
			
			// Iterates through the file line by line and a voting record
			// object is created and stored for each line.
			String line;
			while ((line = in.readLine()) != null) {
				records.add(factory.make(line));
			}
		} catch (Exception e) {
			/* TODO Manage errors. */
		} finally {
			try {
				// Closes the input reader.
				in.close();
			} catch (Exception e) {}
		}
		
		return records;
	}
}
