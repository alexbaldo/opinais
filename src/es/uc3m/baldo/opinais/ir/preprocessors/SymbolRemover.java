package es.uc3m.baldo.opinais.ir.preprocessors;

public class SymbolRemover implements PreProcessor<String> {

	public String process (String item) {
		return item.replaceAll("[^A-Za-z0-9 ]", " ");
	}
	
}
