package org.esupportail.sifacmissions.services.sifac;

import java.util.List;

import org.esupportail.sifacmissions.domain.beans.Mission;
import org.esupportail.sifacmissions.domain.beans.MissionDetails;

/**
 * Service d'accès aux données de l'application SIFAC.
 *
 * @author Yves Deschamps (Universite de Lille 1)
 * @author Florent Cailhol (Anyware Services)
 */
public interface SifacService {

    /**
     * Récupère l'année de la mise en ligne de l'application SIFAC.
     *
     * @return Première année de fonctionnement
     */
    Integer getFirstYear();

    /**
     * Récupère la liste des missions pour un utilisateur et une année donnés.
     * Le nom et prénom sont ceux de l'utilisateur et doivent être sans accent.
     *
     * @param matricule Numéro de matricule
     * @param nom Nom
     * @param prenom Prénom
     * @param year Année
     * @return Liste des frais de mission
     * @throws SifacException
     */
    List<Mission> getFraisMissions(String matricule, String nom, String prenom, Integer year) throws SifacException;

    /**
     * Récupère la liste des frais pour l'utilisateur et la mission donnés.
     *
     * @param matricule Numéro de matricule
     * @param mission Numéro de mission
     * @return Liste de détails
     * @throws SifacException
     */
    List<MissionDetails> getMissionDetails(String matricule, String mission) throws SifacException;

}
