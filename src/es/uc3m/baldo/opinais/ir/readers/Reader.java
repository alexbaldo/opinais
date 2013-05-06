package es.uc3m.baldo.opinais.ir.readers;

import java.io.File;
import java.util.Set;

/**
 * Reader.
 * <p>All readers must implement this interface.</p>
 * 
 * @T type of object.
 * 
 * @author Alejandro Baldominos
 */
public interface Reader<T> {

	/**
	 * <p>Reads a set of objects of the specified type which 
	 * are stored in a file.</p>
	 * @param file the input file.
	 * @return a set of objects extracted from the input file.
	 */
	public Set<T> read (File file);
}
