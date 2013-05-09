package es.uc3m.baldo.opinais.ir.items;

import es.uc3m.baldo.opinais.core.types.Type;

/**
 * Tweet.
 * <p>A particular type of a {@link TextItem}, representing 
 * a Tweet (a publication in the microblogging network Twitter</p>
 * 
 * @author Alejandro Baldominos
 */
public class Tweet extends TextItem {
	
	/*
	 * The tweet's id.
	 * This field is only used for hashing and comparison,
	 * as it is the only final field.
	 * 
	 */
	private final int id;
		
	/**
	 * <p>Creates a new tweet.</p>
	 * @param id the tweet's id.
	 * @param text the tweet's text.
	 * @param type the type the tweet belongs to.
	 */
	public Tweet (int id, String text, Type type) {
		super(text, type);
		this.id = id;
	}

	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public boolean equals (Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
}
