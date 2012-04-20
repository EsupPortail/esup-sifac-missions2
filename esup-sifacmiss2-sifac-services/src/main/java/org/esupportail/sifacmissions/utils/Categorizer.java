package org.esupportail.sifacmissions.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * Classe utilitaire permettant de détecter la catégorie associée à un mot clé.
 * 
 * @author Florent Cailhol (Anyware Services)
 */
public class Categorizer {

	private Properties words;
	private Map<String, String> dictionary;
	
	/**
	 * Constructeur.
	 */
	public Categorizer() {
		this(new Properties());
	}
	
	/**
	 * Constructeur.
	 * 
	 * @param words Liste des mots permettant d'identifier les catégories
	 */
	public Categorizer(Properties words) {
		setWords(words);
	}
	
	/**
	 * @return Liste des mots permettant d'identifier les catégories
	 */
	public Properties getWords() {
		return words;
	}
	
	/**
	 * @param words Liste des mots permettant d'identifier les catégories
	 */
	public void setWords(Properties words) {
		this.words = words;
		this.dictionary = new HashMap<String, String>();
		
		Iterator<Entry<Object, Object>> it = words.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			
			String category = entry.getKey().toString().toLowerCase();
			String wordList = entry.getValue().toString().toLowerCase();
			
			// TODO Add coma support
			String[] keywords = wordList.split("\\s+");
			
			for (String word : keywords) {
				dictionary.put(word, category);
			}
		}
	}
	
	/**
	 * Retourne la catégorie associée à un mot clé.
	 * 
	 * @param word Mot clé
	 * @return Catégorie ou null si aucune catégorie ne correspond.
	 */
	public String getCategory(String word) {
		return dictionary.get(word.toLowerCase());
	}

}
