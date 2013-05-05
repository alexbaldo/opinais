package es.uc3m.baldo.opinais.ir.items;

import es.uc3m.baldo.opinais.core.Type;

public abstract class TextItem {

	private String text;
	
	private Type type;
		
	public TextItem (String text, Type type) {
		this.text = text;
		this.type = type;
	}
	
	public String getText () {
		return text;
	}
	
	public void setText (String text) {
		this.text = text;
	}
	
	public Type getType () {
		return type;
	}
}
