/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.sifacmissions.domain;

import java.io.Serializable;
import java.util.List;

import org.esupportail.sifacmissions.domain.beans.Mission;
import org.esupportail.sifacmissions.domain.beans.MissionDetails;
import org.esupportail.sifacmissions.domain.beans.User;
import org.esupportail.sifacmissions.services.sifac.SifacException;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public interface DomainService extends Serializable
{
    /**
     * @param uid
     * @return a user.
     */
    User getUser(String uid);

    /**
     * @return the first year of SIFAC application.
     */
    Integer getFirstYear();

    /**
     * @param id
     * @return the name of user (without accent).
     */
    String getNom(String id);

    /**
     * @param id
     * @return the given name of user (without accent).
     */
    String getPrenom(String id);

    /**
     * @param matricule
     * @param nom
     * @param prenom
     * @param year
     * @return a list of frais missions.
     * @throws SifacException
     */
    List<Mission> getFraisMissions(String matricule, String nom, String prenom, Integer year) throws SifacException;
    
    /**
     * @param matricule
     * @param mission
     * @return a list of mission details.
     * @throws SifacException
     */
    List<MissionDetails> getMissionDetails(String matricule, String numeroMission) throws SifacException;

    /**
     * @param user
     * @return true if homonyme.
     */
    Boolean isHomonyme(User user);

    /**
     * @return the matricule service.
     */
    MatriculeService getMatriculeService();
}
