package es.uc3m.baldo.opinais.ir.extractors;

public class Feature<T> implements Comparable<Feature<T>> {

	private T key;
	private double entropy;
	
	public Feature (T key, double entropy) {
		this.key = key;
		this.entropy = entropy;
	}
	
	public T getKey () {
		return key;
	}
	
	public double getEntropy () {
		return this.entropy;
	}

	public int compareTo(Feature<T> other) {
		return Double.compare(other.entropy, this.entropy);
	}

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals (Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Feature<?> other = (Feature<?>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}	
	
	@Override
	public String toString() {
		return "Feature [key=" + key + ", entropy=" + entropy + "]";
	}
}
