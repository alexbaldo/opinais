package es.uc3m.baldo.opinais.ir.preprocessors;

public class LowerCaser implements PreProcessor<String> {

	public String process (String text) {
		return text.toLowerCase();
	}
	
}
