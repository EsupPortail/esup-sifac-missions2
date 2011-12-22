package org.esupportail.sifacmissions.web.utils;

import java.text.Normalizer;

public abstract class StringUtils {

	/**
	 * Transform the accentuated letters to their non-accentuated form.
	 * 
     *  @param str accentuated string
     *  @return non-accentuated string
     */
	public static String removeAccent(String str) {  
		return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
	}
	
}
