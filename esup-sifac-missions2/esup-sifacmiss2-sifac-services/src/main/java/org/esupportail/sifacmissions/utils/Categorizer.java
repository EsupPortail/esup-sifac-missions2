package org.esupportail.sifacmissions.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class Categorizer {

	private Properties words;
	private Map<String, String> dictionary;
	
	public Categorizer() {
		this(new Properties());
	}
	
	public Categorizer(Properties words) {
		setWords(words);
	}
	
	public Properties getWords() {
		return words;
	}
	
	public void setWords(Properties words) {
		this.words = words;
		this.dictionary = new HashMap<String, String>();
		
		Iterator<Entry<Object, Object>> it = words.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			
			String category = entry.getKey().toString().toLowerCase();
			String wordList = entry.getValue().toString().toLowerCase();
			
			String[] keywords = wordList.split("\\s+");
			
			for (String word : keywords) {
				dictionary.put(word, category);
			}
		}
	}
	
	public String getCategory(String word) {
		return dictionary.get(word.toLowerCase());
	}
}
