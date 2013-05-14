package es.uc3m.baldo.opinais.ir.extractors;

/**
 * TextItem.
 * <p>An item which contains some text, and has some 
 * associated type.</p>
 * 
 * @param <E> the type of the value stored by the feature.
 * 
 * @author Alejandro Baldominos
 */
public class Feature<E> implements Comparable<Feature<E>> {

	/*
	 * The value stored by this feature.
	 */
	private E value;
	
	/*
	 * A measure of the information contained by
	 * this feature. The highest this value is, the
	 * most appropriate this feature is for 
	 * classification.
	 */
	private double entropy;
	
	/**
	 * <p>Constructs a new feature.</p>
	 * @param value the value stored by this feature.
	 * @param entropy a measure of quality of this feature 
	 * (the highest, the better).
	 */
	public Feature (E value, double entropy) {
		this.value = value;
		this.entropy = entropy;
	}
	
	/**
	 * Returns the value stored by this feature.
	 * @return the value stored by this feature.
	 */
	public E getValue () {
		return value;
	}
	
	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public int compareTo (Feature<E> other) {
		return Double.compare(other.entropy, this.entropy);
	}

	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
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
		Feature<?> other = (Feature<?>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}	
	
	/** 
	 * {@inheritDoc} 
	 */
	@Override
	public String toString () {
		return "Feature [key=" + value + ", entropy=" + entropy + "]";
	}
}
