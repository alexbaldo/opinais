package es.uc3m.baldo.opinais.core;

import java.util.HashSet;
import java.util.Set;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EvolutionaryAlgorithm ea = new EvolutionaryAlgorithm("opinais.properties");
		Set<Individual> individuals = new HashSet<Individual>();
		
		individuals.add(new Individual(Type.SELF, new Bit[] {Bit.ZERO, Bit.ONE, Bit.ONE}));
		individuals.add(new Individual(Type.NON_SELF, new Bit[] {Bit.ONE, Bit.ONE, Bit.ONE}));
		
		ea.setIndividuals(individuals);
		ea.run();
	}

}
