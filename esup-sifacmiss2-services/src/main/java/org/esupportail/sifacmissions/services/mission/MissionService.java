package org.esupportail.sifacmissions.services.mission;

import java.util.List;

import org.esupportail.sifacmissions.models.Mission;
import org.esupportail.sifacmissions.models.MissionDetails;

/**
 * Service d'accès aux données de l'application SIFAC.
 *
 * @author Yves Deschamps (Universite de Lille 1)
 * @author Florent Cailhol (Anyware Services)
 */
public interface MissionService {

    /**
     * Récupère l'année de la mise en ligne de l'application SIFAC.
     *
     * @return Première année de fonctionnement
     */
    int getFirstYear();

    /**
     * Récupère la liste des missions pour un utilisateur et une année donnés.
     *
     * @param matricule Numéro de matricule
     * @param year Année
     * @return Liste des frais de mission
     * @throws MissionException
     */
    List<Mission> getFraisMissions(String matricule, int year) throws MissionException;

    /**
     * Récupère la liste des frais pour l'utilisateur et la mission donnés.
     *
     * @param matricule Numéro de matricule
     * @param mission Numéro de mission
     * @return Liste de détails
     * @throws MissionException
     */
    List<MissionDetails> getMissionDetails(String matricule, String mission) throws MissionException;

}
