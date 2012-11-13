package org.esupportail.sifacmissions.web.beans;

/**
 * Ce composant permet de décrire le modèle de données du formulaire de
 * sélection de l'année utilisée par le service de récupération des frais de
 * missions.
 *
 * @author Florent Cailhol (Anyware Services)
 */
public class YearSelectionForm {

    private int year;

    /**
     * @return Année
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year Année
     */
    public void setYear(int year) {
        this.year = year;
    }

}
