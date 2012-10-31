package org.esupportail.sifacmissions.services.matricule;

/**
 * @author Yves Deschamps (Universite Lille1 - France)
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
