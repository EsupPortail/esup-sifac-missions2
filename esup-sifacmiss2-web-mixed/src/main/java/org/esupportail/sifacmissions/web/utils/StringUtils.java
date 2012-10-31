package org.esupportail.sifacmissions.web.utils;

import java.text.Normalizer;

/**
 * Cette classe permet de faciliter la manipulation des chaînes de caractères.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public abstract class StringUtils {

    /**
     * Transforme les lettres accentuées en leur équivalent sans-accent.
     *
     * @param str Chaîne de caractères accentuée
     * @return Chaîne de caractères sans accent
     */
    public static String removeAccent(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}
