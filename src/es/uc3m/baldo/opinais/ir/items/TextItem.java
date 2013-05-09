package es.uc3m.baldo.opinais.ir.items;

import es.uc3m.baldo.opinais.core.types.Type;

/**
 * TextItem.
 * <p>An item which contains some text, and has some 
 * associated type.</p>
 * 
 * @author Alejandro Baldominos
 */
public abstract class TextItem {

	/*
	 * The item's text.
	 */
	private String text;
	
	/*
	 * The type the item belongs to.
	 */
	private Type type;
		
	/**
	 * <p>Constructs a new text item.</p>
	 * @param text the item's text.
	 * @param type the type the item belongs to.
	 */
	public TextItem (String text, Type type) {
		this.text = text;
		this.type = type;
	}
	
	/**
	 * <p>Returns the item's text.</p>
	 * @return the item's text.
	 */
	public String getText () {
		return text;
	}
	
	/**
	 * <p>Sets the item's text.</p>
	 * @param text the item's text.
	 */
	public void setText (String text) {
		this.text = text;
	}
	
	/**
	 * <p>Returns the item's type.</p>
	 * @return the item's type.
	 */
	public Type getType () {
		return type;
	}
}
