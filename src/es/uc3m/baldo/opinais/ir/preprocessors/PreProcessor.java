package es.uc3m.baldo.opinais.ir.preprocessors;

import java.util.regex.Pattern;

public interface PreProcessor<T> {

	public static final Pattern SEPARATOR = Pattern.compile(" ");
	
	public T process (T item);
	
}
