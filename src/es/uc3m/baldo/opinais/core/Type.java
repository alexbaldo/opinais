package es.uc3m.baldo.opinais.core;

/**
 * Type.
 * <p>Represents the type of a detector, which may
 * either be a self or non-self detector, depending
 * on whether it is specialized on detecting self or
 * non-self individuals respectively.</p>
 * 
 * @author Alejandro Baldominos
 */
public enum Type {
	/**
	 * <p>Self individuals.</p>
	 */
	SELF, 
	
	/**
	 * <p>Non-Self individuals.</p>
	 */
	NON_SELF
}
