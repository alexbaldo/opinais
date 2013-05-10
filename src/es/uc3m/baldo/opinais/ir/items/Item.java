package es.uc3m.baldo.opinais.ir.items;

import es.uc3m.baldo.opinais.core.types.Type;

/**
 * Item.
 * <p>An item which has some associated type.</p>
 * 
 * @author Alejandro Baldominos
 */
public abstract class Item {

	/*
	 * The type the item belongs to.
	 */
	private Type type;
		
	/**
	 * <p>Constructs a new item.</p>
	 * @param type the type the item belongs to.
	 */
	public Item (Type type) {
		this.type = type;
	}
	
	/**
	 * <p>Returns the item's type.</p>
	 * @return the item's type.
	 */
	public Type getType () {
		return type;
	}
}
