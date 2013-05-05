package es.uc3m.baldo.opinais.ir.extractors;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import es.uc3m.baldo.opinais.core.Type;
import es.uc3m.baldo.opinais.ir.items.TextItem;

public class TextFeatureExtractor implements FeatureExtractor<String, TextItem> {
	
	private static final Pattern SEPARATOR = Pattern.compile(" +");
	
	private int nFeatures;
	
	public TextFeatureExtractor (int nFeatures) {
		this.nFeatures = nFeatures;
	}
	
	@Override
	public String[] extractFeatures (Set<? extends TextItem> items) {
		String[] features = new String[nFeatures];
		
		Map<String, Integer> wordsOccurrences = new HashMap<String, Integer>();
		
		System.out.println("\tCounting words occurrences...");
		for (TextItem item : items) {
			Set<String> presentInItem = new HashSet<String>();
			for (String word : SEPARATOR.split(item.getText())) {
				if (word.length() > 0 && !presentInItem.contains(word)) {
					if (wordsOccurrences.containsKey(word)) {
						wordsOccurrences.put(word, wordsOccurrences.get(word) + 1);
					} else {
						wordsOccurrences.put(word, 1);
					}
					presentInItem.add(word);
				}
			}
		}
		
		System.out.println("\tCalculating information gains for each word...");
		Set<Feature<String>> valuedFeatures = new TreeSet<Feature<String>>(); 
		int size = items.size();
		double ig = informationGain(items);
		for (String word : wordsOccurrences.keySet()) {
			Set<TextItem> presentSubSet = new HashSet<TextItem>();
			Set<TextItem> absentSubSet = new HashSet<TextItem>();
			for (TextItem item : items) {
				if (item.getText().contains(" " + word + " ")) {
					presentSubSet.add(item);
				} else {
					absentSubSet.add(item);
				}
			}
			
			double pctAppearance = (double) wordsOccurrences.get(word) / size;
			double entropy = ig - (pctAppearance * informationGain(presentSubSet)
												       + (1 - pctAppearance) * informationGain(absentSubSet));
			
			valuedFeatures.add(new Feature<String>(word, entropy));
		}
		
		System.out.println("\tExtracting most relevant features...");
		int i = 0;
		for (Feature<String> feature : valuedFeatures) {
			if (i >= nFeatures) {
				break;
			}
			features[i] = feature.getKey();
			i++;
		}
		
		return features;
	}
	
	private double informationGain(Set<? extends TextItem> items) {
		double ig = 0;
		
		int size = items.size();
		for (Type type : Type.values()) {
			double hits = 0;
			for (TextItem item : items) {
				if (item.getType() == type) {
					hits++;
				}
			}
			
			ig += -(hits / size) * Math.log(hits / size) / Math.log(2);
		}
		
		if (Double.isInfinite(ig) || Double.isNaN(ig)) {
			return 0;
		}
		
		return ig;
	}

}
