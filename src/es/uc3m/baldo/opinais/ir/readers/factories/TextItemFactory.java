package es.uc3m.baldo.opinais.ir.readers.factories;

import es.uc3m.baldo.opinais.ir.items.TextItem;

public interface TextItemFactory<T extends TextItem> {
	
	public T make (String document);

}
