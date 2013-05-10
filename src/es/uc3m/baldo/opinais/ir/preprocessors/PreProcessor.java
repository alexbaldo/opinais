package es.uc3m.baldo.opinais.ir.preprocessors;

import java.util.regex.Pattern;

/**
 * PreProcessor.
 * <p>All pre-processors must implement this interface.</p>
 * 
 * @param <T> type of object.
 * 
 * @author Alejandro Baldominos
 */
public interface PreProcessor<T> {

	/* 
	 * Text separator, usually one or more empty spaces.
	 * It is compiled for efficiency purposes.
	 */
	static final Pattern SEPARATOR = Pattern.compile(" +");
	
	/**
	 * <p>Performs some processing over an item.</p>
	 * @param item the input item.
	 * @return the item after the processing is performed over it.
	 */
	public T process (T item);
	
}
