package org.esupportail.sifacmissions.services.matricule;

/**
 * @author Florent Cailhol (Anyware Services)
 */
public interface MatriculeService {

    /**
     * Récupère le numéro de matricule de l'utilisateur spécifié.
     *
     * @param uid Identifiant de l'utilisateur
     * @return Numéro de matricule
     */
    String getMatricule(String uid);

}
