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
	 * Self individuals.
	 */
	SELF, 
	
	/**
	 * Non-Self individuals.
	 */
	NON_SELF
}
