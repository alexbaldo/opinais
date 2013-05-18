package es.uc3m.baldo.opinais.ir.items;

import java.util.Arrays;

import es.uc3m.baldo.opinais.core.Bit;
import es.uc3m.baldo.opinais.core.types.Type;

/**
 * VoteRecord.
 * <p>A particular type of {@link Item}, representing 
 * a vote record from the Congressional Voting Records Dataset
 * from the UCI Machine Learning Repository
 * (http://archive.ics.uci.edu/ml/datasets/Congressional+Voting+Records).</p>
 * 
 * @author Alejandro Baldominos
 */
public class VotingRecord extends Item {
	
	/**
	 * <p>Stores the possible values for
	 * each vote.</p>
	 */
	public enum Vote {
		YEA(),
		NAY(),
		UNKNOWN();
		
		/**
		 * <p>Returns the binary representation for
		 * this vote, so that 'yea' is represented as 01,
		 * 'nay' is represented as 10 and 'unknown' is
		 * represented as 00.</p>
		 * @return the binary representation for this vote.
		 */
		public Bit[] getBinaryRepresentation () {
			switch (this) {
				case YEA:
					return new Bit[] {Bit.ZERO, Bit.ONE};
				case NAY:
					return new Bit[] {Bit.ONE, Bit.ZERO};
				default:
					return new Bit[] {Bit.ZERO, Bit.ZERO};
			}
		}
		
		/**
		 * <p>Returns the vote given its textual representation.</p>
		 * @param representation the textual representation for the
		 * vote.
		 * @return the vote represented by a given textual
		 * representation.
		 */
		public static Vote getVote (char representation) {
			switch (representation) {
				case 'y':
					return Vote.YEA;
				case 'n':
					return Vote.NAY;
				default:
					return Vote.UNKNOWN;
			}
		}
	};
	
	/*
	 * Stores the actual votes for this record.
	 */
	private final Vote[] votes;
	
	/**
	 * <p>Creates a new vote record.</p>
	 * @param votes the list of votes, which can either be
	 * 'y', 'n' or '?'.
	 * @param type the type the item belongs to.
	 */
	public VotingRecord (char[] votes, Type type) {
		super(type);
		this.votes = new Vote[votes.length];
		for (int i = 0; i < votes.length; i++) {
			this.votes[i] = Vote.getVote(votes[i]);
		}
	}
	
	/**
	 * <p>Retrieves the list of votes.</p>
	 * @return the list of votes for this record.
	 */
	public Vote[] getVotes () {
		return this.votes;
	}

	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VotingRecord other = (VotingRecord) obj;
		if (!Arrays.equals(votes, other.votes))
			return false;
		return true;
	}
	
	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(votes);
		return result;
	}	
}
