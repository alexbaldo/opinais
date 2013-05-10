package es.uc3m.baldo.opinais.ir.items;

import es.uc3m.baldo.opinais.core.types.Type;

/**
 * TextItem.
 * <p>An item which contains some text, and has some 
 * associated type.</p>
 * 
 * @author Alejandro Baldominos
 */
public abstract class TextItem extends Item {

	/*
	 * The item's text.
	 */
	private String text;
			
	/**
	 * <p>Constructs a new text item.</p>
	 * @param text the item's text.
	 * @param type the type the item belongs to.
	 */
	public TextItem (String text, Type type) {
		super(type);
		this.text = text;
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
}
