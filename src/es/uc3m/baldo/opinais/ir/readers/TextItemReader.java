package es.uc3m.baldo.opinais.ir.readers;

import java.io.File;
import java.util.Set;

import es.uc3m.baldo.opinais.ir.items.TextItem;

public interface TextItemReader<T extends TextItem> {

	public Set<T> read (File file);
}
