package es.uc3m.baldo.opinais.ir.vectorizers;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.ir.items.VotingRecord;
import es.uc3m.baldo.opinais.ir.items.VotingRecord.Vote;

/**
 * VotingRecordVectorizer.
 * <p>Vectorizes a voting record.</p>
 * 
 * @author Alejandro Baldominos
 */
public class VotingRecordVectorizer implements Vectorizer<VotingRecord> {
	
	/**
	 * <p>Vectorizes the voting record.</p>
	 * <p>To do so, binary representations for all votes
	 * are appended.</p>
	 * @param item the voting record item to be vectorized.
	 * @return an array of bits representing the voting record.
	 */
	@Override
	public Bit[] vectorize (VotingRecord item) {
		// Creates the binary array.
		// Each vote requires two bits.
		Vote[] votes = item.getVotes();
		Bit[] bits = new Bit[votes.length * 2];

		// Fills the binary array.
		for (int i = 0; i < bits.length; i+=2) {
			Bit[] voteBits = votes[i/2].getBinaryRepresentation();
			bits[i] = voteBits[0];
			bits[i+1] = voteBits[1];
		}
		
		return bits;
	}
	
}
