package es.uc3m.baldo.opinais.ir.items;

import es.uc3m.baldo.opinais.core.Type;

public class Tweet extends TextItem {
	
	public int id;
		
	public Tweet (int id, String text, Type type) {
		super(text, type);
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
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
	
}
